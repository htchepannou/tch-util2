/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;

import junit.framework.TestCase;

/**
 *
 * @author tcheer
 */
public class CommandLineTest
    extends TestCase
{
    public CommandLineTest (String testName)
    {
        super (testName);
    }

    public void testExec ()
        throws Exception
    {
        System.out.println ("exec");
        String cmd = "ls " + System.getProperty ("java.io.tmpdir");
        CommandLine instance = new CommandLine (cmd);
        int result = instance.exec ();
        assertEquals ("result", 0, result);
    }
}
