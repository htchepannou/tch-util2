/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author herve
 */
public class ClassLoaderUtil
{
    //-- Public method
    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName   The base package
     * @param visitor       Visitor that will inspect each class found
     * 
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static void scanClasses (String packageName, ClassVisitor visitor)
        throws ClassNotFoundException,
               IOException
    {
        ClassLoader classLoader = Thread.currentThread ().getContextClassLoader ();
        assert classLoader != null;
        String path = packageName.replace ('.', '/');
        Enumeration<URL> resources = classLoader.getResources (path);
        List<File> dirs = new ArrayList<File> ();
        while ( resources.hasMoreElements () )
        {
            URL resource = resources.nextElement ();
            dirs.add (new File (resource.getFile ()));
        }
        for ( File directory: dirs )
        {
            scanClasses (directory, packageName, visitor);
        }
    }


    //-- Private methods
    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> scanClasses (File directory, String packageName, ClassVisitor visitor)
        throws ClassNotFoundException
    {
        List<Class> classes = new ArrayList<Class> ();
        if ( !directory.exists () )
        {
            return classes;
        }
        File[] files = directory.listFiles ();
        for ( File file: files )
        {
            if ( file.isDirectory () )
            {
                String xpackageName = packageName + "." + file.getName ();
                scanClasses (file, xpackageName, visitor);
            }
            else if ( file.getName ().endsWith (".class") )
            {
                String classname = packageName + '.' + file.getName ().substring (0, file.getName ().length () - 6);
                Class clazz = Class.forName (classname);
                visitor.visit (clazz);
            }
        }
        return classes;
    }

    //-- Inner classes
    /**
     * This is a visitor for classes
     */
    public static interface ClassVisitor
    {
        public void visit (Class clazz);
    }
}
