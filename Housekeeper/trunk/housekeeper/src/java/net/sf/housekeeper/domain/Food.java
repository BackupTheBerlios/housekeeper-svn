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
 * A concrete food item that has been purchased.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class Food extends Item
{

    /**
     * Name of the Bound Bean Property "expiry".
     */
    public static final String PROPERTYNAME_EXPIRY = "expiry";

    /**
     * The date before the item should have been consumed entirely.
     */
    private Date               expiry;

    /**
     * Creates a new Food object with default values. Name, quantity and the
     * expiry date remain unset.
     */
    public Food()
    {
        super();
        setExpiry(null);
    }

    /**
     * Creates a new item as a deep copy of an existing Food object.
     * 
     * @param original The item to be cloned.
     */
    public Food(final Food original)
    {
        super(original);

        final Date clonedExpiry = original.getExpiry() == null ? null
                : (Date) original.getExpiry().clone();

        setExpiry(clonedExpiry);
    }

    /**
     * Creates a new Food object with specified values.
     * 
     * @param name The name of the item. Must not be null.
     * @param quantity The quantity which one exemplar of this item contains.
     * @param expiry The date until this item should be consumed.
     */
    public Food(final String name, final String quantity, final Date expiry)
    {
        super(name, quantity);
        setExpiry(expiry);
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
     * Gets the expiry date of this food.
     * 
     * @return Returns the expiry date or null if none has been set.
     */
    public Date getExpiry()
    {
        return expiry;
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
        Food castedObj = (Food) o;
        return ((this.expiry == null ? castedObj.expiry == null : this.expiry
                .equals(castedObj.expiry)));
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        int hashCode = super.hashCode();
        hashCode = 31 * hashCode + (expiry == null ? 0 : expiry.hashCode());
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
        buffer.append("[Food:");
        buffer.append(" expiry: ");
        buffer.append(expiry);
        buffer.append(super.toString());
        buffer.append("]");
        return buffer.toString();
    }
}