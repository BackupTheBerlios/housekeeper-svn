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

import java.util.Iterator;

import org.jdom.Element;


/**
 * Converts Housekeeper data JDOM trees of version 2 to version 3.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class Version2To3Converter implements DocumentVersionConverter
{
    private final DocumentVersionConverter preliminaryConverter;
    
    Version2To3Converter()
    {
        this(null);
    }
    
    Version2To3Converter(final DocumentVersionConverter preliminaryConverter)
    {
        this.preliminaryConverter = preliminaryConverter;
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.persistence.jdom.DocumentVersionConverter#convert(org.jdom.Element)
     */
    public Element convert(Element oldRoot)
    {
        if (preliminaryConverter != null) {
            oldRoot = preliminaryConverter.convert(oldRoot);
        }
        //Set version
        oldRoot.setAttribute("version", "3");
        
        final Iterator iterator = oldRoot.getChildren(FoodItemConverter.ELEMENT_FOODITEM).iterator();
        while (iterator.hasNext())
        {
            Element food = (Element) iterator.next();
            Element quantityElement = food.getChild("quantity");
            if (quantityElement != null)
            {
                final String desc = quantityElement.getAttributeValue("value");
                food.removeChild("quantity");
                
                final Element descriptionElement = new Element("description");
                descriptionElement.setText(desc);
                food.addContent(descriptionElement);
            }

        }
        return oldRoot;
    }

}
