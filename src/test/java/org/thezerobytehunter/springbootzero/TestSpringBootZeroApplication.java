package org.thezerobytehunter.springbootzero;

import org.springframework.boot.SpringApplication;

public class TestSpringBootZeroApplication {

    public static void main( String[] args ) {
        SpringApplication.from( SpringBootZeroApplication::main ).with( TestcontainersConfiguration.class ).run( args );
    }

}
