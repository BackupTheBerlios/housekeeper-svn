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

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;

/**
 * Holds a list of items and provides operations to add, delete and change a
 * Book.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class FoodManager
{

    /**
     * Log to be used for this class.
     */
    private static final Log LOG = LogFactory.getLog(FoodManager.class);

    /**
     * Flag indicating if any data has been changed.
     */
    private boolean          hasChanged;

    /**
     * Holds a list of all items on hand.
     */
    private final EventList  foodList;

    /**
     * Creates a new manager with no entries. Afterwards, {@link #hasChanged()}
     * returns <code>false</code>.
     */
    public FoodManager()
    {
        this(new ArrayList());
    }

    /**
     * Creates a new manager with an existing list of food. Afterwards,
     * {@link #hasChanged()}returns <code>false</code>.
     * 
     * @param items A list of {@link Food}s. The type of the elements the
     *            collection are NOT checked by this method.
     */
    public FoodManager(final List items)
    {
        //TODO Check if the elements of the list are all FoodItems.
        foodList = new BasicEventList(items);
        resetChangedStatus();
    }

    /**
     * Adds a Food object to the list and notifies observers of the supply
     * {@link javax.swing.ListModel}about the change. Afterwards,
     * {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param item the item to add to the supply.
     */
    public void add(final Food item)
    {
        foodList.add(item);
        hasChanged = true;

        LOG.debug("Added convenience food: " + item);
    }

    /**
     * Duplicates the provided item and adds it to the to the list. Afterwards,
     * {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param item The item to be duplicated.
     */
    public void duplicate(final Food item)
    {
        final Food clonedItem = new Food(item);
        add(clonedItem);
        hasChanged = true;
    }

    /**
     * Provides an iterator for the list of items on hand.
     * 
     * @return an iterator for the list of items on hand.
     */
    public Iterator getItemsIterator()
    {
        return foodList.iterator();
    }

    /**
     * Returns the items on hold. You must NOT modify the returned list object
     * directly. Use the methods of this manager instead.
     * 
     * @return the supply.
     */
    public EventList getSupplyList()
    {
        return foodList;
    }

    /**
     * Removes a Food object from the supply. Afterwards, {@link #hasChanged()}
     * returns <code>true</code>.
     * 
     * @param item the item to remove from the supply.
     */
    public void remove(final Food item)
    {
        foodList.remove(item);
        hasChanged = true;

        LOG.debug("Removed food: " + item);
    }

    /**
     * Clears the list of all food on hand and replaces it with a new one.
     * Afterwards, {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param food A collection of food.
     */
    public void replaceAll(final Collection food)
    {
        foodList.clear();
        foodList.addAll(food);
        hasChanged = true;

        LOG.debug("Replaced all food objects");
    }

    /**
     * Updates an item in this manager. If you change a {@link Food}object you
     * MUST call this method so Observers can be notified of the update.
     * Afterwards, {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param item The item which should be updated.
     */
    public void update(final Food item)
    {
        final int index = foodList.indexOf(item);
        foodList.set(index, item);
        hasChanged = true;

        LOG.debug("Updated food: " + item);
    }

    /**
     * Resets the status of the "has changed" attribute. After calling this,
     * {@link #hasChanged()}returns false.
     */
    public void resetChangedStatus()
    {
        hasChanged = false;
    }

    /**
     * Returns true if any data has been changed since the last call to
     * {@link #resetChangedStatus()}.
     * 
     * @return True if any dat has changed, false otherwise.
     */
    public boolean hasChanged()
    {
        return hasChanged;
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
        FoodManager castedObj = (FoodManager) o;
        return this.foodList == null ? castedObj.foodList == null
                : this.foodList.equals(castedObj.foodList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        int hashCode = 1;
        hashCode = 31 * hashCode + (foodList == null ? 0 : foodList.hashCode());
        return hashCode;
    }
}