package net.sourceforge.housekeeper.storage;

import net.sourceforge.housekeeper.domain.AssortimentItem;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public interface AssortimentItemMapper extends DataMapper
{
    
    void add(AssortimentItem item);
}
