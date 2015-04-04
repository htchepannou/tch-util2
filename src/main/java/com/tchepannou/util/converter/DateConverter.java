/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util.converter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author herve
 */
public class DateConverter 
        implements Converter
{
    //-- Private
    private static DateFormat FORMAT = new SimpleDateFormat ("yyyy-MM-dd");
    
    //-- Converter override
    @Override
    public Object convert (Object value)
    {
        if (value instanceof Date)
        {
            return (Date)value;
        }
        else if (value instanceof Number)
        {
            return new Date (((Number)value).longValue());
        }
        else if (value instanceof String)
        {
            try
            {
                return FORMAT.parse ((String)value);
            }
            catch (ParseException e)
            {
                return null;
            }
        }
        else if (value != null && value.getClass ().isArray ())
        {
            Object v = ((Object [])value)[0];
            return convert (v);
        }
        else
        {
            return null;
        }
    }
}
