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

package net.sourceforge.housekeeper.storage;


import net.sourceforge.housekeeper.domain.ArticleDescription;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import org.jdom.input.SAXBuilder;

import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Iterator;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @see
 * @since
 */
class JDOMStorage extends MemoryStorage
{
    //~ Static fields/initializers ---------------------------------------------

    /** The path to the file that is used for data storage */
    private static final File FILE = new File(System.getProperty("user.home"),
                                              "housekeeper_jdom.xml");

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new JDOMStorage object.
     */
    JDOMStorage()
    {
    }

    //~ Methods ----------------------------------------------------------------

    /* (non-Javadoc)
     * @see net.sourceforge.housekeeper.storage.Storage#loadData()
     */
    public void loadData()
    {
        SAXBuilder builder = new SAXBuilder();

        try
        {
            builder.build(FILE);
        }
        catch (JDOMException e)
        {
            System.err.println(FILE + " is not well-formed.");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.housekeeper.storage.Storage#saveData()
     */
    public void saveData()
    {
        Element root = new Element("Housekeeper");

        for (Iterator i = articleDescriptions.iterator(); i.hasNext();)
        {
            ArticleDescription object  = (ArticleDescription)i.next();
            Element            element = new Element("ArticleDescription");
            element.setAttribute("ID", "" + object.getID());
            element.setAttribute("Name", object.getName());
            element.setAttribute("Store", object.getStore());
            element.setAttribute("Quantity", "" + object.getQuantity());
            element.setAttribute("Unit", object.getUnit());
            element.setAttribute("Price", "" + object.getPrice());
            root.addContent(element);
        }


        Document     doc = new Document(root);

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

        try
        {
            OutputStream stream = new BufferedOutputStream(new FileOutputStream(FILE));
            outputter.output(doc, stream);
        }
        catch (IOException e)
        {
            System.err.println(e);
        }
    }
}
