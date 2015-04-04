/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util.converter;


import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Implementation of {@link Converter} for {@link java.lang.String}
 * 
 * @author herve
 */
public class StringConverter 
        implements Converter
{
    //-- Converter overrides
    public Object convert(Object value)
    {
        if (value != null && value.getClass ().isArray ())
        {
            Object v = ((Object [])value)[0];
            return convert (v);
        }
        else if (value instanceof Enum)
        {
            return ((Enum)value).name();
        }
        else if (value instanceof Currency)
        {
            return ((Currency)value).getCurrencyCode();
        }
        else if (value instanceof Locale)
        {
            return ((Locale)value).toString();
        }
        else if (value instanceof TimeZone)
        {
            return ((TimeZone)value).toString();
        }
        else
        {
            return value != null ? value.toString() : null;
        }
    }
}
