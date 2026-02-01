package org.thezerobytehunter.springbootzero.base.payload.responses;

import java.time.Instant;

public interface IResponse {
    Long getId( );

    Instant getCreatedAt( );

    String getCreatedBy( );

    Instant getUpdatedAt( );

    String getUpdatedBy( );

    Boolean getIsDeleted( );
}