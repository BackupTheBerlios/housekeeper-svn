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

import java.io.IOException;

import net.sf.housekeeper.domain.Household;

/**
 * PersistenceServices are able to store and load domain objects from/to a persistence data source. Examples for such a service
 * are mapping objects to an XML-file or a database backend.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public interface PersistenceService
{

    /**
     * Returns saved domain objects from this
     * PersistenceService. For example, invoking this
     * method could create objects from a default XML-File. 
     * 
     * @return A Household object holding the loaded data.
     * @throws IOException If the data couldn't be retrieved.
     * @throws UnsupportedFileVersionException if the version or format of the
     *  data source is not supported.
     */
    Household loadData() throws IOException, UnsupportedFileVersionException;

    /**
     * Saves all domain objects of the given household persistently.
     * 
     * @param household The Household to be saved.
     * @throws IOException If the data couldn't be stored.
     */
    void saveData(final Household household) throws IOException;
}