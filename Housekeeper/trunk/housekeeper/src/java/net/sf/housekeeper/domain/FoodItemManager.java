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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.ListModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    public static final FoodItemManager INSTANCE = new FoodItemManager();

    /**
     * Log to be used for this class.
     */
    private static final Log LOG = LogFactory.getLog(FoodItemManager.class);
    
    /**
     * Holds a list of all items on hand.
     */
    private ArrayListModel        supply;

    /**
     * Do not use this constructor, use the public Singleton instance instead.
     * This one is only package private because of the unit tests.
     */
    FoodItemManager()
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

        LOG.debug("Added item: " + item);
    }
    
    /**
     * Duplicates the provided item and adds it to the supply.
     * 
     * @param item The item to be duplicated.
     */
    public void duplicate(final FoodItem item)
    {
        final FoodItem clonedItem = (FoodItem)item.clone();
        add(clonedItem);
    }

    /**
     * Returns the items on hold as a ListModel.
     * 
     * @return the supply.
     */
    public ListModel getSupplyListModel()
    {
        return supply;
    }
    
    /**
     * Returns the items on hold.
     * 
     * @return the supply.
     */
    public List getSupplyList()
    {
        return Collections.unmodifiableList(supply);
    }

    /**
     * Clears the list of all food items on hand and replaces it with a new one.
     * 
     * @param foodItems A collection of food items.
     */
    public void replaceAll(final Collection foodItems)
    {
        supply.clear();
        supply.addAll(foodItems);
        
        LOG.debug("Replaced all items with: " + supply.toString());
    }

    /**
     * Provides an iterator for the list of items on hand.
     * 
     * @return an iterator for the list of items on hand.
     */
    public Iterator getSupplyIterator()
    {
        return supply.iterator();
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
        
        LOG.debug("Marked item as changed: " + supply.get(index));
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
        
        LOG.debug("Removed item: " + item);
    }

}