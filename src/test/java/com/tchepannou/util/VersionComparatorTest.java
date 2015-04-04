/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util;

import java.util.Comparator;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class VersionComparatorTest extends TestCase {
    
    public VersionComparatorTest(String testName) {
        super(testName);
    }


    public void testCompare_1()
    {
        Comparator c = new VersionComparator();
        String v1 = "1.1";
        String v2 = "1.1";
        assertTrue (v1 + " vs" + v2, c.compare(v1, v2) == 0);
    }


    public void testCompare_2()
    {
        Comparator c = new VersionComparator();
        String v1 = "3.1";
        String v2 = "10.1";
        assertTrue (v1 + " vs " + v2, c.compare(v1, v2) < 0);
    }


    public void testCompare_3()
    {
        Comparator c = new VersionComparator();
        String v1 = "1.1";
        String v2 = "1.1.1";
        assertTrue (v1 + " vs" + v2, c.compare(v1, v2) < 0);
    }
}
