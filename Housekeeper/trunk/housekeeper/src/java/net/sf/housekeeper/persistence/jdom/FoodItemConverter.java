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

import net.sf.housekeeper.domain.Food;

import org.jdom.Element;

/**
 * Utility class for converting between JDOM Elements and
 * {@link net.sf.housekeeper.domain.Food}objects.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class FoodItemConverter
{

    /**
     * Prevents instanciation.
     */
    private FoodItemConverter()
    {
    }

    /**
     * Converts a JDOM Element object into a {@link Food}.
     * 
     * @param foodElement The element to convert. It is not checked if this
     *            Element actually can be converted to a Food.
     * @return The Food object build from the given Element.
     */
    static Food convert(final Element foodElement)
    {
        final Food food = new Food();

        //Name
        final String itemName = foodElement.getAttributeValue("name");
        food.setName(itemName);

        //Quantity
        final Element quantityElement = foodElement.getChild("quantity");
        if (quantityElement != null)
        {
            String quantity = quantityElement.getAttributeValue("value");
            food.setQuantity(quantity);
        }

        //Expiry
        final Element expiryElement = foodElement.getChild("expiry");
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
            Date expiryDate = expiryCalendar.getTime();
            food.setExpiry(expiryDate);
        }

        return food;
    }

    /**
     * Converts a {@link Food}object into a JDOM Element.
     * 
     * @param item The item to be converted.
     * @return The JDOM element representing the given Food.
     */
    static Element convert(final Food item)
    {
        final Element xmlElement = new Element("foodItem");

        //Name
        xmlElement.setAttribute("name", item.getName());

        //Quantity
        if (item.getQuantity() != null)
        {
            final Element quantityElement = new Element("quantity");
            quantityElement.setAttribute("value", item.getQuantity());
            xmlElement.addContent(quantityElement);
        }

        //Expiry
        if (item.getExpiry() != null)
        {
            final Element expiryElement = new Element("expiry");
            final Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(item.getExpiry());
            expiryElement.setAttribute("day", ""
                    + calendar.get(GregorianCalendar.DAY_OF_MONTH));
            final int month = calendar.get(GregorianCalendar.MONTH) + 1;
            expiryElement.setAttribute("month", "" + month);
            expiryElement.setAttribute("year", ""
                    + calendar.get(GregorianCalendar.YEAR));
            xmlElement.addContent(expiryElement);
        }

        return xmlElement;
    }
}