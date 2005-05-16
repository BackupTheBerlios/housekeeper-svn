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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import net.sf.housekeeper.domain.ExpirableItem;
import net.sf.housekeeper.domain.Item;
import net.sf.housekeeper.util.ExpiryDateComparator;

import org.springframework.context.MessageSource;
import org.springframework.richclient.table.ColumnToSort;
import org.springframework.richclient.table.SortableTableModel;
import org.springframework.richclient.table.TableUtils;
import org.springframework.richclient.table.renderer.DateTimeTableCellRenderer;
import org.springframework.util.Assert;


/**
 * A table showing a list of {@link net.sf.housekeeper.domain.ExpirableItem}s.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ExpirableItemsTable extends JTable
{
    private ItemsTableModel tableModel;
    
    /**
     * Creates a new list.
     * 
     * @param rows != null
     * @param messageSource != null
     */
    public ExpirableItemsTable(List rows, MessageSource messageSource)
    {
        Assert.notNull(rows);
        Assert.notNull(messageSource);
        
        tableModel = new ItemsTableModel(rows, messageSource);
        setModel(tableModel);
        TableUtils.attachSorter(this);
        
        final SortableTableModel sortableModel = (SortableTableModel)
                getModel();
        sortableModel.setComparator(2, new ExpiryDateComparator());
        
        //Sort expiry dates by default
        sortableModel.sortByColumn(new ColumnToSort(0, 2));
        
        assignDateColumnRenderer(this, 2);
        setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    }
    
    /**
     * Creates an empty list.
     * 
     * @param messageSource != null
     */
    public ExpirableItemsTable(MessageSource messageSource)
    {
        this(new ArrayList(), messageSource);
    }
    
    /**
     * Returns the selected item.
     * 
     * @return Null if none is selected.
     */
    public ExpirableItem getSelected()
    {
        if (hasSelection())
        {
            final int selectedRow = getSelectedRow();
            final SortableTableModel sortModel = (SortableTableModel)getModel();
            final int convertedRow = sortModel
                    .convertSortedIndexToDataIndex(selectedRow);
            return (ExpirableItem) tableModel.getRow(convertedRow);
        }

        return null;
    }
    
    /**
     * Returns if there is a selection.
     * 
     * @return True, if there is a selection. False otherwise.
     */
    public boolean hasSelection()
    {
        return getSelectedRow() != -1;
    }
    
    /**
     * Returns true if this table contains a specific item.
     * 
     * @param item The item to test. != null
     * @return True if the table contains this item. False otherwise.
     */
    public boolean containsItem(Item item)
    {
        final boolean exists = tableModel.rowOf(item) != -1;
        return exists;
    }

    /**
     * Adds an item.
     * 
     * @param item != null
     */
    public void addItem(Item item)
    {
        tableModel.addRow(item);
    }
    
    /**
     * Removes an item.
     * 
     * @param item != null
     */
    public void removeItem(Item item)
    {
        final int row = tableModel.rowOf(item);
        remove(row);
    }

    /**
     * Replaces all items in this table with other ones.
     * 
     * @param items A list of {@link ExpirableItem}s.
     */
    public void replaceAll(List items)
    {
        tableModel.clear();
        tableModel.addRows(items);
    }
    
    private void assignDateColumnRenderer(JTable table, int column)
    {
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.SHORT);
        final TableCellRenderer dateRenderer = new DateTimeTableCellRenderer(
                dateFormat);
        table.getColumnModel().getColumn(column).setCellRenderer(dateRenderer);
    }
}
