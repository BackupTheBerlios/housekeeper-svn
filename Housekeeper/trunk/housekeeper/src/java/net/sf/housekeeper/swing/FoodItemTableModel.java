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

import java.util.Date;

import javax.swing.ListModel;

import net.sf.housekeeper.domain.FoodItem;

import com.jgoodies.binding.adapter.TableAdapter;

/**
 * Provides a TableModel for displaying
 * {@link net.sf.housekeeper.domain.FoodItem}objects in a table. It
 * automcatically fires appropriate Events on any change in these objects or the
 * list.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class FoodItemTableModel extends TableAdapter
{

    /**
     * The labels for the table columns.
     */
    private static final String[] COLUMNS = { "Name", "Quantity", "Expiry" };

    /**
     * Creates a new StockItemsTableModel.
     * 
     * @param model The list to be used as data source.
     */
    public FoodItemTableModel(ListModel model)
    {
        super(model, COLUMNS);
    }
    

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == 2)
        {
            return Date.class;
        }
        return String.class;
    }
    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        final FoodItem item = (FoodItem) getRow(rowIndex);
        switch (columnIndex)
        {
            case 0:
                return item.getName();
            case 1:
                return item.getQuantity();
            case 2:
                return item.getExpiry();
            default:
                throw new IllegalStateException("Unknown column");
        }
    }

}