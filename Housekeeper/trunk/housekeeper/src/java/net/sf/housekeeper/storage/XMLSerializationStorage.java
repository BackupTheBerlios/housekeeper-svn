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

import net.sf.housekeeper.domain.StockItem;

import com.odellengineeringltd.glazedlists.BasicEventList;
import com.odellengineeringltd.glazedlists.EventList;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class XMLSerializationStorage implements Storage
{
    /** The path to the file that is used for data storage */
    private static final File FILE =
        new File(System.getProperty("user.dir"), "housekeeper_ser.xml");

    /** All items that are on stock */
    private EventList stockItems;

    /**
     * Initializes a XMLSerializationStorage.
     */
    XMLSerializationStorage()
    {
        stockItems = new BasicEventList();
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.storage.DataMapper#saveData()
     */
    public void saveData() throws FileNotFoundException
    {
        XMLEncoder xmlEncoder =
            new XMLEncoder(
                new BufferedOutputStream(new FileOutputStream(FILE)));
        xmlEncoder.writeObject(new ArrayList(stockItems));
        xmlEncoder.close();
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.storage.DataMapper#loadData()
     */
    public void loadData() throws FileNotFoundException
    {
        XMLDecoder xmlDecoder =
            new XMLDecoder(new BufferedInputStream(new FileInputStream(FILE)));
        ArrayList stock = (ArrayList)xmlDecoder.readObject();
        xmlDecoder.close();

        stockItems.clear();
        stockItems.addAll(stock);
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.storage.DataMapper#getAllStockItems()
     */
    public EventList getAllStockItems()
    {
        return stockItems;
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
