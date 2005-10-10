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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.housekeeper.persistence.jibx.JiBXPersistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides methods to replace or store domain objects using the default
 * {@link net.sf.housekeeper.persistence.PersistenceService}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class PersistenceController
{

    private static final Log LOG = LogFactory
            .getLog(PersistenceController.class);

    private final PersistenceService jibxPersistence;

    /**
     * File used for loading and saving
     */
    private File dataFile;

    /**
     * Initializes the persistence service to use.
     */
    public PersistenceController()
    {
        final String customDataFile = System
                .getProperty("net.sf.housekeeper.persistence.datafile");
        if (customDataFile != null)
        {
            dataFile = new File(customDataFile);
        } else
        {
            final String homeDirString = System.getProperty("user.home");
            final File hkDir = new File(homeDirString, ".housekeeper");

            // Create directory for data if it doesn't exist.
            hkDir.mkdir();

            dataFile = new File(hkDir, "data.xml");
        }

        LOG.info("Using data file: " + dataFile);

        jibxPersistence = new JiBXPersistence();
    }

    /**
     * Loads the saved data using the default {@link PersistenceService}.
     * 
     * @throws IOException If the data couldn't be retrieved.
     */
    public Household load() throws IOException
    {
        LOG.info("Loading data from: " + dataFile);

        final InputStream dataStream = new BufferedInputStream(
                new FileInputStream(dataFile));

        Household savedHousehold = jibxPersistence.loadData(dataStream);
        return savedHousehold;
    }

    /**
     * Persistently saves domain objects using the default
     * {@link PersistenceService}.
     * 
     * @param domain The domain which shall be saved.
     * @throws IOException If the data couldn't be stored.
     */
    public void save(final Household domain) throws IOException
    {
        LOG.info("Saving data to: " + dataFile);

        final OutputStream dataStream = new BufferedOutputStream(
                new FileOutputStream(dataFile));
        jibxPersistence.saveData(domain, dataStream);
        dataStream.flush();
        dataStream.close();
    }

}
