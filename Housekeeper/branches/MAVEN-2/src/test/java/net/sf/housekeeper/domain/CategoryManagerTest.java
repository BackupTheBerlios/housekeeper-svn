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

import java.util.ArrayList;
import java.util.List;

import net.sf.housekeeper.testutils.DataGenerator;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Tests for {@link net.sf.housekeeper.domain.CategoryDAO}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoryManagerTest extends TestCase
{

    private CategoryDAO dao;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        dao = DataGenerator.createEmptyCategoryManager();

        super.setUp();
    }

    public void testAdd()
    {
        final Category root = new Category();
        final Category child = new Category();

        dao.store(root);
        child.setParent(root);
        dao.store(child);

        Assert.assertTrue(dao.findAllTopLevelCategories().contains(root));
        Assert.assertFalse(dao.findAllTopLevelCategories().contains(child));
        Assert.assertTrue(dao.findAll().contains(root));
        Assert.assertTrue(dao.findAll().contains(child));
    }

    public void testAddNonLeafCategory()
    {
        final Category root = new Category();
        final Category child = new Category();
        root.addChild(child);

        try
        {
            dao.store(root);
            fail("Must throw exception");
        } catch (IllegalArgumentException e)
        {
            return;
        }
    }

    public void testRemoveRoot()
    {
        final Category root = new Category();
        dao.store(root);
        dao.delete(root);

        Assert.assertTrue(!dao.findAll().contains(root));
        Assert.assertTrue(!dao.findAllTopLevelCategories().contains(root));
    }

    public void testRemoveNonRoot()
    {
        final Category root = new Category();
        dao.store(root);
        final Category child = new Category();
        root.addChild(child);
        dao.store(child);

        dao.delete(child);

        Assert.assertTrue(!dao.findAll().contains(child));
    }

    public void testRecursiveRemove()
    {
        final Category root = new Category();
        dao.store(root);
        final Category child = new Category();
        child.setParent(root);
        dao.store(child);

        dao.delete(root);

        Assert.assertFalse(dao.findAllTopLevelCategories().contains(child));
    }

    public void testUpdateParentNonNullToNonNull()
    {
        final Category oldParent = new Category();
        final Category newParent = new Category();
        final Category child = new Category();

        // Setup
        dao.store(oldParent);
        dao.store(newParent);
        oldParent.addChild(child);
        dao.store(child);

        child.setParent(newParent);
        dao.store(child);

        Assert.assertFalse(oldParent.contains(child));
        Assert.assertTrue(newParent.hasChild(child));
        Assert.assertTrue(child.getParent() == newParent);
    }

    public void testUpdateParentNonNullToNull()
    {
        final Category oldParent = new Category();
        final Category child = new Category();

        // Setup
        dao.store(oldParent);
        oldParent.addChild(child);
        dao.store(child);

        child.setParent(null);
        dao.store(child);

        Assert.assertFalse(oldParent.hasChild(child));
        Assert.assertTrue(child.getParent() == null);
        Assert.assertTrue(dao.findAllTopLevelCategories().contains(child));
    }

    public void testUpdateParentNullToNonNull()
    {
        final Category newParent = new Category();
        final Category child = new Category();

        // Setup
        dao.store(newParent);
        dao.store(child);

        child.setParent(newParent);
        dao.store(child);

        Assert.assertTrue(newParent.hasChild(child));
        Assert.assertTrue(child.getParent() == newParent);
        Assert.assertFalse(dao.findAllTopLevelCategories().contains(child));
    }

    public void testGetAllCategoriesInclusiveNull()
    {
        assertTrue(dao.findAllInclusiveNull().contains(Category.NULL_OBJECT));
    }

    public void testGetAllCategoriesExcept()
    {
        final Category a = new Category();
        final Category b = new Category();
        final Category child = new Category();
        child.setParent(b);
        dao.store(a);
        dao.store(b);
        dao.store(child);

        final List l = dao.findAllExcept(b);
        assertTrue(l.contains(a));
        assertFalse(l.contains(b));
        assertFalse(l.contains(child));
    }

    public void testReplaceAll()
    {
        final Category a = new Category();
        dao.store(a);

        final ArrayList<Category> newL = new ArrayList<Category>();
        final Category b = new Category();
        newL.add(b);

        dao.deleteAll();
        dao.store(newL);

        final List<Category> l = dao.findAll();
        assertFalse(l.contains(a));
        assertTrue(l.contains(b));
    }

}
