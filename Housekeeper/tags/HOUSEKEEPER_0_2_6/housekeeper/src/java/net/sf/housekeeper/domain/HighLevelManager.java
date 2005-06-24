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
     * Creates {@link ExpirableItem}s in the correct quantity from a
     * {@link ShoppingListItem}and adds them to the supply. Also, the
     * SupplyItem is removed from the ShoppingList.
     * 
     * @param boughtItem != null. Must exist in the shopping list.
     * @throws IllegalArgumentException if the item isn't on the shopping list.
     */
    public void buy(ShoppingListItem boughtItem, ExpirableItem itemPrototype)
            throws IllegalArgumentException
    {
        Assert.notNull(supplyManager);
        Assert.notNull(shoppingListManager);

        Assert.notNull(boughtItem);
        Assert.notNull(itemPrototype);

        shoppingListManager.remove(boughtItem);
        final Collection shoppingListItems = createSupplyItems(boughtItem
                .getQuantity(), itemPrototype);
        supplyManager.addAll(shoppingListItems);
    }

    /**
     * Creates a number of {@link ExpirableItem}s.
     * 
     * @param prototype A prototype for the items to create. != null
     * @param quantity The number of items to create. > 0
     * @return The created {@link ExpirableItem}s. != null
     */
    public Collection createSupplyItems(final int quantity,
                                        final ExpirableItem prototype)
    {
        final ArrayList createdItems = new ArrayList(quantity);
        createdItems.add(prototype);

        for (int i = 1; i < quantity; i++)
        {
            createdItems.add(new ExpirableItem(prototype));
        }
        return createdItems;
    }
}
