
package de.berlios.housekeeper.webapp.action;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import de.berlios.housekeeper.webapp.action.BasePage;
import de.berlios.housekeeper.webapp.jsf.CategoryConverter;
import de.berlios.housekeeper.model.Category;
import de.berlios.housekeeper.model.StockItem;
import de.berlios.housekeeper.service.Manager;

public class StockItemForm extends BasePage implements Serializable {
    private Manager manager;
    private StockItem stockItem = new StockItem();
    private String id;

    private Set categories = new HashSet();
    
    private CategoryConverter converter = null;
    
    public StockItemForm() {

    }

    public Set getCategories() {	
        return categories;
    }

    public void setCategories(Set categories) {
        this.categories = categories;
    }
    
    public void setManager(Manager manager) {
        this.manager = manager;
        
	List allCategories = manager.getObjects(Category.class);
	for (Iterator iter = allCategories.iterator(); iter.hasNext();) {
	    Category element = (Category) iter.next();
	    categories.add(new SelectItem(element, element.getName()));
	}
	
	converter = new CategoryConverter();
	converter.setCategories(allCategories);
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String delete() {
        manager.removeObject(StockItem.class, stockItem.getId());
        addMessage("stockItem.deleted");

        return "list";
    }

    public String edit() {
        if (id != null) {
            stockItem = (StockItem) manager.getObject(StockItem.class, new Long(id));
        } else {
            stockItem = new StockItem();
        }

        return "edit";
    }

    public String save() {
        boolean isNew = (stockItem.getId() == null);

        manager.saveObject(stockItem);

        String key = (isNew) ? "stockItem.added" : "stockItem.updated";
        addMessage(key);

        if (isNew) {
            return "list";
        } else {
            return "edit";
        }
    }
    
    public Converter getConvert() {
	return converter;
    }
}
