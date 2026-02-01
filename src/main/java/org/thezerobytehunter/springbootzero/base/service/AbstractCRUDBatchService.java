package org.thezerobytehunter.springbootzero.base.service;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.UpdatableRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thezerobytehunter.springbootzero.base.payload.requests.IRequest;
import org.thezerobytehunter.springbootzero.base.payload.responses.IResponse;

import java.io.Serializable;
import java.util.*;

@Service
@AllArgsConstructor
public abstract class AbstractCRUDBatchService<
        Entity extends UpdatableRecord< Entity >,
        Pojo extends Serializable,
        Request extends IRequest,
        Response extends IResponse
        > implements IAbstractCRUDBatchService< Entity, Pojo, Request, Response > {
    private final DSLContext dslContext;

    @Transactional
    @Override
    public void batchCreate( Collection< Request > requests ) {
        Map< String, Set< String > > errorMessages = new HashMap<>( );

        requests.forEach( request -> {
            validateRequestData( request, null, errorMessages );
        } );

        if ( !errorMessages.isEmpty( ) ) {
            StringBuilder errorMessage = new StringBuilder( );
            errorMessages.forEach( ( key, value ) -> errorMessage.append( key ).append( ": " ).append( value ).append( "\n" ) );

            throw new RuntimeException( errorMessage.toString( ) );
        }

        Set< Entity > entities = new HashSet<>( );
        requests.forEach( request -> entities.add( convertToEntity( request ) ) );

        dslContext.batchStore( entities ).execute( );
    }

    public abstract void validateRequestData( Request request, Entity entity, Map< String, Set< String > > errorMessages );

    public abstract Entity convertToEntity( Request request );

    public abstract Entity updateEntity( Request request, Entity entity );

    public abstract Response convertToResponse( Entity entity );
}