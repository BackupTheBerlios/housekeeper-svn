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

import javax.swing.ListModel;

import com.jgoodies.binding.list.ArrayListModel;

/**
 * Holds a list of items and provides operations to add, delete and change a
 * Book.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class FoodItemManager
{

    /**
     * Singleton instance.
     */
    public static FoodItemManager INSTANCE = new FoodItemManager();

    /**
     * Holds a list of all items on hand.
     */
    private ArrayListModel        supply;

    /**
     * Prevents instanciation.
     */
    private FoodItemManager()
    {
        supply = new ArrayListModel();
    }

    /**
     * Adds a FoodItem to the supply and notifies observers of the supply
     * ListModel about the change.
     * 
     * @param item the item to add to the supply.
     */
    public void add(final FoodItem item)
    {
        supply.add(item);
    }

    /**
     * Returns the items on hold in ListModel.
     * 
     * @return the supply.
     */
    public ListModel getSupply()
    {
        return supply;
    }

    /**
     * Marks the FoodItem at the specified index as changed observers of the
     * supply ListModel about the change.
     * 
     * @param index
     */
    public void markItemChanged(int index)
    {
        supply.fireContentsChanged(index);
    }

    /**
     * Removes a FoodItem from the supply and notifies observers of the supply
     * ListModel about the change.
     * 
     * @param item the item to remove from the supply.
     */
    public void remove(final FoodItem item)
    {
        supply.remove(item);
    }

}