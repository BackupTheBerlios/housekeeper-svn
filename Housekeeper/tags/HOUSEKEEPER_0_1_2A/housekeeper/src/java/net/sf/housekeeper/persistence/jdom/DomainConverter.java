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

package net.sf.housekeeper.persistence.jdom;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import net.sf.housekeeper.domain.FoodItem;
import net.sf.housekeeper.domain.FoodItemManager;

import org.jdom.Document;
import org.jdom.Element;


/**
 * Utility class to convert the all in-memory domain objects into a 
 * JDOM {@link org.jdom.Document}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class DomainConverter
{
    /**
     * Prevents instanciation.
     */
    private DomainConverter()
    {
        
    }
    
    /**
     * Creates domain objects from the document and replaces all in-memory objects.
     * 
     * @param document The document to convert to domain objects.
     */
    public static void replaceDomain(final Document document)
    {
        final Element root = document.getRootElement();
        
        final Collection foodItems = new LinkedList();
        final Iterator foodItemIterator = root.getChildren("foodItem").iterator();
        while (foodItemIterator.hasNext())
        {
            final Element element = (Element)foodItemIterator.next();
            final FoodItem item = FoodItemConverter.convert(element);
            foodItems.add(item);
        }
        
        FoodItemManager.INSTANCE.replaceAll(foodItems);
    }
    
    /**
     * Creates a JDOM document with all in-memory domain objects.
     * 
     * @return the JDOM document.
     */
    public static Document createDocument()
    {
        final Element root = new Element("housekeeper");
        final Iterator iter = FoodItemManager.INSTANCE.getSupplyIterator();
        while (iter.hasNext())
        {
            final FoodItem item = (FoodItem)iter.next();
            root.addContent(FoodItemConverter.convert(item));
        }
        
        final Document document = new Document(root);
        return document;
    }
}
