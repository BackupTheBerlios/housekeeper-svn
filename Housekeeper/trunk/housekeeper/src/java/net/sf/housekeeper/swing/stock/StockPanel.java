package net.sf.housekeeper.swing.stock;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.sf.housekeeper.domain.StockItem;
import net.sf.housekeeper.storage.StorageFactory;

import com.odellengineeringltd.glazedlists.EventList;
import com.odellengineeringltd.glazedlists.SortedList;
import com.odellengineeringltd.glazedlists.jtable.ListTable;
import com.odellengineeringltd.glazedlists.jtable.TableComparatorChooser;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class StockPanel extends JPanel
{
    private static final StockPanel INSTANCE = new StockPanel();

    private ListTable table;

    /**
     * Creates a new StockPanel object.
     */
    private StockPanel()
    {
        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton(new NewStockItemAction()));
        buttonPanel.add(new JButton(new DeleteStockItemAction()));

        final EventList items = StorageFactory.getCurrentStorage().getAllStockItems();
        final SortedList sortedList = new SortedList(items, null);
        table = new ListTable(sortedList, new StockTableCell());
        new TableComparatorChooser(table, sortedList, false);
        
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(table.getTableScrollPane(), BorderLayout.CENTER);
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
            return (StockItem)table.getSelected();
    }

    private class DeleteStockItemAction extends AbstractAction
    {

        private DeleteStockItemAction()
        {
            super("Delete Item");
        }
        
        public void actionPerformed(ActionEvent arg0)
        {
            final StockItem item = getSelectedItem();

            if (item != null)
            {
                StorageFactory.getCurrentStorage().remove(item);
            }
        }
    }
    
    private class NewStockItemAction extends AbstractAction
    {

        private NewStockItemAction()
        {
            super("Add item to stock");
        }

        public void actionPerformed(ActionEvent arg0)
        {
            StockItemDialog d = new StockItemDialog();
            StockItem item = d.show("Add item to stock");

            if (item != null)
            {
                StorageFactory.getCurrentStorage().add(item);
            }
        }
    }
}
