package org.thezerobytehunter.springbootzero.base.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thezerobytehunter.springbootzero.base.config.AppConfig;
import org.thezerobytehunter.springbootzero.base.entity.BaseEntity;
import org.thezerobytehunter.springbootzero.base.payload.requests.IRequest;
import org.thezerobytehunter.springbootzero.base.payload.responses.IResponse;
import org.thezerobytehunter.springbootzero.base.repository.AbstractRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public abstract class AbstractCRUDService<
        Entity extends BaseEntity,
        Request extends IRequest,
        Response extends IResponse
        > implements IAbstractCRUDService< Entity, Request, Response > {
    private final AbstractRepository< Entity > repository;
    private final AppConfig appConfig;

    @Transactional
    @Override
    public Entity create( Request request ) {
        Map< String, Set< String > > errorMessages = new HashMap<>( );
        validateRequestData( request, null, errorMessages );

        if ( !errorMessages.isEmpty( ) ) {
            StringBuilder errorMessage = new StringBuilder( );
            errorMessages.forEach( ( key, value ) -> errorMessage.append( key ).append( ": " ).append( value ).append( "\n" ) );

            throw new RuntimeException( errorMessage.toString( ) );
        }

        Entity entity = convertToEntity( request );
        return repository.save( entity );
    }

    @Transactional( readOnly = true )
    public Response readOne( Long id ) {
        Entity entity = getById( id );

        return convertToResponse( entity );
    }

    @Transactional( readOnly = true )
    public Set< Response > readAll( ) {
        Set< Entity > allEntities = getAll( );

        return allEntities.stream( ).map( this::convertToResponse ).collect( Collectors.toSet( ) );
    }

    @Transactional
    public Entity update( Request request ) {
        Map< String, Set< String > > errorMessages = new HashMap<>( );

        Entity entity = getById( request.getId( ) );
        validateRequestData( request, entity, errorMessages );

        if ( !errorMessages.isEmpty( ) ) {
            StringBuilder errorMessage = new StringBuilder( );
            errorMessages.forEach( ( key, value ) -> errorMessage.append( key ).append( ": " ).append( value ).append( "\n" ) );

            throw new RuntimeException( errorMessage.toString( ) );
        }

        return updateEntity( request, entity );
    }

    @Transactional
    public void delete( Long id ) {
        Entity entity = getById( id );

        if ( appConfig.getSoftDelete( ) ) {
            entity.setIsDeleted( true );
            repository.save( entity );
        } else {
            repository.deleteById( id );
        }
    }

    public abstract void validateRequestData( Request request, Entity entity, Map< String, Set< String > > errorMessages );

    public abstract Entity convertToEntity( Request request );

    public abstract Entity updateEntity( Request request, Entity entity );

    public abstract Response convertToResponse( Entity entity );

    public Entity getById( Long id ) {
        Optional< Entity > foundEntity = repository.findById( id );

        if ( appConfig.getSoftDelete( ) ) {
            if ( foundEntity.isEmpty( ) ) {
                throw new RuntimeException( "Entity not found" ); // TODO: Custom Exception
            }

            Entity entity = foundEntity.get( );

            if ( entity.getIsDeleted( ) ) {
                throw new RuntimeException( "Entity is in Archive." ); // TODO: Custom Exception
            }

            return entity;
        }

        return foundEntity.orElseThrow( ( ) -> new RuntimeException( "Entity not found" ) ); // TODO: Custom Exception
    }

    public abstract Set< Entity > getAll( );
}