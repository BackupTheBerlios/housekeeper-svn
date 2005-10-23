package net.sf.housekeeper.domain;

import java.util.Calendar;
import java.util.Date;

import net.sf.housekeeper.testutils.DataGenerator;

import junit.framework.TestCase;

/**
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ExpirableItemTest extends TestCase
{

    /**
     * Tests if the attributes are set to the correct values using the
     * constructor.
     */
    public void testExpiringItemStringStringDate()
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

        final Calendar returnedItemDate = Calendar.getInstance();
        returnedItemDate.setTime(item.getExpiry());
        final int returnedDay = returnedItemDate.get(Calendar.DAY_OF_MONTH);
        final int returnedMonth = returnedItemDate.get(Calendar.MONTH);
        final int returnedYear = returnedItemDate.get(Calendar.YEAR);

        assertEquals(itemName, item.getName());
        assertEquals(itemQuantity, item.getDescription());
        assertEquals(day, returnedDay);
        assertEquals(month, returnedMonth);
        assertEquals(year, returnedYear);
    }

    /**
     * Tests the setting of a normal expiry date.
     */
    public void testSetExpiry()
    {
        final ExpirableItem item = new ExpirableItem();
        final Calendar nowCal = Calendar.getInstance();

        item.setExpiry(nowCal.getTime());

        final Calendar itemCal = Calendar.getInstance();
        itemCal.setTime(item.getExpiry());

        assertEquals(nowCal.get(Calendar.DAY_OF_MONTH), itemCal
                .get(Calendar.DAY_OF_MONTH));
        assertEquals(nowCal.get(Calendar.MONTH), itemCal.get(Calendar.MONTH));
        assertEquals(nowCal.get(Calendar.YEAR), itemCal.get(Calendar.YEAR));
    }

    /**
     * Tests the unsetting of an expiry date.
     */
    public void testUnsetExpiry()
    {
        final ExpirableItem item = new ExpirableItem();
        item.setExpiry(new Date());
        item.setExpiry(null);
        assertNull(item.getExpiry());
    }

    /**
     * Tests setting a normal name.
     */
    public void testSetNormalName()
    {
        // Test normal
        final ExpirableItem item = new ExpirableItem();
        final String name = "aname";
        item.setName(name);
        assertEquals(name, item.getName());
    }

    /**
     * Tests setting the name to null.
     */
    public void testSetNullName()
    {
        try
        {
            final ExpirableItem item = new ExpirableItem();
            item.setName(null);
            fail("Item.setName(String) must not allow null as parameter");
        } catch (IllegalArgumentException e)
        {
            // No code as the exception is expected be thrown.
        }
    }

    /**
     * Tests setting the quantity.
     */
    public void testSetQuantity()
    {
        final ExpirableItem item = new ExpirableItem();
        final String quantity = "quant";
        item.setDescription(quantity);
        assertEquals(quantity, item.getDescription());
    }

    /**
     * Tests unsetting the quantity.
     */
    public void testUnsetQuantity()
    {
        final ExpirableItem item = new ExpirableItem();
        item.setDescription("");
        assertNull(item.getDescription());
    }

    /**
     * Tests if isEqual() returns true if two items are euqal.
     */
    public void testEquality()
    {
        final ExpirableItem item = DataGenerator.createComplexItem();
        final ExpirableItem item2 = DataGenerator.createComplexItem();

        final boolean itemEqualsItem2 = item.isEqual(item2);
        final boolean item2EqualsItem = item2.isEqual(item);

        assertTrue("Items are reported as not equal but they are.",
                itemEqualsItem2);
        assertTrue("Items are reported as not equal but they are.",
                item2EqualsItem);
    }

    /**
     * Tests equals() to return false if two items are not equal.
     */
    public void testInequality()
    {
        final ExpirableItem item = DataGenerator.createComplexItem();

        final ExpirableItem differentName = DataGenerator.createComplexItem();
        differentName.setName("OtherName");
        final boolean isEqualName = item.equals(differentName);
        assertFalse(isEqualName);

        final ExpirableItem differentQuantity = DataGenerator
                .createComplexItem();
        differentQuantity.setDescription("OtherQuantity");
        final boolean isEqualQuantity = item.equals(differentQuantity);
        assertFalse(isEqualQuantity);

        final ExpirableItem differentExpiry = DataGenerator.createComplexItem();
        differentExpiry.setExpiry(new Date(2));
        final boolean isEqualDifExpiry = item.equals(differentExpiry);
        assertFalse(isEqualDifExpiry);

        final boolean equalsNull = item.equals(null);
        assertFalse(equalsNull);
    }

    /**
     * Tests if an item gets cloned properly.
     */
    public void testClone()
    {
        final ExpirableItem originalItem = DataGenerator.createComplexItem();
        final ExpirableItem clonedItem = new ExpirableItem(originalItem);
        assertTrue(clonedItem.isEqual(originalItem));
    }

    public void testExpiringItemItem()
    {
        final Item i = new Item();
        final ExpirableItem expItem = new ExpirableItem(i);

        assertEquals(i.getName(), expItem.getName());
        assertEquals(i.getDescription(), expItem.getDescription());
        assertEquals(i.getCategory(), expItem.getCategory());
    }
}
