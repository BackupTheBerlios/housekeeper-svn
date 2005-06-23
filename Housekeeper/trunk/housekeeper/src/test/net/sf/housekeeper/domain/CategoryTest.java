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

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * Test the {@link net.sf.housekeeper.domain.Category} class.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoryTest extends TestCase
{
    
    private Category category;
    
    private Category child;
    
        /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        category = new Category();
        child = new Category();
    }
    
    public void testConstructor()
    {
        Assert.assertNotNull(category.getName());
        Assert.assertNotNull(category.getId());
        Assert.assertNull(category.getParent());
    }
    
    public void testAddChild()
    {       
        category.addChild(child);
        
        boolean isChild = false;
        final Iterator iter = category.getChildrenIterator();
        while (iter.hasNext())
        {
            if (iter.next() == child)
            {
                isChild = true;
            }
        }
        
        Assert.assertTrue(isChild);
        Assert.assertTrue(child.getParent() == category);
    }
    
    public void testAddSameChildTwoTimes()
    {       
        category.addChild(child);
        category.addChild(child);
        
        Assert.assertEquals(1, category.getNumberOfChildren());
    }
    
    public void testContains()
    {
        final Category deepChild = new Category();
        
        category.addChild(child);
        child.addChild(deepChild);
        
        Assert.assertTrue(category.contains(category));
        Assert.assertTrue(category.contains(child));
        Assert.assertTrue(category.contains(deepChild));
    }
    
    public void testGetRecursiveCategories()
    {
        final Category deepChild = new Category();
        category.addChild(child);
        child.addChild(deepChild);
        
        final List allCats = category.getRecursiveCategories();
        Assert.assertEquals(3, allCats.size());
        Assert.assertTrue(allCats.contains(category));
        Assert.assertTrue(allCats.contains(child));
        Assert.assertTrue(allCats.contains(deepChild));
    }
    
    public void testIsChild()
    {
        final Category deepChild = new Category();
        category.addChild(child);
        child.addChild(deepChild);
        
        Assert.assertTrue(category.hasChild(child));
        Assert.assertFalse(category.hasChild(deepChild));
    }

    public void testIsLeaf()
    {
        Assert.assertTrue(category.isLeaf());
        
        category.addChild(child);
        
        Assert.assertFalse(category.isLeaf());
    }
    
    public void testIsTopLevel()
    {
        Assert.assertTrue(child.isTopLevel());
        
        category.addChild(child);
        
        Assert.assertFalse(child.isTopLevel());
    }
    
    public void testRemoveChild()
    {
        category.addChild(child);
        
        category.removeChild(child);
        
        boolean isChild = false;
        final Iterator iter = category.getChildrenIterator();
        while (iter.hasNext())
        {
            if (iter.next() == child)
            {
                isChild = true;
            }
        }
        
        Assert.assertFalse(isChild);
        Assert.assertNull(child.getParent());
    }

}
