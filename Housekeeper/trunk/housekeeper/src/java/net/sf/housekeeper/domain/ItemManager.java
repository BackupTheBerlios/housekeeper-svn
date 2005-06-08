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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.housekeeper.event.HousekeeperEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Holds a list of items and provides operations to add, delete and change an
 * {@link Item}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ItemManager extends AbstractManager
{

    /**
     * Log to be used for this class.
     */
    private static final Log LOG = LogFactory.getLog(ItemManager.class);

    /**
     * Holds a list of all managed items.
     */
    private final ArrayList  items;

    /**
     * Creates a new manager with no entries. Afterwards, {@link #hasChanged()}
     * returns <code>false</code>.
     */
    public ItemManager()
    {
        this.items = new ArrayList();
        resetChangedStatus();
    }

    /**
     * Adds an object to the list. After execution,
     * {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param item the item to add to the supply.
     */
    public void add(final Item item)
    {
        items.add(item);
        setChanged();

        if (LOG.isDebugEnabled())
        {
            LOG.debug("Added: " + item);
        }

        publishEvent(HousekeeperEvent.ADDED, item);
    }

    /**
     * Duplicates the provided item and adds it to the to the list. Afterwards,
     * {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param item The item to be duplicated.
     */
    public void duplicate(final ExpirableItem item)
    {
        final ExpirableItem clonedItem = new ExpirableItem(item);
        add(clonedItem);
    }
    
    /**
     * Duplicates the provided item and adds it to the to the list. Afterwards,
     * {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param item The item to be duplicated.
     */
    public void duplicate(final Item item)
    {
        final Item clonedItem = new Item(item);
        add(clonedItem);
    }

    /**
     * Provides an iterator for all managed items.
     * 
     * @return an iterator for all managed items. Is not null.
     */
    public Iterator iterator()
    {
        return items.iterator();
    }

    /**
     * Returns the items on hold. You cannot modify the returned list object
     * directly. Use the methods of this manager instead.
     * 
     * @return the supply.
     */
    public List getAllItems()
    {
        return Collections.unmodifiableList(items);
    }

    /**
     * Returns all items of a specific category. If category is null, then all
     * items are returned.
     * 
     * @param category The category of the items.
     * @return The items which match the category.
     */
    public List getItemsForCategory(final Category category)
    {
        if (category == null)
        {
            return getAllItems();
        }

        final List categoryItems = new ArrayList();
        final Iterator allItemsIter = items.iterator();
        while (allItemsIter.hasNext())
        {
            final Item item = (Item) allItemsIter.next();
            final boolean isOfCategory = category.contains(item.getCategory());
            if (isOfCategory)
            {
                categoryItems.add(item);
            }
        }
        return categoryItems;
    }

    /**
     * Removes an object from the supply. Afterwards,
     * {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param item the item to remove from the supply.
     */
    public void remove(final Item item)
    {
        items.remove(item);
        setChanged();

        if (LOG.isDebugEnabled())
        {
            LOG.debug("Removed: " + item);
        }

        publishEvent(HousekeeperEvent.REMOVED, item);
    }

    /**
     * Clears the list of items and replaces it with a new one. Afterwards,
     * {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param newItems The new items. Must not be null.
     */
    public void replaceAll(final Collection newItems)
    {
        items.clear();
        items.addAll(newItems);
        setChanged();

        LOG.debug("Replaced all items");
    }

    /**
     * Updates an item in this manager. If you change a object you
     * MUST call this method so Observers can be notified of the update.
     * Afterwards, {@link #hasChanged()}returns <code>true</code>.
     * 
     * @param item The item which should be updated.
     */
    public void update(final Item item)
    {
        setChanged();

        if (LOG.isDebugEnabled())
        {
            LOG.debug("Updated food: " + item);
        }

        publishEvent(HousekeeperEvent.MODIFIED, item);
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
        ItemManager castedObj = (ItemManager) o;
        return this.items == null ? castedObj.items == null : this.items
                .equals(castedObj.items);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        int hashCode = 1;
        hashCode = 31 * hashCode + (items == null ? 0 : items.hashCode());
        return hashCode;
    }

}
