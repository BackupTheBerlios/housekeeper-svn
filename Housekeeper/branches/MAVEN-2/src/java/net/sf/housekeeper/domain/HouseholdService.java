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
public final class HouseholdService
{

    private ItemDAO<ExpirableItem> supplyDAO;

    private ItemDAO<ShoppingListItem> shoppingListDAO;

    /**
     * @param shoppingListDAO The shoppingListDAO to set.
     */
    public void setShoppingListDAO(
            ItemDAO<ShoppingListItem> shoppingListDAO)
    {
        this.shoppingListDAO = shoppingListDAO;
    }

    /**
     * @param supplyDAO The supplyDAO to set.
     */
    public void setSupplyDAO(ItemDAO<ExpirableItem> supplyDAO)
    {
        this.supplyDAO = supplyDAO;
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
        Assert.notNull(supplyDAO);
        Assert.notNull(shoppingListDAO);
        Assert.notNull(boughtItem);
        Assert.notNull(itemPrototype);

        shoppingListDAO.delete(boughtItem);
        final Collection<ExpirableItem> shoppingListItems = createSupplyItems(
                boughtItem.getQuantity(), itemPrototype);
        supplyDAO.store(shoppingListItems);
    }

    /**
     * Creates a number of {@link ExpirableItem}s.
     * 
     * @param prototype A prototype for the items to create. != null
     * @param quantity The number of items to create. > 0
     * @return The created {@link ExpirableItem}s. != null
     */
    public ArrayList<ExpirableItem> createSupplyItems(final int quantity,
            final ExpirableItem prototype)
    {
        final ArrayList<ExpirableItem> createdItems = new ArrayList<ExpirableItem>(
                quantity);
        createdItems.add(prototype);

        for (int i = 1; i < quantity; i++)
        {
            createdItems.add(new ExpirableItem(prototype));
        }
        return createdItems;
    }
}
