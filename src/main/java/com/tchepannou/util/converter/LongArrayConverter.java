/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util.converter;

/**
 *
 * @author herve
 */
public class LongArrayConverter 
        implements Converter
{
    //-- Attributes
    private Converter _delegate = new LongConverter ();

    //-- Converter override
    @Override
    public Object convert (Object value)
    {
        Class type = value.getClass ();
        if (type.isArray ())
        {
            Object[] array = (Object[])value;
            long[]   result = new long [array.length];
            for (int i=0 ; i<array.length ; i++)
            {
                result[i] = ((Number)_delegate.convert (array[i])).longValue ();
            }
            return result;
        }
        else
        {
            long xvalue = ((Number)_delegate.convert (value)).longValue ();
            return new long[] {xvalue};
        }
    }
}
