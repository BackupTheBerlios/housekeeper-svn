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

package net.sf.housekeeper.domain.inmemory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.Item;
import net.sf.housekeeper.domain.ItemDAO;
import net.sf.housekeeper.event.HousekeeperEvent;
import net.sf.housekeeper.event.HousekeeperEventPublisher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A default implementation of {@link net.sf.housekeeper.domain.ItemDAO}
 * which uses an in-memory collection for holding the objects.
 * 
 * @author Adrian Gygax
 * @version $Revision: 729 $, $Date: 2005-10-10 02:00:29 +0200 (Mo, 10 Okt 2005) $
 */
public final class InMemoryItemDAO<E extends Item> extends HousekeeperEventPublisher implements
        ItemDAO<E>
{

    private static final String LINE_SEPARATOR = System
            .getProperty("line.separator");

    /**
     * Log to be used for this class.
     */
    private static final Log LOG = LogFactory.getLog(InMemoryItemDAO.class);

    /**
     * Holds a list of all managed items.
     */
    private final ArrayList<E> items;

    /**
     * Creates a new manager with no entries.
     */
    public InMemoryItemDAO()
    {
        this.items = new ArrayList<E>();
    }


    public void deleteAll()
    {
        items.clear();
    }

    public List<E> findAll()
    {
        return new ArrayList<E>(items);
    }

    public List<E> findAllOfCategory(Category category)
    {
        if (category == null)
        {
            return findAll();
        }

        final ArrayList<E> categoryItems = new ArrayList<E>();
        for (E e : items)
        {
            final boolean isOfCategory = category.contains(e.getCategory());
            if (isOfCategory)
            {
                categoryItems.add(e);
            }
        }

        return categoryItems;
    }

    /**
     * Returns all items in a textual representation.
     * 
     * @return != null
     */
    public String getItemsAsText()
    {
        final StringBuffer buffer = new StringBuffer();
        for (Item item : items)
        {
            buffer.append(item.toString());
            buffer.append(LINE_SEPARATOR);
        }
        return buffer.toString();
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
        for (E item : findAllOfCategory(oldCategory))
        {
            item.setCategory(newCategory);
            store(item);
        }
    }

    /**
     * Adds an object to the list.
     * 
     * @param item != null.
     */
    public void store(final E item)
    {
        if (items.contains(item)) {
            LOG.debug("Updated: " + item);
            
            publishEvent(HousekeeperEvent.MODIFIED, item);
        } else {
            items.add(item);
            
            LOG.debug("Added: " + item);
            publishEvent(HousekeeperEvent.ADDED, item);
        }
    }

    public void store(Collection<E> items)
    {
        for (E e : items)
        {
            store(e);
        }
    }

    /**
     * Removes an item.
     * 
     * @param item != null.
     * @throws IllegalArgumentException if the item to be removed doesn't exist.
     */
    public void delete(E item) throws IllegalArgumentException
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

    public boolean exists(E item)
    {
        return items.contains(item);
    }


    public E find(String name)
    {
        for (E item : items)
        {
            if (item.getName().equals(name))
            {
                return item;
            }
        }
        return null;
    }

}
