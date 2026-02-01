package org.thezerobytehunter.springbootzero.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitForeignKeyNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {
    @Bean
    public ImplicitNamingStrategyJpaCompliantImpl implicitNamingStrategy( ) {
        return new ImplicitNamingStrategyJpaCompliantImpl( ) {
            @Override
            public Identifier determineForeignKeyName( ImplicitForeignKeyNameSource source ) {
                String tableName = source.getTableName( ).getText( );
                String referencedTableName = source.getReferencedTableName( ).getText( );

                String fkName = "FK_%s_%s".formatted( tableName.toUpperCase( ), referencedTableName.toUpperCase( ) );

                return toIdentifier( fkName, source.getBuildingContext( ) );
            }
        };
    }
}