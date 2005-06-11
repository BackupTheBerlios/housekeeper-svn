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
 * http://housekeeper.berlios.de
 */

package net.sf.housekeeper.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.util.Assert;

/**
 * Implements business logic which spans over multiple managers.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class HighLevelManager
{

    private ItemManager supplyManager;

    private ItemManager shoppingListManager;

    /**
     * @param shoppingListManager The shoppingListManager to set.
     */
    public void setShoppingListManager(ItemManager shoppingListManager)
    {
        this.shoppingListManager = shoppingListManager;
    }

    /**
     * @param supplyManager The supplyManager to set.
     */
    public void setSupplyManager(ItemManager supplyManager)
    {
        this.supplyManager = supplyManager;
    }

    /**
     * 
     * @param boughtItem != null. Must exist in the shopping list.
     * @throws IllegalArgumentException if the item isn't on the shopping list.
     */
    public void buy(ShoppingListItem boughtItem)
            throws IllegalArgumentException
    {
        Assert.notNull(boughtItem);
        Assert.notNull(supplyManager);
        Assert.notNull(shoppingListManager);

        shoppingListManager.remove(boughtItem);
        final Collection shoppingListItems = createSupplyItems(boughtItem, null);
        supplyManager.addAll(shoppingListItems);
    }

    /**
     * Creates {@link ExpirableItem}s from a {@link ShoppingListItem}.
     * 
     * @param shoppingListItem != null
     * @param expiry The expiry date for the newly created items.
     * @return The created {@link ExpirableItem}s. != null
     */
    public Collection createSupplyItems(
                                        final ShoppingListItem shoppingListItem,
                                        final Date expiry)
    {
        final int numberOfItems = shoppingListItem.getQuantity();
        final ArrayList createdItems = new ArrayList(numberOfItems);

        for (int i = 0; i < numberOfItems; i++)
        {
            final ExpirableItem item = new ExpirableItem(shoppingListItem);
            item.setExpiry(expiry);
            createdItems.add(item);
        }

        return createdItems;
    }
}
