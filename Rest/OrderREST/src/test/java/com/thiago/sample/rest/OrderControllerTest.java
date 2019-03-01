package com.thiago.sample.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiago.sample.data.order.Order;
import com.thiago.sample.data.order.OrderService;
import com.thiago.sample.data.orderproduct.OrderProduct;
import com.thiago.sample.data.product.Product;
import com.thiago.sample.data.product.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = OrderApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@TestPropertySource( locations = "classpath:application-test.properties" )
public class OrderControllerTest
{
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ObjectMapper objectMapper;
    private String url;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Before
    public void testSetup()
    {
        objectMapper = new ObjectMapper();
        url = "http://localhost:".concat( String.valueOf( port ) );

        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList( (request, body, execution) ->
                {
                    request.getHeaders().add( "TestHeader", "Hi!" );

                    return execution.execute( request, body );
                })
        );

        // Adding some fake data
        final Product p = productService.save( Product.builder()
                .price( BigDecimal.TEN )
                .name( "OCP" )
                .build() );

        final List<OrderProduct> products = new ArrayList<>();
        products.add( OrderProduct.builder()
                .quantity(1)
                .product( p )
                .build() );

        final Order order = new Order();
        order.setProducts( products );

        orderService.save( order );
    }

    @Test
    public void testFindAll() throws IOException
    {
        final Object o = restTemplate.getForObject( url.concat( "/findAll" ), String.class );

        final OrderVO[] orders = objectMapper.readValue( (String)o, OrderVO[].class );

        Assert.assertEquals( 1, orders.length );
    }
}