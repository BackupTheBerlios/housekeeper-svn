package de.berlios.housekeeper.webapp.action;

import de.berlios.housekeeper.model.User;

public class UserFormTest extends BasePageTestCase {
    private UserForm bean;

    protected void setUp() throws Exception {    
        super.setUp();
        bean = (UserForm) getManagedBean("userForm");
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
        bean = null;
    }
    
    public void testEdit() throws Exception {
        bean.setUsername("tomcat");
        assertEquals(bean.edit(), "editProfile");
        assertNotNull(bean.getUser().getUsername());
        assertFalse(bean.hasErrors());
    }

    public void testSave() throws Exception {
        user.setPassword("tomcat");
        user.setConfirmPassword("tomcat");
        bean.setUser(user);

        assertEquals(bean.save(), "mainMenu");
        assertNotNull(bean.getUser());
        assertFalse(bean.hasErrors());
    }
    
    public void testRemove() throws Exception {
        User user2Delete = new User();
        user2Delete.setId(new Long(2));
        bean.setUser(user2Delete);
        assertEquals(bean.delete(), "list");
        assertFalse(bean.hasErrors());
    }
}
