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

package net.sf.housekeeper.swing.item;

import junit.framework.TestCase;
import net.sf.housekeeper.domain.ExpirableItem;
import net.sf.housekeeper.domain.ItemManager;
import net.sf.housekeeper.event.SupplyEvent;
import net.sf.housekeeper.testutils.DataGenerator;

import org.easymock.MockControl;
import org.springframework.context.MessageSource;


/**
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class SupplyViewTest extends TestCase
{
    
    private ItemsTableView view;

    protected void setUp()
    {
        view = new ItemsTableView();
        MockControl mockControl = MockControl.createNiceControl(MessageSource.class);
        MessageSource messageSourceMock = (MessageSource)mockControl.getMock();
        mockControl.replay();
        view.initTable(messageSourceMock);
    }
    
    public void testRemoveItem()
    {
        final ExpirableItem item = new ExpirableItem();
        final SupplyEvent event = new SupplyEvent(SupplyEvent.REMOVED, item);
        
        view.addItem(item);
        view.onApplicationEvent(event);
        
        assertFalse(!view.modelContains(item));
    }
}
