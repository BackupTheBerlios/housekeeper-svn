package net.sf.housekeeper.persistence.jdom;

import java.util.ArrayList;
import java.util.Iterator;

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

    private static final String ELEMENT_ITEM         = "item";

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
        final ArrayList items = getItems(root);
        
        household.getFoodManager().replaceAll(items);

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
        final Element root = new Element(ELEMENT_ROOT);

        final Iterator iter = household.getFoodManager()
        .getItemsIterator();
        while (iter.hasNext())
        {
            final ExpiringItem item = (ExpiringItem) iter.next();
            root.addContent(ExpiringItemConverter.convert(item));
        }
        
        return root;
    }

    /**
     * Creates {@link ExpiringItem}objects from an item container.
     * 
     * @param itemElement The container with the item elements.
     * @return A list of {@link ExpiringItem}objects.
     */
    private ArrayList getItems(final Element itemElement)
    {
        final ArrayList food = new ArrayList();
        final Iterator foodItemIterator = itemElement
                .getChildren(ELEMENT_ITEM).iterator();
        while (foodItemIterator.hasNext())
        {
            final Element element = (Element) foodItemIterator.next();
            final ExpiringItem item = ExpiringItemConverter.convert(element);
            food.add(item);
        }
        return food;
    }

}