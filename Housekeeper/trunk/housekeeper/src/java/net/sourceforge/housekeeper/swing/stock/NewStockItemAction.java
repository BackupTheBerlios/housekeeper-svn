package net.sourceforge.housekeeper.swing.stock;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import net.sourceforge.housekeeper.domain.AssortimentItem;
import net.sourceforge.housekeeper.domain.StockItem;
import net.sourceforge.housekeeper.storage.StorageFactory;
import net.sourceforge.housekeeper.swing.DataUpdateMediator;
import net.sourceforge.housekeeper.swing.MainFrame;
import net.sourceforge.housekeeper.swing.assortimentItem.AssortimentItemPanel;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @see
 * @since
 */
public final class NewStockItemAction extends AbstractAction
{
    public static final NewStockItemAction INSTANCE = new NewStockItemAction();
    
    private NewStockItemAction()
    {
        super("Add item to stock");
    }

	public void actionPerformed(ActionEvent arg0)
	{
        AssortimentItem article = AssortimentItemPanel.getInstance().getSelectedArticle();
        
        if (article != null)
        {
             String date = JOptionPane.showInputDialog(MainFrame.INSTANCE, "Please enter the Best Before End date for the item to be added");
             
             StockItem item = new StockItem(article, date);
             StorageFactory.getCurrentStorage().getStockItemMapper().add(item);
             DataUpdateMediator.getInstance().update();
        } 
                                                            
	}

}
