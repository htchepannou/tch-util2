/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util;
import java.io.Serializable;
import java.util.Comparator;


/**
 * Comparator for version number
 * 
 * @author herve
 */
public class VersionComparator 
        implements Comparator, Serializable
{
    //-- Static
    public static final long serialVersionUID = 1L;
    private static final String SEPARATOR_REGEX = "\\.";
            
    //-- Comparator overrides
    public int compare(Object o1, Object o2)
    {
        String[] v1 = o1 != null ? o1.toString().split(SEPARATOR_REGEX) : new String[] {};
        String[] v2 = o2 != null ? o2.toString().split(SEPARATOR_REGEX) : new String[] {};
        int      imax = Math.min(v1.length, v2.length);
        
        for (int i=0 ; i<imax ; i++)
        {
            int diff = compare (v1[i], v2[i]);
            if (diff != 0)
            {
                return diff;
            }
        }
        
        return v1.length - v2.length;
    }
    
    private int compare (String s1, String s2)
    {
        try
        {
            int i1 = Integer.parseInt(s1);
            int i2 = Integer.parseInt(s2);
            return i1-i2;
        }
        catch (NumberFormatException e)
        {
            return s1.compareTo(s2);
        }
        catch (NullPointerException e)
        {
            return -1;
        }
    }

}
