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

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

import net.sf.housekeeper.domain.Food;
import net.sf.housekeeper.swing.util.DateCellRenderer;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;

/**
 * A panel with a table for display of {@link Food}objects.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class FoodListView extends JPanel
{

    /**
     * Creates a new view. All objects passed as parameters must fit to each
     * other.
     * 
     * @param sortedList A list with {@link Food}objects.
     * @param tableModel The TableModel for the table.
     * @param selectionModel The SelectionModel for the table.
     * @param expiryHeaderName The header name of the expiry column.
     */
    public FoodListView(final SortedList sortedList,
            final EventTableModel tableModel,
            final EventSelectionModel selectionModel,
            final String expiryHeaderName)
    {
        //Init table
        final JTable supplyTable = new JTable(tableModel);

        //Adds the sorting functionality to the table
        addSorting(supplyTable, sortedList);

        supplyTable.setSelectionModel(selectionModel);
        supplyTable.getSelectionModel()
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Register renderer for the expiry date
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.SHORT);
        final TableCellRenderer dateRenderer = new DateCellRenderer(dateFormat);
        supplyTable.getColumn(expiryHeaderName).setCellRenderer(dateRenderer);

        add(new JScrollPane(supplyTable));
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

}