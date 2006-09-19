package de.berlios.housekeeper.webapp.action;

import de.berlios.housekeeper.webapp.action.BasePageTestCase;
import de.berlios.housekeeper.model.Category;

public class CategoryFormTest extends BasePageTestCase {
    private CategoryForm bean;

    protected void setUp() throws Exception {    
        super.setUp();
        bean = (CategoryForm) getManagedBean("categoryForm");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        bean = null;
    }

    public void testAdd() throws Exception {
        Category category = new Category();

        // set required fields

        bean.setCategory(category);

        assertEquals(bean.save(), "list");
        assertFalse(bean.hasErrors());
    }

    public void testEdit() throws Exception {
        log.debug("testing edit...");
        bean.setId("1");

        assertEquals(bean.edit(), "edit");
        assertNotNull(bean.getCategory());
        assertFalse(bean.hasErrors());
    }

    public void testSave() {
        bean.setId("1");

        assertEquals(bean.edit(), "edit");
        assertNotNull(bean.getCategory());
        Category category = bean.getCategory();

        // update required string fields 

        bean.setCategory(category);

        assertEquals(bean.save(), "edit");
        assertFalse(bean.hasErrors());
    }

    public void testRemove() throws Exception {
        Category category = new Category();
        category.setId(new Long("2"));
        bean.setCategory(category);

        assertEquals(bean.delete(), "list");
        assertFalse(bean.hasErrors());
    }
}
