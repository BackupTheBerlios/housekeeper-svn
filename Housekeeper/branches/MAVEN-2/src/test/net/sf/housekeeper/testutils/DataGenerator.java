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

package net.sf.housekeeper.testutils;

import java.util.Calendar;

import net.sf.housekeeper.domain.ExpirableItem;
import net.sf.housekeeper.domain.ItemDAO;
import net.sf.housekeeper.domain.ShoppingListItem;
import net.sf.housekeeper.domain.inmemory.InMemoryCategoryDAO;
import net.sf.housekeeper.domain.inmemory.InMemoryItemDAO;

import org.easymock.MockControl;
import org.springframework.context.ApplicationContext;

/**
 * A Factory for various domain objects and XML documents.
 * 
 * @author Adrian Gygax.
 * @version $Revision$, $Date$
 */
public final class DataGenerator
{

    /**
     * Prefix for the test data within the classpath.
     */
    private static final String prefix = "/net/sf/housekeeper/persistence/";

    /**
     * A file which is not XML.
     */
    public static final String NOT_AN_XML_FILE = prefix + "notAnXmlFile.xml";

    /**
     * Test data for document version 2.
     */
    public static final String VERSION2_DATA = prefix + "data_v2.xml";

    /**
     * Test data for document version 3.
     */
    public static final String VERSION3_DATA = prefix + "data_v3.xml";

    /**
     * Test data for document version 4.
     */
    public static final String VERSION4_DATA = prefix + "data_v4.xml";

    /**
     * Test data for the current version.
     */
    public static final String CURRENT_VERSION_DATA = VERSION4_DATA;

    private DataGenerator()
    {

    }

    /**
     * Creates a {@link ExpirableItem}object which has all attributes set to a
     * value.
     * 
     * @return Not null.
     */
    public static ExpirableItem createComplexItem()
    {
        final String itemName = "aName";
        final String itemQuantity = "aQuantity";

        final Calendar itemDate = Calendar.getInstance();
        final int day = 23;
        final int month = 5;
        final int year = 2004;
        itemDate.set(year, month, day, 15, 22);

        final ExpirableItem item = new ExpirableItem(itemName, itemQuantity,
                itemDate.getTime());

        return item;
    }

    /**
     * Creates a {@link ExpirableItem}object which has only its name set.
     * 
     * @return Not null.
     */
    public static ExpirableItem createSimpleItem()
    {
        final String itemName = "aName";

        final ExpirableItem item = new ExpirableItem(itemName, null, null);

        return item;
    }

    /**
     * Creates an empty ItemManager with mocked dependencies.
     * 
     * @return != null
     */
    public static InMemoryItemDAO<ExpirableItem> createEmptySupplyManager()
    {
        InMemoryItemDAO<ExpirableItem> manager = new InMemoryItemDAO<ExpirableItem>();
        manager.setApplicationContext(createApplicationContextMock());
        return manager;
    }

    /**
     * Creates an empty ItemManager with mocked dependencies.
     * 
     * @return != null
     */
    public static InMemoryItemDAO<ShoppingListItem> createEmptyShoppingListManager()
    {
        InMemoryItemDAO<ShoppingListItem> manager = new InMemoryItemDAO<ShoppingListItem>();
        manager.setApplicationContext(createApplicationContextMock());
        return manager;
    }

    /**
     * Creates an empty ItemManager with mocked dependencies.
     * 
     * @return != null
     */
    public static InMemoryCategoryDAO createEmptyCategoryManager()
    {
        InMemoryCategoryDAO manager = new InMemoryCategoryDAO();
        manager.setApplicationContext(createApplicationContextMock());
        manager.setSupplyDAO(createItemManagerMock());
        return manager;
    }

    public static ApplicationContext createApplicationContextMock()
    {
        MockControl control = MockControl
                .createNiceControl(ApplicationContext.class);
        ApplicationContext mock = (ApplicationContext) control.getMock();
        return mock;
    }

    public static ItemDAO createItemManagerMock()
    {
        MockControl control = MockControl.createNiceControl(ItemDAO.class);
        ItemDAO mock = (ItemDAO) control.getMock();
        return mock;
    }
}
