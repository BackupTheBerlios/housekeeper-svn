package net.sourceforge.housekeeper.storage;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @see
 * @since
 */
public interface DataMapperFactory
{
    public AssortimentItemMapper getAssortimentItemMapper();
    
    public StockItemMapper getStockItemMapper();
    
    
    public void saveData();
    
    public void loadData();

}
