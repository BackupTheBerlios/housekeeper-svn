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

import java.util.List;

/**
 * Interface for Data Access Objects for {@link Category} objects.
 * 
 * @author Adrian Gygax
 */
public interface CategoryDAO
{

    /**
     * Deletes a category. All items of that category are reassigned to that
     * category's parent to assure referential integrity.
     * 
     * @param category != null
     */
    void delete(Category category);

    /**
     * Deletes all categories. Referential integerity doesn't need to be
     * maintained.
     */
    void deleteAll();

    /**
     * Returns all categories.
     * 
     * @return != null
     */
    List<Category> findAll();

    /**
     * Returns all categories except for a category and its children.
     * 
     * @param discardedCategory != null
     * @return != null
     */
    List<Category> findAllExcept(Category discardedCategory);

    /**
     * Returns all categories inclusive {@link Category#NULL_OBJECT}.
     * 
     * @return != null
     */
    List<Category> findAllInclusiveNull();

    /**
     * Returns all categories except for a category and its children but
     * inclusive {@link Category#NULL_OBJECT}.
     * 
     * @param discardedCategory != null
     * @return != null
     */
    List<Category> findAllInclusiveNullExcept(Category discardedCategory);

    /**
     * Returns all categories which have no parent.
     * 
     * @return != null
     */
    List<Category> findAllTopLevelCategories();

    /**
     * Inserts or updates a category. It is added to its parent. If the category
     * has changed its parent it is also removed from its previous parent.
     * 
     * @param category != null
     */
    void store(Category category);

    /**
     * Adds a couple of categories to the data store.
     * 
     * @param categories != null
     */
    void store(List<Category> categories);

}
