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

import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.housekeeper.domain.Food;
import net.sf.housekeeper.domain.FoodManager;
import net.sf.housekeeper.util.LocalisationManager;

/**
 * Presenter for an overview of the supply.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class SupplyPresenter
{

    /**
     * Action object for deleting an action.
     */
    private final Action            deleteItemAction;

    /**
     * Action object for duplicating an existing item.
     */
    private final Action            duplicateItemAction;

    /**
     * Action object for editing an item.
     */
    private final Action            editItemAction;

    /**
     * Action object for creating a new item.
     */
    private final Action            newItemAction;

    /**
     * The parent Frame of this panel.
     */
    private final Frame             parent;

    /**
     * The manager for food objects.
     */
    private final FoodManager       itemManager;

    private final FoodListPresenter convPresenter;

    private final SupplyView        view;

    /**
     * Creates a new SupplyPanel.
     * 
     * @param parentFrame The parent frame for this panel.
     * @param itemManager The manager which holds the food items.
     */
    SupplyPresenter(final Frame parentFrame, final FoodManager itemManager)
    {
        super();

        this.parent = parentFrame;
        this.itemManager = itemManager;

        //Field instanciations
        newItemAction = new NewAction();
        duplicateItemAction = new DuplicateAction();
        editItemAction = new EditAction();
        deleteItemAction = new DeleteAction();

        final ListSelectionListener selectionListener = new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e)
            {
                updateActionEnablement();
            }
        };

        convPresenter = new FoodListPresenter(itemManager, "convenienceFood");
        convPresenter.addTableSelectionListener(selectionListener);

        view = new SupplyView(newItemAction, duplicateItemAction,
                editItemAction, deleteItemAction);
        view.addPanel(convPresenter.getView());

        updateActionEnablement();
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
     */
    private void addNewItem()
    {
        final Food item = new Food();
        boolean canceled = openEditor(item);
        if (!canceled)
        {
            itemManager.add(item);
        }
    }

    /**
     * Opens a dialog for editing the selected item.
     */
    private void editSelectedItem()
    {
        final Food selected = convPresenter.getSelected();
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
        final FoodEditorView view = new FoodEditorView(parent);
        final FoodEditorPresenter editor = new FoodEditorPresenter(
                view, item);

        return editor.show();
    }

    /**
     * Enables and disables Actions (and thus the buttons) depending on the
     * current selection state.
     */
    private void updateActionEnablement()
    {
        boolean hasSelection = convPresenter.hasSelection();
        duplicateItemAction.setEnabled(hasSelection);
        editItemAction.setEnabled(hasSelection);
        deleteItemAction.setEnabled(hasSelection);
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
        }

        public void actionPerformed(ActionEvent arg0)
        {
            convPresenter.deleteSelected();
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
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            convPresenter.duplicateSelected();
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

        private NewAction()
        {
            super();
            final String name = LocalisationManager.INSTANCE
                    .getText("gui.supply.new");
            putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent arg0)
        {
            addNewItem();
        }
    }

}