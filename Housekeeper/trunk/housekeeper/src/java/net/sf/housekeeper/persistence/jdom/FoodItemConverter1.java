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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.sf.housekeeper.domain.FoodItem;

import org.jdom.Element;

/**
 * Utility class for converting between JDOM Elements and
 * {@link net.sf.housekeeper.domain.FoodItem}objects.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class FoodItemConverter1
{

    /**
     * Prevents instanciation.
     */
    private FoodItemConverter1()
    {
    }

    /**
     * Converts a JDOM Element object into a {@link FoodItem}.
     * 
     * @param foodItemElement The element to convert. It is not checked if this
     *            Element actually can be converted to a FoodItem.
     * @return The FoodItem object build from the given Element.
     */
    static FoodItem convert(final Element foodItemElement)
    {
        //Name
        final String itemName = foodItemElement.getAttributeValue("name");

        //Quantity
        final String quantity = foodItemElement.getChild("quantity")
                .getAttributeValue("value");

        //Expiry
        Date expiryDate = null;
        final Element expiryElement = foodItemElement.getChild("expiry");
        if (expiryElement != null)
        {
            final int day = Integer.parseInt(expiryElement
                    .getAttributeValue("day"));
            final int month = Integer.parseInt(expiryElement
                    .getAttributeValue("month"));
            final int year = Integer.parseInt(expiryElement
                    .getAttributeValue("year"));
            final Calendar expiryCalendar = new GregorianCalendar(year,
                    month - 1, day);
            expiryDate = expiryCalendar.getTime();
        }

        final FoodItem foodItem = new FoodItem(itemName, quantity, expiryDate);
        return foodItem;
    }

    /**
     * Converts a {@link FoodItem}object into a JDOM Element.
     * 
     * @param item The item to be converted.
     * @return The JDOM element representing the given FoodItem.
     */
    static Element convert(final FoodItem item)
    {
        final Element xmlElement = new Element("foodItem");

        //Name
        xmlElement.setAttribute("name", item.getName());

        //Quantity
        final Element quantityElement = new Element("quantity");
        quantityElement.setAttribute("value", item.getQuantity());
        xmlElement.addContent(quantityElement);

        //Expiry
        if (item.getExpiry() != null)
        {
            final Element expiryElement = new Element("expiry");
            final Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(item.getExpiry());
            expiryElement.setAttribute("day", ""
                    + calendar.get(GregorianCalendar.DAY_OF_MONTH));
            expiryElement.setAttribute("month", ""
                    + calendar.get(GregorianCalendar.MONTH));
            expiryElement.setAttribute("year", ""
                    + calendar.get(GregorianCalendar.YEAR));
            xmlElement.addContent(expiryElement);
        }

        return xmlElement;
    }
}