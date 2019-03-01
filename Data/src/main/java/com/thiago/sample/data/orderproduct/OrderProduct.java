package com.thiago.sample.data.orderproduct;

import com.thiago.sample.data.order.Order;
import com.thiago.sample.data.product.Product;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@ToString( of = { "id", "product", "quantity" } )
@Builder
@EqualsAndHashCode( of = "id" )
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderProduct
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private BigInteger id;

    @ManyToOne( fetch = FetchType.EAGER )
    private Product product;

    @ManyToOne
    private Order order;

    private int quantity;

    public BigDecimal getTotal()
    {
        return product.getPrice().multiply( BigDecimal.valueOf( quantity ) );
    }
}