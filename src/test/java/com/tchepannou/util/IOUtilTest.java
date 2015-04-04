/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import junit.framework.TestCase;


/**
 *
 * @author herve
 */
public class IOUtilTest extends TestCase
{

    public IOUtilTest(String testName)
    {
        super(testName);
    }

    public void testDelete_File () throws Exception
    {
        String filename = getName() + "_" + System.currentTimeMillis();
        File in = new File(System.getProperty("java.io.tmpdir"), filename);

        createFile(in, "content");

        IOUtil.delete (in);

        assertFalse(in.getAbsolutePath() + " not deleted", in.exists());
    }

    public void testDelete_Directory () throws Exception
    {
        String filename = getName() + "_" + System.currentTimeMillis();
        File tmp = new File(System.getProperty("java.io.tmpdir"));
        File src = createDir(tmp, filename + "_in");
        File d1 = createDir(src, "1");
        File d11 = createDir(d1, "1.1");
        File d12 = createDir(d1, "1.2");
        File d2 = createDir(src, "2");
        File d21 = createDir(d2, "2.1");
        File f22 = createFile(d2, "2.2.txt", "content");

        IOUtil.delete (src);

        assertFalse(f22.getAbsolutePath() + " not deleted", f22.exists());
        assertFalse(d21.getAbsolutePath() + " not deleted", d21.exists());
        assertFalse(d2.getAbsolutePath() + " not deleted", d2.exists());
        
        assertFalse(d12.getAbsolutePath() + " not deleted", d12.exists());
        assertFalse(d11.getAbsolutePath() + " not deleted", d11.exists());
        assertFalse(d1.getAbsolutePath() + " not deleted", d1.exists());
        
        assertFalse(src.getAbsolutePath() + " not deleted", src.exists());        
    }    
    public void testCopy_File() throws Exception
    {
        String filename = getName() + "_" + System.currentTimeMillis();
        File in = new File(System.getProperty("java.io.tmpdir"), filename);
        File out = new File(System.getProperty("java.io.tmpdir"), filename + "_2");

        createFile(in, "content");

        IOUtil.copy(in, out);

        assertTrue(out.getAbsolutePath() + " not found", out.exists());
        assertEquals(out.getAbsolutePath() + " content", out, "content");
    }


    public void testCopy_Dir() throws Exception
    {
        String filename = getName() + "_" + System.currentTimeMillis();
        File tmp = new File(System.getProperty("java.io.tmpdir"));
        File src = createDir(tmp, filename + "_in");
        File d1 = createDir(src, "1");
        createDir(d1, "1.1");
        createDir(d1, "1.2");
        File d2 = createDir(src, "2");
        createDir(d2, "2.1");
        createFile(d2, "2.2.txt", "content");

        File tgt = createDir(tmp, filename + "_out");
        IOUtil.copy(src, tgt);

        File[] tgt_1 = tgt.listFiles();
        assertEquals("number of children - level1", 2, tgt_1.length);

        File[] tgt_1_1 = tgt_1[0].listFiles();
        assertEquals("number of children - level2", 2, tgt_1_1.length);
    }


    private File createDir(File parent, String name)
    {
        File in = new File(parent, name);
        in.mkdirs();
        return in;
    }


    private File createFile(File parent, String name, String content) throws IOException
    {
        File in = new File(parent, name);
        createFile(in, content);

        return in;
    }


    private void createFile(File f, String content) throws IOException
    {
        ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
        FileOutputStream out = new FileOutputStream(f);
        try
        {
            IOUtil.copy(in, out);
        }
        finally
        {
            out.close();
        }
    }


    private void assertEquals(String msg, File f, String expected) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtil.copy(f, out);
        assertEquals(msg, expected, out.toString());
    }


}
