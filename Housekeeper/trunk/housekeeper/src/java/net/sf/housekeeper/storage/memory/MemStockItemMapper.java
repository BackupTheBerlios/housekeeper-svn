package net.sf.housekeeper.storage.memory;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.housekeeper.domain.StockItem;
import net.sf.housekeeper.storage.StockItemMapper;

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

    /* (non-Javadoc)
     * @see net.sourceforge.housekeeper.storage.StockItemMapper#remove(net.sourceforge.housekeeper.domain.StockItem)
     */
    public void remove(StockItem item)
    {
        items.remove(item);
        
    }

}