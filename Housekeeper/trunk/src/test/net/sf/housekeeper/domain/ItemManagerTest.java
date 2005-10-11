package net.sf.housekeeper.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.housekeeper.domain.inmemory.InMemoryItemDAO;

import org.easymock.MockControl;
import org.springframework.context.ApplicationContext;

import junit.framework.TestCase;

/**
 * Tests {@link net.sf.housekeeper.domain.ItemDAO}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class ItemManagerTest extends TestCase
{

    private InMemoryItemDAO manager;

    private MockControl control;

    private ApplicationContext mock;

    protected void setUp() throws Exception
    {
        control = MockControl.createNiceControl(ApplicationContext.class);
        mock = (ApplicationContext) control.getMock();
        manager = new InMemoryItemDAO();
        manager.setApplicationContext(mock);
    }

    /**
     * Tests if an added item is contained in the list.
     */
    public void testAdd()
    {
        final ExpirableItem item = new ExpirableItem();
        manager.store(item);
        final boolean isItemInList = manager.findAll().contains(item);
        assertTrue("The item added cannot be found in the list", isItemInList);
    }

    /**
     * Tests if supply list is not null.
     */
    public void testGetSupplyList()
    {
        final List supply = manager.findAll();
        assertNotNull("getSupplyList() returns null", supply);
    }

    /**
     * Tests replacement of all items.
     */
    public void testReplaceAll()
    {
        final ExpirableItem oldItem = new ExpirableItem();
        manager.store(oldItem);

        final Collection<Item> col = new ArrayList<Item>();
        final ExpirableItem newItem = new ExpirableItem();
        newItem.setName("NewItem");
        col.add(newItem);
        manager.deleteAll();
        manager.store(col);

        assertFalse("Manager still knows old item", manager.findAll()
                .contains(oldItem));
        assertTrue("Manager does not know new item", manager.findAll()
                .contains(newItem));
    }

    /**
     * Tests the removal of items.
     */
    public void testRemove()
    {
        final ExpirableItem item = new ExpirableItem();
        manager.store(item);
        manager.delete(item);

        final boolean isItemInList = manager.findAll().contains(item);
        assertFalse("The manager still knows the removed item", isItemInList);
    }

}