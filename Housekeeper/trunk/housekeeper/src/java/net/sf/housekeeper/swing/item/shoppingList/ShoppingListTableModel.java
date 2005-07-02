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

package net.sf.housekeeper.swing.item.shoppingList;

import org.springframework.context.MessageSource;

import net.sf.housekeeper.domain.ShoppingListItem;
import net.sf.housekeeper.swing.item.ItemsTableModel;

/**
 * A {@link javax.swing.table.TableModel}for
 * {@link net.sf.housekeeper.domain.ShoppingListItem}s.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ShoppingListTableModel extends ItemsTableModel
{

    private static final long serialVersionUID = -4714266963861989063L;

    /**
     * @param messages
     */
    public ShoppingListTableModel(MessageSource messages)
    {
        super(ShoppingListItem.class, messages);
    }

    protected String[] createColumnPropertyNames()
    {
        return new String[] { "quantity", "name", "description" };
    }

    protected Class[] createColumnClasses()
    {
        return new Class[] { Integer.class, String.class, String.class };
    }

}
