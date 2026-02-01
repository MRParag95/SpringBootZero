package org.thezerobytehunter.springbootzero.config;

import org.jooq.conf.RenderImplicitJoinType;
import org.springframework.boot.jooq.autoconfigure.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {
    @Bean
    public DefaultConfigurationCustomizer jooqDefaultConfigurationCustomizer( ) {
        return c -> c.settings( )
                .withRenderImplicitJoinType( RenderImplicitJoinType.LEFT_JOIN )
                .withRenderImplicitJoinToManyType( RenderImplicitJoinType.LEFT_JOIN );
    }
}