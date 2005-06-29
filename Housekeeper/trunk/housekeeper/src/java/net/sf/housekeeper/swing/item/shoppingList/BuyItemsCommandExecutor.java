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

package net.sf.housekeeper.swing.item.shoppingList;

import java.util.Collection;

import net.sf.housekeeper.domain.ExpirableItem;
import net.sf.housekeeper.domain.HighLevelManager;
import net.sf.housekeeper.domain.ShoppingListItem;
import net.sf.housekeeper.swing.item.ItemsView;
import net.sf.housekeeper.swing.item.supply.ExpirableItemPropertiesForm;

import org.springframework.core.closure.support.AbstractConstraint;
import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.application.PageComponent;
import org.springframework.richclient.application.View;
import org.springframework.richclient.application.ViewDescriptor;
import org.springframework.richclient.application.config.ApplicationWindowAware;
import org.springframework.richclient.application.support.AbstractApplicationPage;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.util.Assert;

/**
 * Moves a selected shopping list item to the supply.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class BuyItemsCommandExecutor extends
        AbstractActionCommandExecutor implements ApplicationWindowAware
{

    private ItemsView         itemsView;

    private ApplicationWindow window;

    private ViewDescriptor    viewDescriptor;

    private HighLevelManager  highLevelManager;

    /**
     *  
     */
    public BuyItemsCommandExecutor()
    {
        setEnabled(true);
    }

    /**
     * @param viewDescriptor The viewDescriptor to set.
     */
    public void setViewDescriptor(ViewDescriptor viewDescriptor)
    {
        this.viewDescriptor = viewDescriptor;
    }

    /**
     * @param highLevelManager The highLevelManager to set.
     */
    public void setHighLevelManager(HighLevelManager highLevelManager)
    {
        this.highLevelManager = highLevelManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.command.ActionCommandExecutor#execute()
     */
    public void execute()
    {
        Assert.notNull(highLevelManager);

        final AbstractApplicationPage page = (AbstractApplicationPage) window
                .getPage();
        itemsView = (ItemsView) findPageComponent(page.getPageComponents(),
                                                  viewDescriptor.getId());

        final ShoppingListItem selectedItem = (ShoppingListItem) itemsView
                .getSelectedItem();
        if (selectedItem != null)
        {
            final ExpirableItem itemPrototype = new ExpirableItem(selectedItem);
            final ExpirableItemPropertiesForm form = new ExpirableItemPropertiesForm(
                    itemPrototype);
            final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
                    form, null) {

                protected void onAboutToShow()
                {
                    setEnabled(true);
                }

                protected boolean onFinish()
                {
                    form.commit();
                    highLevelManager.buy(selectedItem, itemPrototype);
                    return true;
                }
            };
            dialog.showDialog();

            
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.config.ApplicationWindowAware#setApplicationWindow(org.springframework.richclient.application.ApplicationWindow)
     */
    public void setApplicationWindow(ApplicationWindow window)
    {
        this.window = window;
    }

    private PageComponent findPageComponent(final Collection pageComponents,
                                            final String viewDescriptorId)
    {
        return (PageComponent) new AbstractConstraint() {

            private static final long serialVersionUID = -5022322885507003473L;

            public boolean test(Object arg)
            {
                if (arg instanceof View)
                {
                    return ((View) arg).getId().equals(viewDescriptorId);
                }
                return false;
            }
        }.findFirst(pageComponents);
    }

}
