package net.sourceforge.housekeeper.swing.stock;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.sourceforge.housekeeper.domain.StockItem;
import net.sourceforge.housekeeper.storage.StorageFactory;
import net.sourceforge.housekeeper.swing.DataUpdateMediator;

/**
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class NewStockItemAction extends AbstractAction
{

    public static final NewStockItemAction INSTANCE = new NewStockItemAction();

    private static final String TITLE = "Add item to stock";

    private NewStockItemAction()
    {
        super(TITLE);
    }

    public void actionPerformed(ActionEvent arg0)
    {

        StockItemDialog d = new StockItemDialog();
        StockItem item = d.show(TITLE);

        if (item != null)
        {
            StorageFactory.getCurrentStorage().getStockItemMapper().add(item);
            DataUpdateMediator.getInstance().update();
        }

    }

}