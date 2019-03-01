package com.thiago.sample.data.product;

import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@EntityListeners( AuditingEntityListener.class )
@Audited
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode( of = "id" )
@Entity
public class Product
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private BigInteger id;

    private String name;
    private BigDecimal price;

    @Column( nullable = false )
    @Version
    private Long version;

    @Column( nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    @LastModifiedDate
    private Date versionTimestamp;
}