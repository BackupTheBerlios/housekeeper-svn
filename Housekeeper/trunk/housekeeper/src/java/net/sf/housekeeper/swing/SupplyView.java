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

package net.sf.housekeeper.swing;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.housekeeper.domain.Food;
import net.sf.housekeeper.domain.FoodManager;
import net.sf.housekeeper.swing.util.EventObjectListener;
import net.sf.housekeeper.util.LocalisationManager;

import org.springframework.richclient.application.PageComponentContext;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.command.support.GlobalCommandIds;

/**
 * View showing a table with convenience foods and a table with misc food.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class SupplyView extends AbstractView
{

    private FoodTableView                  activePresenter;

    private final EditCommandExecutor      editExecutor               = new EditCommandExecutor();

    private final NewCommandExecutor       newConvenienceFoodExecutor = new NewCommandExecutor(
                                                                              Food.CATEGORY_CONVENIENCE_FOOD);

    private final NewCommandExecutor       newMiscFoodExecutor        = new NewCommandExecutor(
                                                                              Food.CATEGORY_MISC);

    private final DuplicateCommandExecutor duplicateExecutor          = new DuplicateCommandExecutor();

    private final DeleteCommandExecutor    deleteExecutor             = new DeleteCommandExecutor();

    private FoodManager                    foodManager;

    private JPanel                         view;

    private FoodTableView                  convPresenter;

    private FoodTableView                  miscPresenter;

    /**
     * Creates a new SupplyPanel.
     */
    public SupplyView()
    {
        super();

        newConvenienceFoodExecutor.setEnabled(true);
        newMiscFoodExecutor.setEnabled(true);

        convPresenter = new FoodTableView();
        convPresenter.setCategory("convenienceFood");

        miscPresenter = new FoodTableView();
        miscPresenter.setCategory("misc");
    }

    /**
     * Sets the manager which holds the data to display.
     * 
     * @param manager
     */
    public void setFoodManager(final FoodManager manager)
    {
        this.foodManager = manager;
        convPresenter.setFoodList(manager.getSupplyList());
        miscPresenter.setFoodList(manager.getSupplyList());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.support.AbstractView#registerLocalCommandExecutors(org.springframework.richclient.application.PageComponentContext)
     */
    protected void registerLocalCommandExecutors(PageComponentContext context)
    {
        context.register(GlobalCommandIds.DELETE, deleteExecutor);
        context.register("editCommand", editExecutor);
        context.register("duplicateCommand", duplicateExecutor);
        context.register("newConvenienceFoodCommand",
                         newConvenienceFoodExecutor);
        context.register("newMiscFoodCommand", newMiscFoodExecutor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.support.AbstractView#createControl()
     */
    protected JComponent createControl()
    {
        view = new JPanel();
        view.setLayout(new GridLayout(1, 0));

        JPanel convPanel = (JPanel) convPresenter.getControl();
        final String convPanelTitle = LocalisationManager.INSTANCE
                .getText("domain.food.convenienceFoods");
        convPanel.setBorder(BorderFactory.createTitledBorder(convPanelTitle));
        view.add(convPanel);

        JPanel miscPanel = (JPanel) miscPresenter.getControl();
        final String miscPanelTitle = LocalisationManager.INSTANCE
                .getText("domain.food.misc");
        miscPanel.setBorder(BorderFactory.createTitledBorder(miscPanelTitle));
        view.add(miscPanel);

        final EventObjectListener selectionListener = new EventObjectListener() {

            public void handleEvent(EventObject event)
            {
                updateActivePresenter((FoodTableView) event.getSource());
                updateActionEnablement();

            }
        };
        convPresenter.addTableSelectionListener(selectionListener);
        miscPresenter.addTableSelectionListener(selectionListener);
        
        final MouseListener doubleClickListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2
                        && e.getButton() == MouseEvent.BUTTON1) {
                    if (editExecutor.isEnabled()) {
                        editExecutor.execute();
                    }
                }
            }
        };
        convPresenter.addTableMouseListener(doubleClickListener);
        miscPresenter.addTableMouseListener(doubleClickListener);

        updateActionEnablement();

        return view;
    }
    
    /**
     * Opens a dialog for adding a new item.
     * 
     * @param category The category to assign to the item.
     */
    private void addNewItem(final String category)
    {
        final Food item = new Food();
        boolean canceled = openEditor(item);
        if (!canceled)
        {
            item.setCategory(category);
            foodManager.add(item);
        }
    }

    /**
     * Deletes the item which is selected in the table.
     */
    private void deleteSelectedItem()
    {
        final Food selectedItem = activePresenter.getSelected();
        foodManager.remove(selectedItem);
    }

    /**
     * Duplicates the selected item.
     */
    private void duplicateSelectedItem()
    {
        final Food selectedItem = activePresenter.getSelected();
        foodManager.duplicate(selectedItem);
    }

    /**
     * Opens a dialog for editing the selected item.
     */
    private void editSelectedItem()
    {
        final Food selected = activePresenter.getSelected();
        boolean canceled = openEditor(selected);
        if (!canceled)
        {
            foodManager.update(selected);
        }
    }

    /**
     * Opens an editor for the specified Item.
     * 
     * @param item The item to be edited.
     * @return True if the dialog has been canceled, false otherwise.
     */
    private boolean openEditor(Food item)
    {
        final FoodEditorView editorView = new FoodEditorView(getActiveWindow()
                .getControl());
        final FoodEditorPresenter editor = new FoodEditorPresenter(editorView,
                item);

        return editor.show();
    }

    /**
     * Enables and disables Actions (and thus the buttons) depending on the
     * current selection state.
     */
    private void updateActionEnablement()
    {
        boolean hasSelection = activePresenter != null
                && activePresenter.hasSelection();
        duplicateExecutor.setEnabled(hasSelection);
        editExecutor.setEnabled(hasSelection);
        deleteExecutor.setEnabled(hasSelection);
    }

    private void updateActivePresenter(final FoodTableView presenter)
    {
        if (presenter != activePresenter)
        {
            if (activePresenter != null)
            {
                activePresenter.clearSelection();
            }

            activePresenter = presenter;
        }
    }

    private class DeleteCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            deleteSelectedItem();
        }
    }

    /**
     * Duplicates the selected item.
     */
    private class DuplicateCommandExecutor extends
            AbstractActionCommandExecutor
    {

        public void execute()
        {
            duplicateSelectedItem();
        }
    }

    /**
     * Shows a dialog for modifying the currently selected item and updates it.
     */
    private class EditCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            editSelectedItem();
        }
    }

    /**
     * Shows a dialog for adding a new item.
     */
    private class NewCommandExecutor extends AbstractActionCommandExecutor
    {

        private final String category;

        private NewCommandExecutor(final String category)
        {
            super();
            this.category = category;
        }

        public void execute()
        {
            addNewItem(category);
        }
    }

}
