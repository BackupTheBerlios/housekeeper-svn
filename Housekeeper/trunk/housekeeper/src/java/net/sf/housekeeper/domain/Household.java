/*
 * This file is part of Housekeeper.
 * 
 * Housekeeper is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Housekeeper is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Housekeeper; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Copyright 2003-2004, The Housekeeper Project
 * http://housekeeper.sourceforge.net
 */

package net.sf.housekeeper.domain;

import java.util.List;

import com.odellengineeringltd.glazedlists.BasicEventList;


/**
 * Represents a household.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class Household
{
    private List stockItems;
    
    private static Household INSTANCE = new Household();
    
    public Household()
    {
        stockItems = new BasicEventList();
    }
    
    public Household(final List stockItems)
    {
        stockItems.clear();
        stockItems.addAll(stockItems);
    }

    /**
     * @return Returns the iNSTANCE.
     */
    public static Household instance()
    {
        return INSTANCE;
    }
    
    /**
     * @param instance The iNSTANCE to set.
     */
    public static void setINSTANCE(Household instance)
    {
        INSTANCE = instance;
    }
    
    /**
     * @return Returns the stockItems.
     */
    public List getStockItems()
    {
        return stockItems;
    }
    
    public void add(final StockItem item)
    {
        stockItems.add(item);
    }
    
    public void remove(final StockItem item)
    {
        stockItems.remove(item);
    }
    
    public void update(final StockItem item)
    {
        stockItems.remove(item);
        stockItems.add(item);
    }
    
}
