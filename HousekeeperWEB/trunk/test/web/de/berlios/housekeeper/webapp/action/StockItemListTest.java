package de.berlios.housekeeper.webapp.action;

import de.berlios.housekeeper.webapp.action.BasePageTestCase;

public class StockItemListTest extends BasePageTestCase {
    private StockItemList bean;
    protected void setUp() throws Exception {    
        super.setUp();
        bean = (StockItemList) getManagedBean("stockItemList");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        bean = null;
    }
    public void testSearch() throws Exception {
        assertTrue(bean.getStockItems().size() >= 1);
        assertFalse(bean.hasErrors());
    }
}
