/*
 * This file is part of Housekeeper.
 *
 * Housekeeper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Housekeeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Housekeeper; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA  02111-1307  USA
 *
 * Copyright 2003-2004, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper.swing.purchase;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.sourceforge.housekeeper.domain.Purchase;
import net.sourceforge.housekeeper.swing.MainFrame;
import net.sourceforge.housekeeper.swing.SwingUtils;
import net.sourceforge.housekeeper.swing.assortimentItem.AssortimentItemTableModel;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @see
 * @since
 */
public final class PurchaseDialog extends JDialog
{
    
    private TableModel model;

	//~ Constructors -----------------------------------------------------------
    public PurchaseDialog()
    {
        super(MainFrame.INSTANCE, true);
        
        
        JTable table = new JTable(new AssortimentItemTableModel());
        
        final String[] COLUMNHEADERS = 
                                                      {
                                                          "Name",
                                                          "Dealer",
                                                          "Quantity",
                                                          "Unit",
                                                          "Price"
                                                      };
        
		model = new DefaultTableModel(COLUMNHEADERS, 1);
        JTable selected = new JTable(model);
        
        JPanel first = new JPanel();
        first.setLayout(new BoxLayout(first, BoxLayout.X_AXIS));
        first.add(new JScrollPane(table));
        
        JButton add = new JButton("Add");

        first.add(new JButton("Add"));
        first.add(new JScrollPane(selected));        
        
        getContentPane().add(first);
        
    }
    
    public Purchase show(String title)
    {
        setTitle(title);
        pack();
        SwingUtils.centerOnComponent(this, getParent());
        show();
        return null;
    }
    
    private class AddButtonListener implements ActionListener
    {

		public void actionPerformed(ActionEvent e)
		{
            DefaultTableModel m = (DefaultTableModel)model;

			
		}
        
    }
    
}
