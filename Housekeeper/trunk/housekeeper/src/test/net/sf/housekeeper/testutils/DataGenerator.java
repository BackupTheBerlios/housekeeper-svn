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
 * http://housekeeper.sourceforge.net
 */

package net.sf.housekeeper.testutils;

import java.util.Calendar;

import net.sf.housekeeper.domain.ExpiringItem;
import net.sf.housekeeper.domain.Household;

/**
 * A Factory for various domain objects and XML documents.
 * 
 * 
 * @author Adrian Gygax.
 * @version $Revision$, $Date$
 */
public final class DataGenerator
{

    /**
     * Prefix for the test data within the classpath.
     */
    private static final String prefix               = "/net/sf/housekeeper/persistence/";

    /**
     * A file which is not XML.
     */
    public static final String  NOT_AN_XML_FILE      = prefix
                                                             + "notAnXmlFile.xml";

    /**
     * Test data for document version 2.
     */
    public static final String  VERSION2_DATA        = prefix + "data_v2.xml";

    /**
     * Test data for document version 3.
     */
    public static final String  VERSION3_DATA        = prefix + "data_v3.xml";
    
    /**
     * Test data for document version 4.
     */
    public static final String  VERSION4_DATA        = prefix + "data_v4.xml";

    /**
     * Test data for the current version.
     */
    public static final String  CURRENT_VERSION_DATA = VERSION4_DATA;

    private DataGenerator()
    {

    }

    /**
     * Creates a {@link Household}object which is populated with a complex and
     * a simple {@link ExpiringItem}.
     * 
     * @return Not null.
     */
    public static Household createHousehold()
    {
        final Household household = new Household();
        household.getItemManager().add(createComplexItem());
        household.getItemManager().add(createSimpleItem());

        return household;
    }

    /**
     * Creates a {@link ExpiringItem}object which has all attributes set to a value.
     * 
     * @return Not null.
     */
    public static ExpiringItem createComplexItem()
    {
        final String itemName = "aName";
        final String itemQuantity = "aQuantity";

        final Calendar itemDate = Calendar.getInstance();
        final int day = 23;
        final int month = 5;
        final int year = 2004;
        itemDate.set(year, month, day, 15, 22);

        final ExpiringItem item = new ExpiringItem(itemName, itemQuantity, itemDate.getTime());

        return item;
    }

    /**
     * Creates a {@link ExpiringItem}object which has only its name set.
     * 
     * @return Not null.
     */
    public static ExpiringItem createSimpleItem()
    {
        final String itemName = "aName";

        final ExpiringItem item = new ExpiringItem(itemName, null, null);

        return item;
    }
}