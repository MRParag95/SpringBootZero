package org.thezerobytehunter.springbootzero.base.payload.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.slf4j.MDC;

import java.time.Instant;
import java.util.Collection;

@Builder
@JsonInclude( JsonInclude.Include.NON_NULL )
public record MessageResponse< T >(
        String status,
        int code,
        String message,
        String traceId,
        Instant timestamp,
        T data,
        Collection< String > errorMessages
) {
    public MessageResponse {
        if ( timestamp == null ) timestamp = Instant.now( );
        if ( traceId == null ) traceId = MDC.get( "traceId" );
    }


    public static < T > MessageResponse< T > success( String message, T data ) {
        return MessageResponse.< T >builder( )
                .status( "SUCCESS" )
                .code( 200 )
                .message( message )
                .data( data )
                .build( );
    }

    public static < T > MessageResponse< T > success( String message ) {
        return success( message, null );
    }

    public static < T > MessageResponse< T > error( String message, int code ) {
        return MessageResponse.< T >builder( )
                .status( "ERROR" )
                .code( code )
                .message( message )
                .build( );
    }

    public static < T > MessageResponse< T > error( String message, int code, Collection< String > errors ) {
        return MessageResponse.< T >builder( )
                .status( "ERROR" )
                .code( code )
                .message( message )
                .errorMessages( errors )
                .build( );
    }
}