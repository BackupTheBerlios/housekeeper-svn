package net.sf.housekeeper.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.ListModel;

import junit.framework.TestCase;

/**
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class FoodItemManagerTest extends TestCase
{

    public void testAdd()
    {
        final FoodItemManager manager = new FoodItemManager();
        final FoodItem item = new FoodItem();
        manager.add(item);
        final boolean isItemInList = manager.getSupplyList().contains(item);
        assertTrue("The item added cannot be found in the list", isItemInList);
    }

    public void testGetSupplyList()
    {
        final FoodItemManager manager = new FoodItemManager();
        final List supply = manager.getSupplyList();
        assertNotNull("getSupplyList() returns null", supply);
    }

    public void testGetSupplyListModel()
    {
        final FoodItemManager manager = new FoodItemManager();
        final ListModel supply = manager.getSupplyListModel();
        assertNotNull("getSupplyListModel() returns null", supply);
    }

    public void testReplaceAll()
    {
        final FoodItemManager manager = new FoodItemManager();
        final FoodItem oldItem = new FoodItem();
        manager.add(oldItem);

        final Collection col = new ArrayList();
        final FoodItem newItem = new FoodItem();
        col.add(newItem);
        manager.replaceAll(col);

        assertFalse("Manager still knows old item", manager.getSupplyList()
                .contains(oldItem));
        assertTrue("Manager does not know new item", manager.getSupplyList()
                .contains(newItem));
    }

    public void testRemove()
    {
        final FoodItemManager manager = new FoodItemManager();
        final FoodItem item = new FoodItem();
        manager.add(item);
        manager.remove(item);

        final boolean isItemInList = manager.getSupplyList().contains(item);
        assertFalse("The manager still knows the removed item", isItemInList);
    }

}