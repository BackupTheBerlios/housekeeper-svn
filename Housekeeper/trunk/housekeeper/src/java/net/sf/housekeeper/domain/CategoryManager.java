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

import java.util.HashMap;

import org.springframework.richclient.application.support.ApplicationServicesAccessor;

/**
 * Manages categories for items.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoryManager extends ApplicationServicesAccessor
{

    private Category      rootCategory;

    private final HashMap categoryMap = new HashMap();

    /**
     * Initializes this manager with the default categories.
     */
    public CategoryManager()
    {
        rootCategory = createCategory("food", "domain.food");
        rootCategory.addChild(createCategory("convenienceFood",
                                             "domain.food.convenienceFoods"));
        rootCategory.addChild(createCategory("misc", "domain.food.misc"));
    }

    private Category createCategory(String id, String nameID)
    {
        final String convName = getMessage(nameID);
        final Category cat = new Category(id, convName);
        addToMap(cat);
        return cat;
    }

    private void addToMap(final Category cat)
    {
        categoryMap.put(cat.getId(), cat);
    }

    /**
     * Returns the category for a given ID.
     * 
     * @param id The ID of the category.
     * @return The category or null if it doesn't exist.
     */
    public Category getCategory(final String id)
    {
        final Category cat = (Category) categoryMap.get(id);
        return cat;
    }

    /**
     * Returns the root category.
     * 
     * @return the root category.
     */
    public Category getRootCategory()
    {
        return rootCategory;
    }
}