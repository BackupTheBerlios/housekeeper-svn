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

package net.sf.housekeeper.domain;

import org.springframework.util.ToStringCreator;

/**
 * A container for a Housekeeper domain.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class Household
{

    private final ItemManager foodManager;


    /**
     * Creates a new domain with no data.
     */
    public Household()
    {
        this(new ItemManager());
    }

    /**
     * Creates a new domain using an existing {@link ItemManager}.
     * 
     * @param manager The manager for food objects. Must not
     *            be null.
     */
    public Household(final ItemManager manager)
    {
        this.foodManager = manager;
    }

    /**
     * Returns the manager for food objects in this domain.
     * 
     * @return The manager. Is not null.
     */
    public ItemManager getFoodManager()
    {
        return foodManager;
    }

    /**
     * Replaces the domain objects of this Household with the ones of another
     * Household.
     * 
     * @param domain The domain to get the objects from. Must not be null.
     */
    public void replaceAll(final Household domain)
    {
        foodManager.replaceAll(domain.getFoodManager()
                .getAllItems());
    }

    /**
     * Returns true if any data has been changed since the last call to
     * {@link #resetChangedStatus()}.
     * 
     * @return True if any dat has changed, false otherwise.
     */
    public boolean hasChanged()
    {
        final boolean hasChanged = foodManager.hasChanged();
        return hasChanged;
    }

    /**
     * Resets the status of the "has changed" attribute. After calling this,
     * {@link #hasChanged()}returns false.
     */
    public void resetChangedStatus()
    {
        foodManager.resetChangedStatus();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null)
        {
            return false;
        }
        if (o.getClass() != getClass())
        {
            return false;
        }
        Household castedObj = (Household) o;
        final boolean isConvEqual = this.foodManager == null ? castedObj.foodManager == null
                : this.foodManager
                        .equals(castedObj.foodManager);
        return isConvEqual;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        int hashCode = 1;
        hashCode = 31
                * hashCode
                + (foodManager == null ? 0 : foodManager
                        .hashCode());
        return hashCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return ToStringCreator.propertiesToString(this);
    }
}