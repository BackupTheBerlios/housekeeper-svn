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


import net.sourceforge.housekeeper.model.Article;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 */
public final class XMLStorage extends Storage
{
    //~ Static fields/initializers ---------------------------------------------

    /** TODO DOCUMENT ME! */
    private static final String FILENAME = "/home/phelan/test.xml";

    //~ Instance fields --------------------------------------------------------

    /** TODO DOCUMENT ME! */
    private List articles;

    /** TODO DOCUMENT ME! */
    private XMLDecoder xmlDecoder;

    /** TODO DOCUMENT ME! */
    private XMLEncoder xmlEncoder;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new XMLStorage object.
    
     */
    XMLStorage()
    {
        articles = new ArrayList();
    }

    //~ Methods ----------------------------------------------------------------

    /* (non-Javadoc)
     * @see net.sourceforge.housekeeper.storage.Storage#getArticles()
     */
    public List getArticles()
    {
        return articles;
    }

    public Article getArticle(int i)
    {
        return (Article)articles.get(i);
    }
    
    /**
     * TODO DOCUMENT ME!
     *
     * @param article DOCUMENT ME!
     */
    public void add(Article article)
    {
        articles.add(article);
        update();
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

            articles = (List)result;
            update();
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
    public void remove(Article article)
    {
        articles.remove(article);
        update();
    }

    /**
     * TODO DOCUMENT ME!
     */
    public void saveData()
    {
        try
        {
            xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILENAME)));

            xmlEncoder.writeObject(articles);

            xmlEncoder.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    /**
     * TODO DOCUMENT ME!
     */
    public void update()
    {
        setChanged();
        notifyObservers();
    }
}
