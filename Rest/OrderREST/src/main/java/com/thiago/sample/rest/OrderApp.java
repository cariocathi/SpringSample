package com.thiago.sample.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication( scanBasePackages = {
        "com.thiago.sample.configuration",
        "com.thiago.sample.util",
        "com.thiago.sample.rest"
})
public class OrderApp
{
    public static void main( final String... args )
    {
        SpringApplication.run( OrderApp.class, args );
    }
}