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
    
    public void testAddExisting()
    {
        final Category c = new Category();
        manager.add(c);
        try
        {
            manager.add(c);
            fail("Must throw exception");
        } catch (IllegalArgumentException e)
        {
            //Expected
            return;
        }
    }
    
    public void testAdd()
    {
        final Category root = new Category();
        final Category child = new Category();
        
        manager.add(root);
        child.setParent(root);
        manager.add(child);
        
        Assert.assertTrue(manager.getTopLevelCategories().contains(root));
        Assert.assertFalse(manager.getTopLevelCategories().contains(child));
        Assert.assertTrue(manager.getAllCategories().contains(root));
        Assert.assertTrue(manager.getAllCategories().contains(child));
    }
    
    public void testAddNonLeafCategory()
    {
        final Category root = new Category();
        final Category child = new Category();
        root.addChild(child);
        
        try
        {
            manager.add(root);
            fail("Must throw exception");
        } catch (IllegalArgumentException e)
        {
            return;
        }
    }
    
    public void testRemoveRoot()
    {
        final Category root = new Category();
        manager.add(root);
        manager.remove(root);
        
        Assert.assertTrue(!manager.getAllCategories().contains(root));
        Assert.assertTrue(!manager.getTopLevelCategories().contains(root));
    }
    
    public void testRemoveNonRoot()
    {
        final Category root = new Category();
        manager.add(root);
        final Category child = new Category();
        root.addChild(child);
        manager.add(child);
        
        manager.remove(child);
        
        Assert.assertTrue(!manager.getAllCategories().contains(child));
    }
    
    public void testRecursiveRemove()
    {
        final Category root = new Category();
        manager.add(root);
        final Category child = new Category();
        root.addChild(child);
        manager.add(child);
        
        manager.remove(root);
        
        Assert.assertTrue(!manager.getAllCategories().contains(child));
    }
    
    public void testUpdateParentNonNullToNonNull()
    {
        final Category oldParent = new Category();
        final Category newParent = new Category();
        final Category child = new Category();
        
        //Setup
        manager.add(oldParent);
        manager.add(newParent);
        oldParent.addChild(child);
        manager.add(child);
        
        
        child.setParent(newParent);
        manager.update(child, oldParent);
        
        Assert.assertFalse(oldParent.contains(child));
        Assert.assertTrue(newParent.hasChild(child));
        Assert.assertTrue(child.getParent() == newParent);
    }
    
    public void testUpdateParentNonNullToNull()
    {
        final Category oldParent = new Category();
        final Category child = new Category();
        
        //Setup
        manager.add(oldParent);
        oldParent.addChild(child);
        manager.add(child);
        
        child.setParent(null);
        manager.update(child, oldParent);

        Assert.assertFalse(oldParent.hasChild(child));
        Assert.assertTrue(child.getParent() == null);
        Assert.assertTrue(manager.getTopLevelCategories().contains(child));
    }
    
    public void testUpdateParentNullToNonNull()
    {
        final Category newParent = new Category();
        final Category child = new Category();
        
        //Setup
        manager.add(newParent);
        manager.add(child);
        
        child.setParent(newParent);
        manager.update(child, null);

        Assert.assertTrue(newParent.hasChild(child));
        Assert.assertTrue(child.getParent() == newParent);
        Assert.assertFalse(manager.getTopLevelCategories().contains(child));
    }

}
