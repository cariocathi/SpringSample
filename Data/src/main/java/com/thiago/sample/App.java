package com.thiago.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@ComponentScan( basePackages = {
        "com.thiago.sample.configuration",
        "com.thiago.sample.util"
})
@SpringBootApplication
public class App
{
    public static void main( final String... args )
    {
        SpringApplication.run( App.class, args );
    }
}