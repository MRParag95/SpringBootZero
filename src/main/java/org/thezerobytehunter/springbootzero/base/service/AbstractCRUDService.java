package org.thezerobytehunter.springbootzero.base.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thezerobytehunter.springbootzero.base.entity.BaseEntity;
import org.thezerobytehunter.springbootzero.base.payload.requests.IRequest;
import org.thezerobytehunter.springbootzero.base.payload.responses.IResponse;
import org.thezerobytehunter.springbootzero.base.repository.AbstractRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public abstract class AbstractCRUDService<
        Entity extends BaseEntity,
        Request extends IRequest,
        Response extends IResponse
        > implements IAbstractCRUDService< Entity, Request, Response > {
    private final AbstractRepository< Entity > repository;

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
    public List< Response > readAll( ) {
        List< Entity > allEntities = getAll( );

        return allEntities.stream( ).map( this::convertToResponse ).toList( );
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


    public abstract void validateRequestData( Request request, Entity entity, Map< String, Set< String > > errorMessages );

    public abstract Entity convertToEntity( Request request );

    public abstract Entity updateEntity( Request request, Entity entity );

    public abstract Response convertToResponse( Entity entity );

    public abstract Entity getById( Long id );

    public abstract List< Entity > getAll( );
}