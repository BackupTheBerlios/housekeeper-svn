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

package net.sourceforge.housekeeper.swing;


import net.sourceforge.housekeeper.model.ArticleDescription;
import net.sourceforge.housekeeper.storage.StorageFactory;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;


/**
 * TODO DOCUMENT ME!
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since
 */
final class ArticleDescriptionTableModel extends AbstractTableModel
    implements Observer
{
    //~ Static fields/initializers ---------------------------------------------

    private static ArticleDescriptionTableModel instance = new ArticleDescriptionTableModel();

    /** TODO DOCUMENT ME! */
    private static final Class doubleClass = new Double(0).getClass();

    /** TODO DOCUMENT ME! */
    private static final Class integerClass = new Integer(0).getClass();

    /** TODO DOCUMENT ME! */
    private static final Class stringClass = new String().getClass();

    /** TODO DOCUMENT ME! */
    private static final Class[] columnClasses = 
                                                 {
                                                     stringClass,
                                                     stringClass,
                                                     integerClass,
                                                     stringClass,
                                                     doubleClass
                                                 };

    /** TODO DOCUMENT ME! */
    private static final String[] columnHeaders = 
                                                  {
                                                      "Name",
                                                      "Dealer",
                                                      "Quantity",
                                                      "Unit",
                                                      "Price"
                                                  };
    private static Object[]       tableData;

    //~ Constructors -----------------------------------------------------------

    private ArticleDescriptionTableModel()
    {
        updateTableData();
        StorageFactory.getCurrentStorage().addObserver(this);
        
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static ArticleDescriptionTableModel getInstance()
    {
        return instance;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class getColumnClass(int columnIndex)
    {
        return columnClasses[columnIndex];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount()
    {
        return columnHeaders.length;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int columnIndex)
    {
        return columnHeaders[columnIndex];
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @param row DOCUMENT ME!
     *
     * @return DOCUMENT ME!
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
                return article.getQuantityUnit();

            case 4:
                return new Double(article.getPrice());

            default:
                return null;
        }
    }
    
    private void updateTableData()
    {
        tableData = StorageFactory.getCurrentStorage().getArticles().toArray();
    }

    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable o, Object arg)
    {
        updateTableData();
        fireTableDataChanged();
    }
}
