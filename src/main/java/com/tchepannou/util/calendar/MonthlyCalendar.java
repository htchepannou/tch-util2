/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util.calendar;

import com.tchepannou.util.DateUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class represents a monthly calendar
 *
 * @author herve
 */
public class MonthlyCalendar
{
    //-- Attributes
    private int _year;
    private int _month;
    private int _day;
    private List<? extends CalendarEvent> _events = new ArrayList<CalendarEvent> ();


    //-- Getter/Setter
    /**
     * Returns the first day of the week
     */
    public int getFirstDayOfWeek ()
    {
        Calendar cal = Calendar.getInstance ();
        cal.set (Calendar.YEAR, _year);
        cal.set (Calendar.MONTH, _month);
        cal.set (Calendar.DAY_OF_MONTH, 1);

        return cal.get (Calendar.DAY_OF_WEEK);
    }

    /**
     * Returns the last day of the month
     */
    public int getLastDayOfMonth ()
    {
        Calendar cal = Calendar.getInstance ();
        
        cal.setTime (DateUtil.createDate (_year, _month, 1));
        cal.add (Calendar.MONTH, 1);
        cal.add (Calendar.DAY_OF_MONTH, -1);

        return cal.get (Calendar.DAY_OF_MONTH);
    }

    /**
     * Check if there are event of a given type at a given day of the month
     *
     * @param day day of the month
     * @param type type of event
     *
     * @return <code>true</code> if events found
     */
    public boolean has (int day, String type)
    {
        if (_events == null)
        {
            return false;
        }

        for (CalendarEvent evt : _events)
        {
            if (type.equals (evt.getType ()))
            {
                Calendar cal = Calendar.getInstance ();
                cal.setTime (evt.getDate ());
                if (day == cal.get (Calendar.DAY_OF_MONTH))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * If there are any event at a given day of the month
     *
     * @param day Day of the month
     *
     * @return <code>true</code> if any event found
     */
    public boolean has (int day)
    {
        if (_events == null)
        {
            return false;
        }

        for (CalendarEvent evt : _events)
        {
            Calendar cal = Calendar.getInstance ();
            cal.setTime (evt.getDate ());
            if (day == cal.get (Calendar.DAY_OF_MONTH))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value of the next month.
     * If the current month is <code>December</code>, the next month will be <code>January</code>
     */
    public int getNextMonth ()
    {
        Date date = DateUtil.createDate (_year, _month, 1);
        Calendar cal = Calendar.getInstance ();
        cal.setTime (date);
        cal.add (Calendar.MONTH, 1);
        return cal.get (Calendar.MONTH);
    }

    /**
     * Returns the value of the previous month.
     * If the current month is <code>January</code>, the previous month will be <code>December</code>
     */
    public int getPreviousMonth ()
    {
        Date date = DateUtil.createDate (_year, _month, 1);
        Calendar cal = Calendar.getInstance ();
        cal.setTime (date);
        cal.add (Calendar.MONTH, -1);
        return cal.get (Calendar.MONTH);
    }

    /**
     * Return the year of the next month
     */
    public int getNextMonthYear ()
    {
        int month = getNextMonth ();
        return month == Calendar.JANUARY ? _year+1 : _year;
    }

    /**
     * Return the year of the previous month
     */
    public int getPreviousMonthYear ()
    {
        int month = getPreviousMonth ();
        return month == Calendar.DECEMBER ? _year-1 : _year;
    }

    public int getYear ()
    {
        return _year;
    }

    public void setYear (int year)
    {
        this._year = year;
    }

    public int getMonth ()
    {
        return _month;
    }

    public void setMonth (int month)
    {
        this._month = month;
    }

    public int getDay ()
    {
        return _day;
    }

    public void setDay (int day)
    {
        this._day = day;
    }

    public List<? extends CalendarEvent> getEvents (int day)
    {
        Calendar cal = Calendar.getInstance ();
        List<CalendarEvent> result = new ArrayList<CalendarEvent> ();
        for (CalendarEvent ev : _events)
        {
            cal.setTime (ev.getDate ());
            if (cal.get (Calendar.DAY_OF_MONTH) == day)
            {
                result.add (ev);
            }
        }
        return result;
    }
    public List<? extends CalendarEvent> getEvents ()
    {
        return _events;
    }

    public void setEvents (List<? extends CalendarEvent> events)
    {
        this._events = events;
    }


}
