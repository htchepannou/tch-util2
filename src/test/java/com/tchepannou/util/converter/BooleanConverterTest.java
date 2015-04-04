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
public class BooleanConverterTest extends TestCase
{

    Converter _converter;


    public BooleanConverterTest(String testName)
    {
        super(testName);
    }


    @Override
    protected void setUp() throws Exception
    {
        _converter = new BooleanConverter();
    }


    public void testConvert_String()
    {
        assertEquals("true", true, _converter.convert("true"));
        assertEquals("false", false, _converter.convert("false"));
        assertEquals("on", true, _converter.convert("on"));
        assertEquals("off", false, _converter.convert("off"));
        assertEquals("1", true, _converter.convert("1"));
        assertEquals("0", false, _converter.convert("0"));
    }


    public void testConvert_Number()
    {
        assertEquals("1", true, _converter.convert(1));
        assertEquals("0", false, _converter.convert(0));
    }


    public void testConvert_Boolean()
    {
        assertEquals("true", true, _converter.convert(true));
        assertEquals("false", false, _converter.convert(false));
    }


    public void testConvert_Array()
    {
        String[] value = {"y", "n"};
        assertEquals(Boolean.TRUE, _converter.convert(value));
    }


}
