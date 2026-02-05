package org.thezerobytehunter.springbootzero.base.service;

import org.thezerobytehunter.springbootzero.base.entity.BaseEntity;
import org.thezerobytehunter.springbootzero.base.payload.requests.IRequest;
import org.thezerobytehunter.springbootzero.base.payload.responses.IResponse;

import java.util.Set;

public interface IAbstractCRUDService<
        Entity extends BaseEntity,
        Request extends IRequest,
        Response extends IResponse
        > {
    Entity create( Request request );

    Response readOne( Long id );

    Set< Response > readAll( );

    Entity update( Request request );

    void delete( Long id );
}