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


/**
 * A concrete article, that has been purchased. There can be multiple objects
 * of this class for every {@link ArticleDescription}.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
public class PurchasedArticle
{
    //~ Instance fields --------------------------------------------------------

    /** The type of article the object is */
    private ArticleDescription article;

    /** Information about consumptions of this object */
    private Collection consumptions;

    /** The date, before the article should be consumed entirely */
    private Date bestBeforeEnd;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new PurchasedArticle object with default values. The related
     * article is an empty one, there are no consumptions and the date is set
     * to today.
     */
    public PurchasedArticle()
    {
        article       = new ArticleDescription();
        consumptions  = new ArrayList();
        bestBeforeEnd = new Date();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Sets the corresponding attribute to a new value.
     *
     * @param article The new value.
     */
    public void setArticle(ArticleDescription article)
    {
        this.article = article;
    }

    /**
     * Gets the corresponding attribute.
     *
     * @return The corresponding attribute.
     */
    public ArticleDescription getArticle()
    {
        return article;
    }

    /**
     * Sets the corresponding attribute to a new value.
     *
     * @param bestBeforeEnd The new value.
     */
    public void setBestBeforeEnd(Date bestBeforeEnd)
    {
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
     * Sets the corresponding attribute to a new value.
     *
     * @param consumption The new value.
     */
    public void setConsumption(Collection consumption)
    {
        this.consumptions = consumption;
    }

    /**
     * Gets the corresponding attribute.
     *
     * @return The corresponding attribute.
     */
    public Collection getConsumption()
    {
        return consumptions;
    }

    /**
     * Adds a consumption reducing the remaining available quantity.
     *
     * @param consumption The consumption to be added.
     */
    public void add(Consumption consumption)
    {
        consumptions.add(consumption);
    }
}
