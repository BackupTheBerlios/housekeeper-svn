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

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renders a date in a table cell.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class DateCellRenderer extends DefaultTableCellRenderer
{

    /**
     * Format to render the date.
     */
    private final SimpleDateFormat dateFormat;

    /**
     * Create a new DateTableCellRenderer that renders Dates as formatted
     * Strings.
     * 
     * @param format Format to use for rendering dates.
     */
    public DateCellRenderer(final SimpleDateFormat format)
    {
        dateFormat = format;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus, int row,
                                                   int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (value != null)
        {
            final Date date = (Date) value;
            final String prettyDate = dateFormat.format(date);
            setText(prettyDate);
        } else
        {
            setText("");
        }
        
        return this;
    }
}