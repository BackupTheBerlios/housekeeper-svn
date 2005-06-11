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

import java.util.Collection;
import java.util.Date;

import net.sf.housekeeper.testutils.DataGenerator;
import junit.framework.TestCase;

/**
 * Tests for {@link net.sf.housekeeper.domain.HighLevelManager}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class HighLevelManagerTest extends TestCase
{

    private static final String TESTITEM_NAME = "TestItem";

    private HighLevelManager    highLevelManager;

    private ShoppingListItem    item;

    private ItemManager         shoppingListManager;

    private ItemManager         supplyManager;

    /**
     * Tests a normal buy scenario.
     */
    public void testBuy()
    {
        shoppingListManager.add(item);
        highLevelManager.buy(item);

        final boolean itemWasRemovedFromShoppingList = !shoppingListManager
                .exists(item);
        assertTrue(itemWasRemovedFromShoppingList);
        final boolean itemAddedToSupply = supplyManager
                .findItemWithName(TESTITEM_NAME) != null;
        assertTrue(itemAddedToSupply);
    }

    /**
     * Tests handling of trying to buy an item which is not on the shopping
     * list.
     */
    public void testBuyItemNotInShoppingList()
    {
        try
        {
            highLevelManager.buy(item);
            fail("IllegalArgumentException must be thrown if item doesn't exist in shopping list");
        } catch (IllegalArgumentException e)
        {
            //expected behaviour
        }
    }

    /**
     * Tests if
     * {@link HighLevelManager#createSupplyItems(ShoppingListItem, Date)}
     * creates the correct number of objects.
     */
    public void testCreateSupplyItems()
    {
        final int quantity = 3;
        item.setQuantity(quantity);
        final Date date = new Date();
        final Collection createdItems = highLevelManager
                .createSupplyItems(item, date);

        assertEquals(createdItems.size(), quantity);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        item = new ShoppingListItem();
        item.setName(TESTITEM_NAME);
        shoppingListManager = DataGenerator.createEmptyItemManager();
        supplyManager = DataGenerator.createEmptyItemManager();
        highLevelManager = new HighLevelManager();
        highLevelManager.setShoppingListManager(shoppingListManager);
        highLevelManager.setSupplyManager(supplyManager);
    }

}
