package net.sf.housekeeper.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.easymock.MockControl;
import org.springframework.context.ApplicationContext;

import junit.framework.TestCase;

/**
 * Tests {@link net.sf.housekeeper.domain.SupplyManager}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class ItemManagerTest extends TestCase
{

    private SupplyManager manager;
    private MockControl control;
    private ApplicationContext mock;
    
    
    protected void setUp() throws Exception
    {
        control = MockControl.createNiceControl(ApplicationContext.class);
        mock = (ApplicationContext) control.getMock();
        manager = new SupplyManager();
        manager.setApplicationContext(mock);
    }
    
    /**
     * Tests if an added item is contained in the list.
     *
     */
    public void testAdd()
    {
        final ExpirableItem item = new ExpirableItem();
        manager.add(item);
        final boolean isItemInList = manager.getAllItems().contains(item);
        assertTrue("The item added cannot be found in the list", isItemInList);
    }

    /**
     * Tests if supply list is not null.
     *
     */
    public void testGetSupplyList()
    {
        final List supply = manager.getAllItems();
        assertNotNull("getSupplyList() returns null", supply);
    }

    /**
     * Tests replacement of all items.
     *
     */
    public void testReplaceAll()
    {
        final ExpirableItem oldItem = new ExpirableItem();
        manager.add(oldItem);

        final Collection col = new ArrayList();
        final ExpirableItem newItem = new ExpirableItem();
        newItem.setName("NewItem");
        col.add(newItem);
        manager.replaceAll(col);

        assertFalse("Manager still knows old item", manager.getAllItems()
                .contains(oldItem));
        assertTrue("Manager does not know new item", manager.getAllItems()
                .contains(newItem));
    }

    /**
     * Tests the removal of items.
     *
     */
    public void testRemove()
    {
        final ExpirableItem item = new ExpirableItem();
        manager.add(item);
        manager.remove(item);

        final boolean isItemInList = manager.getAllItems().contains(item);
        assertFalse("The manager still knows the removed item", isItemInList);
    }

}