/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util;

import java.util.ArrayList;
import java.util.Iterator;
import junit.framework.TestCase;

public class PagedCollectionTest extends TestCase
{
    /**
     *
     */
    public PagedCollectionTest()
    {
        super();
    }

    /**
     * @param arg0
     */
    public PagedCollectionTest(String arg0)
    {
        super(arg0);
    }

    public void test2Pages()
    {
        ArrayList col = new ArrayList();
        for ( int i=1 ; i<=10 ; i++)
        {
            col.add( new Integer( i));
        }

        /* Page 1 */
        PagedCollection pcol = new PagedCollection( col, 1, 9 );
        assertEquals( "1. end", 9, pcol.getEnd() );
        assertEquals( "1. index", 1, pcol.getPage() );
        assertEquals( "1. pages", 2, pcol.getPageCount() );
        assertEquals( "1. start", 1, pcol.getStart() );
        assertEquals( "1. totalSize", 10, pcol.getTotalSize() );
        assertNotNull( "1. items", pcol.getItems() );
        assertEquals( "1. items.size", 9, pcol.getItems().size() );
        Iterator it = pcol.getItems().iterator();
        for ( int i=1 ; it.hasNext() ; i++ )
        {
            int j = i;
            assertEquals( "1. item[" + i + "] != " + j, new Integer(j), it.next() );
        }

        /* Page 2 */
        pcol = new PagedCollection( col, 2, 9 );
        assertEquals( "2. end", 10, pcol.getEnd() );
        assertEquals( "2. index", 2, pcol.getPage() );
        assertEquals( "2. pages", 2, pcol.getPageCount() );
        assertEquals( "2. start", 10, pcol.getStart() );
        assertEquals( "2. totalSize", 10, pcol.getTotalSize() );
        assertNotNull( "2. items", pcol.getItems() );
        assertEquals( "2. items.size", 1, pcol.getItems().size() );
        it = pcol.getItems().iterator();
        for ( int i=1 ; it.hasNext() ; i++ )
        {
            int j = i+9;
            assertEquals( "1. item[" + i + "] != " + j, new Integer(j), it.next() );
        }
    }
}
