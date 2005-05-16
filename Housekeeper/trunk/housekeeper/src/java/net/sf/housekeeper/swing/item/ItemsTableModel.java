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

import java.util.Date;
import java.util.List;

import net.sf.housekeeper.domain.ExpirableItem;

import org.springframework.context.MessageSource;
import org.springframework.richclient.table.BeanTableModel;


final class ItemsTableModel extends BeanTableModel
{

    ItemsTableModel(List rows, MessageSource messages)
    {
        super(ExpirableItem.class, rows, messages);
        setRowNumbers(false);
    }

    protected String[] createColumnPropertyNames()
    {
        return new String[] { "name", "description", "expiry" };
    }

    protected boolean isCellEditableInternal(Object row, int columnIndex)
    {
        return false;
    }

    protected Class[] createColumnClasses()
    {
        return new Class[] { String.class, String.class, Date.class };
    }

}