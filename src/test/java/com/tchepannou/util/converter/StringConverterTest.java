/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util.converter;

import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class StringConverterTest extends TestCase {
    
    Converter _converter;
    
    public StringConverterTest(String testName) 
    {
        super(testName);
    }

    @Override
    protected void setUp () throws Exception
    {
        _converter = new StringConverter ();
    }

    public void testConvert_String()
    {
        assertEquals("test", _converter.convert("test"));
    }

    public void testConvert_Number()
    {
        assertEquals("test", "100", _converter.convert(100));
    }

    public void testConvert_Array()
    {
        String[] value = {"10", "1"};
        assertEquals("10", _converter.convert(value));
    }
}
