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

package net.sf.housekeeper.storage;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.housekeeper.domain.StockItem;

import com.odellengineeringltd.glazedlists.BasicEventList;
import com.odellengineeringltd.glazedlists.EventList;

/**
 * Storage technique using simple XML serialization. Should only be used for
 * quick testing purposes. If a class gets modified, previously saved files
 * cannot be read anymore.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class XMLSerializationStorage implements Storage
{

    /** The path to the file that is used for data storage. */
    private File                                file;

    /** All items that are on stock. */
    private EventList                           stockItems;

    /** singleton instance. */
    public static final XMLSerializationStorage INSTANCE = new XMLSerializationStorage();

    /**
     * Initializes an XMLSerializationStorage.
     */
    private XMLSerializationStorage()
    {
        String defaultDir = System.getProperty("user.dir");
        file = new File(defaultDir, "housekeeper_ser.xml");
        stockItems = new BasicEventList();
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
        xmlEncoder.writeObject(new ArrayList(stockItems));
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
        List stock = (ArrayList) xmlDecoder.readObject();
        xmlDecoder.close();

        stockItems.clear();
        stockItems.addAll(stock);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.storage.DataMapper#getAllStockItems()
     */
    public EventList getAllStockItems()
    {
        return stockItems;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.storage.DataMapper#
     *      add(net.sf.housekeeper.domain.StockItem)
     */
    public void add(final StockItem stockItem)
    {
        stockItems.add(stockItem);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.storage.DataMapper#
     *      remove(net.sf.housekeeper.domain.StockItem)
     */
    public void remove(final StockItem item)
    {
        stockItems.remove(item);
    }

}