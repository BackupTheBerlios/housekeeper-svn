package net.sf.housekeeper.swing.stock;

import javax.swing.JTable;

import net.sf.housekeeper.domain.StockItem;

import com.odellengineeringltd.glazedlists.jtable.TableFormat;

/**
 * @author
*/
final class StockTableCell implements TableFormat
{

    /* (non-Javadoc)
     * @see com.odellengineeringltd.glazedlists.jtable.TableFormat#getFieldCount()
     */
    public int getFieldCount()
    {
        return 2;
    }

    /* (non-Javadoc)
     * @see com.odellengineeringltd.glazedlists.jtable.TableFormat#getFieldName(int)
     */
    public String getFieldName(int arg0)
    {
        if (arg0 == 0)
        {
            return "Item";
        }
        return "Best before end";
    }

    /* (non-Javadoc)
     * @see com.odellengineeringltd.glazedlists.jtable.TableFormat#getFieldValue(java.lang.Object, int)
     */
    public Object getFieldValue(Object arg0, int arg1)
    {
        final StockItem item = (StockItem)arg0;

        if (arg1 == 0)
        {
            return item.getName();
        }
        return item.getBestBeforeEnd();
    }

    /* (non-Javadoc)
     * @see com.odellengineeringltd.glazedlists.jtable.TableFormat#configureTable(javax.swing.JTable)
     */
    public void configureTable(JTable arg0)
    {
        //Nothing to do
    }

}
