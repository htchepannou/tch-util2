package com.tchepannou.util.converter;

import java.util.Currency;

/**
 * User: herve
 * Date: 13-11-10 11:46 AM
 */
public class CurrencyConverter
        implements Converter
{
    //-- Converter overrides
    public Object convert(Object value)
    {
        if (value instanceof String)
        {
            return Currency.getInstance(value.toString());
        }
        else if (value instanceof Currency)
        {
            return value;
        }
        else
        {
            return null;
        }
    }
}
