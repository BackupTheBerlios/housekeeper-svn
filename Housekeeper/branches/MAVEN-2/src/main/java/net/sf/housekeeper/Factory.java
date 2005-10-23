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

package net.sf.housekeeper;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.ExpirableItem;
import net.sf.housekeeper.domain.Item;
import net.sf.housekeeper.domain.ShoppingListItem;

import org.springframework.rules.Rules;
import org.springframework.rules.RulesSource;
import org.springframework.rules.constraint.Range;
import org.springframework.rules.support.DefaultRulesSource;

import sun.text.SupplementaryCharacterData;

public final class Factory
{

    private Factory()
    {

    }

    public static final RulesSource createRulesSource()
    {
        final DefaultRulesSource rulesSource = new DefaultRulesSource();
        rulesSource.addRules(createExpirableItemRules());
        rulesSource.addRules(createShoppingListItemRules());
        rulesSource.addRules(createCategoryRules());
        return rulesSource;
    }

    private static Rules createShoppingListItemRules()
    {
        return new Rules(ShoppingListItem.class)
        {
            protected void initRules()
            {
                add(Item.PROPERTYNAME_NAME, required());
                add(ShoppingListItem.PROPERTYNAME_QUANTITY, gt(0));
            }
        };
    }
    
    private static Rules createExpirableItemRules()
    {
        return new Rules(ExpirableItem.class)
        {
            protected void initRules()
            {
                add(Item.PROPERTYNAME_NAME, required());
            }
        };
    }
    
    private static Rules createCategoryRules()
    {
        return new Rules(Category.class)
        {
            protected void initRules()
            {
                add(Category.PROPERTYNAME_NAME, required());
            }
        };
    }

}
