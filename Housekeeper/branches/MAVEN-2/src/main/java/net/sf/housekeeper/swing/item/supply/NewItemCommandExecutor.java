/*
 * This file is part of Housekeeper.
 * 
 * Housekeeper is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Housekeeper is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Housekeeper; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Copyright 2003-2004, The Housekeeper Project
 * http://housekeeper.berlios.de
 */

package net.sf.housekeeper.swing.item.supply;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.ExpirableItem;
import net.sf.housekeeper.domain.Item;
import net.sf.housekeeper.domain.ItemDAO;

import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.form.Form;
import org.springframework.util.Assert;

/**
 * Shows a dialog for adding a new supply item.
 */
public final class NewItemCommandExecutor extends
        AbstractActionCommandExecutor
{

    private ItemDAO<ExpirableItem> itemDAO;

    public NewItemCommandExecutor()
    {
        setEnabled(true);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.swing.item.AbstractNewItemCommandExecutor#createForm(net.sf.housekeeper.domain.Item)
     */
    protected Form createForm(Item object)
    {
        final ExpirableItemPropertiesForm form = new ExpirableItemPropertiesForm(
                (ExpirableItem) object);
        return form;
    }

    /**
     * Sets the {@link ItemDAO}for adding newly created items.
     * 
     * @param dao != null
     */
    public void setItemDAO(final ItemDAO<ExpirableItem> dao)
    {
        this.itemDAO = dao;
    }

    public void execute()
    {
        Assert.notNull(itemDAO,
                "itemManager must be set for proper operation.");
    
        final ExpirableItem foodObject = new ExpirableItem();
        foodObject.setCategory(Category.getSelectedCategory());
        final Form form = createForm(foodObject);
        final FormBackedDialogPage dialogPage = new FormBackedDialogPage(form);
        final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
                dialogPage)
        {
    
            protected void onAboutToShow()
            {
                setEnabled(true);
            }
    
            protected boolean onFinish()
            {
                form.commit();
                itemDAO.store(foodObject);
                return true;
            }
        };
        dialog.showDialog();
    }

}
