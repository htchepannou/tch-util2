/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util.converter;


import java.util.Date;
import junit.framework.TestCase;


/**
 *
 * @author herve
 */
public class DoubleConverterTest extends TestCase
{

    Converter _converter;


    public DoubleConverterTest(String testName)
    {
        super(testName);
    }


    @Override
    protected void setUp() throws Exception
    {
        _converter = new DoubleConverter();
    }


    public void testConvert_String()
    {
        assertEquals(new Double(10.5), _converter.convert("10.5"));
    }


    public void testConvert_Number()
    {
        assertEquals(new Double(100), _converter.convert(100));
    }


    public void testConvert_Date()
    {
        long now = System.currentTimeMillis();
        assertEquals(new Double(now), _converter.convert(new Date(now)));
    }


    public void testConvert_Array()
    {
        String[] value = {"10", "1"};
        assertEquals(new Double(10), _converter.convert(value));
    }

}
