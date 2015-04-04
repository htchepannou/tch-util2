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
public class VersionUtilTest 
        extends TestCase
{

    public VersionUtilTest(String testName)
    {
        super(testName);
    }

    public void isVersionNumber ()
    {
        assertTrue ("0.1", VersionUtil.isVersionNumber("0.1"));
        assertTrue ("1.1.5", VersionUtil.isVersionNumber("1.1.5"));
        assertFalse ("1.x.x", VersionUtil.isVersionNumber("1.x.x"));
        assertFalse ("log", VersionUtil.isVersionNumber("log"));        
    }
    public void testGetVersionNumber() throws Exception
    {
        String value = VersionUtil.getVersionNumber();
        assertEquals("1.5.1", value);
    }


    public void testGetBuildNumber() throws Exception
    {
        String value = VersionUtil.getBuildNumber();
        assertEquals("0009", value);
    }


}
