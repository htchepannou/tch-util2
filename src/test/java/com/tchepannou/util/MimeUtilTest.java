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
public class MimeUtilTest extends TestCase
{

    public MimeUtilTest(String testName)
    {
        super(testName);
    }


    public void testGetMimeTypeByFile()
    {
        String mime = MimeUtil.getInstance().getMimeTypeByFile("/foo/bar/text.xml");
        assertEquals("application/xml", mime);
    }


    public void testGetMimeTypeByExtension()
    {
        String mime = MimeUtil.getInstance().getMimeTypeByExtension("xml");
        assertEquals("application/xml", mime);
    }


    public void testGetMimeTypeByExtension_WithDot()
    {
        String mime = MimeUtil.getInstance().getMimeTypeByExtension(".xml");
        assertEquals("application/xml", mime);
    }


    public void testGetExtension_XML()
    {
        String ext = MimeUtil.getInstance().getExtension("application/xml");
        assertEquals("xml", ext);
    }

    public void testGetExtension_FLV()
    {
        String ext = MimeUtil.getInstance().getExtension("video/x-flv");
        assertEquals("flv", ext);
    }

    public void testMatches()
    {
        assertTrue("text/xml", MimeUtil.getInstance().matches("text/xml", "text/xml"));
        assertTrue("*/xml", MimeUtil.getInstance().matches("text/xml", "*/xml"));
        assertTrue("*/*", MimeUtil.getInstance().matches("text/xml", "*/*"));
    }

    public void testIsXML ()
    {
       assertTrue ("application/html+xml", MimeUtil.getInstance().isXML("application/html+xml"));
       assertTrue ("application/xml", MimeUtil.getInstance().isXML("application/xml"));
       assertTrue ("text/xml", MimeUtil.getInstance().isXML("text/xml"));
       assertTrue ("*/xml", MimeUtil.getInstance().isXML("*/xml"));
       assertTrue ("*/*", MimeUtil.getInstance().isXML("*/*"));
    }
}
