/*
 * This file is part of Housekeeper.
 *
 * Housekeeper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Housekeeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Housekeeper; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA  02111-1307  USA
 *
 * Copyright 2003, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper.model;


import java.util.Date;


/**
 * Represents a consumption of a {@link PurchasedArticle}. It holds the date
 * and the quantity consumed.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
public class Consumption
{
    //~ Instance fields --------------------------------------------------------

    /** The date of the consumption */
    private Date date;

    /** How much that has been consumed */
    private int quantity;

    //~ Constructors -----------------------------------------------------------

    /**
     * Initializes the attributes with default values. The date is set to
     * today, Quantity is set to zero.
     */
    public Consumption()
    {
        date     = new Date();
        quantity = 0;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Sets the corresponding attribute to a new value.
     *
     * @param store The new value.
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * Gets the corresponding attribute.
     *
     * @return The corresponding attribute.
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * Sets the corresponding attribute to a new value.
     *
     * @param store The new value.
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    /**
     * Gets the corresponding attribute.
     *
     * @return The corresponding attribute.
     */
    public int getQuantity()
    {
        return quantity;
    }
}
