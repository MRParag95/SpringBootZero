package org.thezerobytehunter.springbootzero.base.service;

import org.jooq.UpdatableRecord;
import org.thezerobytehunter.springbootzero.base.entity.BaseEntity;
import org.thezerobytehunter.springbootzero.base.payload.requests.IRequest;
import org.thezerobytehunter.springbootzero.base.payload.responses.IResponse;

import java.io.Serializable;
import java.util.Collection;

public interface IAbstractCRUDBatchService<
        Entity extends UpdatableRecord< Entity >,
        Pojo extends Serializable,
        Request extends IRequest,
        Response extends IResponse
        > {
    void batchCreate( Collection< Request > requests );
}