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
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import net.sourceforge.housekeeper.domain.ArticleDescription;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @see
 * @since
 */
class JDOMStorage extends MemoryStorage
{

    JDOMStorage()
    {

    }

    //~ Static fields/initializers ---------------------------------------------

    /** The name of the file used for data storage */
    private static final String FILENAME = "/home/phelan/temp/jdom.xml";



    //~ Constructors -----------------------------------------------------------


    //~ Methods ----------------------------------------------------------------


    public void loadData()
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
