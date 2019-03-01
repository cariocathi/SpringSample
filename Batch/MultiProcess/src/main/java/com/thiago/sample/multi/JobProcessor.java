package com.thiago.sample.multi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Service
public class JobProcessor implements SchedulingConfigurer
{
    private static final Logger LOG = LoggerFactory.getLogger( JobProcessor.class );

    private ExecutorService executorService;

    private final CronTask task1 = new CronTask( () ->
    {
        LOG.info( "Running task 1" );

        process();
        executorService.shutdown();

        LOG.info( "task1 done!" );
    }, "0 0/1 * * * *" );

    private final CronTask task2 = new CronTask( () ->
    {
        LOG.info( "Running task 2" );

        process();

        LOG.info( "task2 done!" );
    }, "0 0/1 * * * *" );

    private final CronTask task3 = new CronTask( () ->
    {
        LOG.info( "Running task 3" );

        process();

        LOG.info( "task3 done!" );
    }, "0 0/1 * * * *" );

    private final void process()
    {
        try
        {
            TimeUnit.SECONDS.sleep( 15 );
        }
        catch ( final InterruptedException e )
        {
            LOG.error( e.getMessage(), e );
        }
    }

    @Bean( destroyMethod = "shutdown" )
    public ExecutorService taskExecutor()
    {
        //executorService = Executors.newScheduledThreadPool( 0 );
        executorService = Executors.newScheduledThreadPool( Runtime.getRuntime().availableProcessors() );

        return executorService;
    }

    @Override
    public void configureTasks( final ScheduledTaskRegistrar taskRegistrar )
    {
        taskRegistrar.setScheduler( executorService );
        taskRegistrar.addCronTask( task1 );
        taskRegistrar.addCronTask( task2 );
        taskRegistrar.addCronTask( task3 );

        LOG.info( "Tasks configured..." );
    }
}