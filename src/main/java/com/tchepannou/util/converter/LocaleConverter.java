package com.tchepannou.util.converter;

import java.util.Locale;

/**
 * User: herve
 * Date: 13-11-10 11:46 AM
 */
public class LocaleConverter
        implements Converter
{
    //-- Converter overrides
    public Object convert(Object value)
    {
        if (value instanceof String)
        {
            for (Locale loc : Locale.getAvailableLocales())
            {
                if (loc.toString().equals(value))
                {
                    return loc;
                }
            }
            return null;
        }
        else if (value instanceof Locale)
        {
            return value;
        }
        else
        {
            return null;
        }
    }
}
