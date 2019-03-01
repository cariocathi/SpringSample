package com.thiago.sample.report;

import com.thiago.sample.data.order.Order;
import com.thiago.sample.data.order.OrderService;
import com.thiago.sample.data.orderproduct.OrderProduct;
import com.thiago.sample.data.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class JobTest
{
    @Mock
    private OrderService orderService;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks( this );
        orderService = Mockito.mock( OrderService.class );

        final List<Order> orders = new ArrayList<>();
        final List<OrderProduct> products = new ArrayList<>();
        products.add( OrderProduct.builder()
                .product( Product.builder()
                        .price( BigDecimal.TEN )
                        .build() )
                .quantity( 2 )
                .build() );
        products.add( OrderProduct.builder()
                .product( Product.builder()
                        .price( BigDecimal.valueOf( 50 ) )
                        .build() )
                .quantity( 3 )
                .build() );

        Order order = new Order();
        order.setId( BigInteger.ONE );
        order.setProducts( new ArrayList<>( products ) );
        orders.add( order );

        order = new Order();
        order.setId( BigInteger.valueOf( 2 ) );
        order.setProducts( products );
        orders.add( order );
        products.clear();
        products.add( OrderProduct.builder()
                .product( Product.builder()
                        .price( BigDecimal.valueOf( 22.50 ) )
                        .build() )
                .quantity( 2 )
                .build() );
        products.add( OrderProduct.builder()
                .product( Product.builder()
                        .price( BigDecimal.valueOf( 50 ) )
                        .build() )
                .quantity( 3 )
                .build() );

        Mockito.when( orderService.findAll() )
                .thenReturn( orders );
    }

    @Test
    public void testReport()
    {
        final Job job = new Job( orderService );

        job.generateReport();
    }
}