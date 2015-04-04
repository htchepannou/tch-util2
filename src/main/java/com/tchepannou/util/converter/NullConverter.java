/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util.converter;

/**
 *
 * @author herve
 */
public class NullConverter 
        implements Converter
{


    @Override
    public Object convert(Object value)
    {
        return value;
    }
    
}
