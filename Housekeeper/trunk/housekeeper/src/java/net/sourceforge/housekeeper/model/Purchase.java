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


import java.util.Collection;
import java.util.Date;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 */
public class Purchase
{
    //~ Instance fields --------------------------------------------------------

    /** TODO DOCUMENT ME! */
    private Collection purchasedArticle;

    /** TODO DOCUMENT ME! */
    private Date date;

    /** TODO DOCUMENT ME! */
    private int id;

    //~ Constructors -----------------------------------------------------------

    /**
     *
     *
     */
    public Purchase()
    {
        super();

        // TODO Auto-generated constructor stub
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * TODO DOCUMENT ME!
     *
     * @param date DOCUMENT ME!
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @param id DOCUMENT ME!
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getId()
    {
        return id;
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @param purchasedArticle DOCUMENT ME!
     */
    public void setPurchasedArticle(Collection purchasedArticle)
    {
        this.purchasedArticle = purchasedArticle;
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Collection getPurchasedArticle()
    {
        return purchasedArticle;
    }
}
