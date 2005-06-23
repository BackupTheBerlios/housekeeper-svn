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

package net.sf.housekeeper.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import net.sf.housekeeper.domain.CategoryManager;
import net.sf.housekeeper.domain.ItemManagerImpl;
import net.sf.housekeeper.event.HousekeeperEvent;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * Provides methods for importing and exporting data.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ImportExportController implements ApplicationContextAware
{

    private ApplicationContext    applicationContext;

    private CategoryManager       categoryManager;

    private PersistenceController persistenceController;

    private ItemManagerImpl           shoppingListManager;

    private ItemManagerImpl           supplyManager;

    /**
     * Exports all data.
     * 
     * @throws IOException if the data could not be saved.
     */
    public void exportData() throws IOException
    {
        Assert.notNull(categoryManager);
        Assert.notNull(persistenceController);
        Assert.notNull(shoppingListManager);
        Assert.notNull(supplyManager);

        final Household household = new Household(supplyManager.getAllItems(),
                shoppingListManager.getAllItems(), categoryManager.getTopLevelCategories());
        persistenceController.save(household);
    }

    /**
     * Replaces all data with saved ones.
     * 
     * @throws IOException if the data could not be read.
     */
    public void importData() throws IOException
    {
        Assert.notNull(categoryManager);
        Assert.notNull(persistenceController);
        Assert.notNull(shoppingListManager);
        Assert.notNull(supplyManager);

        final Household h = persistenceController.load();
        supplyManager.replaceAll(h.getSupply());
        shoppingListManager.replaceAll(h.getShoppingList());
        categoryManager.replaceAll(h.getCategories());

        if (applicationContext != null)
        {
            applicationContext.publishEvent(new HousekeeperEvent(
                    HousekeeperEvent.DATA_REPLACED, this));
        }
    }

    /**
     * Exports the shopping list to a text file.
     * 
     * @param outputFile The file to export to. != null
     * @throws IOException if the file could not be saved.
     */
    public void exportShoppingListAsText(File outputFile) throws IOException
    {
        final String itemsAsText = shoppingListManager.getItemsAsText();

        final Writer writer = new BufferedWriter(new FileWriter(outputFile,
                false));
        writer.write(itemsAsText);
        writer.close();
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

    /**
     * @param categoryManager The categoryManager to set.
     */
    public void setCategoryManager(CategoryManager categoryManager)
    {
        this.categoryManager = categoryManager;
    }

    /**
     * @param persistenceController The persistenceController to set.
     */
    public void setPersistenceController(
                                         PersistenceController persistenceController)
    {
        this.persistenceController = persistenceController;
    }

    /**
     * @param shoppingListManager The shoppingListManager to set.
     */
    public void setShoppingListManager(ItemManagerImpl shoppingListManager)
    {
        this.shoppingListManager = shoppingListManager;
    }

    /**
     * @param supplyManager The supplyManager to set.
     */
    public void setSupplyManager(ItemManagerImpl supplyManager)
    {
        this.supplyManager = supplyManager;
    }

}
