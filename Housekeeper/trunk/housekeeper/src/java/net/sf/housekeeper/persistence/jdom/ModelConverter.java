package net.sf.housekeeper.persistence.jdom;

import java.util.ArrayList;
import java.util.Iterator;

import net.sf.housekeeper.domain.Food;
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

    private static final String CONVENIENCE_FOOD_CATEGORY = "convenienceFoods";

    private static final String MISC_FOOD_CATEGORY        = "misc";

    private static final String CATEGORY_ATTRIBUTE        = "category";

    private static final String ELEMENT_FOOD_CONTAINER    = "food";

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

        final ArrayList convFood = getConvenienceFood(root);
        if (convFood != null)
        {
            household.getConvenienceFoodManager().replaceAll(convFood);
        }

        final ArrayList miscFood = getMiscFood(root);
        if (miscFood != null)
        {
            household.getMiscFoodManager().replaceAll(miscFood);
        }

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

        final Iterator iter = household.getConvenienceFoodManager()
                .getItemsIterator();
        addFoodContainerElement(root, CONVENIENCE_FOOD_CATEGORY, iter);

        final Iterator iter2 = household.getMiscFoodManager()
                .getItemsIterator();
        addFoodContainerElement(root, MISC_FOOD_CATEGORY, iter2);

        return root;
    }

    /**
     * Creates and adds an element containing food as a child of another
     * {@link Element}. If <code>objectIterator</code> has no objects,
     * <code>root</code> remains unmodified.
     * 
     * @param root The element to add the created element to.
     * @param category The category of the container.
     * @param objectIterator An iterator for the domain objects to convert.
     */
    private void addFoodContainerElement(final Element root,
                                         final String category,
                                         final Iterator objectIterator)
    {
        if (objectIterator.hasNext())
        {
            final Element foodContainer = new Element(ELEMENT_FOOD_CONTAINER);
            foodContainer.setAttribute(CATEGORY_ATTRIBUTE, category);

            while (objectIterator.hasNext())
            {
                final Food item = (Food) objectIterator.next();
                foodContainer.addContent(FoodItemConverter.convert(item));
            }

            root.addContent(foodContainer);
        }
    }

    /**
     * Generates all {@link Food}food objects for the "Convenience Food"
     * category.
     * 
     * @param root The root of the DOM.
     * @return The genrated objects.
     */
    private ArrayList getConvenienceFood(final Element root)
    {
        final Element foodElement = getFoodElement(root,
                                                   CONVENIENCE_FOOD_CATEGORY);
        ArrayList foodItems = null;
        if (foodElement != null)
        {
            foodItems = getFoodItems(foodElement);
        }

        return foodItems;
    }

    /**
     * Generates all {@link Food}food objects for the "Misc" category.
     * 
     * @param root The root of the DOM.
     * @return The genrated objects.
     */
    private ArrayList getMiscFood(final Element root)
    {
        final Element foodElement = getFoodElement(root, MISC_FOOD_CATEGORY);
        ArrayList foodItems = null;
        if (foodElement != null)
        {
            foodItems = getFoodItems(foodElement);
        }

        return foodItems;
    }

    /**
     * Looks up the the JDOM element named "food" which has a category attribute
     * with a value of <code>category</code>.
     * 
     * @param root The root of the DOM.
     * @param category The name of the category.
     * @return The looked-up element. If it doesn't exist, null is returned.
     */
    private Element getFoodElement(final Element root, final String category)
    {

        final Iterator foodElementIterator = root
                .getChildren(ELEMENT_FOOD_CONTAINER).iterator();
        while (foodElementIterator.hasNext())
        {
            Element element = (Element) foodElementIterator.next();
            if (element.getAttributeValue(CATEGORY_ATTRIBUTE).equals(category))
            {
                return element;
            }
        }
        return null;
    }

    /**
     * Creates {@link Food}objects from a food container.
     * 
     * @param foodElement The container with the food elements.
     * @return A list of {@link Food}objects.
     */
    private ArrayList getFoodItems(final Element foodElement)
    {
        final ArrayList food = new ArrayList();
        final Iterator foodItemIterator = foodElement
                .getChildren(ELEMENT_FOOD_ITEM).iterator();
        while (foodItemIterator.hasNext())
        {
            final Element element = (Element) foodItemIterator.next();
            final Food item = FoodItemConverter.convert(element);
            food.add(item);
        }
        return food;
    }

}