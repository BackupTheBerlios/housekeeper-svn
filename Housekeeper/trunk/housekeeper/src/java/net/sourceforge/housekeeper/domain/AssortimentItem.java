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
 * Copyright 2003-2004, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper.domain;


/**
 * An item like banana or a bottle of milk which is part of a vendor's
 * assortiment and can be bought. Notice, that objects of this class doesn't
 * represent a concrete item that has been bought. Use {@link
 * PurchasedArticle} instead.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
public class AssortimentItem extends DomainObject
{
    //~ Instance fields --------------------------------------------------------

    /** A describing name for this item. For example &quote;Banana&quote;. */
    private String name;

    /** The store where the item can be bought. */
    private String store;

    /** The unit this item is measured in, like pieces or litres. */
    private String unit;

    /**
     * The price of one specimen of this item. If this item would be a
     * box of six eggs, the price is for the whole box not for just one egg .
     */
    private double price;

    /**
     * How many or how much of the unit a specimen of this item has. If this
     * item would be a box of six eggs, the quantity would be six.
     */
    private int quantity;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates an item with default attributes. Name, store and unit are set
     * to an empty String. Price and quantity are set to zero.
     */
    public AssortimentItem()
    {
        name     = "";
        store    = "";
        price    = 0;
        quantity = 0;
        unit     = "";
    }

    /**
     * Creates an item with given attributes. If a parameter is null, the
     * according attribute is set to a default value.
     *
     * @param name A describing name for the article. For example Banana.
     * @param store The store where the article can be bought.
     * @param price The price of one specimen of this article. If this article
     *        would be a box of six eggs, the price is for the whole box not
     *        for just one egg.
     * @param quantity How many or how much of the unit a specimen of this
     *        article has. If this article would be a box of six eggs, the
     *        quantity would be six.
     * @param unit The unit this article is measured in, like pieces or litres.
     */
    public AssortimentItem(String name, String store, double price,
                           int quantity, String unit)
    {
        this(); //Assures that no attribute is null

        this.name     = name;
        this.store    = store;
        this.price    = price;
        this.unit     = unit;
        this.quantity = quantity;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Sets the corresponding attribute to a new value.
     *
     * @param name The new value.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the corresponding attribute.
     *
     * @return The corresponding attribute.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the corresponding attribute to a new value.
     *
     * @param price The new value.
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Gets the corresponding attribute.
     *
     * @return The corresponding attribute.
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * Sets the corresponding attribute to a new value.
     *
     * @param quantity The new value.
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

    /**
     * Sets the corresponding attribute to a new value.
     *
     * @param store The new value.
     */
    public void setStore(String store)
    {
        this.store = store;
    }

    /**
     * Gets the corresponding attribute.
     *
     * @return The corresponding attribute.
     */
    public String getStore()
    {
        return store;
    }

    /**
     * Sets the corresponding attribute to a new value.
     *
     * @param quantityUnit The new value.
     */
    public void setUnit(String quantityUnit)
    {
        this.unit = quantityUnit;
    }

    /**
     * Gets the corresponding attribute.
     *
     * @return The corresponding attribute.
     */
    public String getUnit()
    {
        return unit;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "Name: " + name + " Store: " + store + " Price: " + price +
               " Quantity: " + quantity + " " + unit;
    }
}
