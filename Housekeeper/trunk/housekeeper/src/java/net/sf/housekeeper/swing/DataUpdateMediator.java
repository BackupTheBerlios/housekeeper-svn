/*
 * This file is part of Housekeeper.
 *
 * Housekeeper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Housekeeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Housekeeper; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA  02111-1307  USA
 *
 * Copyright 2003-2004, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sf.housekeeper.swing;


import java.util.Observable;


/**
 * An Observable for notification of data updates. Objects which should be
 * notified if data in the storage changes, should add themselves via
 * addObserver(). If an object creates, modifies or deletes data in the
 * storage, this object's update() must be called to propagate it to all
 * Observers.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
public final class DataUpdateMediator extends Observable
{
    //~ Static fields/initializers ---------------------------------------------

    /** Singleton instance */
    private static final DataUpdateMediator INSTANCE = new DataUpdateMediator();

    //~ Constructors -----------------------------------------------------------

    private DataUpdateMediator()
    {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Returns the Singleton instance of this class.
     *
     * @return The Singleton instance.
     */
    public static DataUpdateMediator getInstance()
    {
        return INSTANCE;
    }

    /**
     * Notifies all registered Observers that data has changed.
     */
    public void update()
    {
        setChanged();
        notifyObservers();
    }
}
