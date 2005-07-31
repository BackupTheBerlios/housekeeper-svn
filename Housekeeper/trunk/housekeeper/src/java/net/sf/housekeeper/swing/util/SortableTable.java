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
 * http://housekeeper.berlios.de
 */

package net.sf.housekeeper.swing.util;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JTable;

import org.springframework.richclient.table.BaseTableModel;
import org.springframework.richclient.table.ColumnToSort;
import org.springframework.richclient.table.SortableTableModel;
import org.springframework.richclient.table.TableUtils;
import org.springframework.util.Assert;

/**
 * A table showing a list of {@link net.sf.housekeeper.domain.ExpirableItem}s.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class SortableTable extends JTable
{

    private static final long serialVersionUID = -1472613748365642350L;

    private BaseTableModel lowestModel;

    /**
     * Creates a new list.
     * 
     * @param model !=
     *            null
     */
    public SortableTable(BaseTableModel model)
    {
        Assert.notNull(model);

        this.lowestModel = model;
        setModel(lowestModel);

        TableUtils.attachSorter(this);

        setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    }

    /**
     * Sorts the table by a column.
     * 
     * @param column
     *            The number of the column.
     */
    public void sortByColumn(int column)
    {
        getSortableTableModel().sortByColumn(new ColumnToSort(0, column));
    }

    /**
     * Returns the sorting filter model.
     * 
     * @return != null
     */
    public SortableTableModel getSortableTableModel()
    {
        return (SortableTableModel) getModel();
    }

    /**
     * Returns the selected items.
     * 
     * @return an empty list if there is no item selected.
     */
    public List getSelected()
    {
        final LinkedList selectedObjects = new LinkedList();
        final int[] selectedRowIndexes = getSelectedRows();

        for (int i = 0; i < selectedRowIndexes.length; i++)
        {

            final int convertedRow = getSortableTableModel()
                    .convertSortedIndexToDataIndex(selectedRowIndexes[i]);
            selectedObjects.add(lowestModel.getRow(convertedRow));
        }

        return selectedObjects;
    }

    /**
     * Returns true if there is more than one object selected.
     * 
     * @return true if there is more than one object selected. False otherwise.
     */
    public boolean hasMultipleSelection()
    {
        return getSelectedRowCount() > 1;
    }

    /**
     * Returns true if only one object selected.
     * 
     * @return True, if there if only one object selected. False otherwise.
     */
    public boolean hasSingleSelection()
    {
        return getSelectedRowCount() == 1;
    }

    /**
     * Returns whether there is a selection.
     * 
     * @return True, if there is a selection. False otherwise.
     */
    public boolean hasSelection()
    {
        return getSelectedRowCount() > 0;
    }

    /**
     * Returns true if this table contains a specific object.
     * 
     * @param object
     *            The object to test. != null
     * @return True if the table contains this object. False otherwise.
     */
    public boolean contains(Object object)
    {
        final boolean exists = lowestModel.rowOf(object) != -1;
        return exists;
    }

    /**
     * Adds an object.
     * 
     * @param object !=
     *            null
     */
    public void add(Object object)
    {
        lowestModel.addRow(object);
    }

    /**
     * Removes an item.
     * 
     * @param object !=
     *            null
     */
    public void remove(Object object)
    {
        final int row = lowestModel.rowOf(object);
        remove(row);
    }

    /**
     * Replaces all objects in this table with other ones.
     * 
     * @param objects
     *            A list of objects.
     */
    public void replaceAll(List objects)
    {
        lowestModel.clear();
        lowestModel.addRows(objects);
    }

}
