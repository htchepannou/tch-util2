/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util;

import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class StringUtilTest 
        extends TestCase 
{    
    public StringUtilTest(String testName) 
    {
        super(testName);
    }


    public void testRepeat ()
    {
        assertEquals ("---", StringUtil.repeat ("-", 3));
    }
    public void testRepeat_Null ()
    {
        assertEquals ("", StringUtil.repeat (null, 3));
    }
    public void testRepeat_0 ()
    {
        assertEquals ("", StringUtil.repeat ("-", 0));
    }

    public void testToString()
    {
        Object o = new StringUtilTest (getName ());
        assertEquals ("StringUtilTest{}", StringUtil.toString(o));
    }


    public void testIsEmpty()
    {
        assertTrue ("<empty>", StringUtil.isEmpty(""));
        assertFalse ("foo", StringUtil.isEmpty("foo"));
    }


    public void testIsEmail()
    {
        assertTrue ("herve@tchepannou.com", StringUtil.isEmail("herve@tchepannou.com"));
        assertFalse ("herve@tchepannou", StringUtil.isEmail("herve@tchepannou"));
        assertFalse ("", StringUtil.isEmail(""));
    }


    public void testToInt()
    {
        assertEquals ("1", 1, StringUtil.toInt("1", -1));
        assertEquals ("xxx", -1, StringUtil.toInt("xxx", -1));
    }
    
    public void testMerge ()
    {
        String[] tokens = new String[] {"This", "is", "a", "test"};
        String result = StringUtil.merge(tokens, " ");
        assertEquals ("This is a test", result);
    }
    
    public void testMerge_Capitalize1stCharacter ()
    {
        String[] tokens = new String[] {"This", "is", "a", "test"};
        String result = StringUtil.merge(tokens, " ", true);
        assertEquals ("This Is A Test", result);
    }
}
