package de.berlios.housekeeper.webapp.action;

import java.io.Serializable;
import java.util.List;

import de.berlios.housekeeper.webapp.action.BasePage;
import de.berlios.housekeeper.model.Category;
import de.berlios.housekeeper.service.Manager;

public class CategoryList extends BasePage implements Serializable {
    private Manager manager;

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List getCategorys() {
        return manager.getObjects(Category.class);
    }
}
