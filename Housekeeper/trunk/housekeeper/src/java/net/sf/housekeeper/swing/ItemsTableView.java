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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import net.sf.housekeeper.HousekeeperEvent;
import net.sf.housekeeper.domain.Food;
import net.sf.housekeeper.domain.FoodManager;
import net.sf.housekeeper.swing.util.CustomTableUtils;

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
import org.springframework.richclient.table.BeanTableModel;
import org.springframework.richclient.table.ColumnToSort;
import org.springframework.richclient.table.SortableTableModel;
import org.springframework.richclient.table.renderer.DateTimeTableCellRenderer;
import org.springframework.richclient.util.PopupMenuMouseListener;

/**
 * @author
 * @version $Revision$, $Date$
 */
public final class ItemsTableView extends AbstractView implements
        ApplicationListener
{

    private JTable                         itemsTable;

    private ItemsTableModel                tableModel;

    private final EditCommandExecutor      editExecutor      = new EditCommandExecutor();

    private final NewCommandExecutor       newItemExecutor   = new NewCommandExecutor();

    private final DuplicateCommandExecutor duplicateExecutor = new DuplicateCommandExecutor();

    private final DeleteCommandExecutor    deleteExecutor    = new DeleteCommandExecutor();

    private FoodManager                    foodManager;

    private String                         category;

    /**
     * Creates a new view showing a list of items.
     *  
     */
    public ItemsTableView()
    {
        newItemExecutor.setEnabled(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.factory.AbstractControlFactory#createControl()
     */
    protected JComponent createControl()
    {
        tableModel = new ItemsTableModel(new ArrayList(),
                getApplicationContext());
        itemsTable = createTable(tableModel);

        final JPanel panel = new JPanel();
        //Without that, the tables won't grow and shrink with the window's
        // size.
        panel.setLayout(new GridLayout());
        panel.add(new JScrollPane(itemsTable));

        refresh();

        return panel;
    }

    private JTable createTable(ItemsTableModel model)
    {
        final JTable table = CustomTableUtils
                .createStandardSortableTable(model);

        final SortableTableModel sortableModel = (SortableTableModel) table
                .getModel();
        sortableModel.setComparator(2, new ExpiryDateComparator());

        //Sort expiry dates by default
        sortableModel.sortByColumn(new ColumnToSort(0, 2));

        table.getSelectionModel()
                .addListSelectionListener(new TableSelectionListener());
        table.addMouseListener(new PopupMenuMouseListener(createContextMenu()));
        table.addMouseListener(new DoubleClickListener());
        assignDateColumnRenderer(table, 2);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        return table;
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
        context.register("newCommand", newItemExecutor);
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

    private void setCategory(String category)
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
            if (le.getEventType() == HousekeeperEvent.SELECTED
                    && le.objectIs(String.class))
            {
                final String cat = (String) le.getObject();
                SwingUtilities.invokeLater(new Runnable() {

                    public void run()
                    {
                        setCategory(cat);
                    }
                });
            } else if (le.getEventType() == HousekeeperEvent.DATA_CHANGED)
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
                                    new Object[] { "newCommand",
                                            "duplicateCommand",
                                            GlobalCommandIds.PROPERTIES,
                                            GlobalCommandIds.DELETE });
        return convCommandGroup.createPopupMenu();
    }

    private Food getSelected()
    {
        if (hasSelection())
        {
            final int selectedRow = itemsTable.getSelectedRow();
            final SortableTableModel sortModel = (SortableTableModel) itemsTable
                    .getModel();
            final int convertedRow = sortModel
                    .convertSortedIndexToDataIndex(selectedRow);
            return (Food) tableModel.getRow(convertedRow);
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
    public void setFoodManager(final FoodManager manager)
    {
        this.foodManager = manager;
    }

    private void refresh()
    {
        tableModel.clear();
        tableModel.addRows(foodManager.getItemsForCategory(category));
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

    private final class ItemsTableModel extends BeanTableModel
    {

        private ItemsTableModel(List rows, MessageSource messages)
        {
            super(Food.class, rows, messages);
            setRowNumbers(false);
        }

        protected String[] createColumnPropertyNames()
        {
            return new String[] { "name", "description", "expiry" };
        }

        protected boolean isCellEditableInternal(Object row, int columnIndex)
        {
            return false;
        }

        protected Class[] createColumnClasses()
        {
            return new Class[] { String.class, String.class, Date.class };
        }

    }

    private class DeleteCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final Food selectedItem = getSelected();
            foodManager.remove(selectedItem);
            refresh();
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
            final Food selectedItem = getSelected();
            foodManager.duplicate(selectedItem);
            refresh();
        }
    }

    /**
     * Shows a dialog for modifying the currently selected item and updates it.
     */
    private class EditCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final Food foodObject = getSelected();
            final FormModel formModel = SwingFormModel
                    .createFormModel(foodObject);
            final FoodPropertiesForm form = new FoodPropertiesForm(formModel);

            final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
                    form, getWindowControl()) {

                protected void onAboutToShow()
                {
                    setEnabled(true);
                }

                protected boolean onFinish()
                {
                    formModel.commit();
                    foodManager.update(foodObject);
                    refresh();
                    return true;
                }
            };
            dialog.showDialog();
        }
    }

    /**
     * Shows a dialog for adding a new item.
     */
    private class NewCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final Food foodObject = new Food();
            foodObject.setCategory(category);
            final FormModel formModel = SwingFormModel
                    .createFormModel(foodObject);
            final FoodPropertiesForm form = new FoodPropertiesForm(formModel);

            final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
                    form, getWindowControl()) {

                protected void onAboutToShow()
                {
                    setEnabled(true);
                }

                protected boolean onFinish()
                {
                    formModel.commit();
                    foodManager.add(foodObject);
                    refresh();
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

}