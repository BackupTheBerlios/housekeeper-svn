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
import java.util.Collection;
import java.util.Collections;

import net.sf.housekeeper.domain.StockItem;


/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class XMLSerializationStorage implements Storage
{
    /** The path to the file that is used for data storage */
    private static final File FILE = new File(System.getProperty("user.dir"),
                                              "housekeeper_ser.xml");

    /** All items that are on stock */
    private Collection stockItems;

    /**
     * Initializes a XMLSerializationStorage.
     */
    XMLSerializationStorage()
    {
        stockItems = new ArrayList();
    }
   
	/* (non-Javadoc)
     * @see net.sf.housekeeper.storage.DataMapper#saveData()
     */
    public void saveData() throws FileNotFoundException
	{        
            XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILE)));
            xmlEncoder.writeObject(stockItems);
            xmlEncoder.close();
	}

	/* (non-Javadoc)
     * @see net.sf.housekeeper.storage.DataMapper#loadData()
     */
    public void loadData() throws FileNotFoundException
	{

            XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(FILE)));
            Collection stock = (Collection)xmlDecoder.readObject();
            xmlDecoder.close();

            stockItems = stock;           

	}

    /* (non-Javadoc)
     * @see net.sf.housekeeper.storage.DataMapper#getAllStockItems()
     */
    public Collection getAllStockItems()
    {
        return Collections.unmodifiableCollection(stockItems);
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.storage.DataMapper#add(net.sf.housekeeper.domain.StockItem)
     */
    public void add(StockItem stockItem)
    {
        stockItems.add(stockItem);
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.storage.DataMapper#remove(net.sf.housekeeper.domain.StockItem)
     */
    public void remove(StockItem item)
    {
        stockItems.remove(item);
    }

}
