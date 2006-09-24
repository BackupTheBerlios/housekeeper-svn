package de.berlios.housekeeper.webapp.action;

import java.io.Serializable;
import java.util.List;

import de.berlios.housekeeper.webapp.action.BasePage;
import de.berlios.housekeeper.model.StockItem;
import de.berlios.housekeeper.service.Manager;

public class StockItemList extends BasePage implements Serializable {
    private Manager manager;

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List getStockItems() {
        return manager.getObjects(StockItem.class);
    }
}
