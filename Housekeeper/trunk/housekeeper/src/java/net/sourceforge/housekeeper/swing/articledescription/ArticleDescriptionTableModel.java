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
 * Copyright 2003-2004, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper.swing.articledescription;


import net.sourceforge.housekeeper.domain.ArticleDescription;
import net.sourceforge.housekeeper.storage.StorageFactory;
import net.sourceforge.housekeeper.swing.DataUpdateMediator;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;


/**
 * A table model for display of all Article Descriptions in a table.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
public final class ArticleDescriptionTableModel extends AbstractTableModel
    implements Observer
{
    //~ Static fields/initializers ---------------------------------------------

    /** Singleton instance */
    private static final ArticleDescriptionTableModel INSTANCE = new ArticleDescriptionTableModel();

    /** Double class */
    private static final Class DOUBLECLASS = new Double(0).getClass();

    /** Integer class */
    private static final Class INTEGERCLASS = new Integer(0).getClass();

    /** String class */
    private static final Class STRINGCLASS = new String().getClass();

    /** The classes of the data of the table's columns */
    private static final Class[] COLUMNCLASSES = 
                                                 {
                                                     STRINGCLASS,
                                                     STRINGCLASS,
                                                     INTEGERCLASS,
                                                     STRINGCLASS,
                                                     DOUBLECLASS
                                                 };

    /** Header names for the table */
    private static final String[] COLUMNHEADERS = 
                                                  {
                                                      "Name",
                                                      "Dealer",
                                                      "Quantity",
                                                      "Unit",
                                                      "Price"
                                                  };

    //~ Instance fields --------------------------------------------------------

    /** Holds the ArticleDescription objects */
    private Object[] tableData;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ArticleDescriptionModel object.
     */
    private ArticleDescriptionTableModel()
    {
        updateTableData();
        DataUpdateMediator.getInstance().addObserver(this);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Returns the Singleton instance of this class.
     *
     * @return The Singleton instance
     */
    public static ArticleDescriptionTableModel getInstance()
    {
        return INSTANCE;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class getColumnClass(int columnIndex)
    {
        return COLUMNCLASSES[columnIndex];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount()
    {
        return COLUMNHEADERS.length;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int columnIndex)
    {
        return COLUMNHEADERS[columnIndex];
    }

    /**
     * Returns the ArticleDescription object at the given row.
     *
     * @param row Row of the object to be returned.
     *
     * @return The object at te given row.
     */
    public ArticleDescription getObjectAtRow(int row)
    {
        return (ArticleDescription)tableData[row];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount()
    {
        return tableData.length;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ArticleDescription article = (ArticleDescription)tableData[rowIndex];

        switch (columnIndex)
        {
            case 0:
                return article.getName();

            case 1:
                return article.getStore();

            case 2:
                return new Integer(article.getQuantity());

            case 3:
                return article.getUnit();

            case 4:
                return new Double(article.getPrice());

            default:
                return null;
        }
    }

    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable o, Object arg)
    {
        updateTableData();
        fireTableDataChanged();
    }

    /**
     * Fetches the current list of Article Descriptions from the storage.
     */
    private void updateTableData()
    {
        tableData = StorageFactory.getCurrentStorage()
                                  .getAllArticleDescriptions().toArray();
    }
}
