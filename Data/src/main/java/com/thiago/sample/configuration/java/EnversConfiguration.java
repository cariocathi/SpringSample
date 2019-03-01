package com.thiago.sample.configuration.java;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = "com.thiago.sample.data",
        includeFilters = @ComponentScan.Filter( type = FilterType.REGEX, pattern = "..*RevisionDAO" ),
        repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class
)
@Configuration
public class EnversConfiguration
{
}