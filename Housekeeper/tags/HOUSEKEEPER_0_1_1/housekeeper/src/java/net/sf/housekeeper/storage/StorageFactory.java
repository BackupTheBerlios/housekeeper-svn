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

package net.sf.housekeeper.storage;

/**
 * A Factory for centralized management of Storage techniques. Objects should
 * call {@link #getCurrentStorage()} to receive a reference to the currently
 * used storage technique.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class StorageFactory
{

    /** An instance of the currently used storage technique. */
    private static Storage currentStorage = XMLSerializationStorage.INSTANCE;

    /**
     * Prevents object instanciation.
     */
    private StorageFactory()
    {

    }

    /**
     * Returns a reference to the currently used storage technique.
     *
     * @return Currently used storage technique.
     */
    public static Storage getCurrentStorage()
    {
        return currentStorage;
    }
}
