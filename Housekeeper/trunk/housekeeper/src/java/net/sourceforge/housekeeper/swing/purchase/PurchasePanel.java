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


import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @see
 * @since
 */
public final class PurchasePanel extends JPanel
{
    //~ Static fields/initializers ---------------------------------------------

    /** Singleton instance */
    private static final PurchasePanel INSTANCE = new PurchasePanel();

    //~ Instance fields --------------------------------------------------------

    private JPanel      buttonPanel;
    private JScrollPane scrollPane;
    private JTable      table;

    //~ Constructors -----------------------------------------------------------

    private PurchasePanel()
    {
        table       = new JTable(new PurchasesTableModel());
        scrollPane  = new JScrollPane(table);
        buttonPanel = new JPanel();
        
        buttonPanel.add(new JButton(new NewPurchaseAction()));

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    //~ Methods ----------------------------------------------------------------

    public static PurchasePanel getInstance()
    {
        return INSTANCE;
    }

}
