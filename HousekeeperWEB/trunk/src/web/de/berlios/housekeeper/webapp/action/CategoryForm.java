
package de.berlios.housekeeper.webapp.action;

import java.io.Serializable;

import de.berlios.housekeeper.webapp.action.BasePage;
import de.berlios.housekeeper.model.Category;
import de.berlios.housekeeper.service.Manager;

public class CategoryForm extends BasePage implements Serializable {
    private Manager manager;
    private Category category = new Category();
    private String id;

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String delete() {
        manager.removeObject(Category.class, category.getId());
        addMessage("category.deleted");

        return "list";
    }

    public String edit() {
        if (id != null) {
            category = (Category) manager.getObject(Category.class, new Long(id));
        } else {
            category = new Category();
        }

        return "edit";
    }

    public String save() {
        boolean isNew = (category.getId() == null);

        manager.saveObject(category);

        String key = (isNew) ? "category.added" : "category.updated";
        addMessage(key);

        if (isNew) {
            return "list";
        } else {
            return "edit";
        }
    }
}
