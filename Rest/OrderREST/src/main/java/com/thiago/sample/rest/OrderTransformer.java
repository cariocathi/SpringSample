package com.thiago.sample.rest;

import com.thiago.sample.data.order.Order;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

public class OrderTransformer
{
    public static OrderVO transformFromEntityToVO( final Order order )
    {
        final OrderVO vo = new OrderVO();

        BeanUtils.copyProperties( order, vo, "products" );

        vo.setProductsVO( order.getProducts()
                .stream()
                .map( p ->
                {
                    final OrderProductVO op = new OrderProductVO();
                    BeanUtils.copyProperties( p, op, "order" );

                    return op;
                })
                .collect( Collectors.toList() )
        );

        return vo;
    }
}