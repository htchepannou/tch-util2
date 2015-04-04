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
public class IntegerConverterTest extends TestCase
{

    Converter _converter;


    public IntegerConverterTest(String testName)
    {
        super(testName);
    }


    @Override
    protected void setUp() throws Exception
    {
        _converter = new IntegerConverter();
    }


    public void testConvert_String()
    {
        assertEquals(new Integer(10), _converter.convert("10.5"));
    }


    public void testConvert_Number()
    {
        assertEquals(new Integer(100), _converter.convert(100));
    }


    public void testConvert_Date()
    {
        long now = System.currentTimeMillis();
        assertEquals(new Integer((int)now), _converter.convert(new Date(now)));
    }


    public void testConvert_Array()
    {
        String[] value = {"10", "1"};
        assertEquals(new Integer(10), _converter.convert(value));
    }

}
