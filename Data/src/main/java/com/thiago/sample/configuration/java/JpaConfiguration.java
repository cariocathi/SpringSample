package com.thiago.sample.configuration.java;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
        basePackages = "com.thiago.sample.data",
        excludeFilters = @ComponentScan.Filter( type = FilterType.REGEX, pattern = "..*RevisionDAO" )
)
@ComponentScan( basePackages = "com.thiago.sample.data" )
@EntityScan( basePackages = "com.thiago.sample.data" )
@Configuration
public class JpaConfiguration
{
}
