package net.sf.housekeeper.persistence.jdom;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import net.sf.housekeeper.domain.FoodItem;
import net.sf.housekeeper.domain.Household;

import org.jdom.Element;

/**
 * Converts XML element hierarchies into domain objects and vice-versa. It does
 * only support the latest version of the Housekeeper XML file format. For
 * converting between formats use
 * {@link net.sf.housekeeper.persistence.jdom.DOMConverter} objects.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class ModelConverter
{

    /**
     * The current file version.
     */
    private static final int CURRENT_FILE_VERSION = 1;

    /**
     * Creates domain objects from XML objects.
     * 
     * @return The converted Household object.
     * @param root The root element of the XML object tree. Must not be null.
     */
    Household convertToDomain(final Element root)
    {
        final Collection foodItems = new LinkedList();
        final Iterator foodItemIterator = root.getChildren("foodItem")
                .iterator();
        while (foodItemIterator.hasNext())
        {
            final Element element = (Element) foodItemIterator.next();
            final FoodItem item = FoodItemConverter.convert(element);
            foodItems.add(item);
        }

        final Household household = new Household(foodItems);
        return household;
    }

    /**
     * Creates an XML element hierarchy from the given domain objects.
     * 
     * @param household The data to save.
     * @return the element hierarchy.
     */
    Element convertDomainToXML(final Household household)
    {
        final Element root = new Element("household");
        root.setAttribute("version", "" + CURRENT_FILE_VERSION);

        final Iterator iter = household.getFoodItems().iterator();
        while (iter.hasNext())
        {
            final FoodItem item = (FoodItem) iter.next();
            root.addContent(FoodItemConverter.convert(item));
        }

        return root;
    }

}