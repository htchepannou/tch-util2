/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * This class allow to segment collection in a page/page fashion
 *
 * @author herve
 * @param <T> Object contained in the collection
 */
public class PagedCollection<T>
    implements Serializable,
               Iterable
{
    //-- Static Attribute
    private static final long serialVersionUID = 1L;
    //-- Attributes
    private int _page;
    private int _start;
    private int _end;
    private int _totalSize;
    private int _pageCount;
    private List _items = new ArrayList ();
    private Collection<T> _innerCollection = new ArrayList<T> ();

    //--Constructors
    /**
     * @param col   Collection to segment.
     * @param page  index of the current page (1 is the 1st page).
     * @param pageSize  Max items per page
     */
    public PagedCollection (Collection<T> col, int page, int pageSize)
    {
        if ( col.size () > 0 )
        {
            _totalSize = col.size ();
            _page = page;
            _start = ((page - 1) * pageSize) + 1;
            _end = Math.min (_start + pageSize - 1, _totalSize);
            _pageCount = ((_totalSize % pageSize) == 0)
                ? (_totalSize / pageSize)
                : ((_totalSize / pageSize) + 1);

            Iterator it = col.iterator ();
            for ( int i = 1; it.hasNext () && (i <= _end); i++ )
            {
                Object obj = it.next ();
                if ( i >= _start )
                {
                    _items.add (obj);
                }
            }
        }

        _innerCollection = col;
    }

    //-- Iterable override
    @Override
    public Iterator<T> iterator ()
    {
        return getItems ().iterator ();
    }

    //-- Object overrides
    @Override
    public String toString ()
    {
        return "[" + _page + "/" + _pageCount + " " + getItems () + "]";
    }

    //-- Getter/Setter
    public Object get(int i)
    {
        return _items.get (i);
    }
    
    public int getPage ()
    {
        return _page;
    }

    public Collection<T> getItems ()
    {
        return _items;
    }

    public int getPageCount ()
    {
        return _pageCount;
    }

    public int size ()
    {
        return _items.size ();
    }

    public int getEnd ()
    {
        return _end;
    }

    public int getStart ()
    {
        return _start;
    }

    public int getTotalSize ()
    {
        return _totalSize;
    }

    public boolean hasNext ()
    {
        return _page < _pageCount;
    }

    public boolean hasPrevious ()
    {
        return _page > 1;
    }

    public boolean contains (T obj)
    {
        return _innerCollection.contains (obj) || _items.contains (obj);
    }

    public Collection<T> getInnerCollection ()
    {
        List result = new ArrayList (_items);
        result.addAll (_innerCollection);
        return result;
    }
}
