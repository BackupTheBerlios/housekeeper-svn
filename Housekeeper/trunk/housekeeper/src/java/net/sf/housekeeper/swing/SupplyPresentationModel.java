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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import net.sf.housekeeper.domain.Food;
import net.sf.housekeeper.domain.FoodManager;
import net.sf.housekeeper.swing.util.DateCellRenderer;
import net.sf.housekeeper.util.LocalisationManager;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;

/**
 * Crates and manages a JTable displaying the current Food objects. It makes
 * the table sortable by clicking the table's headers and registers appropriate
 * cell renderers. It provides several methods to operate on the currently
 * selected item.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class SupplyPresentationModel
{

    /**
     * The JavaBean properties of Food which shall be used as values for the
     * columns of an entry.
     */
    private static final String[]     ITEM_PROPERTIES     = {
            Food.PROPERTYNAME_NAME, Food.PROPERTYNAME_QUANTITY,
            Food.PROPERTYNAME_EXPIRY                 };

    /**
     * Defines which columns should be editable directly in the table.
     */
    private static final boolean[]    PROPERTY_MODIFYABLE = { false, false,
            false                                        };

    private static final String       NAME_HEADER         = LocalisationManager.INSTANCE
                                                                  .getText("domain.food.name");

    private static final String       QUANTITY_HEADER     = LocalisationManager.INSTANCE
                                                                  .getText("domain.food.quantity");

    private static final String       EXPIRY_HEADER       = LocalisationManager.INSTANCE
                                                                  .getText("domain.food.expiry");

    /**
     * The labels for the table headers.
     */
    private static final String[]     TABLE_HEADERS       = { NAME_HEADER,
            QUANTITY_HEADER, EXPIRY_HEADER               };

    /**
     * Holds information about the row selections of the table.
     */
    private final EventSelectionModel selectionModel;

    /**
     * The table which has been created.
     */
    private final JTable              supplyTable;

    /**
     * The manager for access to domain food objects.
     */
    private final FoodManager     itemManager;

    /**
     * Creates a new JTable for display of the current items on hand.
     * 
     * @param itemManager The domain to use.
     */
    public SupplyPresentationModel(final FoodManager itemManager)
    {
        this.itemManager = itemManager;

        //Init table
        final SortedList sortedList = new SortedList(itemManager
                .getSupplyList(), null);
        final EventTableModel tableModel = new EventTableModel(sortedList,
                ITEM_PROPERTIES, TABLE_HEADERS, PROPERTY_MODIFYABLE);
        supplyTable = new JTable(tableModel);

        //Adds the sorting functionality to the table
        addSorting(supplyTable, sortedList);

        //Listen for changes of the table row selection
        selectionModel = new EventSelectionModel(sortedList);
        setSelectionModel(supplyTable);

        //Register renderer for the expiry date
        setExpiryDateRenderer(supplyTable);
    }

    /**
     * Adds a listener which gets informed of changes in the row selection
     * within the table.
     * 
     * @param listener The listener to add.
     */
    public void addTableSelectionListener(final ListSelectionListener listener)
    {
        supplyTable.getSelectionModel().addListSelectionListener(listener);
    }

    /**
     * Deletes the item which has been selected in the table.
     */
    public void deleteSelectedItem()
    {
        itemManager.remove(getSelectedItem());
    }

    /**
     * Duplicates the selected item.
     */
    public void duplicateSelectedItem()
    {
        final Food selectedItem = getSelectedItem();
        itemManager.duplicate(selectedItem);
    }

    /**
     * Returns the selected item in the table or null if none is selected.
     * 
     * @return The selected item or null if none has been selected.
     */
    public Food getSelectedItem()
    {
        if (!hasSelection())
        {
            return null;
        }

        final EventList selectionList = selectionModel.getEventList();
        return (Food) selectionList.get(0);
    }

    /**
     * Returns the created table.
     * 
     * @return Returns the supplyTable.
     */
    public JTable getSupplyTable()
    {
        return supplyTable;
    }

    /**
     * Returns if a row in the table is currently selected.
     * 
     * @return True if a row has been selected, false otherwise.
     */
    public boolean hasSelection()
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
     * Adds sorting functionality to the headers.
     * 
     * @param table The table to add sorting to.
     * @param sortedList The list which holds the table's data.
     */
    private void addSorting(final JTable table, final SortedList sortedList)
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

    /**
     * Sets the renderer for the expiry date. It is shown in the current
     * locale's short format.
     * 
     * @param table The table for which the renderer should be set.
     */
    private void setExpiryDateRenderer(final JTable table)
    {
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.SHORT);
        final TableCellRenderer dateRenderer = new DateCellRenderer(dateFormat);
        table.getColumn(EXPIRY_HEADER).setCellRenderer(dateRenderer);
    }

    /**
     * Sets the {@link ListSelectionModel}of the table to
     * {@link SupplyPresentationModel#selectionModel}.
     * 
     * @param table The table for which the selection model shall be set.
     */
    private void setSelectionModel(final JTable table)
    {
        table.setSelectionModel(selectionModel);
        table.getSelectionModel()
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}