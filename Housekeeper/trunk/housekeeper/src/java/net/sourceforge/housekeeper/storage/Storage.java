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


import net.sourceforge.housekeeper.model.ArticleDescription;
import net.sourceforge.housekeeper.model.Purchase;

import java.util.Collection;
import java.util.List;
import java.util.Observable;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 */
public abstract class Storage extends Observable
{
    //~ Methods ----------------------------------------------------------------

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public abstract List getArticles();
    
    public abstract Collection getPurchases();
    
    public abstract void add(Purchase purchase);

    public abstract ArticleDescription getArticle(int i);
    
    /**
     * TODO DOCUMENT ME!
     *
     * @param article DOCUMENT ME!
     */
    public abstract void add(ArticleDescription article);

    /**
     * TODO DOCUMENT ME!
     */
    public abstract void loadData();

    /**
     * TODO DOCUMENT ME!
     *
     * @param article DOCUMENT ME!
     */
    public abstract void remove(ArticleDescription article);

    /**
     * TODO DOCUMENT ME!
     */
    public abstract void saveData();

    /**
     * TODO DOCUMENT ME!
     */
    public abstract void update();
}
