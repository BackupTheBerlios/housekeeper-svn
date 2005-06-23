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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.housekeeper.event.HousekeeperEvent;
import net.sf.housekeeper.event.HousekeeperEventPublisher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * Manages the {@link net.sf.housekeeper.domain.Category}objects in the domain.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoryManager extends HousekeeperEventPublisher
{

    private static final Log LOG = LogFactory.getLog(CategoryManager.class);

    private List             categories;

    private ItemManagerImpl      supplyManager;

    /**
     * Initializes this manager with a default set of categories.
     *  
     */
    public CategoryManager()
    {
        categories = new ArrayList(5);
    }

    /**
     * Adds a category. If parent == null, then category is added as a new top
     * level category. This method doesn't allow multiple adds per call. A
     * category must not have any children when added.
     * 
     * @param category != null. category.isLeaf() == true. Must not exist
     *            already.
     * @throws IllegalArgumentException if the category already exists.
     */
    public void add(final Category category) throws IllegalArgumentException
    {
        Assert.notNull(category);
        if (categories.contains(category))
        {
            throw new IllegalArgumentException("Category already exists: "
                    + category);
        }
        if (!category.isLeaf())
        {
            throw new IllegalArgumentException("Category must be a leaf: "
                    + category);
        }

        final boolean isRoot = category.getParent() == null;
        if (isRoot)
        {
            categories.add(category);
        } else
        {
            category.getParent().addChild(category);
        }

        publishEvent(HousekeeperEvent.ADDED, category);
    }

    /**
     * Returns an unmodifyable list of all root level categories.
     * 
     * @return The categories. Not null.
     */
    public List getTopLevelCategories()
    {
        return Collections.unmodifiableList(categories);
    }

    /**
     * Returns an iterator over all top level categories.
     * 
     * @return != null.
     */
    public Iterator getTopLevelCategoriesIterator()
    {
        return categories.iterator();
    }

    /**
     * Returns a list of all categories.
     * 
     * @return != null
     */
    public List getAllCategories()
    {
        return getAllCategoriesExcept(null);
    }

    /**
     * Returns a list of all categories excluding a category and its children.
     * 
     * @return != null
     */
    public List getAllCategoriesExcept(Category discardedCategory)
    {
        final ArrayList allCats = new ArrayList();
        final Iterator topLevelCats = getTopLevelCategoriesIterator();

        while (topLevelCats.hasNext())
        {
            Category element = (Category) topLevelCats.next();

            final List c = element.getRecursiveCategories();
            allCats.addAll(c);
        }
        if (discardedCategory != null)
        {
            final List discCats = discardedCategory.getRecursiveCategories();
            allCats.removeAll(discCats);
        }

        return Collections.unmodifiableList(allCats);
    }

    /**
     * Removes a category. All items of that category are reassigned to that
     * category's parent to assure referential integrity.
     * 
     * @param category != null
     */
    public void remove(Category category)
    {
        Assert.notNull(category);

        LOG.debug("Removing category: " + category);

        final Category parent = category.getParent();

        supplyManager.reassignToCategory(category, parent);

        if (parent == null)
        {
            categories.remove(category);
        } else
        {
            parent.removeChild(category);
        }

        publishEvent(HousekeeperEvent.REMOVED, category);
    }

    /**
     * Replaces all categories with other ones.
     * 
     * @param categories The categories. Must not be null.
     */
    public void replaceAll(final List categories)
    {
        Assert.notNull(categories);

        this.categories.clear();
        this.categories.addAll(categories);
    }

    /**
     * Sets the itemManager. Needed for assuring referential integerity when
     * deleting a category.
     * 
     * @param im The manager.
     */
    public void setSupplyManager(ItemManagerImpl im)
    {
        this.supplyManager = im;
    }

    /**
     * Updates a Category. A reference to the old parent must be provided so the
     * old parent's reference to the category can be removed.
     * 
     * @param category != null.
     * @param oldParent The previous parent of the category.
     */
    public void update(Category category, Category oldParent)
    {
        Assert.notNull(category);
        final Category newParent = category.getParent();

        //Remove catgory from old parent
        if (oldParent != null && !newParent.equals(oldParent))
        {
            oldParent.removeChild(category);
        }

        //Add category to new parent
        if (newParent != null)
        {
            newParent.addChild(category);
        }

        //Add/Remove category from the list of root categories
        if (newParent == null && !categories.contains(category))
        {
            categories.add(category);
        } else if (newParent != null && categories.contains(category))
        {
            categories.remove(category);
        }

        //Publish events
        if (oldParent != null)
        {
            publishEvent(HousekeeperEvent.MODIFIED, oldParent);
        }
        if (category.getParent() != null)
        {
            publishEvent(HousekeeperEvent.MODIFIED, newParent);
        }
        publishEvent(HousekeeperEvent.MODIFIED, category);

    }

}
