package de.berlios.housekeeper.webapp.action;

import de.berlios.housekeeper.webapp.action.BasePageTestCase;

public class CategoryListTest extends BasePageTestCase {
    private CategoryList bean;
    protected void setUp() throws Exception {    
        super.setUp();
        bean = (CategoryList) getManagedBean("categoryList");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        bean = null;
    }
    public void testSearch() throws Exception {
        assertTrue(bean.getCategorys().size() >= 1);
        assertFalse(bean.hasErrors());
    }
}
