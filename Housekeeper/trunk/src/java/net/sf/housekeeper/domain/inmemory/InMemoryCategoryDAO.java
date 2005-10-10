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
import java.util.List;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.CategoryDAO;
import net.sf.housekeeper.domain.ItemManager;
import net.sf.housekeeper.event.HousekeeperEvent;
import net.sf.housekeeper.event.HousekeeperEventPublisher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * Provides an in-memory implementation for
 * {@link net.sf.housekeeper.domain.CategoryDAO}.
 * 
 * @author Adrian Gygax
 */
public class InMemoryCategoryDAO extends HousekeeperEventPublisher implements
        CategoryDAO
{

    private static final Log LOG = LogFactory.getLog(InMemoryCategoryDAO.class);

    private final ArrayList<Category> categories = new ArrayList<Category>();

    private ItemManager supplyManager;

    /* (non-Javadoc)
     * @see net.sf.housekeeper.domain.CategoryDAO#delete(net.sf.housekeeper.domain.Category)
     */
    public void delete(Category category)
    {
        Assert.notNull(category);

        final Category parent = category.getParent();
        supplyManager.reassignToCategory(category, parent);

        if (parent != null)
        {
            parent.removeChild(category);
        }

        categories.remove(category);

        LOG.debug("Removed category: " + category);
        publishEvent(HousekeeperEvent.REMOVED, category);
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.domain.CategoryDAO#deleteAll()
     */
    public void deleteAll()
    {
        categories.clear();

        LOG.debug("Deleted all categories");
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.domain.CategoryDAO#findAllTopLevelCategories()
     */
    public List<Category> findAllTopLevelCategories()
    {
        final ArrayList<Category> topLevelCategories = new ArrayList<Category>();
        for (Category c : categories)
        {
            if (c.isTopLevel())
            {
                topLevelCategories.add(c);
            }
        }

        return topLevelCategories;
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.domain.CategoryDAO#findAll()
     */
    public List<Category> findAll()
    {
        return findAllExcept(null);
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.domain.CategoryDAO#findAllExcept(net.sf.housekeeper.domain.Category)
     */
    public List<Category> findAllExcept(Category discardedCategory)
    {
        final ArrayList<Category> allCats = new ArrayList<Category>(categories);

        if (discardedCategory != null)
        {
            final List discCats = discardedCategory.getRecursiveCategories();
            allCats.removeAll(discCats);
        }

        return allCats;
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.domain.CategoryDAO#findAllInclusiveNull()
     */
    public List<Category> findAllInclusiveNull()
    {
        final List<Category> l = findAllInclusiveNullExcept(null);
        return l;
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.domain.CategoryDAO#findAllInclusiveNullExcept(net.sf.housekeeper.domain.Category)
     */
    public List<Category> findAllInclusiveNullExcept(Category discardedCategory)
    {
        final List<Category> l = findAllExcept(discardedCategory);
        l.add(Category.NULL_OBJECT);
        return l;
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.domain.CategoryDAO#store(net.sf.housekeeper.domain.Category)
     */
    public void store(Category category)
    {
        Assert.notNull(category);
        if (!category.isLeaf())
        {
            throw new IllegalArgumentException("Category is not a leaf: "
                    + category);
        }

        final boolean isUpdate = categories.contains(category);
        if (isUpdate)
        {
            final Category newParent = category.getParent();

            // Remove category from old parent
            for (Category possibleOldParent : categories)
            {
                final boolean integrityViolated = possibleOldParent
                        .hasChild(category)
                        && !possibleOldParent.equals(newParent);
                if (integrityViolated)
                {
                    possibleOldParent.removeChild(category);
                }
            }

            // Add category to new parent
            if (newParent != null)
            {
                newParent.addChild(category);
            }

            LOG.debug("Updated " + category);
            publishEvent(HousekeeperEvent.MODIFIED, category);
        } else
        {
            if (!category.isTopLevel())
            {
                category.getParent().addChild(category);
            }

            categories.add(category);

            LOG.debug("Added " + category);
            publishEvent(HousekeeperEvent.ADDED, category);
        }

    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.domain.CategoryDAO#store(java.util.List)
     */
    public void store(List<Category> categories)
    {
        this.categories.addAll(categories);
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

}
