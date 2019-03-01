package com.thiago.sample.data.product;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ProductDAO extends PagingAndSortingRepository<Product, BigInteger>
{
    Product findByName( final String name );
    boolean existsByName( final String name );
}