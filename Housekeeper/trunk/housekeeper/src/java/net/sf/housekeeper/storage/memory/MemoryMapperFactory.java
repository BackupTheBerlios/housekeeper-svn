package net.sf.housekeeper.storage.memory;

import net.sf.housekeeper.storage.DataMapperFactory;
import net.sf.housekeeper.storage.StockItemMapper;


/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class MemoryMapperFactory implements DataMapperFactory
{
    
    private MemStockItemMapper stockItemMapper;

    public static final MemoryMapperFactory INSTANCE = new MemoryMapperFactory();
    
    private MemoryMapperFactory()
    {
        stockItemMapper = new MemStockItemMapper();
    }

	public StockItemMapper getStockItemMapper()
	{
		return stockItemMapper;
	}

	public void saveData()
	{
		

	}

	public void loadData()
	{
		

	}

}
