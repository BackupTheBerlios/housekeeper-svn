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

package net.sf.housekeeper.swing.item;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.ExpirableItem;
import net.sf.housekeeper.domain.ItemManager;
import net.sf.housekeeper.event.HousekeeperEvent;

import org.springframework.binding.form.FormModel;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.forms.SwingFormModel;

/**
 * Shows a dialog for adding a new item.
 */
public final class NewItemCommandExecutor extends AbstractActionCommandExecutor
        implements ApplicationListener
{

    private Category    category;

    private ItemManager itemManager;

    /**
     */
    NewItemCommandExecutor()
    {
        setEnabled(true);
    }

    public void setItemManager(final ItemManager itemManager)
    {
        this.itemManager = itemManager;
    }

    public void execute()
    {
        final ExpirableItem foodObject = new ExpirableItem();
        foodObject.setCategory(category);
        final FormModel formModel = SwingFormModel.createFormModel(foodObject);
        final ExpirableItemPropertiesForm form = new ExpirableItemPropertiesForm(
                formModel);
        final FormBackedDialogPage dialogPage = new FormBackedDialogPage(form);
        final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
                dialogPage) {

            protected void onAboutToShow()
            {
                setEnabled(true);
            }

            protected boolean onFinish()
            {
                formModel.commit();
                itemManager.add(foodObject);
                return true;
            }
        };
        dialog.showDialog();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ApplicationEvent e)
    {
        if (e instanceof HousekeeperEvent)
        {
            final HousekeeperEvent le = (HousekeeperEvent) e;
            if (le.isEventType(HousekeeperEvent.SELECTED))
            {
                if (le.getSource() instanceof Category)
                {
                    category = (Category) le.getSource();
                } else
                {
                    category = null;
                }
            }
        }
    }
}
