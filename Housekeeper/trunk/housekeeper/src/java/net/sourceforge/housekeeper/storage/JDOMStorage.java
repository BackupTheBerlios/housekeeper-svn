/*
 * This file is part of housekeeper.
 *
 *	housekeeper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation; either version 2 of the License, or
 *	(at your option) any later version.
 *
 *	housekeeper is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with housekeeper; if not, write to the Free Software
 *	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.sourceforge.housekeeper.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import net.sourceforge.housekeeper.domain.ArticleDescription;
import net.sourceforge.housekeeper.domain.Purchase;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @see
 * @since
 */
public class JDOMStorage implements Storage
{

    public JDOMStorage()
    {
        articleDescriptions = new ArrayList();
        purchases           = new ArrayList();
    }

    //~ Static fields/initializers ---------------------------------------------

    /** The name of the file used for data storage */
    private static final String FILENAME = "/home/phelan/jdom.xml";

    //~ Instance fields --------------------------------------------------------

    private Collection articleDescriptions;
    private Collection purchases;

    //~ Constructors -----------------------------------------------------------


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


    public void loadData()
    {
        // TODO Auto-generated method stub

    }

    public void remove(ArticleDescription article)
    {
        // TODO Auto-generated method stub

    }

    public void saveData()
    {
        Element root = new Element("Housekeeper");
        
        for(Iterator i = articleDescriptions.iterator(); i.hasNext();)
        {
            ArticleDescription object = (ArticleDescription)i.next();
            Element element = new Element("ArticleDescription");
            element.setAttribute("ID", ""+object.getID());
            element.setAttribute("Name", object.getName());
            element.setAttribute("Store", object.getStore());
            element.setAttribute("Quantity", ""+object.getQuantity());
            element.setAttribute("Unit", object.getUnit());
            element.setAttribute("Price", ""+object.getPrice());
            root.addContent(element);
        }
        
        Document doc = new Document(root);
        
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        
        try {
          outputter.output(doc, System.out);       
        }
        catch (IOException e) {
          System.err.println(e);
        }
    }

}
