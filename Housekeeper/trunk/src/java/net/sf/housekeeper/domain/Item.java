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

import org.springframework.core.style.ToStringCreator;

/**
 * An arbitrary item.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class Item
{

    /**
     * Name of the Bound Bean Property "name".
     */
    public static final String PROPERTYNAME_NAME = "name";

    /**
     * Name of the Bound Bean Property "description".
     */
    public static final String PROPERTYNAME_DESCRIPTION = "description";

    /**
     * Name of the Bound Bean Property "category".
     */
    public static final String PROPERTYNAME_CATEGORY = "category";

    /**
     * The name of this item.
     */
    private String name;

    /**
     * A description of this item.
     */
    private String description;

    /**
     * The category.
     */
    private Category category;

    /**
     * Creates a new item with an empty name and description.
     */
    public Item()
    {
        this("", null);
    }

    /**
     * Creates a new item as a deep copy of an existing item. Note that the
     * "category" property is not deep copied.
     * 
     * @param original
     *            The item to be cloned.
     */
    public Item(final Item original)
    {
        setName(original.getName());
        setDescription(original.getDescription());
        setCategory(original.getCategory());
    }

    /**
     * Creates a new item with the given attributes.
     * 
     * @param name
     *            The name of the item. Must not be null.
     * @param description
     *            A description for this item.
     */
    public Item(final String name, final String description)
    {
        setName(name);
        setDescription(description);
    }

    /**
     * @return Returns the category.
     */
    public Category getCategory()
    {
        return category;
    }

    /**
     * @param category
     *            The category to set.
     */
    public void setCategory(Category category)
    {
        if (category == Category.NULL_OBJECT)
        {
            category = null;
        }
        this.category = category;
    }

    /**
     * Returns the name.
     * 
     * @return Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of this item.
     * 
     * @param name
     *            The name to set. Must not be null.
     * @throws IllegalArgumentException
     *             if name is null.
     */
    public void setName(final String name)
    {
        if (name == null)
        {
            throw new IllegalArgumentException(
                    "Parameter 'name' must not be null.");
        }

        this.name = name;
    }

    /**
     * @return Returns the description of this item. Null, if no description has
     *         been set.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description for this item.
     * 
     * @param description
     *            The descrption to set.
     */
    public void setDescription(final String description)
    {
        if (description == null || description.equals(""))
        {
            this.description = null;
        } else
        {
            this.description = description;
        }
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
        Item castedObj = (Item) o;

        final boolean equalName = this.name == null ? castedObj.name == null
                : this.name.equals(castedObj.name);
        final boolean equalDescription = this.description == null ? castedObj.description == null
                : this.description.equals(castedObj.description);
        final boolean equalCategory = this.category == null ? castedObj.category == null
                : this.category.equals(castedObj.category);
        return equalName && equalDescription && equalCategory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        int hashCode = 1;
        hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
        hashCode = 31 * hashCode
                + (description == null ? 0 : description.hashCode());
        hashCode = 31 * hashCode + (category == null ? 0 : category.hashCode());
        return hashCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringCreator(this).toString();
    }

}