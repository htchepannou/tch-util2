/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util.converter;

/**
 * This interface defines a data converter
 * 
 * @author herve
 */
public interface Converter 
{
    /**
     * Convert a value to another type
     * 
     * @param value Value to convert
     * 
     * @return converted value
     */
    public Object convert (Object value);
}
