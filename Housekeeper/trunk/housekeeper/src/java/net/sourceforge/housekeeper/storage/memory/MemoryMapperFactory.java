package net.sourceforge.housekeeper.storage.memory;

import net.sourceforge.housekeeper.storage.AssortimentItemMapper;
import net.sourceforge.housekeeper.storage.DataMapperFactory;
import net.sourceforge.housekeeper.storage.StockItemMapper;


/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class MemoryMapperFactory implements DataMapperFactory
{
    private MemAssItemMapper assItemMapper;
    private MemStockItemMapper stockItemMapper;

    public static final MemoryMapperFactory INSTANCE = new MemoryMapperFactory();
    
    private MemoryMapperFactory()
    {
        assItemMapper = new MemAssItemMapper();
        stockItemMapper = new MemStockItemMapper();
    }

	public AssortimentItemMapper getAssortimentItemMapper()
	{
		return assItemMapper;
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
