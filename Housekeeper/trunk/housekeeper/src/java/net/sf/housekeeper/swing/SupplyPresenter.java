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

import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.Action;

import net.sf.housekeeper.domain.Food;
import net.sf.housekeeper.domain.FoodManager;
import net.sf.housekeeper.swing.util.EventObjectListener;
import net.sf.housekeeper.swing.util.IconGenerator;
import net.sf.housekeeper.util.LocalisationManager;

/**
 * Presenter for an overview of a supply.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class SupplyPresenter
{

    private FoodListPresenter activePresenter;

    private final Action      deleteItemAction;

    private final Action      duplicateItemAction;

    private final Action      editItemAction;

    private final Action      newConvenienceFoodAction;

    private final Action      newMiscFoodAction;

    private final FoodManager itemManager;

    private final SupplyView  view;

    /**
     * Creates a new SupplyPanel.
     * 
     * @param itemManager The manager which holds the food items.
     */
    public SupplyPresenter(final FoodManager itemManager)
    {
        super();

        this.itemManager = itemManager;

        //init actions
        final String newConvName = LocalisationManager.INSTANCE
                .getText("gui.supply.newConvenienceFood");
        newConvenienceFoodAction = new NewAction(newConvName, "convenienceFood");

        final String newMiscName = LocalisationManager.INSTANCE
                .getText("gui.supply.newMiscFood");
        newMiscFoodAction = new NewAction(newMiscName, "misc");

        duplicateItemAction = new DuplicateAction();
        editItemAction = new EditAction();
        deleteItemAction = new DeleteAction();

        //Init button bar
        view = new SupplyView();
        view.addButton(newConvenienceFoodAction);
        view.addButton(newMiscFoodAction);
        view.addButton(duplicateItemAction);
        view.addButton(editItemAction);
        view.addButton(deleteItemAction);

        //Init tables
        final FoodListPresenter convPresenter = new FoodListPresenter(
                itemManager.getSupplyList(), "convenienceFood");
        final FoodListPresenter miscPresenter = new FoodListPresenter(
                itemManager.getSupplyList(), "misc");
        
        view.addPanel(convPresenter.getView());
        view.addPanel(miscPresenter.getView());

        updateActionEnablement();

        final EventObjectListener selectionListener = new EventObjectListener() {

            public void handleEvent(EventObject event)
            {
                updateActivePresenter((FoodListPresenter) event.getSource());
                updateActionEnablement();

            }
        };
        convPresenter.addTableSelectionListener(selectionListener);
        miscPresenter.addTableSelectionListener(selectionListener);
    }

    /**
     * Returns the view of this Presenter.
     * 
     * @return The view.
     */
    public SupplyView getView()
    {
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
            itemManager.add(item);
        }
    }

    /**
     * Deletes the item which is selected in the table.
     */
    private void deleteSelectedItem()
    {
        final Food selectedItem = activePresenter.getSelected();
        itemManager.remove(selectedItem);
    }

    /**
     * Duplicates the selected item.
     */
    private void duplicateSelectedItem()
    {
        final Food selectedItem = activePresenter.getSelected();
        itemManager.duplicate(selectedItem);
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
            itemManager.update(selected);
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
        final FoodEditorView editorView = new FoodEditorView(view.getParentFrame());
        final FoodEditorPresenter editor = new FoodEditorPresenter(editorView, item);

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
        duplicateItemAction.setEnabled(hasSelection);
        editItemAction.setEnabled(hasSelection);
        deleteItemAction.setEnabled(hasSelection);
    }

    private void updateActivePresenter(final FoodListPresenter presenter)
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
    
    /**
     * Deletes the currently selected item.
     */
    private class DeleteAction extends AbstractAction
    {

        private DeleteAction()
        {
            super();
            final String name = LocalisationManager.INSTANCE
                    .getText("gui.supply.delete");
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, IconGenerator.getIcon("delete_edit.gif"));
        }

        public void actionPerformed(ActionEvent arg0)
        {
            deleteSelectedItem();
        }
    }

    /**
     * Duplicates the selected item.
     */
    private class DuplicateAction extends AbstractAction
    {

        private DuplicateAction()
        {
            super();
            final String name = LocalisationManager.INSTANCE
                    .getText("gui.supply.duplicate");
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, IconGenerator.getIcon("copy_edit.gif"));
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            duplicateSelectedItem();
        }

    }

    /**
     * Shows a dialog for modifying the currently selected item and updates it.
     */
    private class EditAction extends AbstractAction
    {

        private EditAction()
        {
            super();
            final String name = LocalisationManager.INSTANCE
                    .getText("gui.supply.edit");
            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, IconGenerator.getIcon("text_edit.gif"));
        }

        public void actionPerformed(ActionEvent arg0)
        {
            editSelectedItem();
        }
    }

    /**
     * Shows a dialog for adding a new item.
     */
    private class NewAction extends AbstractAction
    {

        private final String category;

        private NewAction(final String name, final String category)
        {
            super();
            this.category = category;

            putValue(Action.NAME, name);
            putValue(Action.SMALL_ICON, IconGenerator.getIcon("add_obj.gif"));
        }

        public void actionPerformed(ActionEvent arg0)
        {
            addNewItem(category);
        }
    }

}