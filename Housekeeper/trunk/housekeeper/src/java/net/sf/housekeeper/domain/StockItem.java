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

import java.util.Date;

/**
 * A concrete item that has been purchased.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class StockItem
{

    /** The name of this item. */
    private String name;

    /** The date, before the item should have been consumed entirely. */
    private Date   bestBeforeEnd;

    /**
     * The quantity of one exemplar of this item. For example, a bottle of milk
     * which contains 1 litre would be one item an its quantity would be 1
     * litre.
     */
    private String quantity;

    /**
     * Creates a new StockItem object with specified values.
     * 
     * @param name Name of the item. Must not be null.
     * @param bestBeforeEnd Date until this item should be consumed. Must not be
     *            null.
     * @param quantity The quantity which one exemplar of this item contains.
     */
    public StockItem(final String name, final String quantity,
            final Date bestBeforeEnd)
    {
        setName(name);
        setBestBeforeEnd(bestBeforeEnd);
        setQuantity(quantity);
    }

    /**
     * Creates a new StockItem object with default values. The related article
     * is an empty one, there are no consumptions and the date is set to today.
     */
    public StockItem()
    {
        this("", "", new Date());
    }

    /**
     * Sets the corresponding attribute to a new value.
     * 
     * @param bestBeforeEnd The new value.
     */
    public void setBestBeforeEnd(final Date bestBeforeEnd)
    {
        assert bestBeforeEnd != null : "Parameter bestBeforeEnd must not be null";

        this.bestBeforeEnd = bestBeforeEnd;
    }

    /**
     * Gets the corresponding attribute.
     * 
     * @return The corresponding attribute.
     */
    public Date getBestBeforeEnd()
    {
        return bestBeforeEnd;
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
     * milk which contains 1 litre would be one item an its quantity would be 1
     * litre.
     * 
     * @param quantity The quantity to set.
     */
    public void setQuantity(final String quantity)
    {
        if (quantity == null)
        {
            this.quantity = "";
        } else
        {
            this.quantity = quantity;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone()
    {
        return new StockItem(name, quantity, (Date) bestBeforeEnd.clone());
    }
}