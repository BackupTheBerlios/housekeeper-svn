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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.jdom.JDOMPersistence;

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
        final String homeDirString = System.getProperty("user.home");
        final File hkDir = new File(homeDirString, ".housekeeper");
        
        //Create directory for data if it doesn't exist.
        hkDir.mkdir();
       
        dataFile = new File(hkDir, "data.xml");

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
        final InputStream dataStream = new BufferedInputStream(
                new FileInputStream(dataFile));
        final Household savedHousehold = jdomPersistence.loadData(dataStream);
        dataStream.close();
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
        final OutputStream dataStream = new BufferedOutputStream(
                new FileOutputStream(dataFile));
        jdomPersistence.saveData(currentDomain, dataStream);
        dataStream.flush();
        dataStream.close();
        
        //The data now has been saved.
        currentDomain.resetChangedStatus();
    }

}