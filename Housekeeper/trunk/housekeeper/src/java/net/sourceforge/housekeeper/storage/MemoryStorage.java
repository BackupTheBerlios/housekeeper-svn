/*
 * This file is part of housekeeper.
 *
 *	housekeeper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation; either version 2 of the License, or
 *	(at your option) any later version.
 *
 *	housekeeper is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with housekeeper; if not, write to the Free Software
 *	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.sourceforge.housekeeper.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.sourceforge.housekeeper.domain.AssortimentItem;
import net.sourceforge.housekeeper.domain.Purchase;

/**
 * Stores data in memory thus loading and saving has now effect.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @since 0.1.1
 */
class MemoryStorage implements Storage
{
	protected Collection assortimentItems;
	protected Collection purchases;

	MemoryStorage()
	{
		assortimentItems = new ArrayList();
		purchases = new ArrayList();
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.housekeeper.storage.Storage#getArticles()
	 */
	public Collection getAllAssortimentItems()
	{
		return Collections.unmodifiableCollection(assortimentItems);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.housekeeper.storage.Storage#getAllPurchases()
	 */
	public Collection getAllPurchases()
	{
		return Collections.unmodifiableCollection(purchases);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.housekeeper.storage.Storage#add(net.sourceforge.housekeeper.domain.ArticleDescription)
	 */
	public void add(AssortimentItem article)
	{
		assortimentItems.add(article);
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.housekeeper.storage.Storage#add(net.sourceforge.housekeeper.domain.Purchase)
	 */
	public void add(Purchase purchase)
	{
		purchases.add(purchase);
	}
	public void remove(AssortimentItem article)
	{
		assortimentItems.remove(article);
	}

	public void loadData()
	{

	}

	public void saveData()
	{

	}

}
