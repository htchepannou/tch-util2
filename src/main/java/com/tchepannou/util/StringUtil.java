/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;

import java.net.URL;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String helper methods
 * 
 * @author herve
 */
public class StringUtil
{
    //-- Static attributes -------------
    public static final String EMPTY = "";
    private static final Pattern EMAIL_PATTERN = Pattern.compile ("^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
    private static final String PASSWORD_CHARS = "abcdefghijklmnopqretuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@$+-*_";
    private static final List<String> ISO_COUNTRIES = Arrays.asList(Locale.getISOCountries());
    
    //-- Static methods  ---------------
    /**
     * Repeat a string nth time
     * @param str String to repeat
     * @param count Number of time to repeat
     * @return Repeated string or empty string if <code>count=0</code> or <code>str</code> is <code>null</code>
     */
    public static String repeat (String str, int count)
    {
        if ( str == null )
        {
            return EMPTY;
        }

        StringBuilder buff = new StringBuilder ();
        for ( int i = 0; i < count; i++ )
        {
            buff.append (str);
        }
        return buff.toString ();
    }

    /**
     * Merge a collection of objects into one string
     *
     * @param col collection to merge
     * @param separator separator string
     */
    public static String merge (Collection col, String separator)
    {
        return merge (col.toArray (), separator);
    }

    /**
     * Merge a set of objects into one string
     *
     * @param tokens objects to merge
     * @param separator separator string
     */
    public static String merge (Object[] tokens, String separator)
    {
        if ( tokens == null )
        {
            return null;
        }
        String[] array = new String[ tokens.length ];
        for ( int i = 0; i < tokens.length; i++ )
        {
            Object token = tokens[i];
            array[i] = token != null
                ? token.toString ()
                : null;
        }
        return merge (array, separator);
    }

    /**
     * Merge a set of strings into one string
     * 
     * @param tokens Strings to merge
     * @param separator separator string
     */
    public static String merge (String[] tokens, String separator)
    {
        return merge (tokens, separator, false);
    }

    /**
     * Merge a set of long numeric values into one string
     *
     * @param tokens long numeric values to merge
     * @param separator separator string
     */
    public static String merge (long[] tokens, String separator)
    {
        if ( tokens == null )
        {
            return null;
        }
        String[] array = new String[ tokens.length ];
        for ( int i = 0; i < tokens.length; i++ )
        {
            array[i] = String.valueOf (tokens[i]);
        }
        return merge (array, separator);
    }

    /**
     * Merge a set of int numeric values into one string
     *
     * @param tokens long numeric values to merge
     * @param separator separator string
     */
    public static String merge (int[] tokens, String separator)
    {
        StringBuilder sb = new StringBuilder ();
        if ( tokens != null )
        {
            for ( int i = 0; i < tokens.length; i++ )
            {
                long str = tokens[i];
                if ( sb.length () > 0 )
                {
                    sb.append (separator);
                }
                sb.append (String.valueOf (str));
            }
        }
        return sb.toString ();
    }

    /**
     * Merge a set of strings into one string
     * 
     * @param tokens Strings to merge
     * @param separator separator string
     * @param capitalizeFirstChar if <code>true</code>, capitalize the first character of each token
     * 
     * @return merge string
     */
    public static String merge (String[] tokens, String separator, boolean capitalizeFirstChar)
    {
        StringBuilder sb = new StringBuilder ();
        Locale loc = Locale.getDefault ();
        if ( tokens != null )
        {
            for ( int i = 0; i < tokens.length; i++ )
            {
                String str = tokens[i];
                if ( !StringUtil.isEmpty (str) )
                {
                    if ( capitalizeFirstChar )
                    {
                        if ( str.length () > 1 )
                        {
                            str = str.substring (0, 1).
                                toUpperCase (loc) + str.substring (1);
                        }
                        else
                        {
                            str = str.toUpperCase (loc);
                        }
                    }
                    if ( sb.length () > 0 )
                    {
                        sb.append (separator);
                    }
                    sb.append (str);
                }
            }
        }
        return sb.toString ();
    }

    /**
     * Returns the string representation of an object
     */
    public static String toString (Object o)
    {
        if ( o != null )
        {
            if ( o instanceof Object[] )
            {
                Object[] array = ( Object[] ) o;
                StringBuilder sb = new StringBuilder ();
                sb.append ("[");
                for ( int i = 0; i < array.length; i++ )
                {
                    if ( i > 0 )
                    {
                        sb.append (", ");
                    }
                    sb.append (toString (array[i]));
                }
                sb.append ("]");
                return sb.toString ();
            }
            else
            {
                String classname = o.getClass ().
                    getName ();
                if ( classname.startsWith ("java.") )
                {
                    return o.toString ();
                }
                else
                {
                    int i = classname.lastIndexOf (".");
                    String str = i >= 0
                        ? classname.substring (i + 1)
                        : classname;
                    return str + "{}";
                }
            }
        }
        else
        {
            return "null";
        }
    }
    
    public static String toString (Map map)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Object key : map.keySet())
        {
            Object value = map.get(key);
            builder.append('\n').append(key).append(" => ").append(value);
        }
        builder.append("}");
        return builder.toString();
    }


    /**
     * Append a string to a StringBuilder
     */
    public static StringBuilder append (StringBuilder sb, String toAppend)
    {
        if ( !isEmpty (toAppend) )
        {
            sb.append (toAppend);
        }
        return sb;
    }

    /**
     * Test is a string is empty
     */
    public static boolean isEmpty (String str)
    {
        return str == null || str.trim ().
            length () == 0;
    }

    /**
     * Test if an email is empty
     */
    public static boolean isEmail (String email)
    {
        if ( isEmpty (email) )
        {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher (email);
        return matcher.find ();
    }

    public static boolean isURL (String url){
        if (isEmpty(url)){
            return false;
        }

        try{
            new URL(url);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public static boolean isISOCountry (String iso){
        if (isEmpty(iso)){
            return false;
        }
        return ISO_COUNTRIES.contains(iso.toUpperCase());
    }

    /**
     * Convert a string to an integer, if the conversion fails it returns a default valud
     */
    public static byte toByte (String str, byte defaultValue)
    {
        return ( byte ) toDouble (str, defaultValue);
    }

    /**
     * Convert a string to an integer, if the conversion fails it returns a default valud
     */
    public static int toInt (String str, int defaultValue)
    {
        return ( int ) toDouble (str, defaultValue);
    }

    /**
     * Convert a string to an long, if the conversion fails it returns a default valud
     */
    public static long toLong (String str, long defaultValue)
    {
        return ( long ) toDouble (str, defaultValue);
    }

    /**
     * Convert a string to an float, if the conversion fails it returns a default valud
     */
    public static float toFloat (String str, float defaultValue)
    {
        return ( float ) toDouble (str, defaultValue);
    }

    /**
     * Convert a string to an double, if the conversion fails it returns a default valud
     */
    public static double toDouble (String str, double defaultValue)
    {
        if ( !StringUtil.isEmpty (str) )
        {
            try
            {
                return Double.valueOf (str);
            }
            catch ( NumberFormatException e )
            {
                return defaultValue;
            }
        }
        else
        {
            return defaultValue;
        }
    }

    /**
     * Replace a string inside a string
     *
     * @param original Original string
     * @param find String to find
     * @param replacement Replacement string
     *
     * @return string with replaced tokens
     */
    public static String replace (String original, String find, String replacement)
    {
        String result = original;
        while ( true )
        {
            int i = result.indexOf (find);
            if ( i < 0 )
            {
                return result;  // return original if 'find' is not in it.
            }

            String partBefore = result.substring (0, i);
            String partAfter = result.substring (i + find.length ());

            result = partBefore + replacement + partAfter;
        }
    }

    /**
     * Returns the nth first characters of a text
     *
     * @param txt Long text
     * @param maxlen Number of character to return
     * @param suffix Suffix to append to the chopped string (<code>Ex:</code>: ...)
     */
    public static String shortText (String txt, int maxlen, String suffix)
    {
        if ( txt == null )
        {
            return "";
        }
        else if ( txt.length () <= maxlen )
        {
            return txt;
        }
        else
        {
            return txt.substring (0, maxlen) + suffix;
        }
    }

    public static String generatePassword (int length)
    {
        int max = PASSWORD_CHARS.length ();
        StringBuilder buff = new StringBuilder ();
        Random rand = new Random (System.currentTimeMillis ());

        for ( int i = 0; i < length; i++ )
        {
            int index = rand.nextInt (max);
            buff.append (PASSWORD_CHARS.charAt (index));
        }

        return buff.toString ();
    }

    public static String[] trim (String[] values)
    {
        String[] xvalues = new String[ values.length ];
        for ( int i = 0; i < values.length; i++ )
        {
            String value = values[i];
            xvalues[i] = trim (value);
        }
        return xvalues;
    }

    public static String trim (String value)
    {
        return value != null
            ? value.trim ()
            : null;
    }

    /**
     * Convert accent letter (latin character) to their un-accent equivalend.
     * see http://www.rgagnon.com/javadetails/java-0456.html
     * 
     * @param str String with accent
     * @return string without accent
     */
    public static String unaccent (String str)
    {
        String nfdNormalizedString = Normalizer.normalize (str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile ("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher (nfdNormalizedString).replaceAll ("");
    }
    
    
    /**
     * Returns <code>true</code> if a string is HTML
     */
    public static boolean isHtml(String html)
    {
        return HTMLUtil.isHtml (html);
    }

}
