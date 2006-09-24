package de.berlios.housekeeper.webapp.action;

import de.berlios.housekeeper.webapp.action.BasePageTestCase;
import de.berlios.housekeeper.model.StockItem;

public class StockItemFormTest extends BasePageTestCase {
    private StockItemForm bean;

    protected void setUp() throws Exception {    
        super.setUp();
        bean = (StockItemForm) getManagedBean("stockItemForm");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        bean = null;
    }

    public void testAdd() throws Exception {
        StockItem stockItem = new StockItem();

        // set required fields
        stockItem.setName("YcDjKmTjZlVlApMkLqKxIdNhQwPzYbDeJvFgZkHrLpUdNyCuLp");

        bean.setStockItem(stockItem);

        assertEquals(bean.save(), "list");
        assertFalse(bean.hasErrors());
    }

    public void testEdit() throws Exception {
        log.debug("testing edit...");
        bean.setId("1");

        assertEquals(bean.edit(), "edit");
        assertNotNull(bean.getStockItem());
        assertFalse(bean.hasErrors());
    }

    public void testSave() {
        bean.setId("1");

        assertEquals(bean.edit(), "edit");
        assertNotNull(bean.getStockItem());
        StockItem stockItem = bean.getStockItem();

        // update required string fields 
        stockItem.setName("YmGiYiHsNzByUwCtHjGpLfYkMfPyAoWsGhSyOvSvYkIpMgIvQb");

        bean.setStockItem(stockItem);

        assertEquals(bean.save(), "edit");
        assertFalse(bean.hasErrors());
    }

    public void testRemove() throws Exception {
        StockItem stockItem = new StockItem();
        stockItem.setId(new Long("2"));
        bean.setStockItem(stockItem);

        assertEquals(bean.delete(), "list");
        assertFalse(bean.hasErrors());
    }
}
