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

package net.sf.housekeeper.swing.util;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.springframework.richclient.table.SortTableCommand;
import org.springframework.richclient.table.TableSortIndicator;
import org.springframework.richclient.table.TableUtils;


/**
 * @author
 * @version $Revision$, $Date$
 */
public final class CustomTableUtils
{
    
    private CustomTableUtils()
    {
        
    }
    
    public static JTable createStandardSortableTable(TableModel tableModel) {
        JTable table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableUtils.installDefaultRenderers(table);
        attachSorter(table);
        TableUtils.sizeColumnsToFitRowData(table);
        return table;
    }
    
    public static JTable attachSorter(JTable table) {
        TableModel tableModel = table.getModel();
        ShuttleSortableTableModel sortedModel = new ShuttleSortableTableModel(tableModel);
        table.setAutoCreateColumnsFromModel(true);
        table.setModel(sortedModel);
        TableSortIndicator sortIndicator = new TableSortIndicator(table);
        new SortTableCommand(table, sortIndicator.getColumnSortList());
        return table;
    }

}
