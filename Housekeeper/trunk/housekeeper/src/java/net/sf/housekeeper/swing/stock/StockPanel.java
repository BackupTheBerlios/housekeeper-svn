package net.sf.housekeeper.swing.stock;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.sf.housekeeper.domain.StockItem;
import net.sf.housekeeper.storage.StorageFactory;

import com.odellengineeringltd.glazedlists.EventList;
import com.odellengineeringltd.glazedlists.jtable.ListTable;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class StockPanel extends JPanel
{
    private static final StockPanel INSTANCE = new StockPanel();

    private ListTable table2;

    /**
     * Creates a new StockPanel object.
     */
    private StockPanel()
    {
        final EventList items =StorageFactory.getCurrentStorage().getAllStockItems();
        table2 = new ListTable(items, new StockTableCell());
        final JPanel buttonPanel = new JPanel();

        buttonPanel.add(new JButton(NewStockItemAction.INSTANCE));
        buttonPanel.add(new JButton(new DeleteStockItemAction()));

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(table2.getTableScrollPane(), BorderLayout.CENTER);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Returns the Singleton instance of this class.
     *
     * @return The Singleton instance.
     */
    public static StockPanel getInstance()
    {
        return INSTANCE;
    }

    /**
     * Returns the selected Article Description from the table.
     *
     * @return The selected Article Description or null if no table row is
     *         selected.
     */
    public StockItem getSelectedItem()
    {
            return (StockItem)table2.getSelected();
    }

    private class DeleteStockItemAction extends AbstractAction
    {

        private DeleteStockItemAction()
        {
            super("Delete Item");
        }
        
        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent arg0)
        {
            final StockItem item = getSelectedItem();

            if (item != null)
            {
                StorageFactory.getCurrentStorage().remove(item);
            }
        }
    }
}
