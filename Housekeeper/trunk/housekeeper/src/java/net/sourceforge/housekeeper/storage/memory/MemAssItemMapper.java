package net.sourceforge.housekeeper.storage.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.sourceforge.housekeeper.domain.AssortimentItem;
import net.sourceforge.housekeeper.storage.AssortimentItemMapper;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @see
 * @since
 */
public class MemAssItemMapper implements AssortimentItemMapper
{
    private Collection items;
    
    public MemAssItemMapper()
    {
        items = new ArrayList();
    }
    
	public Collection getAll()
	{
        return items;
	}
    
    public void add(AssortimentItem assItem)
    {
        items.add(assItem);
    }

	public void add(Collection col)
	{
		items.addAll(col);
		
	}

}
