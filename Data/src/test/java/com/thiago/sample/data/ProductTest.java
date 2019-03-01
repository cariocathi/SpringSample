package com.thiago.sample.data;

import com.thiago.sample.data.product.Product;
import com.thiago.sample.data.product.ProductService;
import org.hamcrest.CoreMatchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.history.Revisions;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

@FixMethodOrder( value = MethodSorters.NAME_ASCENDING )
public class ProductTest extends BaseMockTest
{
    @Autowired
    private ProductService productService;

    // This is directly affected by the test data (data-h2.sql)
    private BigInteger latestID = BigInteger.TEN;

    @Rollback( false )
    @Test
    public void testA_Create()
    {
        final Product p = productService.save( Product.builder()
                .name( "Sorvete" )
                .price( BigDecimal.valueOf( 4.79 ) )
                .build() );

        assertNotNull( p );
        assertTrue( p.getId().compareTo( BigInteger.ZERO ) > 0 );
        assertEquals( 0, (long)p.getVersion() );
        assertNotNull( p.getVersionTimestamp() );
        assertEquals( latestID, p.getId() );
    }

    @Test
    public void testB_CreateEmpty()
    {
        thrown.expect( IllegalStateException.class );
        thrown.expectMessage( CoreMatchers.containsString( "Name is required" ) );
        thrown.expectMessage( CoreMatchers.containsString( "Price is required" ) );

        productService.save( Product.builder().build() );
    }

    @Test
    public void testC_FindRevisions()
    {
        final Revisions<Integer, Product> rev = productService.findProductRevisions( latestID );
        // We should have one history entry due to the insert above
        assertFalse( rev.getContent().isEmpty() );
    }

    @Test
    public void testD_CreateDupeName()
    {
        // Name can't be duplicated
        thrown.expect( IllegalStateException.class );
        thrown.expectMessage( CoreMatchers.containsString( "name already registered" ) );

        productService.save( Product.builder()
                .name( "Sorvete" )
                .price( BigDecimal.TEN )
                .build()
        );
    }

    @Rollback( false )
    @Test
    public void testE_Update()
    {
        Product p = productService.findOne( latestID );
        final Product newP = new Product();
        //BeanUtils.copyProperties( p, newP );
        newP.setId( p.getId() );
        newP.setName( "Ice Cream" );
        newP.setPrice( p.getPrice() );
        newP.setVersion( p.getVersion() );

        p = productService.save( newP );

        assertEquals( "Ice Cream", p.getName() );
    }

    @Test
    public void testF_UpdateVersionMismatch()
    {
        thrown.expect( IllegalStateException.class );
        thrown.expectMessage( CoreMatchers.containsString( "Stale object" ) );

        productService.save( Product.builder()
                .id( latestID )
                .version( (long) -1)
                .build() );
    }

    @Test
    public void testG_FindRevisions()
    {
        final Revisions<Integer, Product> rev = productService.findProductRevisions( latestID );
        // We should have two history entries; 1 for the insert and another for the update
        assertEquals( 2, rev.getContent().size() );
    }

    @Test
    public void testH_Version()
    {
        final Product p = productService.findByName( "Ice Cream" );

        // 0 for the insert, 1 for the update
        assertEquals( 1, (long)p.getVersion() );
    }

    @Test
    public void testI_Paging()
    {
        final Sort nameSort = new Sort( "name" );
        int pageNum = 0;
        final PageRequest page = new PageRequest( pageNum, 2, nameSort );
        Page<Product> result = productService.findAll( page );

        assertEquals( 5, result.getTotalPages() );// 10 items, 2 per page, 5 pages
        assertEquals(10, result.getTotalElements() );// 10 elements
        assertEquals( 2, result.getNumberOfElements() );// 2 elements per page

        /*while ( true )
        {
            result.getContent().forEach( System.out::println );

            if ( !result.hasNext() )
                break;

            result = productService.findAll( new PageRequest( ++pageNum, 2, nameSort ) );
        }*/
    }
}