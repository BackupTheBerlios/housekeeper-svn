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

package net.sf.housekeeper.swing.stock;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import net.sf.housekeeper.domain.StockItem;

import com.odellengineeringltd.glazedlists.jtable.TableFormat;

/**
 * Backend for a {@link com.odellengineeringltd.glazedlists.jtable.ListTable}
 * for displaying {@link net.sf.housekeeper.domain.StockItem}objects.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class StockTableCell implements TableFormat
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
        return "Best before end";
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

        if (arg1 == 0) { return item.getName(); }
        return item.getBestBeforeEnd();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.odellengineeringltd.glazedlists.jtable.TableFormat#configureTable(javax.swing.JTable)
     */
    public void configureTable(JTable arg0)
    {
        arg0.getColumnModel().getColumn(1).setCellRenderer(new DateRenderer());
    }

    /**
     * Renders a {@link Date}object in a localized way.
     */
    private class DateRenderer implements TableCellRenderer
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
            String date = DateFormat.getDateInstance().format((Date) arg1);
            return new JLabel(date);
        }
    }
}