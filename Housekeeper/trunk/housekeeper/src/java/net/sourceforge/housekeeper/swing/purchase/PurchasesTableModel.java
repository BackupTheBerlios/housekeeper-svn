package net.sourceforge.housekeeper.swing.purchase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import net.sourceforge.housekeeper.domain.Purchase;
import net.sourceforge.housekeeper.storage.StorageFactory;
import net.sourceforge.housekeeper.swing.DataUpdateMediator;


//~ Inner Classes ----------------------------------------------------------

class PurchasesTableModel extends AbstractTableModel
    implements Observer
{

    private final Class   dateClass     = new Date().getClass();
    private final Class   doubleClass   = new Double(0).getClass();
    private List          purchasesList;
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

    PurchasesTableModel()
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
        //TODO
        //Collection purchasesCollection = StorageFactory.getCurrentStorage().getAllPurchases();
        //purchasesList = new ArrayList(purchasesCollection);
    }
}