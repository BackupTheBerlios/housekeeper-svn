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

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.jdom.JDOMPersistence;
import net.sf.housekeeper.util.ConfigurationManager;

/**
 * Provides methods to replace or store domain objects in a
 * {@link net.sf.housekeeper.domain.Household}using the default
 * {@link net.sf.housekeeper.persistence.PersistenceService}.
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
     * Persistence using JDOM.
     */
    private final PersistenceService     jdomPersistence;

    /**
     * File used for loading and saving
     */
    private final File                   dataFile;

    /**
     * Initializes the persistence service to use, which is currently fixed to
     * {@link JDOMPersistence}.
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
     * then replaces all domain objects in a {@link Household}.
     * 
     * @param currentDomain The domain which shall be replaced with another.
     * @throws IOException If the data couldn't be retrieved.
     * @throws UnsupportedFileVersionException if the version or format of the
     *             data source is not supported.
     */
    public void replaceDomainWithSaved(final Household currentDomain)
            throws IOException, UnsupportedFileVersionException
    {
        final Household savedHousehold = jdomPersistence.loadData(dataFile);
        currentDomain.replaceAll(savedHousehold);
        
        //The data has not been changed by the user.
        currentDomain.resetChangedStatus();
    }

    /**
     * Persistently saves the current domain objects using the default
     * {@link PersistenceService}.
     * 
     * @param currentDomain The domain which shall be saved.
     * @throws IOException If the data couldn't be stored.
     */
    public void saveDomainData(final Household currentDomain)
            throws IOException
    {
        jdomPersistence.saveData(currentDomain, dataFile);
        
        //The data now has been saved.
        currentDomain.resetChangedStatus();
    }

}