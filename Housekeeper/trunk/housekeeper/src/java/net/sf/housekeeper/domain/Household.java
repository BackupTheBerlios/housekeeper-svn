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

/**
 * A container for a Housekeeper domain. It consists of references to manager
 * objects of domain objects.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class Household
{

    private final FoodManager food;

    /**
     * Creates a new domain with no data.
     */
    public Household()
    {
        this(new FoodManager());
    }

    /**
     * Creates a new domain using an existing {@link FoodManager}.
     * 
     * @param foodManager The manager which holds the domain data. Must not be
     *            null.
     */
    public Household(final FoodManager foodManager)
    {
        this.food = foodManager;
    }

    /**
     * Returns the manager which allows access and manipulation of
     * {@link Food} objects in this domain.
     * 
     * @return The manager. Is not null.
     */
    public FoodManager getFoodManager()
    {
        return food;
    }

    /**
     * Replaces the domain objects of this Household with the ones of another
     * Household.
     * 
     * @param domain The domain to get the objects from. Must not be null.
     */
    public void replaceAll(final Household domain)
    {
        food.replaceAll(domain.getFoodManager().getSupplyList());
    }
    
    /**
     * Returns true if any data has been changed since the last call to
     * {@link #resetChangedStatus()}.
     * 
     * @return True if any dat has changed, false otherwise.
     */
    public boolean hasChanged()
    {
        return food.hasChanged();
    }
    
    /**
     * Resets the status of the "has changed" attribute. After calling this,
     * {@link #hasChanged()}returns false.
     */
    public void resetChangedStatus()
    {
        food.resetChangedStatus();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != getClass()) {
            return false;
        }
        Household castedObj = (Household) o;
        return ((this.food == null
            ? castedObj.food == null
            : this.food.equals(castedObj.food)));
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31
            * hashCode
            + (food == null ? 0 : food.hashCode());
        return hashCode;
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[Household:");
        buffer.append(" food: ");
        buffer.append(food);
        buffer.append("]");
        return buffer.toString();
    }
}