package com.thiago.sample.data.order;

import com.thiago.sample.data.orderproduct.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@EntityListeners( AuditingEntityListener.class )
@Audited
@Data
@EqualsAndHashCode( of = "id" )
@NoArgsConstructor
@AllArgsConstructor
@Entity( name = "Orders" )
public class Order
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private BigInteger id;

    @OneToMany( mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @NotAudited
    private List<OrderProduct> products;

    @Version
    private Long version;

    @CreatedDate
    private Date createTimestamp;

    @LastModifiedDate
    private Date updateTimestamp;

    public BigDecimal getOrderTotal()
    {
        return products.stream()
                .map( OrderProduct::getTotal )
                .reduce( BigDecimal.ZERO, BigDecimal::add );
    }

    @PrePersist
    @PreUpdate
    public void adopt()
    {
        if ( products == null )
            return;

        products.forEach( p -> p.setOrder( this ) );
    }
}