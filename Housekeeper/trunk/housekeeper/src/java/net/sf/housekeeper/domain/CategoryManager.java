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
import java.util.List;

import net.sf.housekeeper.HousekeeperEvent;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.richclient.application.Application;
import org.springframework.util.Assert;

/**
 * Manages the {@link net.sf.housekeeper.domain.Category}objects in the domain.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoryManager implements ApplicationContextAware
{

    private ArrayList          categories;

    private ApplicationContext applicationContext;

    /**
     * Initializes this manager with a default set of categories.
     *  
     */
    public CategoryManager()
    {
        categories = new ArrayList(1);

        final String categoryName = Application.services().getMessages()
                .getMessage("domain.food");
        final String convName = Application.services().getMessages()
                .getMessage("domain.food.convenienceFoods");
        final String miscName = Application.services().getMessages()
                .getMessage("domain.food.misc");

        final Category rootCategory = new Category(categoryName);
        rootCategory.addChild(new Category(convName));
        rootCategory.addChild(new Category(miscName));
        categories.add(rootCategory);
    }

    /**
     * Returns an unmodifyable list of all root level categories.
     * 
     * @return The categories. Not null.
     */
    public List getCategories()
    {
        return Collections.unmodifiableList(categories);
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
        applicationContext.publishEvent(new HousekeeperEvent(
                HousekeeperEvent.CATEGORIES_MODIFIED, this));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException
    {
        this.applicationContext = arg0;
    }
}