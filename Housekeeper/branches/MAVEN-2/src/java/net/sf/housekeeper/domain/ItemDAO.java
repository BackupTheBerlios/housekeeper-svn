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

import java.util.Collection;
import java.util.List;

/**
 * Provides operations to add, delete and change {@link Item}s.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public interface ItemDAO<E extends Item>
{

    /**
     * Removes an object from the supply.
     * 
     * @param item the item to remove from the supply.
     * @throws IllegalArgumentException if the item to be removed doesn't exist.
     */
    void delete(final E item) throws IllegalArgumentException;

    void deleteAll();

    /**
     * Tests if an item exists.
     * 
     * @param item != null.
     * @return True, if the item exists, false otherwise.
     */
    boolean exists(final E item);

    /**
     * Tries to find an item with a given name.
     * 
     * @param name != null
     * @return The first item with the given name or null if none exists.
     */
    E find(String name);

    /**
     * Returns the items on hold. You must not modify the returned list object
     * directly. Use the methods of this manager instead.
     * 
     * @return the supply.
     */
    List<E> findAll();

    /**
     * Returns all items of a specific category and its sub-categories. If
     * category is null, then all items are returned.
     * 
     * @param category The category of the items.
     * @return The items which match the category.
     */
    List<E> findAllOfCategory(final Category category);

    /**
     * Returns all items in a textual representation.
     * 
     * @return != null
     */
    String getItemsAsText();

    /**
     * Reassigns all items of a category and its subcategories to another
     * category.
     * 
     * @param oldCategory The current category.
     * @param newCategory The new category to assign the items to.
     */
    void reassignToCategory(Category oldCategory, Category newCategory);

    /**
     * Adds a collection of items.
     * 
     * @param items The items to add.
     */
    void store(final Collection<E> items);

    /**
     * Adds an object to the list.
     * 
     * @param item the item to add to the supply.
     */
    void store(final E item);
}
