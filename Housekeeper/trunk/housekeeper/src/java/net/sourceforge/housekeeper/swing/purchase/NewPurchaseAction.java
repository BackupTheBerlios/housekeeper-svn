package net.sourceforge.housekeeper.swing.purchase;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @see
 * @since
 */
public final class NewPurchaseAction extends AbstractAction
{
    
    public NewPurchaseAction()
    {
        super("New Purchase");
    }

	public void actionPerformed(ActionEvent e)
	{
		PurchaseDialog dialog = new PurchaseDialog();
        dialog.show("New Purchase");

	}

}
