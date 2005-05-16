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

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.ExpirableItem;
import net.sf.housekeeper.domain.Item;
import net.sf.housekeeper.domain.ItemManager;
import net.sf.housekeeper.event.CategoryEvent;
import net.sf.housekeeper.event.HousekeeperEvent;
import net.sf.housekeeper.event.SupplyEvent;

import org.springframework.binding.form.FormModel;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.richclient.application.PageComponentContext;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.command.support.GlobalCommandIds;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.forms.SwingFormModel;
import org.springframework.richclient.table.ColumnToSort;
import org.springframework.richclient.table.SortableTableModel;
import org.springframework.richclient.table.TableUtils;
import org.springframework.richclient.table.renderer.DateTimeTableCellRenderer;
import org.springframework.richclient.util.PopupMenuMouseListener;

/**
 * A view for the display of all items on hand.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ItemsTableView extends AbstractView implements
        ApplicationListener
{

    private JTable                         itemsTable;

    private ItemsTableModel                tableModel;

    private final EditCommandExecutor      editExecutor      = new EditCommandExecutor();

    private final DuplicateCommandExecutor duplicateExecutor = new DuplicateCommandExecutor();

    private final DeleteCommandExecutor    deleteExecutor    = new DeleteCommandExecutor();

    private ItemManager                    itemManager;

    private Category                       category;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.factory.AbstractControlFactory#createControl()
     */
    protected JComponent createControl()
    {
        initTable(getApplicationContext());
        configureTable(itemsTable);
        refresh();

        final JPanel panel = new JPanel();
        //Without that, the tables won't grow and shrink with the window's
        // size.
        panel.setLayout(new GridLayout());
        panel.add(new JScrollPane(itemsTable));

        return panel;
    }

    void initTable(MessageSource messageSource)
    {
        tableModel = new ItemsTableModel(new ArrayList(), messageSource);
        itemsTable = new JTable(tableModel);
    }

    private void configureTable(JTable table)
    {
        TableUtils.attachSorter(table);

        final SortableTableModel sortableModel = (SortableTableModel) table
                .getModel();
        sortableModel.setComparator(2, new ExpiryDateComparator());

        //Sort expiry dates by default
        sortableModel.sortByColumn(new ColumnToSort(0, 2));

        table.getSelectionModel()
                .addListSelectionListener(new TableSelectionListener());
        table.getSelectionModel()
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new PopupTriggerListener(createContextMenu()));
        table.addMouseListener(new DoubleClickListener());
        assignDateColumnRenderer(table, 2);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.support.AbstractView#registerLocalCommandExecutors(org.springframework.richclient.application.PageComponentContext)
     */
    protected void registerLocalCommandExecutors(PageComponentContext context)
    {
        context.register(GlobalCommandIds.DELETE, deleteExecutor);
        context.register(GlobalCommandIds.PROPERTIES, editExecutor);
        context.register("duplicateCommand", duplicateExecutor);
    }

    /**
     * Enables and disables Actions (and thus the buttons) depending on the
     * current selection state.
     */
    private void updateActionEnablement()
    {
        boolean hasSelection = hasSelection();
        duplicateExecutor.setEnabled(hasSelection);
        editExecutor.setEnabled(hasSelection);
        deleteExecutor.setEnabled(hasSelection);
    }

    private void setCategory(Category category)
    {
        this.category = category;
        refresh();
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

            if (le instanceof SupplyEvent)
            {
                setCategory(category);
            } else if (le instanceof CategoryEvent
                    && le.isEventType(HousekeeperEvent.SELECTED))
            {
                final Category cat;
                if (le.objectIsNotNull())
                {
                    cat = (Category) le.getSource();
                } else
                {
                    cat = null;
                }

                SwingUtilities.invokeLater(new Runnable() {

                    public void run()
                    {
                        setCategory(cat);
                    }
                });
            } else if (le.isEventType(HousekeeperEvent.DATA_REPLACED))
            {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run()
                    {
                        refresh();
                    }
                });
            }
        }
    }

    private boolean hasSelection()
    {
        return itemsTable.getSelectedRow() != -1;
    }

    private JPopupMenu createContextMenu()
    {
        CommandGroup convCommandGroup = getWindowCommandManager()
                .createCommandGroup(
                                    "tablePopupCommandGroup",
                                    new Object[] { "newItemCommand",
                                            "duplicateCommand",
                                            GlobalCommandIds.PROPERTIES,
                                            GlobalCommandIds.DELETE });
        return convCommandGroup.createPopupMenu();
    }

    private ExpirableItem getSelected()
    {
        if (hasSelection())
        {
            final int selectedRow = itemsTable.getSelectedRow();
            final SortableTableModel sortModel = (SortableTableModel) itemsTable
                    .getModel();
            final int convertedRow = sortModel
                    .convertSortedIndexToDataIndex(selectedRow);
            return (ExpirableItem) tableModel.getRow(convertedRow);
        }

        return null;
    }

    private void assignDateColumnRenderer(JTable table, int column)
    {
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.SHORT);
        final TableCellRenderer dateRenderer = new DateTimeTableCellRenderer(
                dateFormat);
        table.getColumnModel().getColumn(column).setCellRenderer(dateRenderer);
    }

    /**
     * Sets the manager which holds the data to display.
     * 
     * @param manager
     */
    public void setItemManager(final ItemManager manager)
    {
        this.itemManager = manager;
    }

    boolean modelContains(Item item)
    {
        final boolean exists = tableModel.rowOf(item) != -1;
        return exists;
    }

    void addItem(Item item)
    {
        tableModel.addRow(item);
    }

    private void refresh()
    {
        tableModel.clear();
        tableModel.addRows(itemManager.getItemsForCategory(category));
    }

    private final class DoubleClickListener extends MouseAdapter
    {

        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2)
            {
                getWindowCommandManager().getActionCommand("propertiesCommand")
                        .execute();
            }
        }
    }

    private final class TableSelectionListener implements ListSelectionListener
    {

        public void valueChanged(ListSelectionEvent e)
        {
            if (!e.getValueIsAdjusting())
            {
                updateActionEnablement();
            }

        }
    }

    private class DeleteCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final ExpirableItem selectedItem = getSelected();
            itemManager.remove(selectedItem);
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
            final ExpirableItem selectedItem = getSelected();
            itemManager.duplicate(selectedItem);
        }
    }

    /**
     * Shows a dialog for modifying the currently selected item and updates it.
     */
    private class EditCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final ExpirableItem foodObject = getSelected();
            final FormModel formModel = SwingFormModel
                    .createFormModel(foodObject);
            final ExpirableItemPropertiesForm form = new ExpirableItemPropertiesForm(
                    formModel);

            final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
                    form, getWindowControl()) {

                protected void onAboutToShow()
                {
                    setEnabled(true);
                }

                protected boolean onFinish()
                {
                    formModel.commit();
                    itemManager.update(foodObject);
                    return true;
                }
            };
            dialog.showDialog();
        }
    }

    private static class ExpiryDateComparator implements Comparator
    {

        public int compare(Object o1, Object o2)
        {
            if (o1 == null && o2 == null)
            {
                return 0;
            }

            //Null is treated as being greater as any non-null expiry date.
            //This has the effect of displaying items without an expiry
            //At the end of a least-expiry-first table of items.
            if (o1 == null)
            {
                return 1;
            }
            if (o2 == null)
            {
                return -1;
            }

            final Date date1 = (Date) o1;
            final Date date2 = (Date) o2;
            return date1.compareTo(date2);
        }
    }

    private class PopupTriggerListener extends PopupMenuMouseListener
    {

        private PopupTriggerListener(final JPopupMenu menu)
        {
            super(menu);
        }

        /**
         * Selects the row beneath the cursor before showing the popup menu
         */
        protected boolean onAboutToShow(MouseEvent e)
        {
            final int row = itemsTable.rowAtPoint(e.getPoint());
            itemsTable.getSelectionModel().setSelectionInterval(row, row);
            return super.onAboutToShow(e);
        }
    }
}