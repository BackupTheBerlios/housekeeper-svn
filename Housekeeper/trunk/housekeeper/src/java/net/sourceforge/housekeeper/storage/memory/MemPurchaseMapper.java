package net.sourceforge.housekeeper.storage.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.sourceforge.housekeeper.domain.Purchase;
import net.sourceforge.housekeeper.storage.DataMapper;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @see
 * @since
 */
public class MemPurchaseMapper implements DataMapper
{
    private Collection purchases;
    
    public MemPurchaseMapper()
    {
        purchases = new ArrayList();
    }

	public Collection getAll()
	{
        return Collections.unmodifiableCollection(purchases);
	}
    
    public void add(Purchase purchase)
    {
        purchases.add(purchase);
    }

	public void add(Collection col)
	{
		purchases.addAll(col);
		
	}

}
