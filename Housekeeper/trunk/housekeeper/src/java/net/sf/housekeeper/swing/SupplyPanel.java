package net.sf.housekeeper.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import net.sf.housekeeper.domain.FoodItem;
import net.sf.housekeeper.domain.FoodItemManager;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.renderpack.DateTableCellRenderer;

/**
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class SupplyPanel extends JPanel
{

    private final EventSelectionModel selectionModel;

    private final Action              newItemAction;

    private final Action              duplicateItemAction;

    private final Action              editItemAction;

    private final Action              deleteItemAction;

    private final String[]            TABLE_HEADERS   = { "Name", "Quantity",
            "Expiry"                                 };

    private final String[]            ITEM_PROPERTIES = {
            FoodItem.PROPERTYNAME_NAME, FoodItem.PROPERTYNAME_QUANTITY,
            FoodItem.PROPERTYNAME_EXPIRY             };

    private final boolean[] PROPERTY_MODIFYABLE = { false, false, false };
    
    SupplyPanel()
    {
        super();
        setLayout(new BorderLayout());

        //Init table
        final SortedList sortedList = new SortedList(FoodItemManager.INSTANCE
                .getSupplyList(), null);
        final EventTableModel tableModel = new EventTableModel(sortedList,
                TABLE_HEADERS, ITEM_PROPERTIES, PROPERTY_MODIFYABLE);
        final JTable table = new JTable(tableModel);

        //Add sorting functionality to headers
        final TableComparatorChooser comparatorChooser = new TableComparatorChooser(
                table, sortedList, false);
        comparatorChooser.chooseComparator(2, 0, false);

        //Listen for changes of the table row selection
        selectionModel = new EventSelectionModel(sortedList);
        table.setSelectionModel(selectionModel.getListSelectionModel());
        table.getSelectionModel()
                .addListSelectionListener(new ListSelectionListener() {

                    public void valueChanged(ListSelectionEvent e)
                    {
                        updateActionEnablement();
                    }
                });
        table.getSelectionModel()
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Register renderer for the expiry date
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.LONG);
        final TableCellRenderer dateRenderer = new DateTableCellRenderer(
                dateFormat.toPattern());
        table.getColumn("Expiry").setCellRenderer(dateRenderer);

        //init actions
        newItemAction = new NewAction();
        duplicateItemAction = new DuplicateAction();
        editItemAction = new EditAction();
        deleteItemAction = new DeleteAction();

        initButtons();

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void initButtons()
    {
        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton(newItemAction));
        buttonPanel.add(new JButton(duplicateItemAction));
        buttonPanel.add(new JButton(editItemAction));
        buttonPanel.add(new JButton(deleteItemAction));
        add(buttonPanel, BorderLayout.NORTH);
        updateActionEnablement();
    }

    private FoodItem getSelectedItem()
    {
        EventList selectionList = selectionModel.getEventList();

        int numSelected = selectionList.size();
        Object firstSelected = null;
        if (numSelected >= 1)
        {
            firstSelected = selectionList.get(0);
        }

        return (FoodItem) firstSelected;
    }

    private boolean hasSelection()
    {
        EventList selectionList = selectionModel.getEventList();
        int numSelected = selectionList.size();
        if (numSelected > 0)
        {
            return true;
        }
        return false;
    }

    /**
     * Opens an editor for the specified Item.
     * 
     * @param item The item to be edited.
     * @return True if the dialog has been canceled, false otherwise.
     */
    private boolean openEditor(FoodItem item)
    {
        FoodItemEditorDialog dialog = new FoodItemEditorDialog(
                MainFrame.INSTANCE, item);
        dialog.open();
        return dialog.hasBeenCanceled();
    }

    /**
     * Enables and disables Actions (and thus the buttons) depending on the
     * current selection state.
     */
    private void updateActionEnablement()
    {
        boolean hasSelection = hasSelection();
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
            super("Delete");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            FoodItemManager.INSTANCE.remove(getSelectedItem());
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
            final FoodItem selected = getSelectedItem();
            boolean canceled = openEditor(selected);
            if (!canceled)
            {
                FoodItemManager.INSTANCE.update(selected);
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
                FoodItemManager.INSTANCE.add(item);
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
            FoodItemManager.INSTANCE.duplicate(selectedItem);
        }

    }

}