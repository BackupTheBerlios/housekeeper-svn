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

package net.sf.housekeeper.domain;

import net.sf.housekeeper.testutils.DataGenerator;
import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * Tests for {@link net.sf.housekeeper.domain.CategoryManager}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoryManagerTest extends TestCase
{

    private CategoryManager manager;
    
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        manager = DataGenerator.createEmptyCategoryManager();
        
        super.setUp();
    }
    
    public void testUpdate()
    {
        final Category oldParent = new Category();
        final Category newParent = new Category();
        final Category child = new Category();
        
        oldParent.addChild(child);
        child.setParent(newParent);
        manager.update(child, oldParent);
        
        Assert.assertFalse(oldParent.contains(child));
        Assert.assertTrue(newParent.hasChild(child));
        Assert.assertTrue(child.getParent() == newParent);
    }
}
