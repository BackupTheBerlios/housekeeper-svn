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
public final class FoodItemManager
{

    /**
     * Log to be used for this class.
     */
    private static final Log LOG = LogFactory.getLog(FoodItemManager.class);

    /**
     * Flag indicating if any data has been changed.
     */
    private boolean          hasChanged;

    /**
     * Holds a list of all items on hand.
     */
    private final EventList  supply;

    /**
     * Creates a new manager with no entries. Afterwards, {@link #hasChanged()}
     * returns <code>false</code>.
     */
    public FoodItemManager()
    {
        this(new ArrayList());
    }

    /**
     * Creates a new manager with a collection of items.Afterwards,
     * {@link #hasChanged()}returns <code>false</code>.
     * 
     * @param items A collection of {@link FoodItem}s. The type of the elements
     *            the collection are NOT checked by this method.
     */
    public FoodItemManager(final Collection items)
    {

        this(new ArrayList(items));
    }

    /**
     * Creates a new manager with a list of items.Afterwards,
     * {@link #hasChanged()}returns <code>false</code>.
     * 
     * @param items A list of {@link FoodItem}s. The type of the elements the
     *            collection are NOT checked by this method.
     */
    public FoodItemManager(final List items)
    {
        //TODO Check if the elements of the list are all FoodItems.
        supply = new BasicEventList(items);
        resetChangedStatus();
    }

    /**
     * Adds a FoodItem to the supply and notifies observers of the supply
     * {@link javax.swing.ListModel}about the change. Afterwards,
     * {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param item the item to add to the supply.
     */
    public void add(final FoodItem item)
    {
        supply.add(item);
        hasChanged = true;

        LOG.debug("Added item: " + item);
    }

    /**
     * Duplicates the provided item and adds it to the supply. Afterwards,
     * {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param item The item to be duplicated.
     */
    public void duplicate(final FoodItem item)
    {
        final FoodItem clonedItem = (FoodItem) item.clone();
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
        return supply.iterator();
    }

    /**
     * Returns the items on hold. You must NOT modify the returned list object
     * directly. Use the methods of this manager instead.
     * 
     * @return the supply.
     */
    public EventList getSupplyList()
    {
        return supply;
    }

    /**
     * Removes a FoodItem from the supply. Afterwards, {@link #hasChanged()}
     * returns <code>true</code>.
     * 
     * @param item the item to remove from the supply.
     */
    public void remove(final FoodItem item)
    {
        supply.remove(item);
        hasChanged = true;

        LOG.debug("Removed item: " + item);
    }

    /**
     * Clears the list of all food items on hand and replaces it with a new one.
     * Afterwards, {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param foodItems A collection of food items.
     */
    public void replaceAll(final Collection foodItems)
    {
        supply.clear();
        supply.addAll(foodItems);
        hasChanged = true;

        LOG.debug("Replaced all items");
    }

    /**
     * Updates an item in this manager. Afterwards, {@link #hasChanged()}
     * returns <code>true</code>.
     * 
     * @param item The item which should be updated.
     */
    public void update(final FoodItem item)
    {
        final int index = supply.indexOf(item);
        supply.set(index, item);
        hasChanged = true;

        LOG.debug("Updated item: " + item);
    }

    /**
     * Resets the status of the "has changed" attribute. After calling this,
     * {@link #hasChanged()}returns false.
     */
    void resetChangedStatus()
    {
        hasChanged = false;
    }

    /**
     * Returns true if any data has been changed since the last call to
     * {@link #resetChangedStatus()}.
     * 
     * @return True if any dat has changed, false otherwise.
     */
    boolean hasChanged()
    {
        return hasChanged;
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
        FoodItemManager castedObj = (FoodItemManager) o;
        return this.supply == null
            ? castedObj.supply == null
            : this.supply.equals(castedObj.supply);
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (supply == null ? 0 : supply.hashCode());
        return hashCode;
    }
}