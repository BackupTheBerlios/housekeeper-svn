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

package net.sourceforge.housekeeper.storage;


import net.sourceforge.housekeeper.entities.ArticleDescription;
import net.sourceforge.housekeeper.entities.Purchase;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Stores data using simple XML serialization.
 * 
 * <p>
 * Beware that the storage format is not robust. If an attribute of a domain
 * class gets changed all previously saved data cannot be restored anymore.
 * </p>
 * 
 * <p>
 * This implementation should be used for testing puropses only
 * </p>
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
final class XMLSerializationStorage implements Storage
{
    //~ Static fields/initializers ---------------------------------------------

    /** The name of the file used for data storage */
    private static final String FILENAME = "/home/phelan/test.xml";

    //~ Instance fields --------------------------------------------------------

    private Collection articleDescriptions;
    private Collection purchases;
    private XMLDecoder xmlDecoder;
    private XMLEncoder xmlEncoder;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new XMLStorage object.
     */
    XMLSerializationStorage()
    {
        articleDescriptions = new ArrayList();
        purchases           = new ArrayList();
    }

    //~ Methods ----------------------------------------------------------------

    /* (non-Javadoc)
     * @see net.sourceforge.housekeeper.storage.Storage#getArticles()
     */
    public Collection getAllArticleDescriptions()
    {
        return Collections.unmodifiableCollection(articleDescriptions);
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Collection getAllPurchases()
    {
        return Collections.unmodifiableCollection(purchases);
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @param article DOCUMENT ME!
     */
    public void add(ArticleDescription article)
    {
        articleDescriptions.add(article);
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @param purchase DOCUMENT ME!
     */
    public void add(Purchase purchase)
    {
        purchases.add(purchase);
    }

    /**
     * TODO DOCUMENT ME!
     */
    public void loadData()
    {
        try
        {
            xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(FILENAME)));

            Object result = xmlDecoder.readObject();

            xmlDecoder.close();

            articleDescriptions = (List)result;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @param article DOCUMENT ME!
     */
    public void remove(ArticleDescription article)
    {
        articleDescriptions.remove(article);
    }

    /**
     * TODO DOCUMENT ME!
     */
    public void saveData()
    {
        try
        {
            xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILENAME)));

            xmlEncoder.writeObject(articleDescriptions);

            xmlEncoder.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }
}
