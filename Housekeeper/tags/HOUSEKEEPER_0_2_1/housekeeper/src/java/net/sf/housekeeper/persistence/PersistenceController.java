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

package net.sf.housekeeper.persistence;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import net.sf.housekeeper.domain.FoodItemManager;
import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.jdom.JDOMPersistence;
import net.sf.housekeeper.util.ConfigurationManager;

/**
 * Provides methods to replace or store the current domain objects using the
 * default {@link net.sf.housekeeper.persistence.PersistenceService}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class PersistenceController
{

    /**
     * Singleton instance.
     */
    private static PersistenceController instance;

    /**
     * Persistance using JDOM.
     */
    private final PersistenceService     jdomPersistence;

    /**
     * File used for loading and saving
     */
    private final File                   dataFile;

    /**
     * Initializes the persistence service to use.
     *  
     */
    private PersistenceController()
    {
        final File dataDir = (File) ConfigurationManager.INSTANCE
                .getConfiguration()
                .getProperty(ConfigurationManager.DATA_DIRECTORY);

        dataFile = new File(dataDir, "data.xml");

        jdomPersistence = new JDOMPersistence();
    }

    /**
     * Returns the Singleton instance.
     * 
     * @return the Singleton instance.
     */
    public static PersistenceController instance()
    {
        if (instance == null)
        {
            instance = new PersistenceController();
        }

        return instance;
    }

    /**
     * Loads the saved data using the default {@link PersistenceService}and
     * replaces all domain objects with them.
     * 
     * @throws IOException If the data couldn't be retrieved.
     * @throws UnsupportedFileVersionException if the version or format of the
     *             data source is not supported.
     */
    public void replaceDomainWithSaved() throws IOException,
            UnsupportedFileVersionException
    {
        final Household household = jdomPersistence.loadData(dataFile);
        FoodItemManager.instance().replaceAll(household.getFoodItems());
        FoodItemManager.instance().resetChangedStatus();
    }

    /**
     * Persistently saves the current domain objects using the default
     * {@link PersistenceService}.
     * 
     * @throws IOException If the data couldn't be stored.
     */
    public void saveDomainData() throws IOException
    {
        final Collection foodItems = FoodItemManager.instance().getSupplyList();
        final Household household = new Household(foodItems);
        jdomPersistence.saveData(household, dataFile);
        FoodItemManager.instance().resetChangedStatus();
    }

}