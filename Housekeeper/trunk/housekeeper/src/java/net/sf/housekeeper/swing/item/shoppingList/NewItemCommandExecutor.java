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
 * http://housekeeper.sourceforge.net
 */

package net.sf.housekeeper.swing.item.shoppingList;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.ShoppingListItem;
import net.sf.housekeeper.domain.ShoppingListManager;
import net.sf.housekeeper.domain.SupplyManager;

import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.util.Assert;

/**
 * Shows a dialog for adding a new item.
 */
public final class NewItemCommandExecutor extends AbstractActionCommandExecutor
{

    private ShoppingListManager supplyManager;

    /**
     * Creates a new executor which is always enabled.
     *  
     */
    public NewItemCommandExecutor()
    {
        setEnabled(true);
    }

    /**
     * Sets the {@link SupplyManager}for adding newly created items.
     * 
     * @param itemManager != null
     */
    public void setManager(final ShoppingListManager itemManager)
    {
        Assert.notNull(itemManager);
        this.supplyManager = itemManager;
    }

    public void execute()
    {
        Assert.notNull(supplyManager,
                       "itemManager must be set for proper operation.");

        final ShoppingListItem foodObject = new ShoppingListItem();
        foodObject.setCategory(Category.getSelectedCategory());
        final ShoppingListItemPropertiesForm form = new ShoppingListItemPropertiesForm(
                foodObject);
        final FormBackedDialogPage dialogPage = new FormBackedDialogPage(form);
        final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
                dialogPage) {

            protected void onAboutToShow()
            {
                setEnabled(true);
            }

            protected boolean onFinish()
            {
                form.commit();
                supplyManager.add(foodObject);
                return true;
            }
        };
        dialog.showDialog();
    }

}
