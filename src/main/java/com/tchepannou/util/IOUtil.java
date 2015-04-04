/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utility for IO manipulations
 * 
 * @author herve
 */
public class IOUtil
{
    //-- Attributes
    private static final int BUFFER_LEN = 1024*1024;  // 1 Meg    
    private static byte[] _buffer = new byte[BUFFER_LEN];
    
    
    //-- Public methods
    public static void delete (File file)
    {
        if (file.isDirectory())
        {
            File[] children = file.listFiles();
            if (children != null)
            {
                for (int i=0 ; i<children.length ; i++)
                {
                    delete (children[i]);
                }
            }
        }
        file.delete();
    }
    
    public static int copy (File in, File out)
        throws IOException
    {
        if (in.isDirectory())
        {
            return copyDirs (in, out);
        }
        else
        {
            return copyFile (in, out);
        }
    }
    
    protected static int copyFile (File in, File out)
        throws IOException
    {
        FileInputStream fin = new FileInputStream (in);
        try
        {
            FileOutputStream fout = new FileOutputStream (out);
            try
            {
                return copy (fin, fout);
            }
            finally
            {
                fout.close ();
            }
        }
        finally
        {
            fin.close ();
        }
    }
    
    protected static int copyDirs (File in, File out)
        throws IOException
    {
        int count = 0;
        File[] infs = in.listFiles();
        if (infs != null)
        {
            for (int i=0 ; i<infs.length ; i++)
            {
                File inf = infs[i];
                File outf = new File (out, inf.getName());
                if (inf.isDirectory())
                {
                    if (!outf.exists())
                    {
                        if (!outf.mkdirs())
                        {
                            throw new IOException ("Unable to create directory " + outf.getAbsolutePath());
                        }
                        count++;
                    }
                    
                    count += copyDirs (inf, outf);
                }
                else
                {                    
                    copyFile(inf, outf);
                    count++;
                }
            }
        }
        
        return count;
    }
    
    
    public static int copy (byte[] in, OutputStream out)
        throws IOException
    {
        ByteArrayInputStream bin = new ByteArrayInputStream (in);
        try
        {
            return copy (bin, out);
        }
        finally
        {
            bin.close ();
        }
    }
    
    public static int copy (byte[] in, File out)
        throws IOException
    {
        ByteArrayInputStream bin = new ByteArrayInputStream (in);
        try
        {
            return copy (bin, out);
        }
        finally
        {
            bin.close ();
        }
    }
    
    public static int copy (InputStream in, File fout)
        throws IOException
    {
        FileOutputStream out = new FileOutputStream (fout);
        try
        {
            return copy (in, out);
        }
        finally
        {
            out.close ();
        }
        
    }
    
    public static int copy (File fin, OutputStream out)
        throws IOException
    {
        FileInputStream in = new FileInputStream (fin);
        try
        {
            return copy (in, out);
        }
        finally
        {
            in.close ();
        }
    }
    
    public static int copy (InputStream in, OutputStream out)
        throws IOException
    {
        int count = 0;
        synchronized (_buffer)
        {
            int len = 0;
            while ((len = in.read(_buffer, 0, BUFFER_LEN)) > 0)
            {
                out.write (_buffer, 0, len);
                count += len;
            }
        }
        return count;
    }
}
