package com.thiago.sample.data.product;

import com.thiago.sample.util.Criterion;
import com.thiago.sample.util.Criterions;
import com.thiago.sample.util.FilterTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductSpecification implements Specification<Product>
{
    private final Criterions criterions;

    @Override
    public Predicate toPredicate( final Root<Product> root, final CriteriaQuery<?> query, final CriteriaBuilder cb )
    {
        final Optional<Criterion> id = criterions.findByAttributeAndFilterType( "id",
                FilterTypeEnum.EXACT_MATCH );

        if ( id.isPresent() )
            return cb.equal( root.get( id.get().getAttribute() ), id.get().getValues()[ 0 ] );

        return null;
    }
}