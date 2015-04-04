/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;

import java.io.File;
import java.io.IOException;

/**
 * Command line utility
 *
 * @author tcheer
 */
public class CommandLine
{
    //-- Static Attribues
    public static final int SUCCESS = 0;

    // Attribute
    private final String _command;
    private StreamGobbler _errorGobbler;
    private StreamGobbler _outputGobbler;

    //-- Constructor
    public CommandLine (final String command)
    {
        if ( command == null )
        {
            throw new IllegalArgumentException ("Command cannot be null");
        }
        this._command = command;
    }

    //-- Public methods
    public int exec ()
        throws IOException,
               InterruptedException
    {
        return exec (null);
    }

    public int exec (File workingDir)
        throws IOException, InterruptedException
    {
        // Execute the command
        Runtime rt = Runtime.getRuntime ();
        Process proc = rt.exec (_command, null, workingDir);

        // Get error/output
        _errorGobbler = new StreamGobbler (proc.getErrorStream (), "ERROR");
        _outputGobbler = new StreamGobbler (proc.getInputStream (), "OUTPUT");

        // kick them off
        _errorGobbler.start ();
        _outputGobbler.start ();

        // any error???
        int exitVal = proc.waitFor ();
        return exitVal;
    }


    public String getStdErr ()
    {
        return _errorGobbler.toString ();
    }

    public String getStdOut ()
    {
        return _outputGobbler.toString ();
    }

    public String getCommand ()
    {
        return _command;
    }
}
