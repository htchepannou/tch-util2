package com.tchepannou.util.converter;

/**
 * User: herve
 * Date: 13-11-10 11:46 AM
 */
public class EnumConverter
        implements Converter
{
    //-- Attribute
    public Class<? extends Enum> _type;

    //-- Constructor
    public EnumConverter(Class<? extends Enum> type)
    {
        _type = type;
    }


    //-- Converter overrides
    public Object convert(Object value)
    {
        if (value instanceof String)
        {
            try
            {
                return Enum.valueOf(_type, (String)value);
            }
            catch (Exception e)
            {
                return null;
            }
        }
        else if (value != null && value.getClass().equals(_type))
        {
            return value;
        }
        else
        {
            return null;
        }
    }
}
