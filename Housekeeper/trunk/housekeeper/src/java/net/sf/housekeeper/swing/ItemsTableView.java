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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import net.sf.housekeeper.HousekeeperEvent;
import net.sf.housekeeper.domain.Food;
import net.sf.housekeeper.domain.FoodManager;
import net.sf.housekeeper.util.LocalisationManager;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.richclient.application.PageComponentContext;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.command.support.GlobalCommandIds;
import org.springframework.richclient.table.renderer.DateTimeTableCellRenderer;
import org.springframework.richclient.util.PopupMenuMouseListener;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextFilterList;

/**
 * Presents a table of {@link Food}objects. It is sortable and can filter after
 * categories of {@link Food}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ItemsTableView extends AbstractView implements ApplicationListener
{

    /**
     * The JavaBean properties of Food which shall be used as values for the
     * columns of an entry.
     */
    private static final String[] ITEM_PROPERTIES = { Food.PROPERTYNAME_NAME,
            Food.PROPERTYNAME_DESCRIPTION, Food.PROPERTYNAME_EXPIRY };

    private String                category;

    private EventSelectionModel   selectionModel;

    private final JTable          table;

    private TextFilterList        textFiltered;

    private FoodManager foodManager;
    
    private final EditCommandExecutor      editExecutor               = new EditCommandExecutor();

    private final NewCommandExecutor       newItemExecutor = new NewCommandExecutor();

    private final DuplicateCommandExecutor duplicateExecutor          = new DuplicateCommandExecutor();

    private final DeleteCommandExecutor    deleteExecutor             = new DeleteCommandExecutor();

    /**
     * Creates a new view showing no items.
     *  
     */
    public ItemsTableView()
    {
        table = new JTable();

        //Change selection to the entry the mouse pointer points at before
        //popup menu is shown. See bug #003496
        table.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    final int row = table.rowAtPoint(e.getPoint());
                    table.getSelectionModel().setSelectionInterval(row, row);
                }
            }
        });

        setFoodList(new BasicEventList());
        
        newItemExecutor.setEnabled(true);
        updateActionEnablement();
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
        context.register("newCommand",
                         newItemExecutor);
    }
    
    /**
     * Sets the manager which holds the data to display.
     * 
     * @param manager
     */
    public void setFoodManager(final FoodManager manager)
    {
        this.foodManager = manager;
        setFoodList(manager.getSupplyList());
    }
    
    /**
     * Returns the selected object.
     * 
     * @return The selected object.
     */
    private Food getSelected()
    {
        if (!hasSelection())
        {
            return null;
        }

        final EventList selectionList = selectionModel.getEventList();
        return (Food) selectionList.get(0);
    }

    /**
     * Returns if a row in the table is currently selected.
     * 
     * @return True if a row has been selected, false otherwise.
     */
    private boolean hasSelection()
    {
        return !selectionModel.isSelectionEmpty();
    }

    /**
     * Sets a group of commands which are available in the context menu of the
     * table.
     * 
     * @param commands A group of commands.
     */
    private void setPopupMenuCommandGroup(CommandGroup commands)
    {
        final JPopupMenu popupMenu = commands.createPopupMenu();
        table.addMouseListener(new PopupMenuMouseListener(popupMenu));
    }

    /**
     * Sets the category to filter after.
     * 
     * @param category
     */
    private void setCategory(final String category)
    {
        this.category = category;
        setFilterText(category);
    }

    /**
     * Changes the table to show an other list of Food objects.
     * 
     * @param list
     */
    private void setFoodList(final EventList list)
    {
        //Initialize sorted and filtered list
        final SortedList sortedList = new SortedList(list, null);
        textFiltered = new TextFilterList(sortedList,
                new String[] { Food.PROPERTYNAME_CATEGORY });
        setFilterText(category);

        assignTableModel(table, textFiltered);

        //Assign selection model and sorting functionality
        selectionModel = assignSelectionModel(table, textFiltered);
        assignSorting(table, sortedList);
    }

    /**
     * Creates a JPanel which holds the table.
     */
    protected JComponent createControl()
    {
        assignDateColumnRenderer(table, 2);
        
        final MouseListener doubleClickListener = new MouseAdapter() {

            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2
                        && e.getButton() == MouseEvent.BUTTON1)
                {
                    if (editExecutor.isEnabled())
                    {
                        editExecutor.execute();
                    }
                }
            }
        };
        table.addMouseListener(doubleClickListener);

        CommandGroup convCommandGroup = getWindowCommandManager()
                .createCommandGroup(
                                    "tablePopupCommandGroup",
                                    new Object[] { "newCommand",
                                            "duplicateCommand",
                                            "editCommand", "deleteCommand" });
        setPopupMenuCommandGroup(convCommandGroup);
        
        final JPanel panel = new JPanel();
        //Without that, the tables won't grow and shrink with the window's
        // size.
        panel.setLayout(new GridLayout());
        panel.add(new JScrollPane(table));
        return panel;
    }

    private void assignDateColumnRenderer(JTable table, int column)
    {
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.SHORT);
        final TableCellRenderer dateRenderer = new DateTimeTableCellRenderer(
                dateFormat);
        table.getColumnModel().getColumn(column).setCellRenderer(dateRenderer);
    }

    private EventSelectionModel assignSelectionModel(JTable table,
                                                     EventList list)
    {
        final EventSelectionModel selectionModel = new EventSelectionModel(list);
        selectionModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e)
            {
                updateActionEnablement();
            }
        });

        table.setSelectionModel(selectionModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return selectionModel;
    }

    /**
     * Adds sorting functionality to the headers.
     * 
     * @param table The table to add sorting to.
     * @param sortedList The list which holds the table's data.
     */
    private void assignSorting(final JTable table, final SortedList sortedList)
    {
        final TableComparatorChooser comparatorChooser = new TableComparatorChooser(
                table, sortedList, false);
        comparatorChooser.getComparatorsForColumn(2).clear();
        comparatorChooser.getComparatorsForColumn(2).add(new Comparator() {

            public int compare(Object o1, Object o2)
            {
                final Date date1 = ((Food) o1).getExpiry();
                final Date date2 = ((Food) o2).getExpiry();

                //Null is treated as being greater as any non-null expiry date.
                //This has the effect of displaying items without an expiry
                //At the end of a least-expiry-first table of items.
                if (date1 == null)
                {
                    if (date2 == null)
                    {
                        return 0;
                    }
                    return 1;
                }
                if (date2 == null)
                {
                    return -1;
                }

                return date1.compareTo(date2);
            }
        });
        comparatorChooser.chooseComparator(2, 0, false);
    }

    private void assignTableModel(JTable table, EventList foodList)
    {
        final String nameHeader = LocalisationManager.INSTANCE
                .getText("domain.food.name");
        final String quantityHeader = LocalisationManager.INSTANCE
                .getText("domain.food.description");
        final String expiryHeader = LocalisationManager.INSTANCE
                .getText("domain.food.expiry");
        final String[] tableHeaders = { nameHeader, quantityHeader,
                expiryHeader };
        final boolean[] tableRowModifyable = { false, false, false };

        //Make and assign table model
        final EventTableModel tableModel = new EventTableModel(foodList,
                ITEM_PROPERTIES, tableHeaders, tableRowModifyable);
        table.setModel(tableModel);
    }

    private void setFilterText(String text)
    {
        textFiltered.getFilterEdit().setText(text);
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
        boolean hasSelection = hasSelection();
        duplicateExecutor.setEnabled(hasSelection);
        editExecutor.setEnabled(hasSelection);
        deleteExecutor.setEnabled(hasSelection);
    }
    
    private class DeleteCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final Food selectedItem = getSelected();
            foodManager.remove(selectedItem);
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
        }
    }

    /**
     * Shows a dialog for modifying the currently selected item and updates it.
     */
    private class EditCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final Food selected = getSelected();
            boolean canceled = openEditor(selected);
            if (!canceled)
            {
                foodManager.update(selected);
            }
        }
    }

    /**
     * Shows a dialog for adding a new item.
     */
    private class NewCommandExecutor extends AbstractActionCommandExecutor
    {

        private NewCommandExecutor()
        {
            super();
        }

        public void execute()
        {
            final Food item = new Food();
            boolean canceled = openEditor(item);
            if (!canceled)
            {
                item.setCategory(category);
                foodManager.add(item);
            }
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ApplicationEvent e)
    {
        if (e instanceof HousekeeperEvent) {
            final HousekeeperEvent le = (HousekeeperEvent)e;
            if (le.getEventType() == HousekeeperEvent.SELECTED && le.objectIs(String.class))
            {
                final String cat = (String)le.getObject();
                SwingUtilities.invokeLater(new Runnable() {

                    public void run()
                    {
                        setCategory(cat);
                    }
                });
                
            }
            
        }
        
    }


}