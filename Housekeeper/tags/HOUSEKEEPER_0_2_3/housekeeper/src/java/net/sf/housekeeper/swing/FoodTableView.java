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
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import net.sf.housekeeper.domain.Food;
import net.sf.housekeeper.swing.util.EventObjectListener;
import net.sf.housekeeper.util.LocalisationManager;

import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.CommandGroup;
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
public final class FoodTableView extends AbstractView
{

    /**
     * The JavaBean properties of Food which shall be used as values for the
     * columns of an entry.
     */
    private static final String[] ITEM_PROPERTIES = { Food.PROPERTYNAME_NAME,
            Food.PROPERTYNAME_DESCRIPTION, Food.PROPERTYNAME_EXPIRY };

    private String                category;

    private final List            selectionListeners;

    private EventSelectionModel   selectionModel;

    private final JTable          table;

    private TextFilterList        textFiltered;

    /**
     * Creates a new view showing no items.
     *  
     */
    public FoodTableView()
    {
        this.selectionListeners = new LinkedList();
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
    }

    /**
     * Add a listener which gets informed if the selection changes.
     * 
     * @param listener The listener to add.
     */
    public void addTableSelectionListener(final EventObjectListener listener)
    {
        selectionListeners.add(listener);
    }

    /**
     * Adds a MouseListener to the table.
     * 
     * @param listener The listener to add.
     */
    public void addTableMouseListener(final MouseListener listener)
    {
        table.addMouseListener(listener);
    }

    /**
     * Clears the current selection.
     *  
     */
    public void clearSelection()
    {
        selectionModel.clearSelection();
    }

    /**
     * Returns the category this table filters after.
     * 
     * @return The category. Is not null.
     */
    public String getCategory()
    {
        return category;
    }

    /**
     * Returns the selected object.
     * 
     * @return The selected object.
     */
    public Food getSelected()
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
    public boolean hasSelection()
    {
        return !selectionModel.isSelectionEmpty();
    }

    /**
     * Sets a group of commands which are available in the context menu of the
     * table.
     * 
     * @param commands A group of commands.
     */
    public void setPopupMenuCommandGroup(CommandGroup commands)
    {
        final JPopupMenu popupMenu = commands.createPopupMenu();
        table.addMouseListener(new PopupMenuMouseListener(popupMenu));
    }

    /**
     * Sets the category to filter after.
     * 
     * @param category
     */
    public void setCategory(final String category)
    {
        this.category = category;
        setFilterText(category);
    }

    /**
     * Changes the table to show an other list of Food objects.
     * 
     * @param list
     */
    public void setFoodList(final EventList list)
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
                notifyListeners();
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

    /**
     * Notifes the listeners of a selection change.
     *  
     */
    private void notifyListeners()
    {
        final Iterator iter = selectionListeners.iterator();
        while (iter.hasNext())
        {
            EventObjectListener l = (EventObjectListener) iter.next();
            l.handleEvent(new EventObject(this));
        }
    }

    private void setFilterText(String text)
    {
        textFiltered.getFilterEdit().setText(text);
    }

}