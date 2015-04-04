
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author herve
 */
public class DateUtil
{
    public static Date today ()
    {
        Calendar cal = Calendar.getInstance ();
        int year = cal.get (Calendar.YEAR);
        int month = cal.get (Calendar.MONTH);
        int day = cal.get (Calendar.DAY_OF_MONTH);
        return DateUtil.createDate (year, month, day);
    }

    public static Date tomorrow ()
    {
        return dayAfter (today ());
    }

    public static Date yesterday ()
    {
        return dayBefore (today ());
    }

    public static Date dayBefore (Date date)
    {
        return addDays(date, -1);
    }

    public static Date dayAfter (Date date)
    {
        return addDays(date, 1);
    }

    public static Date addDays (Date date, int amount)
    {
        Calendar cal = Calendar.getInstance ();
        cal.setTime (date);
        cal.add (Calendar.DAY_OF_MONTH, amount);
        return cal.getTime ();
    }

    public static Date addMonths (Date date, int amount)
    {
        Calendar cal = Calendar.getInstance ();
        cal.setTime (date);
        cal.add (Calendar.MONTH, amount);
        return cal.getTime ();
    }

    public static Date addYears (Date date, int amount)
    {
        Calendar cal = Calendar.getInstance ();
        cal.setTime (date);
        cal.add (Calendar.YEAR, amount);
        return cal.getTime ();
    }

    public static Date createDate (int year, int month, int day)
    {
        return createDate (year, month, day, 0, 0, 0);
    }

    public static Date createDate (int year, int month, int day, int hour, int minute)
    {
        return createDate (year, month, day, hour, minute, 0);
    }

    public static Date createDate (int year, int month, int day, int hour, int minute, int second)
    {
        Calendar cal = Calendar.getInstance ();

        cal.set (Calendar.YEAR, year);
        cal.set (Calendar.MONTH, month);
        cal.set (Calendar.DAY_OF_MONTH, day);
        cal.set (Calendar.HOUR_OF_DAY, hour);
        cal.set (Calendar.MINUTE, minute);
        cal.set (Calendar.SECOND, second);
        cal.set (Calendar.MILLISECOND, 0);
        
        return cal.getTime ();
    }

    public static Date clone (Date date)
    {
        return (date != null)
            ? new Date (date.getTime ())
            : null;
    }
}
