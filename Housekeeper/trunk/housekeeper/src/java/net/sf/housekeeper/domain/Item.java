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

/**
 * A physical object present in a household.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class Item
{

    /**
     * Name of the Bound Bean Property "name".
     */
    public static final String PROPERTYNAME_NAME     = "name";

    /**
     * Name of the Bound Bean Property "quantity".
     */
    public static final String PROPERTYNAME_QUANTITY = "quantity";

    /**
     * The name of this item.
     */
    private String             name;

    /**
     * The quantity of one exemplar of this item.
     */
    private String             quantity;

    /**
     * Creates a new item with an empty name and an unset quantity.
     */
    public Item()
    {
        this("", null);
    }

    /**
     * Creates a new item as a deep copy of an existing item.
     * 
     * @param original The item to be cloned.
     */
    public Item(final Item original)
    {
        final String clonedName = original.getName() == null ? null
                : new String(original.getName());
        final String clonedQuantity = original.getQuantity() == null ? null
                : new String(original.getQuantity());

        setName(clonedName);
        setQuantity(clonedQuantity);
    }

    /**
     * Creates a new item with the given attributes.
     * 
     * @param name The name of the item. Must not be null.
     * @param quantity The quantity of the item.
     */
    public Item(final String name, final String quantity)
    {
        setName(name);
        setQuantity(quantity);
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
     * @param name The name to set. Must not be null.
     * @throws IllegalArgumentException if name is null.
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
     * @return Returns the quantity.
     */
    public String getQuantity()
    {
        return quantity;
    }

    /**
     * Sets the quantity of one exemplar of this item. For example, a bottle of
     * milk which contains 1 litre would be one item and its quantity would be 1
     * litre.
     * 
     * @param quantity The quantity to set. If this is an empty string the
     *            quantity becomes unset.
     */
    public void setQuantity(final String quantity)
    {
        if (quantity == null || quantity.equals(""))
        {
            this.quantity = null;
        } else
        {
            this.quantity = quantity;
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
        return ((this.name == null ? castedObj.name == null : this.name
                .equals(castedObj.name)) && (this.quantity == null ? castedObj.quantity == null
                : this.quantity.equals(castedObj.quantity)));
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
        hashCode = 31 * hashCode + (quantity == null ? 0 : quantity.hashCode());
        return hashCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[Item:");
        buffer.append(" name: ");
        buffer.append(name);
        buffer.append(" quantity: ");
        buffer.append(quantity);
        buffer.append("]");
        return buffer.toString();
    }
}