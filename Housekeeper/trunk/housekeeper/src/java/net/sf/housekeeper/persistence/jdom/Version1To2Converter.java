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

import org.jdom.Element;


/**
 * @author
 * @version $Revision$, $Date$
 */
final class Version1To2Converter implements DocumentVersionConverter
{


    public Element convert(Element oldRoot)
    {        
        //Set version
        final Element newRoot = new Element(oldRoot.getName());
        newRoot.setAttribute("version", "2");
        
        //Copy foodItem elements
        final Collection foodItems = oldRoot.cloneContent();

        final Element convenianceElement = new Element("food");
        convenianceElement.setAttribute("category", "convenienceFoods");
        convenianceElement.addContent(foodItems);
        
        newRoot.addContent(convenianceElement);
        return newRoot;
    }
    

}
