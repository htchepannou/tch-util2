package com.tchepannou.util.converter;

import java.util.TimeZone;

/**
 * User: herve
 * Date: 13-11-10 11:46 AM
 */
public class TimeZoneConverter
        implements Converter
{
    //-- Converter overrides
    public Object convert(Object value)
    {
        if (value instanceof String)
        {
            return TimeZone.getTimeZone(value.toString());
        }
        else if (value instanceof TimeZone)
        {
            return value;
        }
        else
        {
            return null;
        }
    }
}
