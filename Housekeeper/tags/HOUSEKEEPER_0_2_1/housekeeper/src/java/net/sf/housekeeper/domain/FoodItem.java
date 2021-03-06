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

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * A concrete item that has been purchased.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class FoodItem
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
     * Name of the Bound Bean Property "expiry".
     */
    public static final String PROPERTYNAME_EXPIRY   = "expiry";

    /**
     * The name of this item.
     */
    private String             name;

    /**
     * The date before the item should have been consumed entirely.
     */
    private Date               expiry;

    /**
     * The quantity of one exemplar of this item.
     */
    private String             quantity;

    /**
     * Creates a new FoodItem object with specified values.
     * 
     * @param name The name of the item. Must not be null.
     * @param quantity The quantity which one exemplar of this item contains.
     * @param expiry The date until this item should be consumed.
     */
    public FoodItem(final String name, final String quantity, final Date expiry)
    {
        setName(name);
        setQuantity(quantity);
        setExpiry(expiry);
    }

    /**
     * Creates a new FoodItem object with default values. Name, quantity and the
     * expiry date remain unset.
     */
    public FoodItem()
    {
        this("", null, null);
    }

    /**
     * Sets the expiry date of this item to one second before midnight of the
     * given date. A value of null specifies that the expiry for this item
     * should not be set to a value.
     * 
     * @param expiry The new expiry date or null to unset the date.
     */
    public void setExpiry(final Date expiry)
    {
        if (expiry != null)
        {
            final Date truncatedExpiry = DateUtils
                    .truncate(expiry, Calendar.DAY_OF_MONTH);
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(truncatedExpiry);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.SECOND, -1);
            this.expiry = calendar.getTime();
        } else
        {
            this.expiry = null;
        }
    }

    /**
     * Gets the expiry date of this item.
     * 
     * @return Returns the expiry date or null if none has been set.
     */
    public Date getExpiry()
    {
        return expiry;
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
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        int hashCode = super.hashCode();
        hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
        hashCode = 31 * hashCode + (expiry == null ? 0 : expiry.hashCode());
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
        buffer.append("[FoodItem:");
        buffer.append(" name: ");
        buffer.append(name);
        buffer.append(" quantity: ");
        buffer.append(quantity);
        buffer.append(" expiry: ");
        buffer.append(expiry);
        buffer.append("]");
        return buffer.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone()
    {
        FoodItem inst = new FoodItem();
        inst.name = this.name == null ? null : new String(this.name);
        inst.expiry = this.expiry == null ? null : (Date) this.expiry.clone();
        inst.quantity = this.quantity == null ? null
                : new String(this.quantity);
        return inst;
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
        FoodItem castedObj = (FoodItem) o;
        return ((this.name == null ? castedObj.name == null : this.name
                .equals(castedObj.name))
                && (this.expiry == null ? castedObj.expiry == null
                        : this.expiry.equals(castedObj.expiry)) && (this.quantity == null ? castedObj.quantity == null
                : this.quantity.equals(castedObj.quantity)));
    }
}