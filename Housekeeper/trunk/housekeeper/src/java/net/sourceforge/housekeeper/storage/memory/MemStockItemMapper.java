package net.sourceforge.housekeeper.storage.memory;

import java.util.ArrayList;
import java.util.Collection;

import net.sourceforge.housekeeper.domain.StockItem;
import net.sourceforge.housekeeper.storage.StockItemMapper;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class MemStockItemMapper implements StockItemMapper
{
    private Collection items;
    
    MemStockItemMapper()
    {
        items = new ArrayList();
    }

	public Collection getAll()
	{
		return items;
	}

	public void add(Collection col)
	{
		items.addAll(col);
		
	}

	public void add(StockItem item)
	{
		items.add(item);
		
	}

}
