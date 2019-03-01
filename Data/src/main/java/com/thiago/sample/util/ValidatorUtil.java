package com.thiago.sample.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Configuration
public class ValidatorUtil
{
    @Autowired
    private ApplicationContext context;

    @Bean
    public MessageSource messageSource()
    {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename( "ValidationMessages" );

        return messageSource;
    }

    public boolean notValid( final DataBinder binder, final Validator... validators )
    {
        boolean notValid = false;
        Arrays.stream( validators )
                .forEach( binder::addValidators );
        binder.validate();

        if ( binder.getBindingResult().hasErrors() )
            notValid = true;

        return notValid;
    }

    /**
     * Returns a <code>List<String></code> containing all errors bound to the binder
     *
     * @param binder
     * @return
     */
    public List<String> getErrors( final DataBinder binder )
    {
        return binder.getBindingResult()
                .getAllErrors()
                .stream()
                .map( e -> context.getMessage( e, Locale.US ) )
                .collect( Collectors.toList() );
    }

    /**
     * Returns a single String with all errors joined by '|'
     *
     * @param binder
     * @return
     */
    public String getErrorsSingle( final DataBinder binder )
    {
        return getErrors( binder )
                .stream()
                .map( s -> s.concat( "|" ) )
                .collect( Collectors.joining() );
    }
}