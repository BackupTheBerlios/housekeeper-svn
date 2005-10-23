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

package net.sf.housekeeper.util;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Forwards {@link org.springframework.context.ApplicationEvent}s to objects
 * which want to react to them but which are not automatically registered as
 * listeners by the container. Particularly useful for
 * {@link org.springframework.richclient.command.ActionCommand}s.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ApplicationEventHelper implements ApplicationListener
{

    private final ArrayList<ApplicationListener> listeners = new ArrayList<ApplicationListener>(
            3);

    /**
     * Add a listener to forward events to.
     * 
     * @param listener != null
     */
    public void addListener(ApplicationListener listener)
    {
        listeners.add(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ApplicationEvent arg0)
    {
        final Iterator i = listeners.iterator();
        while (i.hasNext())
        {
            ApplicationListener element = (ApplicationListener) i.next();
            element.onApplicationEvent(arg0);
        }

    }

}
