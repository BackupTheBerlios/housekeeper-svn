package net.sf.housekeeper.storage;

import java.io.FileNotFoundException;

import com.odellengineeringltd.glazedlists.EventList;

import net.sf.housekeeper.domain.StockItem;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public interface Storage
{
    EventList getAllStockItems();
    
    void add(StockItem stockItem);
    
    void remove(StockItem item);
    
    void loadData() throws FileNotFoundException;
    
    void saveData() throws FileNotFoundException;
}
