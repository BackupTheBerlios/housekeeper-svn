package net.sf.housekeeper.storage;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public interface DataMapperFactory
{   
    
    public StockItemMapper getStockItemMapper();
    
    
    public void saveData();
    
    public void loadData();

}
