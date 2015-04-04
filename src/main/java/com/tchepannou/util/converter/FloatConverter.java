/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util.converter;
import com.tchepannou.util.StringUtil;
import java.util.Date;



/**
 *
 * @author herve
 */
public class FloatConverter 
        implements Converter
{
    //-- Converter override
    @Override
    public Object convert (Object value)
    {
        if (value instanceof Number)
        {
            return ((Number)value).floatValue();
        }
        else if (value instanceof String)
        {
            return StringUtil.toFloat (( String ) value, 0.0f);
        }
        else if (value instanceof Date)
        {
            return (float)((Date)value).getTime ();
        }
        else if (value != null && value.getClass ().isArray ())
        {
            Object v = ((Object [])value)[0];
            return convert (v);
        }
        else
        {
            return 0f;
        }
    }
}
