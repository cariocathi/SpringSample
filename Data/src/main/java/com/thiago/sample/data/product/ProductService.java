package com.thiago.sample.data.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.history.Revisions;

import java.math.BigInteger;

public interface ProductService
{
    Product save( final Product p );
    Product findOne( final BigInteger id );
    Iterable<Product> findAll();
    Page<Product> findAll( final PageRequest page );
    Product findByName( final String name );
    Revisions<Integer, Product> findProductRevisions( final BigInteger productId );
}