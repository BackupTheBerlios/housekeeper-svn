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


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;


/**
 * A <code>Purchase</code> consists of articles one has purchased on a shopping
 * tour.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @see PurchasedArticle
 * @since 0.1
 */
public class Purchase
{
    //~ Instance fields --------------------------------------------------------

    /** The articles, that have been purchased. */
    private Collection purchasedArticles;

    /** The date, the articles have been purchased. */
    private Date date;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a <code>Purchase</code> object with default values for the
     * attributes. It contains an empty list of articles and the current date.
     */
    public Purchase()
    {
        purchasedArticles = new ArrayList();
        date = new Date();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Sets the corresponding attribute to a new value.
     *
     * @param date The new value.
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
     * @param purchasedArticles The new value.
     */
    public void setPurchasedArticles(Collection purchasedArticles)
    {
        this.purchasedArticles = purchasedArticles;
    }

    /**
     * Gets the corresponding attribute.
     *
     * @return The corresponding attribute.
     */
    public Collection getPurchasedArticles()
    {
        return purchasedArticles;
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public double getTotalAmount()
    {
        double totalAmount = 0;

        for (Iterator iter = purchasedArticles.iterator(); iter.hasNext();)
        {
            PurchasedArticle article = (PurchasedArticle)iter.next();
            totalAmount += article.getArticle().getPrice();
        }


        return totalAmount;
    }

    /**
     * Adds an article to the list of purchased articles.
     *
     * @param article The article, that should be added.
     */
    public void add(PurchasedArticle article)
    {
        purchasedArticles.add(article);
    }

    /**
     * Adds articles from a collection to the list of purchased articles.
     *
     * @param articles The articles, that should be added.
     */
    public void add(Collection articles)
    {
        purchasedArticles.addAll(articles);
    }

    /**
     * Removes an article from the list of purchased articles.
     *
     * @param article The article, that should be removed.
     */
    public void remove(PurchasedArticle article)
    {
        purchasedArticles.remove(article);
    }
}
