package net.sourceforge.housekeeper.swing.stock;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import net.sourceforge.housekeeper.domain.StockItem;
import net.sourceforge.housekeeper.storage.StorageFactory;
import net.sourceforge.housekeeper.swing.DataUpdateMediator;
import net.sourceforge.housekeeper.swing.TableModelTemplate;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class StockPanel extends JPanel implements Observer
{
    private static final StockPanel INSTANCE = new StockPanel();
    
    private JPanel      buttonPanel;
    private JTable      table;
    private TableModelTemplate model;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AssortimentItemPanel object.
     */
    private StockPanel()
    {
        model = new StockTableModel();
        table       = new JTable(model);
        buttonPanel = new JPanel();

        //buttonPanel.add(new JButton(NewAssortimentItemAction.INSTANCE));
        

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        DataUpdateMediator.getInstance().addObserver(this);
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
    public StockItem getSelectedArticle()
    {
        int selectedRow = table.getSelectedRow();

        if (selectedRow > -1)
        {
            return (StockItem)model.getObjectAtRow(selectedRow);
        }
        else
        {
            return null;
        }
    }

    public void update(Observable arg0, Object arg1)
    {
        model.setTableData(StorageFactory.getCurrentStorage().getStockItemMapper().getAll());
    }

}
