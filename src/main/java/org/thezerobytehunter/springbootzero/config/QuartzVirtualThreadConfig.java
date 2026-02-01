package org.thezerobytehunter.springbootzero.config;

import org.springframework.boot.quartz.autoconfigure.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class QuartzVirtualThreadConfig {
    @Bean
    public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer( ) {
        return ( schedulerFactoryBean ) -> {
            schedulerFactoryBean.setTaskExecutor( Executors.newVirtualThreadPerTaskExecutor( ) );
        };
    }
}