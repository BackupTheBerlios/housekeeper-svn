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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * Convenience super class for classes which publish
 * {@link net.sf.housekeeper.event.HousekeeperEvent}s.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public abstract class HousekeeperEventPublisher implements
        ApplicationContextAware
{

    private ApplicationContext  applicationContext;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public final void setApplicationContext(ApplicationContext arg0)
            throws BeansException
    {
        this.applicationContext = arg0;
    }

    /**
     * Creates and publishes a new {@link HousekeeperEvent}.
     * 
     * @param eventType The type of the event. != null
     * @param source The source fo the event. != null
     */
    protected final void publishEvent(String eventType, Object source)
    {
        Assert.notNull(applicationContext);
        Assert.notNull(eventType);
        Assert.notNull(source);

        HousekeeperEvent event = new HousekeeperEvent(eventType, source);
        applicationContext.publishEvent(event);
    }

}
