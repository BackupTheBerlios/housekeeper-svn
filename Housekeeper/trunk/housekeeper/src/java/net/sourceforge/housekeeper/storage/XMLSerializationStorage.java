/*
 * This file is part of Housekeeper.
 *
 * Housekeeper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Housekeeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Housekeeper; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA  02111-1307  USA
 *
 * Copyright 2003-2004, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper.storage;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.List;


/**
 * Stores data using simple XML serialization.
 * 
 * <p>
 * Beware that the storage format is not robust. If an attribute of a domain
 * class gets changed all previously saved data cannot be restored anymore.
 * </p>
 * 
 * <p>
 * This implementation should be used for testing puropses only
 * </p>
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
final class XMLSerializationStorage extends MemoryStorage
{
    //~ Static fields/initializers ---------------------------------------------

    /** The path to the file that is used for data storage */
    private static final File FILE = new File(System.getProperty("user.home"),
                                              "housekeeper_ser.xml");

    //~ Instance fields --------------------------------------------------------

    private XMLDecoder xmlDecoder;
    private XMLEncoder xmlEncoder;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new XMLStorage object.
     */
    XMLSerializationStorage()
    {
    }

    //~ Methods ----------------------------------------------------------------

    /* (non-Javadoc)
     * @see net.sourceforge.housekeeper.storage.Storage#loadData()
     */
    public void loadData()
    {
        try
        {
            xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(FILE)));

            Object result = xmlDecoder.readObject();

            xmlDecoder.close();

            articleDescriptions = (List)result;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.housekeeper.storage.Storage#saveData()
     */
    public void saveData()
    {
        try
        {
            xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILE)));

            xmlEncoder.writeObject(articleDescriptions);

            xmlEncoder.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }
}
