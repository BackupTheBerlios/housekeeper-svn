/*
 * This file is part of Housekeeper.
 * 
 * Housekeeper is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Housekeeper is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Housekeeper; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Copyright 2003-2004, The Housekeeper Project
 * http://housekeeper.berlios.de
 */

package net.sf.housekeeper.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ToStringCreator;

/**
 * Events about domain object changes.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class HousekeeperEvent extends ApplicationEvent
{

    private static final long serialVersionUID = -6856493303326984655L;

    private final String eventType;

    /**
     * Event type if a category gets selected by the user.
     */
    public static final String SELECTED = "housekeeperEvent.SELECTED";

    /**
     * Event type if somehting has been added.
     */
    public static final String ADDED = "houskeeperEvent.ADDED";

    /**
     * Event type if something has been added.
     */
    public static final String REMOVED = "houskeeperEvent.REMOVED";

    /**
     * Event type if something has been modified.
     */
    public static final String MODIFIED = "housekeeperEvent.MODIFIED";

    /**
     * Event type if data has been replaced.
     */
    public static final String DATA_REPLACED = "houskeeperEvent.DATA_REPLACED";

    /**
     * Creates a new event.
     * 
     * @param eventType
     *            The type of this event.
     * @param source
     *            The source of this event != null.
     */
    public HousekeeperEvent(String eventType, Object source)
    {
        super(source);
        this.eventType = eventType;
    }

    /**
     * Returns true if the object os of the specified class.
     * 
     * @param clazz
     *            The class to test against.
     * @return True, if the class matches, false otherwise.
     */
    public boolean objectIs(Class clazz)
    {
        if (getSource().getClass().isAssignableFrom(clazz))
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * Returns true, if <code>type<code> is identical to this event's type.
     * 
     * @param type The type to test. 
     * @return true, if <code>type<code> is identical to this event's type.
     */
    public boolean isEventType(String type)
    {
        return type == eventType;
    }

    /**
     * Returns the type of this event.
     * 
     * @return the type.
     */
    public String getEventType()
    {
        return eventType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringCreator(this).toString();
    }

}
