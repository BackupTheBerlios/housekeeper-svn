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
 * http://housekeeper.berlios.de
 */

package net.sf.housekeeper.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.housekeeper.event.HousekeeperEventPublisher;
import net.sf.housekeeper.event.HousekeeperEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A default implementation of {@link net.sf.housekeeper.domain.ItemManager}
 * which uses an in-memory collection for holding the objects.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ItemManagerImpl extends HousekeeperEventPublisher implements
        ItemManager
{

    private static final String LINE_SEPARATOR = System
                                                       .getProperty("line.separator");

    /**
     * Log to be used for this class.
     */
    private static final Log    LOG            = LogFactory
                                                       .getLog(ItemManagerImpl.class);

    /**
     * Holds a list of all managed items.
     */
    private final ArrayList     items;

    /**
     * Creates a new manager with no entries.
     */
    public ItemManagerImpl()
    {
        this.items = new ArrayList();
    }

    /**
     * Adds an object to the list.
     * 
     * @param item the item to add to the supply.
     */
    public void add(final Item item)
    {
        items.add(item);

        if (LOG.isDebugEnabled())
        {
            LOG.debug("Added: " + item);
        }

        publishEvent(HousekeeperEvent.ADDED, item);
    }

    /**
     * Adds a collection of items.
     * 
     * @param items The items to add.
     */
    public void addAll(final Collection items)
    {
        final Iterator iter = items.iterator();
        while (iter.hasNext())
        {
            Item element = (Item) iter.next();
            add(element);
        }
    }

    /**
     * Duplicates the provided item and adds it to the to the list.
     * 
     * @param item The item to be duplicated.
     */
    public void duplicate(final ExpirableItem item)
    {
        final Item clonedItem = new ExpirableItem(item);
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
     * Returns all items of a specific category and its sub-categories. If
     * category is null, then all items are returned.
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
     * Reassigns all items of a category and its subcategories to another
     * category.
     * 
     * @param oldCategory The current category.
     * @param newCategory The new category to assign the items to.
     */
    public void reassignToCategory(Category oldCategory, Category newCategory)
    {
        final Iterator iter = getItemsForCategory(oldCategory).iterator();
        while (iter.hasNext())
        {
            Item element = (Item) iter.next();
            element.setCategory(newCategory);
            update(element);
        }
    }

    /**
     * Removes an object from the supply.
     * 
     * @param item the item to remove from the supply.
     * @throws IllegalArgumentException if the item to be removed doesn't exist.
     */
    public void remove(final Item item) throws IllegalArgumentException
    {
        final boolean itemExisted = items.remove(item);
        if (itemExisted == false)
        {
            throw new IllegalArgumentException("Item doesn't exist: " + item);
        }

        if (LOG.isDebugEnabled())
        {
            LOG.debug("Removed: " + item);
        }

        publishEvent(HousekeeperEvent.REMOVED, item);
    }

    /**
     * Tests if an item exists.
     * 
     * @param item != null.
     * @return True, if the item exists, false otherwise.
     */
    public boolean exists(final Item item)
    {
        return items.contains(item);
    }

    /**
     * Clears the list of items and replaces it with a new one.
     * 
     * @param newItems The new items. Must not be null.
     */
    public void replaceAll(final Collection newItems)
    {
        items.clear();
        items.addAll(newItems);

        LOG.debug("Replaced all items");
    }

    /**
     * Updates an item in this manager. If you change an object you MUST call
     * this method so Observers can be notified of the update.
     * 
     * @param item The item which should be updated.
     */
    public void update(final Item item)
    {
        if (LOG.isDebugEnabled())
        {
            LOG.debug("Updated item: " + item);
        }

        publishEvent(HousekeeperEvent.MODIFIED, item);
    }

    /**
     * Returns all items in a textual representation.
     * 
     * @return != null
     */
    public String getItemsAsText()
    {
        final Iterator items = iterator();
        StringBuffer buffer = new StringBuffer();
        while (items.hasNext())
        {
            ShoppingListItem element = (ShoppingListItem) items.next();
            buffer.append(element.toString());
            buffer.append(LINE_SEPARATOR);
        }
        return buffer.toString();
    }

    /**
     * Tries to find an item with a given name.
     * 
     * @param name != null
     * @return The first item with the given name or null if none exists.
     */
    public Item findItemWithName(String name)
    {
        final Iterator iter = items.iterator();
        while (iter.hasNext())
        {
            Item element = (Item) iter.next();
            if (element.getName().equals(name))
            {
                return element;
            }
        }
        return null;
    }

}
