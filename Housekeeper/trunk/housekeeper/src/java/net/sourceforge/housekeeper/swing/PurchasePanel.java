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


import net.sourceforge.housekeeper.domain.Purchase;
import net.sourceforge.housekeeper.storage.StorageFactory;

import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @see
 * @since
 */
final class PurchasePanel extends JPanel
{
    //~ Static fields/initializers ---------------------------------------------

    /** TODO DOCUMENT ME! */
    private static final PurchasePanel INSTANCE = new PurchasePanel();

    //~ Instance fields --------------------------------------------------------

    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JTable table;

    //~ Constructors -----------------------------------------------------------

    private PurchasePanel()
    {
        table = new JTable(new PurchasesTableModel());
        scrollPane = new JScrollPane(table);
        buttonPanel = new JPanel();

        buttonPanel.add(new JButton(NewPurchaseAction.INSTANCE));

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    //~ Methods ----------------------------------------------------------------

    static PurchasePanel getInstance()
    {
        return INSTANCE;
    }


    //~ Inner Classes ----------------------------------------------------------

    private class PurchasesTableModel extends AbstractTableModel
        implements Observer
    {
        private final Class dateClass = new Date().getClass();
        private final Class doubleClass = new Double(0).getClass();
        private List purchasesList;
        private final Class[] columnClasses = 
                                              {
                                                  dateClass,
                                                  doubleClass
                                              };
        private final String[] columnHeaders = 
                                               {
                                                   "Date",
                                                   "Amount"
                                               };

        private PurchasesTableModel()
        {
            update();
            DataUpdateMediator.getInstance().addObserver(this);
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

        public Purchase getObjectAtRow(int row)
        {
            return (Purchase)purchasesList.get(row);
        }

        /* (non-Javadoc)
         * @see javax.swing.table.TableModel#getRowCount()
         */
        public int getRowCount()
        {
            return purchasesList.size();
        }

        /* (non-Javadoc)
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            Purchase purchase = (Purchase)purchasesList.get(rowIndex);

            switch (columnIndex)
            {
                case 0:
                    return purchase.getDate();

                case 1:
                    return new Double(purchase.getTotalAmount());

                default:
                    return null;
            }
        }

        /* (non-Javadoc)
         * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
         */
        public void update(Observable o, Object arg)
        {
            update();
            fireTableDataChanged();
        }

        private void update()
        {
            Collection purchasesCollection = StorageFactory.getCurrentStorage()
                                             .getAllPurchases();
            purchasesList = new ArrayList(purchasesCollection);
        }
    }
}
