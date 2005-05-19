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

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import net.sf.housekeeper.swing.util.SortableTable;
import net.sf.housekeeper.util.ExpiryDateComparator;

import org.springframework.context.MessageSource;
import org.springframework.richclient.table.BaseTableModel;
import org.springframework.richclient.table.SortableTableModel;
import org.springframework.richclient.table.renderer.DateTimeTableCellRenderer;

/**
 * Creates tables for {@link net.sf.housekeeper.domain.Item}s and its
 * subclasses.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class TableFactory
{

    private static final int EXPIRY_DATE_COLUMN = 2;

    /**
     * Creates a table showing {@link net.sf.housekeeper.domain.Item}s.
     * 
     * @param columnNameProvider
     * @return != null
     */
    public static SortableTable createItemsTable(
                                                 final MessageSource columnNameProvider)
    {
        final BaseTableModel model = new ItemsTableModel(columnNameProvider);
        final SortableTable table = new SortableTable(model);
        return table;
    }

    /**
     * Creates a table showing {@link net.sf.housekeeper.domain.ExpirableItem}s.
     * It is initially sorted by expiry dates.
     * 
     * @param columnNameProvider
     * @return != null
     */
    public static SortableTable createExpirableItemsTable(
                                                          final MessageSource columnNameProvider)
    {
        final BaseTableModel model = new ExpirableItemsTableModel(
                columnNameProvider);
        final SortableTable table = new SortableTable(model);

        final int expiryDateColumn = EXPIRY_DATE_COLUMN;
        final SortableTableModel sortableModel = table.getSortableTableModel();
        sortableModel.setComparator(EXPIRY_DATE_COLUMN,
                                    new ExpiryDateComparator());
        table.sortByColumn(EXPIRY_DATE_COLUMN);
        assignDateColumnRenderer(table, EXPIRY_DATE_COLUMN);
        return table;
    }

    private static void assignDateColumnRenderer(JTable table, int column)
    {
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.SHORT);
        final TableCellRenderer dateRenderer = new DateTimeTableCellRenderer(
                dateFormat);
        table.getColumnModel().getColumn(column).setCellRenderer(dateRenderer);
    }
}
