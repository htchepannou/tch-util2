/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util.converter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author herve
 */
public class TimestampConverter 
        implements Converter
{
    //-- Static
    private static DateFormat FORMAT = new SimpleDateFormat ("yyyy-MM-dd hh:mm");
    
    //-- Converter override
    @Override
    public Object convert (Object value)
    {
        if (value instanceof Timestamp)
        {
            return (Timestamp)value;
        }
        else if (value instanceof Date)
        {
            return new Timestamp(((Date)value).getTime ());
        }
        else if (value instanceof String)
        {
            try
            {
                Date date = FORMAT.parse ((String)value);
                return new Timestamp (date.getTime ());
            }
            catch (ParseException e)
            {
                return null;
            }
        }
        else if (value instanceof Number)
        {
            return new Timestamp (((Number)value).longValue());
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
