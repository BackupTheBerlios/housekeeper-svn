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

package net.sf.housekeeper.swing.item;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.Item;
import net.sf.housekeeper.domain.ItemManager;

import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.form.Form;
import org.springframework.util.Assert;

/**
 * Shows a dialog for adding a new item.
 */
public abstract class AbstractNewItemCommandExecutor extends
        AbstractActionCommandExecutor
{

    private ItemManager supplyManager;

    /**
     * Creates a new executor which is always enabled.
     */
    public AbstractNewItemCommandExecutor()
    {
        setEnabled(true);
    }

    /**
     * Sets the {@link ItemManager}for adding newly created items.
     * 
     * @param itemManager !=
     *            null
     */
    public void setManager(final ItemManager itemManager)
    {
        this.supplyManager = itemManager;
    }

    public void execute()
    {
        Assert.notNull(supplyManager,
                "itemManager must be set for proper operation.");

        final Item foodObject = createItem();
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
                supplyManager.add(foodObject);
                return true;
            }
        };
        dialog.showDialog();
    }

    /**
     * Callback method to provide the actual form to show.
     * 
     * @param object
     *            The object to create the form for.
     * @return != null
     */
    protected abstract Form createForm(Item object);

    /**
     * Callback method to provide an new instance of the specific Item object.
     * 
     * @return != null
     */
    protected abstract Item createItem();

}
