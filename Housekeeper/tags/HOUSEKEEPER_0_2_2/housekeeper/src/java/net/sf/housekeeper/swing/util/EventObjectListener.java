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

package net.sf.housekeeper.swing.util;

import java.util.EventListener;
import java.util.EventObject;


/**
 * Generic EventListener which can handle basic {@link java.util.EventObject}s.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public interface EventObjectListener extends EventListener
{
    
    /**
     * Gets called if an event happend.
     * 
     * @param event The event that happened.
     */
    public void handleEvent(EventObject event);
}
