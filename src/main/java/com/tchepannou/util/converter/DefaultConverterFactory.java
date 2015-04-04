/*
 * Copyright 2012 Herve Tchepannou Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tchepannou.util.converter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

/**
 *
 * @author herve
 */
public class DefaultConverterFactory implements ConverterFactory
{
    //-- Attribute
    private static final DefaultConverterFactory INSTANCE = new DefaultConverterFactory ();
    
    private Map<Class, Converter> _map = new HashMap<Class, Converter> ();
    private ConverterFactory _delegate;
    
    //-- Constructor
    private DefaultConverterFactory ()
    {
        _map.put (boolean.class, new BooleanConverter ());
        _map.put (byte.class, new ByteConverter ());
        _map.put (double.class, new DoubleConverter ());
        _map.put (float.class, new FloatConverter ());
        _map.put (int.class, new IntegerConverter ());
        _map.put (long.class, new LongConverter ());
        
        _map.put (Boolean.class, new BooleanConverter ());
        _map.put (Byte.class, new ByteConverter ());
        _map.put (Currency.class, new CurrencyConverter ());
        _map.put (Date.class, new DateConverter ());
        _map.put (Double.class, new DoubleConverter ());
        _map.put (Float.class, new FloatConverter ());
        _map.put (Integer.class, new IntegerConverter ());
        _map.put (Locale.class, new LocaleConverter());
        _map.put (Long.class, new LongConverter ());
        _map.put (Properties.class, new PropertiesConverter ());
        _map.put (String.class, new StringConverter ());
        _map.put (Time.class, new TimeConverter ());
        _map.put (Timestamp.class, new TimestampConverter ());
        _map.put (TimeZone.class, new TimeZoneConverter ());
    }
    
    //-- Public
    public static DefaultConverterFactory getInstance ()
    {
        return INSTANCE;
    }
    
    public void register (Class type, Converter cv)
    {
        _map.put (type, cv);
    }
    
    public void setDelegate (ConverterFactory delegate)
    {
        _delegate = delegate;
    }
    
    
    //-- Converter override
    public Converter getConverter (Class type)
    {
        Converter cv = _map.get(type);
        
        if (cv == null )
        {
            if (Enum.class.isAssignableFrom(type))
            {
                cv = new EnumConverter(type);
                synchronized (_map)
                {
                    _map.put(type, cv);
                }
            }
            else if (_delegate != null)
            {
                cv = _delegate.getConverter (type);
            }
        }
        return cv;
    }    
}
