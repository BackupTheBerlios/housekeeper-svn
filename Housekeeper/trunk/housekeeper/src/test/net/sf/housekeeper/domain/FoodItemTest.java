package net.sf.housekeeper.domain;

import java.util.Calendar;
import java.util.Date;

import net.sf.housekeeper.testutils.DataGenerator;

import junit.framework.TestCase;

/**
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class FoodItemTest extends TestCase
{

    /**
     * Tests if the attributes are set to the correct values using the
     * constructor.
     */
    public void testFoodItemStringStringDate()
    {
        final String itemName = "aName";
        final String itemQuantity = "aQuantity";

        final Calendar itemDate = Calendar.getInstance();
        final int day = 23;
        final int month = 5;
        final int year = 2004;
        itemDate.set(year, month, day, 15, 22);

        final FoodItem item = new FoodItem(itemName, itemQuantity, itemDate
                .getTime());

        final Calendar returnedItemDate = Calendar.getInstance();
        returnedItemDate.setTime(item.getExpiry());
        final int returnedDay = returnedItemDate.get(Calendar.DAY_OF_MONTH);
        final int returnedMonth = returnedItemDate.get(Calendar.MONTH);
        final int returnedYear = returnedItemDate.get(Calendar.YEAR);

        assertEquals(itemName, item.getName());
        assertEquals(itemQuantity, item.getQuantity());
        assertEquals(day, returnedDay);
        assertEquals(month, returnedMonth);
        assertEquals(year, returnedYear);
    }

    /**
     * Tests the setting of a normal expiry date.
     *  
     */
    public void testSetExpiry()
    {
        final FoodItem item = new FoodItem();
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
        final FoodItem item = new FoodItem();
        item.setExpiry(new Date());
        item.setExpiry(null);
        assertNull(item.getExpiry());
    }

    /**
     * Tests setting a normal name.
     */
    public void testSetNormalName()
    {
        //Test normal
        final FoodItem item = new FoodItem();
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
            final FoodItem item = new FoodItem();
            item.setName(null);
            fail("Item.setName(String) must not allow null as parameter");
        } catch (IllegalArgumentException e)
        {
            //No code as the exception is expected be thrown.
        }
    }

    /**
     * Tests setting the quantity.
     */
    public void testSetQuantity()
    {
        final FoodItem item = new FoodItem();
        final String quantity = "quant";
        item.setQuantity(quantity);
        assertEquals(quantity, item.getQuantity());
    }

    /**
     * Tests unsetting the quantity.
     */
    public void testUnsetQuantity()
    {
        final FoodItem item = new FoodItem();
        item.setQuantity("");
        assertNull(item.getQuantity());
    }

    /**
     * Tests if isEqual() returns true if two items are euqal.
     */
    public void testEquality()
    {
        final FoodItem item = DataGenerator.createComplexItem();
        final FoodItem item2 = DataGenerator.createComplexItem();
        
        final boolean itemEqualsItem2 = item.equals(item2);
        final boolean item2EqualsItem = item2.equals(item);
        
        assertTrue("Items are reported as not equal but they are.", itemEqualsItem2);
        assertTrue("Items are reported as not equal but they are.", item2EqualsItem);
    }
    
    /**
     * Tests equals() to return false if two items are not equal.
     *
     */
    public void testInequality()
    {
        final FoodItem item = DataGenerator.createComplexItem();
        
        final FoodItem differentName = DataGenerator.createComplexItem();
        differentName.setName("OtherName");
        final boolean isEqualName = item.equals(differentName);
        assertFalse(isEqualName);
        
        final FoodItem differentQuantity = DataGenerator.createComplexItem();
        differentQuantity.setQuantity("OtherQuantity");
        final boolean isEqualQuantity = item.equals(differentQuantity);
        assertFalse(isEqualQuantity);
        
        final FoodItem differentExpiry = DataGenerator.createComplexItem();
        differentExpiry.setExpiry(new Date(2));
        final boolean isEqualDifExpiry = item.equals(differentExpiry);
        assertFalse(isEqualDifExpiry);
        
        final boolean equalsNull = item.equals(null);
        assertFalse(equalsNull);
    }

}