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

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import net.sf.housekeeper.domain.Household;

/**
 * Storage technique using simple XML serialization. Should only be used for
 * quick testing purposes. If a class gets modified, previously saved files
 * cannot be read anymore.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class XMLSerialization implements PersistenceService
{

    /** The path to the file that is used for data storage. */
    private File                                file;

    /**
     * Initializes an XMLSerializationStorage.
     */
    XMLSerialization()
    {
        String defaultDir = System.getProperty("user.dir");
        file = new File(defaultDir, "housekeeper_ser.xml");
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.storage.DataMapper#saveData()
     */
    public void saveData() throws FileNotFoundException
    {
        XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(
                new FileOutputStream(file)));
        xmlEncoder.writeObject(Household.instance());
        xmlEncoder.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.storage.DataMapper#loadData()
     */
    public void loadData() throws FileNotFoundException
    {
        XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(
                new FileInputStream(file)));
        final Household household = (Household) xmlDecoder.readObject();
        xmlDecoder.close();

        Household.setINSTANCE(household);
    }

}