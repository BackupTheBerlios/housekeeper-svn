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

import net.sf.housekeeper.domain.Item;
import net.sf.housekeeper.domain.ShoppingListItem;
import net.sf.housekeeper.swing.item.AbstractNewItemCommandExecutor;

import org.springframework.richclient.form.Form;

/**
 * Shows a dialog for adding a new item to a shopping list.
 */
public final class NewItemCommandExecutor extends
        AbstractNewItemCommandExecutor
{

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.swing.item.AbstractNewItemCommandExecutor#createForm(net.sf.housekeeper.domain.Item)
     */
    protected Form createForm(Item object)
    {
        final ShoppingListItemPropertiesForm form = new ShoppingListItemPropertiesForm(
                (ShoppingListItem) object);
        return form;
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.swing.item.AbstractNewItemCommandExecutor#createItem()
     */
    protected Item createItem()
    {
        return new ShoppingListItem();
    }

}
