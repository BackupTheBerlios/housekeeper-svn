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


import net.sourceforge.housekeeper.model.Article;
import net.sourceforge.housekeeper.storage.StorageFactory;

import java.awt.BorderLayout;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 */
final class ArticlePanel extends JPanel
{
    //~ Static fields/initializers ---------------------------------------------

    /** TODO DOCUMENT ME! */
    public static final ArticlePanel INSTANCE = new ArticlePanel();

    //~ Instance fields --------------------------------------------------------

    /** TODO DOCUMENT ME! */
    private JPanel      buttonPanel;

    /** TODO DOCUMENT ME! */
    private JScrollPane scrollPane;

    /** TODO DOCUMENT ME! */
    private JTable table;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ArticlePanel object.
    
     */
    private ArticlePanel()
    {
        table       = new JTable(new ArticleModel());
        scrollPane  = new JScrollPane(table);
        buttonPanel = new JPanel();

        buttonPanel.add(new JButton(NewArticle.INSTANCE));
        buttonPanel.add(new JButton(ModifyArticle.INSTANCE));

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    JTable getTable()
    {
        return table;
    }
    
    Article getSelectedArticle()
    {
        int selectedRow = table.getSelectedRow();
        ArticleModel tableModel = (ArticleModel)table.getModel();
        return tableModel.getObjectAtRow(selectedRow);
    }

    //~ Inner Classes ----------------------------------------------------------

    private class ArticleModel extends AbstractTableModel implements Observer
    {
        private ArticleModel()
        {
            StorageFactory.getCurrentStorage()
                          .addObserver(this);
        }

        public Class getColumnClass(int columnIndex)
        {
            switch (columnIndex)
            {
                case 0:
                    return new String().getClass();

                case 1:
                    return new String().getClass();

                case 2:
                    return new Integer(0).getClass();

                case 3:
                    return new String().getClass();

                case 4:
                    return new Double(0).getClass();

                default:
                    return null;
            }
        }

        /* (non-Javadoc)
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        public int getColumnCount()
        {
            return 5;
        }

        public String getColumnName(int columnIndex)
        {
            switch (columnIndex)
            {
                case 0:
                    return "Name";

                case 1:
                    return "Vendor";

                case 2:
                    return "Quantity";

                case 3:
                    return "Unit";

                case 4:
                    return "Price";

                default:
                    return null;
            }
        }

        /* (non-Javadoc)
         * @see javax.swing.table.TableModel#getRowCount()
         */
        public int getRowCount()
        {
            return StorageFactory.getCurrentStorage()
                                 .getArticles()
                                 .size();
        }

        /* (non-Javadoc)
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt(int rowIndex,
                                 int columnIndex)
        {
            List    articles = StorageFactory.getCurrentStorage()
                                             .getArticles();
            Article article = (Article)articles.get(rowIndex);

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
        public void update(Observable o,
                           Object     arg)
        {
            fireTableDataChanged();
        }
        
        public Article getObjectAtRow(int row)
        {
            return StorageFactory.getCurrentStorage().getArticle(row);
        }
    }
}
