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

    private final FoodManager convenienceFoodManager;

    private final FoodManager miscFoodManager;

    /**
     * Creates a new domain with no data.
     */
    public Household()
    {
        this(new FoodManager(), new FoodManager());
    }

    /**
     * Creates a new domain using an existing {@link FoodManager}for
     * convenience foods.
     * 
     * @param convenienceFoodManager The manager for convenience foods. Must not
     *            be null.
     */
    public Household(final FoodManager convenienceFoodManager)
    {
        this(convenienceFoodManager, new FoodManager());
    }

    /**
     * Creates a new domain with existing {@link FoodManager}for both,
     * convenience foods and miscellaneous food.
     * 
     * @param convenienceFoodManager The manager for convenience foods. Must not
     *            be null.
     * @param miscFoodManager The manager for miscellaneous food. Must not be null.
     */
    public Household(final FoodManager convenienceFoodManager,
            final FoodManager miscFoodManager)
    {
        this.convenienceFoodManager = convenienceFoodManager;
        this.miscFoodManager = miscFoodManager;
    }

    /**
     * Returns the manager for convience food objects in this domain.
     * 
     * @return The manager. Is not null.
     */
    public FoodManager getConvenienceFoodManager()
    {
        return convenienceFoodManager;
    }
    
    /**
     * Returns the manager for miscellaneous food objects in this domain.
     * 
     * @return The manager. Is not null.
     */
    public FoodManager getMiscFoodManager()
    {
        return miscFoodManager;
    }

    /**
     * Replaces the domain objects of this Household with the ones of another
     * Household.
     * 
     * @param domain The domain to get the objects from. Must not be null.
     */
    public void replaceAll(final Household domain)
    {
        convenienceFoodManager.replaceAll(domain.getConvenienceFoodManager()
                .getSupplyList());
        miscFoodManager.replaceAll(domain.getMiscFoodManager().getSupplyList());
    }

    /**
     * Returns true if any data has been changed since the last call to
     * {@link #resetChangedStatus()}.
     * 
     * @return True if any dat has changed, false otherwise.
     */
    public boolean hasChanged()
    {
        final boolean hasChanged = convenienceFoodManager.hasChanged() || miscFoodManager.hasChanged();
        return hasChanged;
    }

    /**
     * Resets the status of the "has changed" attribute. After calling this,
     * {@link #hasChanged()}returns false.
     */
    public void resetChangedStatus()
    {
        convenienceFoodManager.resetChangedStatus();
        convenienceFoodManager.resetChangedStatus();
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
        final boolean isConvEqual = this.convenienceFoodManager == null ? castedObj.convenienceFoodManager == null
                : this.convenienceFoodManager
                .equals(castedObj.convenienceFoodManager);
        final boolean isMiscEqual = this.miscFoodManager == null ? castedObj.miscFoodManager == null
                : this.miscFoodManager
                .equals(castedObj.miscFoodManager);
        return isConvEqual && isMiscEqual;
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
                + (convenienceFoodManager == null ? 0 : convenienceFoodManager
                        .hashCode());
        hashCode = 31
        * hashCode
        + (miscFoodManager == null ? 0 : miscFoodManager
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
        StringBuffer buffer = new StringBuffer();
        buffer.append("[Household:");
        buffer.append(" Convenience Food: ");
        buffer.append(convenienceFoodManager);
        buffer.append(" Miscellaneous Food: ");
        buffer.append(miscFoodManager);
        buffer.append("]");
        return buffer.toString();
    }
}