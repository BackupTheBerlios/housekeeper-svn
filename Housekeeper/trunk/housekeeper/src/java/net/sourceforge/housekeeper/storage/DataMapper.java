package net.sourceforge.housekeeper.storage;

import java.util.Collection;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @see
 * @since
 */
public interface DataMapper
{
    Collection getAll();
    
    void add(Collection col);
}
