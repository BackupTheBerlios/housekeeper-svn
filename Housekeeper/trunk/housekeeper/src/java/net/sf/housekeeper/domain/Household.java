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

import org.springframework.core.ToStringCreator;

/**
 * A container for a Housekeeper domain.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class Household
{

    private final ItemManager     supplyManager;

    private final CategoryManager categoryManager;

    /**
     * The version to be used in the XML file.
     */
    public final int              version = 4;

    /**
     * Creates a new domain with no data.
     */
    public Household()
    {
        this(new ItemManager(), new CategoryManager());
    }

    /**
     * Creates a new domain using an existing {@link ItemManager}.
     * 
     * @param supplyManager The manager for food objects. Must not be null.
     * @param categoryManager The manager for categories. Must not be null.
     */
    public Household(final ItemManager supplyManager,
            final CategoryManager categoryManager)
    {
        this.supplyManager = supplyManager;
        this.categoryManager = categoryManager;

    }

    /**
     * Returns the manager for food objects in this domain.
     * 
     * @return The manager. Is not null.
     */
    public ItemManager getSupplyManager()
    {
        return supplyManager;
    }

    /**
     * Replaces the domain objects of this Household with the ones of another
     * Household.
     * 
     * @param domain The domain to get the objects from. Must not be null.
     */
    public void replaceAll(final Household domain)
    {
        supplyManager.replaceAll(domain.getSupplyManager().getAllItems());
        categoryManager.replaceAll(domain.getCategoryManager()
                .getTopLevelCategories());
    }

    /**
     * Returns the category manager.
     * 
     * @return the category manager.
     */
    public CategoryManager getCategoryManager()
    {
        return categoryManager;
    }

    /**
     * Returns true if any data has been changed since the last call to
     * {@link #resetChangedStatus()}.
     * 
     * @return True if any dat has changed, false otherwise.
     */
    public boolean hasChanged()
    {
        final boolean hasChanged = supplyManager.hasChanged()
                || categoryManager.hasChanged();
        return hasChanged;
    }

    /**
     * Resets the status of the "has changed" attribute. After calling this,
     * {@link #hasChanged()}returns false.
     */
    public void resetChangedStatus()
    {
        supplyManager.resetChangedStatus();
        categoryManager.resetChangedStatus();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringCreator(this).toString();
    }
}
