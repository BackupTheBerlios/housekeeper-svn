/*
 * This file is part of Housekeeper.
 *
 * Housekeeper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Housekeeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Housekeeper; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA  02111-1307  USA
 *
 * Copyright 2003, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper.storage;


import net.sourceforge.housekeeper.domain.AssortimentItem;
import net.sourceforge.housekeeper.domain.Purchase;

import java.util.Collection;


/**
 * Defines the interface a class has to implement to act as a possible storage technique.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
public interface Storage
{
    //~ Methods ----------------------------------------------------------------

    /**
     * Returns an unmodifiable Collection of all Article Descriptions.
     *
     * @return All Article Descriptions.
     */
    public Collection getAllAssortimentItems();

    /**
     * Returns an unmodifiable Collection of all Purchases.
     *
     * @return All Purchases.
     */
    public Collection getAllPurchases();

    /**
     * Adds a Purchase to the Storage.
     *
     * @param purchase The Purchase to be added.
     */
    public void add(Purchase purchase);

    /**
     * Adds an Article Description to the storage
     *
     * @param article Article Description to be added.
     */
    public void add(AssortimentItem articleDescription);

    /**
     * Causes data to be loaded from persistent storage.
     */
    public void loadData();

    /**
     * Removes an Article Description from the storage.
     *
     * @param article Article Description to be removed.
     */
    public void remove(AssortimentItem article);

    /**
     * Causes datat to be saved to persistent storage.
     */
    public void saveData();
}
