/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.util.calendar;

import java.util.Date;

/**
 * This interface defines an event of the calendar
 * @author herve
 */
public interface CalendarEvent
{
    /**
     * Returns the type of event
     */
    public String getType ();

    /**
     * Returns the date of the event
     */
    public Date getDate ();
}
