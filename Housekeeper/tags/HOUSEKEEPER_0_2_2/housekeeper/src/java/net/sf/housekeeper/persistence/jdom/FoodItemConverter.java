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
     * The name of the attribute "name" of the element
     * {@link FoodItemConverter#ELEMENT_CATEGORY}.
     */
    public static final String ATTRIBUTE_CATEGORY_NAME     = "name";
    
    /**
     * The name of the attribute "day" of the element
     * {@link FoodItemConverter#ELEMENT_EXPIRY}.
     */
    public static final String ATTRIBUTE_EXPIRY_DAY     = "day";

    /**
     * The name of the attribute "month" of the element
     * {@link FoodItemConverter#ELEMENT_EXPIRY}.
     */
    public static final String ATTRIBUTE_EXPIRY_MONTH   = "month";

    /**
     * The name of the attribute "year" of the element
     * {@link FoodItemConverter#ELEMENT_EXPIRY}.
     */
    public static final String ATTRIBUTE_EXPIRY_YEAR    = "year";

    /**
     * The name of the attribute "name" of the element
     * {@link FoodItemConverter#ELEMENT_FOODITEM}.
     */
    public static final String ATTRIBUTE_NAME           = "name";

    /**
     * The name of the attribute "value" of the element
     * {@link FoodItemConverter#ELEMENT_QUANTITY}.
     */
    public static final String ATTRIBUTE_QUANTITY_VALUE = "value";

    /**
     * The name of the "category" element.
     */
    public static final String ELEMENT_CATEGORY         = "category";

    /**
     * The name of the expiry element.
     */
    public static final String ELEMENT_EXPIRY           = "expiry";

    /**
     * The name of the root element.
     */
    public static final String ELEMENT_FOODITEM         = "foodItem";

    /**
     * The name of the quantity element.
     */
    public static final String ELEMENT_QUANTITY         = "quantity";

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
        final String itemName = foodElement.getAttributeValue(ATTRIBUTE_NAME);
        food.setName(itemName);

        //Quantity
        final Element quantityElement = foodElement.getChild(ELEMENT_QUANTITY);
        if (quantityElement != null)
        {
            String quantity = quantityElement
                    .getAttributeValue(ATTRIBUTE_QUANTITY_VALUE);
            food.setQuantity(quantity);
        }

        //Expiry
        final Element expiryElement = foodElement.getChild(ELEMENT_EXPIRY);
        if (expiryElement != null)
        {
            final int day = Integer.parseInt(expiryElement
                    .getAttributeValue(ATTRIBUTE_EXPIRY_DAY));
            final int month = Integer.parseInt(expiryElement
                    .getAttributeValue(ATTRIBUTE_EXPIRY_MONTH));
            final int year = Integer.parseInt(expiryElement
                    .getAttributeValue(ATTRIBUTE_EXPIRY_YEAR));
            final Calendar expiryCalendar = new GregorianCalendar(year,
                    month - 1, day);
            Date expiryDate = expiryCalendar.getTime();
            food.setExpiry(expiryDate);
        }

        //Category
        final Element categoryElement = foodElement.getChild(ELEMENT_CATEGORY);
        if (categoryElement != null)
        {
            String category = categoryElement.getAttributeValue(ATTRIBUTE_CATEGORY_NAME);
            food.setCategory(category);
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
        final Element xmlElement = new Element(ELEMENT_FOODITEM);

        //Name
        xmlElement.setAttribute(ATTRIBUTE_NAME, item.getName());

        //Quantity
        if (item.getQuantity() != null)
        {
            final Element quantityElement = new Element(ELEMENT_QUANTITY);
            quantityElement.setAttribute(ATTRIBUTE_QUANTITY_VALUE, item
                    .getQuantity());
            xmlElement.addContent(quantityElement);
        }

        //Expiry
        if (item.getExpiry() != null)
        {
            final Element expiryElement = new Element(ELEMENT_EXPIRY);
            final Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(item.getExpiry());
            expiryElement.setAttribute(ATTRIBUTE_EXPIRY_DAY, ""
                    + calendar.get(GregorianCalendar.DAY_OF_MONTH));
            final int month = calendar.get(GregorianCalendar.MONTH) + 1;
            expiryElement.setAttribute(ATTRIBUTE_EXPIRY_MONTH, "" + month);
            expiryElement.setAttribute(ATTRIBUTE_EXPIRY_YEAR, ""
                    + calendar.get(GregorianCalendar.YEAR));
            xmlElement.addContent(expiryElement);
        }

        //Category
        if (item.getCategory() != null)
        {
            String category = item.getCategory();
            Element categoryElement = new Element(ELEMENT_CATEGORY);
            categoryElement.setAttribute(ATTRIBUTE_CATEGORY_NAME, category);
            xmlElement.addContent(categoryElement);
        }

        return xmlElement;
    }

    /**
     * Prevents instanciation.
     */
    private FoodItemConverter()
    {
    }
}