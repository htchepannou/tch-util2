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
public class ByteConverterTest extends TestCase
{

    Converter _converter;


    public ByteConverterTest(String testName)
    {
        super(testName);
    }


    @Override
    protected void setUp() throws Exception
    {
        _converter = new ByteConverter();
    }


    public void testConvert_String()
    {
        byte b = 10;
        assertEquals(new Byte(b), _converter.convert("10.0"));
    }


    public void testConvert_Number()
    {
        byte b = 100;
        assertEquals(new Byte(b), _converter.convert(100));
    }


    public void testConvert_Date()
    {
        long now = System.currentTimeMillis();
        assertEquals(new Byte((byte)now), _converter.convert(new Date(now)));
    }


    public void testConvert_Array()
    {
        String[] value = {"10", "1"};
        assertEquals(new Byte((byte)10), _converter.convert(value));
    }

}
