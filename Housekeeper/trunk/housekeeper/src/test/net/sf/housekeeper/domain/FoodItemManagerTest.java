package net.sf.housekeeper.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class FoodItemManagerTest extends TestCase
{

    /**
     * Tests if an added item is contained in the list.
     *
     */
    public void testAdd()
    {
        final FoodManager manager = new FoodManager();
        final Food item = new Food();
        manager.addConvenienceFood(item);
        final boolean isItemInList = manager.getSupplyList().contains(item);
        assertTrue("The item added cannot be found in the list", isItemInList);
    }

    /**
     * Tests if supply list is not null.
     *
     */
    public void testGetSupplyList()
    {
        final FoodManager manager = new FoodManager();
        final List supply = manager.getSupplyList();
        assertNotNull("getSupplyList() returns null", supply);
    }

    /**
     * Tests replacement of all items.
     *
     */
    public void testReplaceAll()
    {
        final FoodManager manager = new FoodManager();
        final Food oldItem = new Food();
        manager.addConvenienceFood(oldItem);

        final Collection col = new ArrayList();
        final Food newItem = new Food();
        newItem.setName("NewItem");
        col.add(newItem);
        manager.replaceAll(col);

        assertFalse("Manager still knows old item", manager.getSupplyList()
                .contains(oldItem));
        assertTrue("Manager does not know new item", manager.getSupplyList()
                .contains(newItem));
    }

    /**
     * Tests the removal of items.
     *
     */
    public void testRemove()
    {
        final FoodManager manager = new FoodManager();
        final Food item = new Food();
        manager.addConvenienceFood(item);
        manager.remove(item);

        final boolean isItemInList = manager.getSupplyList().contains(item);
        assertFalse("The manager still knows the removed item", isItemInList);
    }

}