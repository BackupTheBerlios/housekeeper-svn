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

package net.sourceforge.housekeeper.swing;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import net.sourceforge.housekeeper.model.ArticleDescription;
import net.sourceforge.housekeeper.storage.StorageFactory;


//~ Inner Classes ----------------------------------------------------------

class ArticleDescriptionTableModel extends AbstractTableModel implements Observer
{
    private static ArticleDescriptionTableModel instance = new ArticleDescriptionTableModel();
    
    private final Class doubleClass = new Double(0).getClass();
    private final Class integerClass = new Integer(0).getClass();
    private final Class stringClass = new String().getClass();
    private final Class[] columnClasses = 
                                          {
                                              stringClass,
                                              stringClass,
                                              integerClass,
                                              stringClass,
                                              doubleClass
                                          };
    private final String[] columnHeaders = 
                                           {
                                               "Name",
                                               "Dealer",
                                               "Quantity",
                                               "Unit",
                                               "Price"
                                           };

    private ArticleDescriptionTableModel()
    {
        StorageFactory.getCurrentStorage().addObserver(this);
    }

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

    public ArticleDescription getObjectAtRow(int row)
    {
        return StorageFactory.getCurrentStorage().getArticle(row);
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount()
    {
        return StorageFactory.getCurrentStorage().getArticles().size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        List articles = StorageFactory.getCurrentStorage().getArticles();
        ArticleDescription article = (ArticleDescription)articles.get(rowIndex);

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

    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable o, Object arg)
    {
        fireTableDataChanged();
    }
}