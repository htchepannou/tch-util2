/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util.converter;


/**
 *
 * @author herve
 */
public class BooleanConverter
        implements Converter
{


    @Override
    public Object convert(Object value)
    {
        if (value instanceof Boolean)
        {
            return (Boolean)value;
        }
        else if (value instanceof Number)
        {
            return ((Number)value).intValue() == 1;
        }
        else if (value instanceof String)
        {
            String str = (String)value;
            return "true".equalsIgnoreCase(str)
                   || "on".equalsIgnoreCase(str)
                   || "y".equalsIgnoreCase(str)
                   || "1".equalsIgnoreCase(str);
        }
        else if (value != null && value.getClass ().isArray ())
        {
            Object v = ((Object [])value)[0];
            return convert (v);
        }
        else
        {
            return false;
        }
    }

}
