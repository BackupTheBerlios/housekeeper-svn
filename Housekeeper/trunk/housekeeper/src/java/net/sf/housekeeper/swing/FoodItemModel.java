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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;

import net.sf.housekeeper.domain.FoodItemManager;
import net.sf.housekeeper.domain.FoodItem;

import com.jgoodies.binding.list.SelectionInList;

/**
 * This is the UI model managing and editing StockItems. In other words, this
 * class turns the raw data and operations form the Household into a form usable
 * in a user interface.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class FoodItemModel
{

    /**
     * Action for deleting the selected Item.
     */
    private final Action          deleteAction;

    /**
     * Action for editing the selected Item.
     */
    private final Action          editAction;

    /**
     * Action for duplicating the selected Item.
     */
    private final Action          duplicateAction;

    /**
     * Manager for the items in the domain.
     */
    private final FoodItemManager itemManager;

    /**
     * Holds the items list plus the seletion information.
     */
    private final SelectionInList itemSelection;

    /**
     * Action for adding a new Item.
     */
    private final Action          newAction;

    /**
     * Creates a new model with the underlying household.
     * 
     * @param household Manager of the StockItems.
     */
    public FoodItemModel(final FoodItemManager household)
    {
        this.itemManager = household;
        itemSelection = new SelectionInList(household.getSupplyListModel());

        newAction = new NewAction();
        editAction = new EditAction();
        deleteAction = new DeleteAction();
        duplicateAction = new DuplicateAction();
        updateActionEnablement();

        itemSelection
                .addPropertyChangeListener(
                                           SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                                           new SelectionHandler());
    }

    /**
     * Returns the Action for deleting the selected Item.
     * 
     * @return Returns the deleteAction.
     */
    public Action getDeleteAction()
    {
        return deleteAction;
    }

    /**
     * Returns the Action for editing the selected Item.
     * 
     * @return Returns the editAction.
     */
    public Action getEditAction()
    {
        return editAction;
    }

    /**
     * Returns the list of Items with the current selection. Useful to display
     * the managed Items in a JList or JTable.
     * 
     * @return the List of Items with selection
     */
    public SelectionInList getItemSelection()
    {
        return itemSelection;
    }

    /**
     * Returns the Action for adding a new Item.
     * 
     * @return Returns the newAction.
     */
    public Action getNewAction()
    {
        return newAction;
    }

    /**
     * Returns the Action for duplicating an Item.
     * 
     * @return Returns the dublicateAction.
     */
    public Action getDuplicateAction()
    {
        return duplicateAction;
    }

    /**
     * Returns the index of the selected Item in the list.
     * 
     * @return the index of the selected Item in the list.
     */
    private int getSelectedIndex()
    {
        return getItemSelection().getSelectionIndex();
    }

    /**
     * Returns the selected item.
     * 
     * @returnthe selected item.
     */
    private FoodItem getSelectedItem()
    {
        return (FoodItem) getItemSelection().getSelection();
    }

    /**
     * Opens an editor for the specified Item.
     * 
     * @param item The item to be edited.
     * @return True if the dialog has been canceled, false otherwise.
     */
    private boolean openEditor(FoodItem item)
    {
        FoodItemEditorDialog dialog = new FoodItemEditorDialog(MainFrame.INSTANCE, item);
        dialog.open();
        return dialog.hasBeenCanceled();
    }

    /**
     * Enables and disables Actions (and thus the buttons) depending on the
     * current selection state.
     */
    private void updateActionEnablement()
    {
        boolean hasSelection = getItemSelection().hasSelection();
        getDuplicateAction().setEnabled(hasSelection);
        getEditAction().setEnabled(hasSelection);
        getDeleteAction().setEnabled(hasSelection);
    }

    /**
     * Deletes the currently selected item.
     */
    private class DeleteAction extends AbstractAction
    {

        private DeleteAction()
        {
            super("Delete");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            itemManager.remove(getSelectedItem());
        }
    }

    /**
     * Shows a dialog for modifying the currently selected item and updates it.
     */
    private class EditAction extends AbstractAction
    {

        private EditAction()
        {
            super("Edit");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            boolean canceled = openEditor(getSelectedItem());
            if (!canceled)
            {
                itemManager.markItemChanged(getSelectedIndex());
            }
        }
    }

    /**
     * Shows a dialog for adding a new item.
     */
    private class NewAction extends AbstractAction
    {

        private NewAction()
        {
            super("Add item to supply");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            final FoodItem item = new FoodItem();
            boolean canceled = openEditor(item);
            if (!canceled)
            {
                itemManager.add(item);
            }
        }
    }

    /**
     * Duplicates the selected item.
     */
    private class DuplicateAction extends AbstractAction
    {

        private DuplicateAction()
        {
            super("Duplicate");
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            final FoodItem selectedItem = getSelectedItem();
            final FoodItem newItem = (FoodItem) selectedItem.clone();
            itemManager.add(newItem);
        }

    }

    /**
     * Updates the enablement of the buttons when the selection state of the
     * model changes.
     */
    private class SelectionHandler implements PropertyChangeListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent evt)
        {
            updateActionEnablement();
        }
    }

}