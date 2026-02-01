package org.thezerobytehunter.springbootzero.base.payload.requests;

public interface IRequest {
    Long getId( );

    String getRequestTraceUID( );
}