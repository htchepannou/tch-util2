package com.tchepannou.util.converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: herve
 * Date: 14-07-19 2:48 PM
 */
public class PropertiesConverter implements Converter
{
    @Override
    public Object convert(Object value)
    {
        if (value instanceof Properties)
        {
            return (Properties)value;
        }
        else if (value != null)
        {
            String xvalue = value.toString();
            InputStream in = new ByteArrayInputStream((xvalue.getBytes()));
            try
            {
                Properties props = new Properties ();
                props.load(in);
                return props;
            }
            catch(IOException e)
            {
                return null;
            }
        }
        return null;
    }
}
