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
    
    public static final String SELECTED = "housekeeperEvent.selected";

    public HousekeeperEvent(String eventType, Object source) {
        super(source);
        this.eventType = eventType;
    }

    public Object getObject() {
        return getSource();
    }

    public boolean objectIs(Class clazz) {
        if (getSource().getClass().isAssignableFrom(clazz)) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getEventType() {
        return eventType;
    }

    public String toString() {
        return new ToStringCreator(this).appendProperties().toString();
    }
    
}
