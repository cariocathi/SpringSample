package com.thiago.sample.data.product;

import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ProductRevisionDAO extends RevisionRepository<Product, BigInteger, Integer>
{
}