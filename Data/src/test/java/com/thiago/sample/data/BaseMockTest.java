package com.thiago.sample.data;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith( SpringRunner.class )
@DataJpaTest
@TestPropertySource( locations = "classpath:application-test.properties" )
public abstract class BaseMockTest
{
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
}