package net.sourceforge.housekeeper.swing.stock;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.sourceforge.housekeeper.domain.StockItem;
import net.sourceforge.housekeeper.storage.StorageFactory;
import net.sourceforge.housekeeper.swing.DataUpdateMediator;

/**
 * @author Adrian Gygax
 */
final class DeleteStockItemAction extends AbstractAction
{

    public static final DeleteStockItemAction INSTANCE = new DeleteStockItemAction();
    
    private static final String TITLE = "Delete Item";

    private DeleteStockItemAction()
    {
        super(TITLE);
    }
    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0)
    {
        StockItem item = StockPanel.getInstance().getSelectedItem();

        if (item != null)
        {
            StorageFactory.getCurrentStorage().getStockItemMapper()
                    .remove(item);
            DataUpdateMediator.getInstance().update();

        }

    }

}