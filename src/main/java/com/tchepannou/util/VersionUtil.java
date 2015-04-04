/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Utility that provide version information.
 * <p>
 * The version information are located in the resource <code>/version.properties</code> and it contains the following properties:
 * <ul>
 *      <li><code>version.number</code>: Version number. The format is <code>MAJOR.MINOR.RELEASE</code>
 *      <li><code>build.number</code>: Build number
 * </ul>
 * 
 * @author herve
 */
public class VersionUtil
{
    //-- Attribute
    private static final String RESOURCE_NAME = "version.properties";
    private static final String PROP_VERSION_NUMBER = "version.number";
    private static final String PROP_BUILD_NUMBER = "build.number";

    private static Properties __properties;
    
    //-- Public methods
    public static boolean isVersionNumber (String str)
    {
        if (str != null)
        {
            String[] tokens = str.split("\\.");
            return tokens.length > 1;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Returns the version number or return <code>null</code> if not found.
     */
    public static String getVersionNumber ()
    {
        return getProperty (PROP_VERSION_NUMBER);
    }
    
    public static String getBuildNumber ()
    {
        return getProperty (PROP_BUILD_NUMBER);
    }
        
    private static String getProperty (String property)
    {
        if (__properties == null)
        {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            InputStream in = cl.getResourceAsStream (RESOURCE_NAME);
            try
            {
                if (in != null)
                {
                    __properties = new Properties ();
                    __properties.load (in);
                }
                else
                {
                    __properties = new Properties();
                }
            }
            catch (IOException e)
            {
                throw new IllegalStateException ("Unable to load version resource: " + RESOURCE_NAME, e);
            }
            finally
            {
                if (in != null)
                {
                    try
                    {
                        in.close ();
                    }
                    catch (IOException e)
                    {
                        throw new IllegalStateException ("Unexpected error", e);
                    }
                }
            }
        }

        return __properties.getProperty (property);
    }
}
