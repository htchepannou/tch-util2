/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class for Internet MIME types
 * 
 * @author herve
 */
public class MimeUtil
{
    //-- Static Attributes
    private static final MimeUtil INSTANCE = new MimeUtil ();
    private static final String MIME_SEPARATOR = "/";
    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String ALL = "*";
    public static final String PDF = "application/pdf";
    public static final String XML = "application/xml";
    public static final String HTML = "text/html";
    public static final String TEXT = "text/plain";
    public static final String CSV = "text/comma-separated-values";
    //-- Attributes
    private Map<String, String> _mimeTypes = new HashMap<String, String> ();
    private Map<String, String> _extensions = new HashMap<String, String> ();
    private boolean _loaded = false;

    //-- Constructor
    private MimeUtil ()
    {
    }

    //-- Public Method
    public static MimeUtil getInstance ()
    {
        if ( !INSTANCE.loaded () )
        {
            try
            {
                INSTANCE.load ();
            }
            catch ( IOException e )
            {
                throw new IllegalStateException ("Unable to load the mime types", e);
            }
        }
        return INSTANCE;
    }

    public static boolean isXML (String mimeType)
    {
        if ( mimeType == null )
        {
            return false;
        }
        String[] tokens = mimeType.split (MIME_SEPARATOR);
        if ( tokens != null && tokens.length == 2 )
        {
            return tokens[1].equals (ALL) || tokens[1].endsWith ("xml");
        }
        return false;
    }

    public static boolean isFileImage (String path)
    {
        String mime = MimeUtil.getInstance ().getMimeTypeByFile (path);
        return isImage (mime);
    }

    public static boolean isImage (String mimeType)
    {
        if ( StringUtil.isEmpty (mimeType) )
        {
            return false;
        }
        return mimeType.startsWith ("image/");
    }

    public static boolean isVideo (String mimeType)
    {
        if ( StringUtil.isEmpty (mimeType) )
        {
            return false;
        }
        return mimeType.startsWith ("video/");
    }

    /**
     * Return the mime type of a given file
     */
    public String getMimeTypeByFile (String path)
    {
        int i = path.lastIndexOf (FILE_EXTENSION_SEPARATOR);
        String ext = i >= 0
            ? path.substring (i + 1)
            : "";
        String mime = getMimeTypeByExtension (ext);
        return mime;
    }

    /**
     * Return the mime type of a given extension.
     * The extension may start with a leading dot (.)
     */
    public String getMimeTypeByExtension (String extension)
    {
        String ext = extension.startsWith (FILE_EXTENSION_SEPARATOR)
            ? extension.substring (1)
            : extension;
        String mime = _mimeTypes.get (ext.toLowerCase (Locale.getDefault ()));
        return mime;
    }

    /**
     * Returns the file extension for a given MIME type
     */
    public String getExtension (String mimeType)
    {
        return _extensions.get (mimeType);
    }

    public boolean matches (String mimeType, String pattern)
    {
        String[] tokens1 = mimeType.split (MIME_SEPARATOR);
        String[] tokens2 = pattern.split (MIME_SEPARATOR);

        return matchesValue (tokens1[0], tokens2[0]) && matchesValue (tokens1[1], tokens2[1]);
    }

    private boolean matchesValue (String value, String compareWith)
    {
        return ALL.equals (compareWith) || value.equals (compareWith);
    }

    //-- Private method
    protected boolean loaded ()
    {
        return _loaded;
    }

    protected void load ()
        throws IOException
    {
        synchronized ( MimeUtil.class )
        {
            InputStream in = MimeUtil.class.getResourceAsStream ("/mime.properties");
            try
            {
                _mimeTypes = new HashMap<String, String> ();
                _extensions = new HashMap<String, String> ();

                Properties props = new Properties ();
                props.load (in);

                for ( Enumeration names = props.propertyNames (); names.hasMoreElements (); )
                {
                    String ext = ( String ) names.nextElement ();
                    String mimeType = props.getProperty (ext);

                    _mimeTypes.put (ext, mimeType);
                    _extensions.put (mimeType, ext);
                }

                _loaded = true;
            }
            finally
            {
                in.close ();
            }
        }
    }

}
