package com.thiago.sample.data.order;

import java.math.BigInteger;

public interface OrderService
{
    Order findOne( final BigInteger id );
    Iterable<Order> findAll();
    Order save( final Order order );
}