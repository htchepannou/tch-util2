/*
 * Copyright 2013 Herve Tchepannou Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tchepannou.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class HTMLUtilTest
    extends TestCase
{
    public void testIsHTML_BR()
    {
        assertTrue(HTMLUtil.isHtml ("This is a test<br>with love"));
    }
    public void testIsHTML_P()
    {
        assertTrue(HTMLUtil.isHtml ("<p>This is a test<br>with love</p>"));
    }
    public void testIsHtml_DOCX()
        throws IOException
    {
        InputStream in = getClass ().getResourceAsStream ("/docx.html");
        ByteArrayOutputStream out = new ByteArrayOutputStream ();
        IOUtil.copy (in,out);
        String html = new String(out.toByteArray ());
        assertTrue(HTMLUtil.isHtml (html));
    }
    
    public void testCleanup()
    {
        assertEquals("Hello", HTMLUtil.cleanup ("<!-- Comment\nand nice -->Hello"));
    }
}
