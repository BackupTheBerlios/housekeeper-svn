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

import java.awt.Component;
import java.text.DateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import net.sf.housekeeper.domain.StockItem;
import net.sf.housekeeper.storage.StorageFactory;

import com.odellengineeringltd.glazedlists.EventList;
import com.odellengineeringltd.glazedlists.SortedList;
import com.odellengineeringltd.glazedlists.jtable.ListTable;
import com.odellengineeringltd.glazedlists.jtable.StyledDocumentRenderer;
import com.odellengineeringltd.glazedlists.jtable.TableComparatorChooser;
import com.odellengineeringltd.glazedlists.jtable.TableFormat;

/**
 * Generates a table for displaying a table with stock items.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class StockTableFactory
{

    private StockTableFactory()
    {
    }

    /**
     * Generates a table for displaying a table with stock items.
     * 
     * @return Generates a table for displaying a table with stock items.
     */
    static ListTable getStockTable()
    {
        final EventList items = StorageFactory.getCurrentStorage()
                .getAllStockItems();
        final SortedList sortedList = new SortedList(items,
                new StockDescriptionComparator());

        final ListTable table = new ListTable(sortedList,
                new StockTableFormat());

        final TableComparatorChooser chooser = new TableComparatorChooser(
                table, sortedList, false);
        chooser.getComparatorsForColumn(0).clear();
        chooser.getComparatorsForColumn(0)
                .add(new StockDescriptionComparator());

        return table;
    }

    /**
     * Compares the description of an item. First, the names are compared.
     * Second, the quantity is compared. Both comparisions are alphabetically.
     */
    private static class StockDescriptionComparator implements Comparator
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(Object o1, Object o2)
        {
            final StockItem item1 = (StockItem) o1;
            final StockItem item2 = (StockItem) o2;

            int returnValue = item1.getName()
                    .compareToIgnoreCase(item2.getName());
            if (returnValue == 0)
            {
                returnValue = item1.getQuantity()
                        .compareToIgnoreCase(item2.getQuantity());
            }
            return returnValue;
        }

    }

    /**
     * Backend for a
     * {@link com.odellengineeringltd.glazedlists.jtable.ListTable}for
     * displaying {@link net.sf.housekeeper.domain.StockItem}objects.
     */
    private static class StockTableFormat implements TableFormat
    {

        /*
         * (non-Javadoc)
         * 
         * @see com.odellengineeringltd.glazedlists.jtable.TableFormat#
         *      getFieldCount()
         */
        public int getFieldCount()
        {
            return 2;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.odellengineeringltd.glazedlists.jtable.TableFormat#
         *      getFieldName(int)
         */
        public String getFieldName(final int arg0)
        {
            if (arg0 == 0) { return "Item"; }
            return "Best before";
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.odellengineeringltd.glazedlists.jtable.TableFormat#getFieldValue(java.lang.Object,
         *      int)
         */
        public Object getFieldValue(Object arg0, int arg1)
        {
            final StockItem item = (StockItem) arg0;

            if (arg1 == 0) { return item; }
            return item.getBestBefore();
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.odellengineeringltd.glazedlists.jtable.TableFormat#configureTable(javax.swing.JTable)
         */
        public void configureTable(JTable arg0)
        {
            arg0.getColumnModel().getColumn(0)
                    .setCellRenderer(new DescriptionRenderer());
            arg0.getColumnModel().getColumn(1)
                    .setCellRenderer(new DateRenderer());
        }
    }

    /**
     * Renders the description of an item.
     */
    private static class DescriptionRenderer extends StyledDocumentRenderer
    {

        private DescriptionRenderer()
        {
            super(true);
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.odellengineeringltd.glazedlists.jtable.StyledDocumentRenderer#writeObject(javax.swing.JTable,
         *      java.lang.Object, boolean, boolean, int, int)
         */
        public void writeObject(JTable table, Object value, boolean isSelected,
                                boolean hasFocus, int row, int column)
        {
            final StockItem item = (StockItem) value;
            append(item.getName(), strong);
            append("\n", plain);
            append(item.getQuantity(), plain);
        }

    }

    /**
     * Renders a {@link Date}object in a localized way.
     */
    private static class DateRenderer implements TableCellRenderer
    {

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
         *      java.lang.Object, boolean, boolean, int, int)
         */
        public Component getTableCellRendererComponent(JTable arg0,
                                                       Object arg1,
                                                       boolean arg2,
                                                       boolean arg3, int arg4,
                                                       int arg5)
        {
            final String date = DateFormat.getDateInstance()
                    .format((Date) arg1);
            return new JLabel(date);
        }
    }
}