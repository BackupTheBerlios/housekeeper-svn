package net.sf.housekeeper.persistence.jdom;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.sf.housekeeper.domain.Food;
import net.sf.housekeeper.domain.FoodManager;
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
     * Creates a new converter.
     */
    ModelConverter()
    {
        
    }
    
    /**
     * Creates domain objects from XML objects.
     * 
     * @param root The root element of the DOM. Must not be null.
     * @return The Household created from the DOM. Is not null.
     */
    Household convertToDomain(final Element root)
    {
        final List food = new LinkedList();
        final Iterator foodItemIterator = root.getChildren("foodItem")
                .iterator();
        while (foodItemIterator.hasNext())
        {
            final Element element = (Element) foodItemIterator.next();
            final Food item = FoodItemConverter.convert(element);
            food.add(item);
        }
        final FoodManager foodManager = new FoodManager(food);
        final Household household = new Household(foodManager);
        return household;
    }

    /**
     * Creates an XML element hierarchy from the given domain objects.
     * 
     * @param household The data to save. Must not be null.
     * @return the DOM generated from the domain. Is not null.
     */
    Element convertDomainToXML(final Household household)
    {
        final Element root = new Element("household");
        root.setAttribute("version", "" + CURRENT_FILE_VERSION);

        final Iterator iter = household.getFoodManager().getItemsIterator();
        while (iter.hasNext())
        {
            final Food item = (Food) iter.next();
            root.addContent(FoodItemConverter.convert(item));
        }

        return root;
    }

}