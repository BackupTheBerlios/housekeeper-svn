package net.sf.housekeeper.storage;

import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.housekeeper.domain.StockItem;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public interface Storage
{
    Collection getAllStockItems();
    
    void add(StockItem stockItem);
    
    void remove(StockItem item);
    
    void loadData() throws FileNotFoundException;
    
    void saveData() throws FileNotFoundException;
}
