package com.thiago.sample.data.order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Set;

@Repository
public interface OrderDAO extends CrudRepository<Order, BigInteger>
{
    @Override
    @Query( value = "from Orders o join fetch o.products op" +
            " join fetch op.product" )
    Set<Order> findAll();
}