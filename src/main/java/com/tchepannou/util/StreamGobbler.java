/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * See http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html
 * @author herve
 */
class StreamGobbler
    extends Thread
{
    //-- Attributes
    InputStream _is;
    ByteArrayOutputStream _out = new ByteArrayOutputStream ();
    String _type;


    //-- Constructor
    StreamGobbler (InputStream is, String type)
    {
        this._is = is;
        this._type = type;
    }

    
    //-- Thread overrides
    @Override
    public void run ()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader (_is);
            BufferedReader br = new BufferedReader (isr);
            String line = null;
            while ( (line = br.readLine ()) != null )
            {
                _out.write (line.getBytes ());
                _out.write ('\n');
            }
        }
        catch ( IOException ioe )
        {
            ioe.printStackTrace ();
        }
    }

    @Override
    public String toString ()
    {
        return new String (_out.toByteArray ());
    }
}
