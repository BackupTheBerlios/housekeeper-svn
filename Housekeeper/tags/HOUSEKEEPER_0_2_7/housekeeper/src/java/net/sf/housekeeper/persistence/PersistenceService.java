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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * PersistenceServices are able to store and load domain objects from/to a
 * persistence data source. Examples for such a service are mapping objects to
 * an XML-file.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public interface PersistenceService
{

    /**
     * Returns saved domain objects from this PersistenceService. For example,
     * invoking this method could create objects from a default XML-File.
     * 
     * @param dataStream
     *            A stream with the data which should be parsed.
     * @return A Household object holding the loaded data.
     * @throws IOException
     *             If the data couldn't be retrieved.
     * @throws IllegalArgumentException
     *             if the given file is not a valid Housekeeper file.
     */
    Household loadData(final InputStream dataStream) throws IOException,
            IllegalArgumentException;

    /**
     * Saves all domain objects of the given household persistently.
     * 
     * @param household
     *            The Household to be saved.
     * @param dataStream
     *            The stream to the converted data shall be sent to.
     * @throws IOException
     *             If the data couldn't be stored.
     */
    void saveData(final Household household, final OutputStream dataStream)
            throws IOException;

}
