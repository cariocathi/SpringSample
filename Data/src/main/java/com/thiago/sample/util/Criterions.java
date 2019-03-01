package com.thiago.sample.util;

import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Getter
public class Criterions
{
    private final Set<Criterion> criterions;

    public Criterions()
    {
        criterions = new HashSet<>();
    }

    public void addCriteria( final Criterion c )
    {
        criterions.add( c );
    }

    public Optional<Criterion> findBy( final Predicate<Criterion> p )
    {
        return criterions.stream()
                .filter( p::test )
                .findFirst();
    }

    public Optional<Criterion> findByAttribute( final String attribute )
    {
        return findBy( p -> p.getAttribute().equalsIgnoreCase( attribute ) );
    }

    public Optional<Criterion> findByAttributeAndFilterType( final String attribute, final FilterTypeEnum filterType )
    {
        return findBy( p -> p.getAttribute().equalsIgnoreCase( attribute ) &&
                p.getFilterType() == filterType );
    }
}