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

package net.sf.housekeeper.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a household.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class Household
{

    /**
     * The version to be used in the XML file.
     */
    public final String version = "5";

    private ArrayList supply;

    private ArrayList shoppingList;

    private ArrayList categories;

    public Household()
    {
        this(new ArrayList(), new ArrayList(), new ArrayList());
    }

    public Household(List supply, List shoppingList, List categories)
    {
        setSupply(supply);
        setShoppingList(shoppingList);
        setCategories(categories);
    }

    /**
     * @return Returns the categories.
     */
    public List getCategories()
    {
        return categories;
    }

    /**
     * @param categories
     *            The categories to set.
     */
    public void setCategories(List categories)
    {
        this.categories = new ArrayList(categories);
    }

    /**
     * @return Returns the shoppingList.
     */
    public List getShoppingList()
    {
        return shoppingList;
    }

    /**
     * @param shoppingList
     *            The shoppingList to set.
     */
    public void setShoppingList(List shoppingList)
    {
        this.shoppingList = new ArrayList(shoppingList);
    }

    /**
     * @return Returns the supply.
     */
    public List getSupply()
    {
        return supply;
    }

    /**
     * @param supply
     *            The supply to set.
     */
    public void setSupply(List supply)
    {
        this.supply = new ArrayList(supply);
    }
}
