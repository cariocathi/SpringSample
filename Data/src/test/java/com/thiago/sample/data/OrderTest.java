package com.thiago.sample.data;

import com.thiago.sample.data.order.Order;
import com.thiago.sample.data.order.OrderService;
import com.thiago.sample.data.orderproduct.OrderProduct;
import com.thiago.sample.data.product.ProductService;
import org.hamcrest.CoreMatchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

@FixMethodOrder( value = MethodSorters.NAME_ASCENDING )
public class OrderTest extends BaseMockTest
{
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Rollback( false )
    @Test
    public void testA_CreateOrder()
    {
        final List<OrderProduct> products = new ArrayList<>();
        products.add( OrderProduct.builder()
                .product( productService.findOne( BigInteger.ONE ) )
                .quantity( 2 )
                .build() );
        products.add( OrderProduct.builder()
                .product( productService.findOne( BigInteger.valueOf( 2 ) ) )
                .quantity( 3 )
                .build() );

        Order order = new Order();
        order.setProducts( products );
        order = orderService.save( order );

        assertEquals( BigInteger.ONE, order.getId() );

        products.clear();
        products.add( OrderProduct.builder()
                .product( productService.findOne( BigInteger.valueOf( 3 ) ) )
                .quantity( 1 )
                .build() );

        order = new Order();
        order.setProducts( products );
        order = orderService.save( order );

        assertEquals( BigInteger.valueOf( 2 ), order.getId() );
    }

    @Test
    public void testB_Empty()
    {
        thrown.expect( IllegalStateException.class );
        thrown.expectMessage(CoreMatchers.containsString( "Products are required" ) );

        orderService.save( new Order() );
    }

    @Test
    public void testC_Total()
    {
        final Iterable<Order> orders = orderService.findAll();

        StreamSupport.stream( orders.spliterator(), false )
                .map( Order::getOrderTotal )
                .forEach( System.out::println );
    }
}