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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.sf.housekeeper.event.HousekeeperEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * Manages the {@link net.sf.housekeeper.domain.Category}objects in the domain.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoryManager extends AbstractManager
{

    private static final Log LOG = LogFactory.getLog(CategoryManager.class);

    private ArrayList        categories;

    private ItemManager      supplyManager;

    /**
     * Initializes this manager with a default set of categories.
     *  
     */
    public CategoryManager()
    {
        categories = new ArrayList();
    }

    /**
     * Adds a category. If parent == null, then category is added as a new top
     * level category.
     * 
     * @param category The category to add.
     */
    public void add(final Category category)
    {
        Assert.notNull(category);

        final boolean isRoot = category.getParent() == null;
        if (isRoot)
        {
            categories.add(category);
        } else
        {
            category.getParent().addChild(category);
        }

        setChanged();
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

        final Iterator iter = supplyManager.getItemsForCategory(category)
                .iterator();
        while (iter.hasNext())
        {
            Item element = (Item) iter.next();
            element.setCategory(parent);
        }

        if (parent == null)
        {
            categories.remove(category);
        } else
        {
            parent.removeChild(category);
        }

        setChanged();
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

        setChanged();
    }

    /**
     * Sets the itemManager. Needed for assuring referential integerity when
     * deleting a category.
     * 
     * @param im The manager.
     */
    public void setSupplyManager(ItemManager im)
    {
        this.supplyManager = im;
    }

    /**
     * Updates the a Category.
     * 
     * @param category != null.
     */
    public void update(Category category)
    {
        Assert.notNull(category);

        //cleanupRootCategories();
        if (category.getParent() == null && !categories.contains(category))
        {
            categories.add(category);
        } else if (category.getParent() != null
                && categories.contains(category))
        {
            categories.remove(category);
        }

        setChanged();
        publishEvent(HousekeeperEvent.MODIFIED, category);
    }

}
