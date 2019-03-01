package com.thiago.sample.data.order;

import com.thiago.sample.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.transaction.Transactional;
import java.math.BigInteger;

@Service
public class OrderServiceImpl implements OrderService
{
    private final OrderDAO orderDAO;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public OrderServiceImpl( final OrderDAO orderDAO, final ValidatorUtil validatorUtil )
    {
        this.orderDAO = orderDAO;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public Order findOne( final BigInteger id )
    {
        return orderDAO.findOne( id );
    }

    @Override
    public Iterable<Order> findAll()
    {
        return orderDAO.findAll();
    }

    @Transactional
    @Override
    public Order save( final Order order )
    {
        final DataBinder binder = new DataBinder( order );
        binder.addValidators( new PreprocessingValidator() );

        if ( validatorUtil.notValid( binder ) )
            throw new IllegalStateException( validatorUtil.getErrorsSingle( binder ) );

        return orderDAO.save( order );
    }

    private class PreprocessingValidator implements Validator
    {
        @Override
        public boolean supports( final Class<?> clazz )
        {
            return Order.class.equals( clazz );
        }

        @Override
        public void validate( final Object target, final Errors errors )
        {
            final Order order = (Order)target;

            if ( order.getProducts() == null || order.getProducts().isEmpty() )
                errors.reject( "order.products.required" );
        }
    }
}