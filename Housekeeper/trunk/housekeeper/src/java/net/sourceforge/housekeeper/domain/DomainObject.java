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
 * Copyright 2003, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper.domain;


/**
 * This is a Layer Supertype for all domain classes which provides ID handling.
 * All classes in the domain package have to subclass this one.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
class DomainObject
{
    //~ Instance fields --------------------------------------------------------

    /** The identification number of this object */
    private long id;
    
    /** Number of the next available free ID */
    private static long nextId = 0;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new DomainObject object.
     */
    protected DomainObject()
    {
        this.id = nextID();
    }

    /**
     * Creates a new DomainObject object with a predefined ID.
     *
     * @param id The identification number of the new object
     */
    protected DomainObject(long id)
    {
        this.id = id;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Sets the ID number of this object.
     *
     * @param id The identification number of this object
     */
    public void setID(long id)
    {
        this.id = id;
    }

    /**
     * Returns the ID number of this object.
     *
     * @return The identification number of this object
     */
    public long getID()
    {
        return id;
    }
    
    private long nextID()
    {
        return nextId++;
    }
}
