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
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.util.ToStringCreator;

/**
 * @author
 * @version $Revision$, $Date$
 */
public final class Category
{

    public static final Category    CONVENIENCE = new Category(
                                                        "convenienceFood",
                                                        "Convenience Food");

    public static final Category    MISC        = new Category("misc", "Misc");

    private static final Category[] subcats     = { CONVENIENCE, MISC };

    public static final Category    FOOD        = new Category("food", "Food",
                                                        Arrays.asList(subcats));

    private final String            id;

    private String                  name;

    private List                    children;

    /**
     * Creates a new object with no name and children.
     */
    public Category()
    {
        this(new UID().toString(), "");
    }

    /**
     * Creates a new object with a specified ID and name.
     * 
     * @param id
     * @param name
     */
    public Category(String id, String name)
    {
        this(id, name, new LinkedList());
    }

    /**
     * Creates a new object.
     * 
     * @param id
     * @param name
     * @param children
     */
    public Category(String id, String name, List children)
    {
        this.id = id;
        this.name = name;
        this.children = children;
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
    public List getChildren()
    {
        return children;
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
     * @param children The children to set.
     */
    public void setChildren(List children)
    {
        this.children = children;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
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
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return ToStringCreator.propertiesToString(this);
    }
}