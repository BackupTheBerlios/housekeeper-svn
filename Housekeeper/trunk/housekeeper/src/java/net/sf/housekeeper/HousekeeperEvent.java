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
 * http://housekeeper.sourceforge.net
 */

package net.sf.housekeeper;

import org.springframework.context.ApplicationEvent;
import org.springframework.util.ToStringCreator;


/**
 * @author
 * @version $Revision$, $Date$
 */
public class HousekeeperEvent extends ApplicationEvent
{
    
    private final String eventType;
    
    /**
     * Event type if something gets selected by the user.
     */
    public static final String CATEGORY_SELECTED = "housekeeperEvent.selected";
    
    /**
     * Event type if items in supply has been changed, and all views
     * should be refreshed.
     */
    public static final String SUPPLY_MODIFIED = "houskeeperEvent.supplyModified";

    /**
     * Event type if something unspecified has been changed, and all views
     * should be refreshed.
     */
    public static final String CATEGORIES_MODIFIED = "houskeeperEvent.categoriesModified";
    
    /**
     * Creates a new event.
     * 
     * @param eventType The type of this event.
     * @param source The source of this event.
     */
    public HousekeeperEvent(String eventType, Object source) {
        super(source);
        this.eventType = eventType;
    }

    /**
     * Returns the affected object.
     * 
     * @return The object.
     */
    public Object getObject() {
        return getSource();
    }

    /**
     * Returns true if the object os of the specified class.
     * 
     * @param clazz The class to test against.
     * @return True, if the class matches, false otherwise.
     */
    public boolean objectIs(Class clazz) {
        if (getSource().getClass().isAssignableFrom(clazz)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns the event type.
     * 
     * @return The event type.
     */
    public String getEventType() {
        return eventType;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringCreator(this).appendProperties().toString();
    }
    
}
