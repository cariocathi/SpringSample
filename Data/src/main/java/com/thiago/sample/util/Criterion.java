package com.thiago.sample.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode( of = "attribute" )
public class Criterion
{
    private String attribute;
    private FilterTypeEnum filterType;
    private Object[] values;

    public Criterion( final String attribute, final FilterTypeEnum filterType, final Object... values )
    {
        setAttribute( attribute );
        setFilterType( filterType );
        setValues( values );
    }
}