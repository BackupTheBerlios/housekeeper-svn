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

import com.jgoodies.binding.beans.Model;

/**
 * A concrete item that has been purchased. Adheres to the JavaBean
 * specifications.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class FoodItem extends Model
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

    /** The name of this item. */
    private String             name;

    /** The date, before the item should have been consumed entirely. */
    private Date               expiry;

    /**
     * The quantity of one exemplar of this item. For example, a bottle of milk
     * which contains 1 litre would be one item an its quantity would be 1
     * litre.
     */
    private String             quantity;

    /**
     * Creates a new FoodItem object with specified values.
     * 
     * @param name Name of the item. Must not be null.
     * @param bestBeforeEnd Date until this item should be consumed. Must not be
     *            null.
     * @param quantity The quantity which one exemplar of this item contains.
     */
    public FoodItem(final String name, final String quantity,
            final Date bestBeforeEnd)
    {
        setName(name);
        setExpiry(bestBeforeEnd);
        setQuantity(quantity);
    }

    /**
     * Creates a new FoodItem object with default values. The related article is
     * an empty one, there are no consumptions and the expiry date is set to
     * today.
     */
    public FoodItem()
    {
        this("", "", new Date());
    }

    /**
     * Sets the expiry date of this item to one second before midnight of the
     * given date.
     * 
     * @param expiry The new expiry date.
     */
    public void setExpiry(final Date expiry)
    {
        final Object oldValue = getExpiry();
        final Date truncatedExpiry = DateUtils.truncate(expiry,
                                                        Calendar.DAY_OF_MONTH);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(truncatedExpiry);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);
        this.expiry = calendar.getTime();
        
        firePropertyChange(PROPERTYNAME_EXPIRY, oldValue, getExpiry());
    }

    /**
     * Gets the corresponding attribute.
     * 
     * @return The corresponding attribute.
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
     * @param name The name to set.
     */
    public void setName(final String name)
    {
        assert name != null : "Parameter name must not be null";
        Object oldValue = getExpiry();
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, oldValue, name);
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
     * milk which contains 1 litre would be one item an its quantity would be 1
     * litre.
     * 
     * @param quantity The quantity to set.
     */
    public void setQuantity(final String quantity)
    {
        Object oldValue = getExpiry();
        if (quantity == null)
        {
            this.quantity = "";
        } else
        {
            this.quantity = quantity;
        }
        firePropertyChange(PROPERTYNAME_QUANTITY, oldValue, quantity);
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
        if (!super.equals(o))
        {
            return false;
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
        buffer.append(" expiry: ");
        buffer.append(expiry);
        buffer.append(" quantity: ");
        buffer.append(quantity);
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
        inst.expiry = this.expiry == null ? null : (Date) inst.expiry.clone();
        inst.quantity = this.quantity == null ? null
                : new String(this.quantity);
        return inst;
    }
}