package com.thiago.sample.rest;

import com.thiago.sample.data.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class OrderController
{
    private static final Logger LOG = LoggerFactory.getLogger( OrderController.class );

    @Autowired
    private OrderService orderService;

    @GetMapping( path = "/findAll" )
    public ResponseEntity<Iterable<OrderVO>> findAll()
    {
        LOG.info( "/findAll" );

        return new ResponseEntity<>( StreamSupport.stream( orderService.findAll().spliterator(), false )
                .map( OrderTransformer::transformFromEntityToVO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }


}