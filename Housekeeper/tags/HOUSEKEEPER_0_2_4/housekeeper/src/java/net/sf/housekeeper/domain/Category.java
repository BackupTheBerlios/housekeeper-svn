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

package net.sf.housekeeper.domain;

import java.rmi.server.UID;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.util.Assert;
import org.springframework.util.ToStringCreator;

/**
 * A category for an {@link net.sf.housekeeper.domain.Item}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class Category
{

    private LinkedHashSet children;

    private final String  id;

    private String        name;

    private Category      parent;

    /**
     * Creates a new object with no name and children.
     */
    public Category()
    {
        this("");
    }

    /**
     * Creates a new object with no children.
     * 
     * @param name The name for the category. Must not be null.
     */
    public Category(final String name)
    {
        Assert.notNull(name);
        this.id = new UID().toString();
        this.name = name;
        this.children = new LinkedHashSet();
    }

    /**
     * Adds a child category and sets the "parent" property of that object to
     * this category.
     * 
     * @param child The child to add. Must not be null.
     */
    public void addChild(final Category child)
    {
        children.add(child);
        child.setParent(this);
    }

    /**
     * Tests if a category either equals this category or is a children of this
     * category.
     * 
     * @param cat The category to test.
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

    /**
     * @return Returns the children.
     */
    public Set getChildren()
    {
        return children;
    }

    /**
     * Returns an iterator for all children of this category.
     * 
     * @return An iterator for all children. Not null.
     */
    public Iterator getChildrenIterator()
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

    /**
     * Returns the parent of this category.
     * 
     * @return Can be null.
     */
    public Category getParent()
    {
        return parent;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets the parent of this category.
     * 
     * @param parent The parent or null to unset this category's parent.
     */
    public void setParent(Category parent)
    {
        this.parent = parent;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj)
    {
        final Category otherCat = (Category) obj;
        return this.id.equals(otherCat.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return ToStringCreator.propertiesToString(this);
    }
}