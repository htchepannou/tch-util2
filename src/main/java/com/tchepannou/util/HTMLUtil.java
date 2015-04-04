/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author herve
 */
public class HTMLUtil
{
    //-- Static Attributes
    private static final String HTML_PATTERN = ".*\\<[^>]+>.*";
    private static final Map<Character, String> CODES;

    static
    {
        CODES = new HashMap<Character, String> ();
        CODES.put ('<', "&lt;");
        CODES.put ('>', "&gt;");
        CODES.put ('"', "&quot;");
        CODES.put ('&', "&amp;");
        CODES.put ('\n', "<br/>");
    }

    //-- Public methods
    /**
     * Encode a string to be HTML readable.
     *
     * @param str String to encode
     * @return Encoded string
     */
    public static String encode (String str)
    {
        return encode (str, true);
    }

    public static String encode (String str, boolean encodeCRLF)
    {
        if ( (str == null) || (str.length () == 0) )
        {
            return StringUtil.EMPTY;
        }
        else
        {
            try
            {
                StringWriter writer = new StringWriter ();
                StringEscapeUtils.escapeHtml (writer, str);
                String xstr = writer.toString ();
                if ( !encodeCRLF )
                {
                    xstr = xstr.replaceAll ("(?i)<br>", "\n");
                    xstr = xstr.replaceAll ("(?i)<br/>", "\n");
                }
                else
                {
                    xstr = xstr.replaceAll ("\n", "<br/>");
                }
                return xstr;
            }
            catch ( IOException e )
            {
                throw new RuntimeException ("Encoding error", e);
            }
        }
    }

    public static String decode (String html)
    {
        String xhtml = html != null
            ? StringEscapeUtils.unescapeHtml (html)
            : StringUtil.EMPTY;
        xhtml = StringUtil.replace (xhtml, "<br>", "\n");
        xhtml = StringUtil.replace (xhtml, "<br/>", "\n");
        return xhtml;
    }

    public static String encodeEmail (String email)
    {
        if ( email == null )
        {
            return "";
        }
        else
        {
            StringBuffer sb = new StringBuffer ();
            DecimalFormat df = new DecimalFormat ("000");
            for ( int i = 0, len = email.length (); i < len; i++ )
            {
                int ch = email.charAt (i);
                sb.append ("&#" + df.format (ch) + ";");
            }
            return sb.toString ();
        }
    }

    public static String extractText (String html)
        throws IOException
    {
        if ( StringUtil.isEmpty (html) )
        {
            return StringUtil.EMPTY;
        }

        final StringBuffer buff = new StringBuffer ();
        ByteArrayInputStream in = new ByteArrayInputStream (html.getBytes ());
        InputStreamReader reader = new InputStreamReader (in);
        try
        {
            ParserDelegator delegator = new ParserDelegator ();
            ParserCallback pc = new ParserCallback ()
            {
                @Override
                public void handleText (final char[] data, final int pos)
                {
                    buff.append (data);
                }
            };
            delegator.parse (reader, pc, true);
            return buff.toString ();
        }
        finally
        {
            reader.close ();
        }
    }

    public static Map<String, String> extractMetas (String html)
        throws IOException
    {
        final Map<String, String> metas = new HashMap<String, String> ();
        ByteArrayInputStream in = new ByteArrayInputStream (html.getBytes ());
        InputStreamReader reader = new InputStreamReader (in);
        try
        {
            ParserDelegator delegator = new ParserDelegator ();
            ParserCallback pc = new ParserCallback ()
            {
                @Override
                public void handleSimpleTag (Tag t, MutableAttributeSet a, int pos)
                {
                    if ( t.equals (Tag.META) )
                    {
                        Object name = a.getAttribute (HTML.Attribute.NAME);
                        Object content = a.getAttribute (HTML.Attribute.CONTENT);
                        if ( name != null && content != null )
                        {
                            metas.put (name.toString (), content.toString ());
                        }
                    }
                }
            };
            delegator.parse (reader, pc, true);
            return metas;
        }
        finally
        {
            reader.close ();
        }
    }

    public static boolean isHtml (String html)
    {
        return html != null
            ? html.toLowerCase ().matches (HTML_PATTERN)
                || (html.indexOf ("<br") >= 0)
                || (html.indexOf ("</div>") >= 0)
                || (html.indexOf ("</p>") >= 0)
            : false;
    }
    
    public static String cleanup (String html)
    {
        if (html != null)
        {
            // Remove all comments
            html = html.replaceAll("(?s)<!--.*?-->", "");            
            // Remove all styles
            
        }
        return html;        
    }
}
