package net.sourceforge.housekeeper.swing;

import java.util.Collection;

import javax.swing.table.AbstractTableModel;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public abstract class TableModelTemplate extends AbstractTableModel
{

    private String[] headers;
    
    private Object[] tableData;
    
    public TableModelTemplate(String[] headers)
    {
        tableData = new Object[0];
        this.headers = headers;
    }
    
    public Class getColumnClass(int columnIndex)
    {
        return getValueAt(0, columnIndex).getClass();
    }
    
    public int getColumnCount()
    {
        return headers.length;
    }
    
    public String getColumnName(int columnIndex)
    {
        return headers[columnIndex];
    }
    
    /**
     * Returns the AssortimentItem object at the given row.
     *
     * @param row Row of the object to be returned.
     *
     * @return The object at te given row.
     */
    public Object getObjectAtRow(int row)
    {
        return tableData[row];
    }

    public int getRowCount()
    {
        return tableData.length;
    }
    
    public void setTableData(Collection data)
    {
        tableData = data.toArray();
        fireTableDataChanged();
    }
    
}
