package com.thiago.sample.data.product;

import com.thiago.sample.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductDAO productDAO;
    private final ProductRevisionDAO productRevisionDAO;
    private final ValidatorUtil validatorUtil;

    @Autowired
    ProductServiceImpl( final ProductDAO productDAO, final ProductRevisionDAO productRevisionDAO,
                        final ValidatorUtil validatorUtil )
    {
        this.productDAO = productDAO;
        this.productRevisionDAO = productRevisionDAO;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public Product findOne( final BigInteger id )
    {
        return productDAO.findOne( id );
    }

    @Override
    public Iterable<Product> findAll()
    {
        return productDAO.findAll();
    }

    @Override
    public Page<Product> findAll( final PageRequest page )
    {
        return productDAO.findAll( page );
    }

    @Override
    public Product findByName( final String name )
    {
        return productDAO.findByName( name );
    }

    @Transactional
    @Override
    public Product save( final Product p )
    {
        final DataBinder binder = new DataBinder( p );

        if ( validatorUtil.notValid( binder, new PreprocessingValidator() ) )
            throw new IllegalStateException( validatorUtil.getErrorsSingle( binder ) );

        return productDAO.save( p );
    }

    private class PreprocessingValidator implements Validator
    {
        @Override
        public boolean supports( final Class<?> aClass )
        {
            return Product.class.equals( aClass );
        }

        @Override
        public void validate( final Object o, final Errors errors )
        {
            final Product product = (Product)o;
            ValidationUtils.rejectIfEmpty( errors, "name", "name.required" );
            ValidationUtils.rejectIfEmpty( errors, "price", "price.required" );

            if ( product.getPrice() != null &&
                    product.getPrice().compareTo( BigDecimal.ZERO ) <= 0 )
                errors.reject( "price.lessThanZero" );

            if ( productDAO.existsByName( product.getName() ) )
                errors.reject( "product.name.duplicated" );

            if ( product.getId() != null )
            {
                final Product toUpdate = findOne( product.getId() );

                if ( !toUpdate.getVersion().equals( product.getVersion() ) )
                    errors.reject( "version.mismatch" );
            }
        }
    }

    @Override
    public Revisions<Integer, Product> findProductRevisions( final BigInteger productId )
    {
        return productRevisionDAO.findRevisions( productId );
    }
}