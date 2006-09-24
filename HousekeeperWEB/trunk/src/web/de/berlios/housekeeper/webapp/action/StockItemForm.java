
package de.berlios.housekeeper.webapp.action;

import java.io.Serializable;

import de.berlios.housekeeper.webapp.action.BasePage;
import de.berlios.housekeeper.model.StockItem;
import de.berlios.housekeeper.service.Manager;

public class StockItemForm extends BasePage implements Serializable {
    private Manager manager;
    private StockItem stockItem = new StockItem();
    private String id;

    public void setManager(Manager manager) {
        this.manager = manager;
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
}
