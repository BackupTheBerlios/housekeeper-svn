package net.sf.housekeeper.domain;

import javax.swing.ListModel;

import junit.framework.TestCase;


/**
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class FoodItemManagerTest extends TestCase
{

    public void testGetSupply()
    {
        final ListModel supply = FoodItemManager.INSTANCE.getSupply();
        assertNotNull("getSupply() returns null", supply);
    }

}
