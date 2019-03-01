package com.thiago.sample.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;

@SpringBootApplication( scanBasePackages = {
        "com.thiago.sample.configuration",
        "com.thiago.sample.util",
        "${batchPkg}"
})
public class BatchApplication
{
    private static final Logger LOG = LoggerFactory.getLogger( BatchApplication.class );

    @PostConstruct
    public void construct()
    {
        LOG.info( "Get configuration" );
        LOG.info( "Getting DB info" );
        LOG.info( "Acquire datasource" );
        LOG.info( "Connect" );
    }

    public static void main( final String... args )
    {
        new SpringApplicationBuilder( BatchApplication.class ).web( false ).run( args );
    }
}