/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util;

import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class ClassLoaderUtilTest 
    extends TestCase
{
    private boolean _dateUtil = false;
    private boolean _stringUtil = false;


    public ClassLoaderUtilTest(String testName) {
        super(testName);
    }

    public void testScanClasses ()
        throws Exception
    {
        ClassLoaderUtil.ClassVisitor visitor = new ClassLoaderUtil.ClassVisitor () {

            public void visit (Class clazz)
            {
                System.out.println ("Visiting " + clazz);

                if (DateUtil.class.equals (clazz))
                {
                    _dateUtil = true;
                }
                if (StringUtil.class.equals (clazz))
                {
                    _stringUtil = true;
                }
            }
        };
        ClassLoaderUtil.scanClasses ("com.tchepannou.util", visitor);

        assertTrue ("DateUtil", _dateUtil);
        assertTrue ("StringUtil", _stringUtil);
    }

}
