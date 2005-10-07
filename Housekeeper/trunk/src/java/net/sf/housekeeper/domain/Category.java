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

import java.rmi.dgc.VMID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.util.Assert;

/**
 * A category for an {@link net.sf.housekeeper.domain.Item}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class Category
{

    /**
     * Category instance which can be used to express "no category".
     */
    public static final Category NULL_OBJECT = new Category();

    private static Category selectedCategory;

    /**
     * Returns the currently selected Category. Needed as a workaround until
     * CommandExecutors can be registered as ApplicationEvent listeners. Then,
     * AbstractNewItemCommandExecutor can listen for selection changes itself.
     * 
     * @return -
     */
    public static Category getSelectedCategory()
    {
        return selectedCategory;
    }

    /**
     * Sets the currently selected Category.
     * 
     * @param selectedCategory -
     */
    public static void setSelectedCategory(Category selectedCategory)
    {
        if (selectedCategory == NULL_OBJECT)
        {
            selectedCategory = null;
        }
        Category.selectedCategory = selectedCategory;
    }

    private LinkedHashSet<Category> children;

    private final String id;

    private String name;

    private Category parent;

    /**
     * Creates a new category with no name and children.
     */
    public Category()
    {
        this("");
    }

    /**
     * Creates a new category with no children.
     * 
     * @param name
     *            The name for the category. Must not be null.
     */
    public Category(final String name)
    {
        Assert.notNull(name);
        this.id = new VMID().toString();
        this.children = new LinkedHashSet<Category>();
        
        setName(name);
    }

    /**
     * Adds a child category and sets the "parent" property of that object to
     * this category.
     * 
     * @param child
     *            The child to add. Must not be null.
     */
    public void addChild(final Category child)
    {
        Assert.isTrue(!this.equals(child));

        child.parent = this;
        children.add(child);
    }

    /**
     * Tests if a category either equals this category or is a child of this
     * category or a sub-category of any depth.
     * 
     * @param cat
     *            The category to test.
     * @return True, if cat is conatained in this category. False otherwise.
     */
    public boolean contains(Category cat)
    {
        if (cat == this)
        {
            return true;
        }

        final Iterator catIter = children.iterator();
        while (catIter.hasNext())
        {
            Category child = (Category) catIter.next();
            if (child.contains(cat))
            {
                return true;
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null)
        {
            return false;
        }
        if (o.getClass() != getClass())
        {
            return false;
        }

        final Category otherCat = (Category) o;
        return this.id.equals(otherCat.getId());
    }

    /**
     * Retuns a list of all recursive children. The first element of the list is
     * this object.
     * 
     * @return != null
     */
    public List<Category> getRecursiveCategories()
    {
        final ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(this);
        final Iterator iter = getChildrenIterator();
        while (iter.hasNext())
        {
            Category element = (Category) iter.next();
            categories.addAll(element.getRecursiveCategories());
        }
        return categories;
    }

    /**
     * Returns an iterator for all children of this category.
     * 
     * @return An iterator for all children. Not null.
     */
    public Iterator<Category> getChildrenIterator()
    {
        return children.iterator();
    }

    /**
     * @return Returns the id.
     */
    public String getId()
    {
        return id;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    public int getNumberOfChildren()
    {
        return children.size();
    }

    /**
     * Returns the parent of this category.
     * 
     * @return Can be null.
     */
    public Category getParent()
    {
        return parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        int hashCode = 1;
        hashCode = 31 * hashCode + (id == null ? 0 : id.hashCode());
        return hashCode;
    }

    /**
     * Returns wheter this category is a leaf or not.
     * 
     * @return True, if this category has no children. False otherwise.
     */
    public boolean isLeaf()
    {
        return children.isEmpty();
    }

    /**
     * Tests if a given category is a direct child of this category.
     * 
     * @param probableChild
     *            The category to test if it is a child.
     * @return True, if it is a child. False otherwise.
     */
    public boolean hasChild(Category probableChild)
    {
        final Iterator iter = getChildrenIterator();
        while (iter.hasNext())
        {
            if (iter.next() == probableChild)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns wheter this category is at top level or not.
     * 
     * @return True, if this category has no parent. False otherwise.
     */
    public boolean isTopLevel()
    {
        return parent == null;
    }

    /**
     * Removes a child.
     * 
     * @param child !=
     *            null
     */
    public void removeChild(Category child)
    {
        Assert.notNull(child);

        children.remove(child);
        child.parent = null;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets the parent of this Category.
     * 
     * @param newParent
     *            The new parent or null if this category should become a root
     *            category.
     */
    public void setParent(Category newParent)
    {
        Assert
                .isTrue(newParent != this,
                        "A category cannot be its own parent!");
        Assert
                .isTrue(!hasChild(newParent),
                        "Currently, a category's parent cannot be set to one of its current children");

        if (newParent == Category.NULL_OBJECT)
        {
            newParent = null;
        }
        this.parent = newParent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        if (this == NULL_OBJECT)
        {
            return "";
        }
        
        final StringBuffer buffer = new StringBuffer();
        if (parent != null)
        {
            buffer.append(parent.toString());
        }

        buffer.append("/");
        buffer.append(getName());
        return buffer.toString();
    }
}
