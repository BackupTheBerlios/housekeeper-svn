package net.sf.housekeeper.domain;

import java.util.Calendar;

import junit.framework.TestCase;

/**
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class FoodItemTest extends TestCase
{

    /*
     * Class under test for void FoodItem(String, String, Date)
     */
    public final void testFoodItemStringStringDate()
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

    /*
     * Class under test for void FoodItem()
     */
    public final void testFoodItem()
    {

    }

    public final void testSetExpiry()
    {
        //Test normal operation
        final FoodItem item = new FoodItem();
        final Calendar nowCal = Calendar.getInstance();

        item.setExpiry(nowCal.getTime());

        final Calendar itemCal = Calendar.getInstance();
        itemCal.setTime(item.getExpiry());

        assertEquals(nowCal.get(Calendar.DAY_OF_MONTH), itemCal
                .get(Calendar.DAY_OF_MONTH));
        assertEquals(nowCal.get(Calendar.MONTH), itemCal.get(Calendar.MONTH));
        assertEquals(nowCal.get(Calendar.YEAR), itemCal.get(Calendar.YEAR));

        //Test null
        item.setExpiry(null);
        assertNull(item.getExpiry());
    }

    public final void testSetName()
    {
        //Test normal
        final FoodItem item = new FoodItem();
        final String name = "aname";
        item.setName(name);
        assertEquals(name, item.getName());

        //Test null
//        try
//        {
//            item.setName(null);
//            fail("Item.setName(String) must not allow null as parameter");
//        } catch (AssertionError e)
//        {
//            //No code as the exception SHOULD be thrown.
//        }
    }

    public final void testSetQuantity()
    {
        //Test normal
        final FoodItem item = new FoodItem();
        final String quantity = "quant";
        item.setQuantity(quantity);
        assertEquals(quantity, item.getQuantity());

        //Test null
        item.setQuantity(null);
        assertEquals("", item.getQuantity());
    }

}