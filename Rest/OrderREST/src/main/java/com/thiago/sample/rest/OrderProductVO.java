package com.thiago.sample.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thiago.sample.data.order.Order;
import com.thiago.sample.data.orderproduct.OrderProduct;

import java.math.BigDecimal;

public class OrderProductVO extends OrderProduct
{
    @Override
    @JsonIgnore
    public Order getOrder()
    {
        throw new UnsupportedOperationException( "This causes stack overflow!" );
    }

    @Override
    @JsonIgnore
    public void setOrder( final Order order )
    {
        throw new UnsupportedOperationException( "This causes stack overflow!" );
    }

    @Override
    @JsonIgnore
    public BigDecimal getTotal()
    {
        throw new UnsupportedOperationException();
    }
}