package com.thiago.sample.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thiago.sample.data.order.Order;
import com.thiago.sample.data.orderproduct.OrderProduct;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString( of = { "id", "productsVO" } )
public class OrderVO extends Order
{
    private List<OrderProductVO> productsVO;

    @Override
    @JsonIgnore
    public List<OrderProduct> getProducts()
    {
        throw new UnsupportedOperationException( "This causes stack overflow!" );
    }

    @Override
    @JsonIgnore
    public void setProducts( final List<OrderProduct> products )
    {
        throw new UnsupportedOperationException( "This causes stack overflow!" );
    }

    @Override
    @JsonIgnore
    public BigDecimal getOrderTotal()
    {
        throw new UnsupportedOperationException( "NPE" );
    }
}