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

import org.jdom.Element;

/**
 * Utility class to convert the all in-memory domain objects into an XML element
 * hierarchy.
 * <p>
 * This converter supports file version 1.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class DomainConverter1 implements DomainConverter
{

    /**
     * The supported file version.
     */
    private static final int SUPPORTED_FILE_VERSION = 1;

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.jdom.DomainConverter#replaceDomain(org.jdom.Element)
     */
    public void replaceDomain(final Element root)
    {

        //Replace food items
        final Collection foodItems = new LinkedList();
        final Iterator foodItemIterator = root.getChildren("foodItem")
                .iterator();
        while (foodItemIterator.hasNext())
        {
            final Element element = (Element) foodItemIterator.next();
            final FoodItem item = FoodItemConverter1.convert(element);
            foodItems.add(item);
        }
        FoodItemManager.INSTANCE.replaceAll(foodItems);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.jdom.DomainConverter#createDocument()
     */
    public Element convertDomainToXML()
    {
        final Element root = new Element("housekeeper");
        root.setAttribute("version", "" + SUPPORTED_FILE_VERSION);

        final Iterator iter = FoodItemManager.INSTANCE.getItemsIterator();
        while (iter.hasNext())
        {
            final FoodItem item = (FoodItem) iter.next();
            root.addContent(FoodItemConverter1.convert(item));
        }

        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.jdom.DomainConverter#isVersionSupported(int)
     */
    public boolean isVersionSupported(int version)
    {
        return version == SUPPORTED_FILE_VERSION;
    }
}