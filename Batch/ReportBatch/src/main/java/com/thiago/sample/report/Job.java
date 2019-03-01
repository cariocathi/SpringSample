package com.thiago.sample.report;

import com.thiago.sample.data.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Job
{
    private static final Logger LOG = LoggerFactory.getLogger( Job.class );

    private final OrderService orderService;

    @Autowired
    public Job( final OrderService orderService )
    {
        this.orderService = orderService;
    }

    @Bean
    public CommandLineRunner jobRunner()
    {
        return args -> generateReport();
    }

    public void generateReport()
    {
        LOG.info( "Generating report..." );

        orderService.findAll()
                .forEach( o -> System.out.printf( "Order ID: %d\ttotal: $%.2f%n", o.getId(), o.getOrderTotal() ) );
    }
}