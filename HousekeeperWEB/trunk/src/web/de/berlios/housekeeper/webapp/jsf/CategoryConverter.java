package de.berlios.housekeeper.webapp.jsf;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import de.berlios.housekeeper.model.Category;

public class CategoryConverter implements Converter {

    private final Map categoryMap = new HashMap();
    
    public void setCategories(Collection categories)  {
	for (Iterator iter = categories.iterator(); iter.hasNext();) {
	    Category element = (Category) iter.next();
	    categoryMap.put(element.getId(), element);
	}
    }
    
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
	    throws ConverterException {
	Category c = (Category)categoryMap.get(new Long(arg2));
	
	return c;
    }

    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
	    throws ConverterException {
	Category c = (Category)arg2;
	return c.getId().toString();
    }

}
