package net.sf.housekeeper.persistence.jdom;

import java.util.ArrayList;
import java.util.Iterator;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.ExpiringItem;
import net.sf.housekeeper.domain.Household;

import org.jdom.Element;

/**
 * Converts XML element hierarchies into domain objects and vice-versa. It does
 * only support the latest version of the Housekeeper XML file format. For
 * converting between formats use
 * {@link net.sf.housekeeper.persistence.jdom.DocumentVersionConverter}objects.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class ModelConverter
{

    private static final String ELEMENT_FOOD_ITEM         = "foodItem";

    private static final String ELEMENT_ROOT              = "household";

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
        final Household household = new Household();
        final ArrayList items = getFoodItems((Category)household.getCategories().get(0), root);
        
        household.getFoodManager().replaceAll(items);

        return household;
    }

    /**
     * Not supported anymore.
     * 
     * @param household The data to save. Must not be null.
     * @return null.
     */
    Element convertDomainToXML(final Household household)
    {        
        return null;
    }

    /**
     * Creates {@link ExpiringItem}objects from a food container.
     * 
     * @param foodElement The container with the food elements.
     * @return A list of {@link ExpiringItem}objects.
     */
    private ArrayList getFoodItems(final Category categories, final Element foodElement)
    {
        final ArrayList food = new ArrayList();
        final Iterator foodItemIterator = foodElement
                .getChildren(ELEMENT_FOOD_ITEM).iterator();
        while (foodItemIterator.hasNext())
        {
            final Element element = (Element) foodItemIterator.next();
            final ExpiringItem item = ExpiringItemConverter.convert(categories, element);
            food.add(item);
        }
        return food;
    }

}