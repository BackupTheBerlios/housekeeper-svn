/*
 * This file is part of Housekeeper.
 * 
 * Housekeeper is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Housekeeper is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Housekeeper; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Copyright 2003-2004, The Housekeeper Project
 * http://housekeeper.sourceforge.net
 */

package net.sf.housekeeper.swing;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;

import net.sf.housekeeper.domain.Household;

/**
 * Lets the user manage the {@link net.sf.housekeeper.domain.StockItem}objects.
 * It provides access to displaying, adding and modifying items.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class StockPanel extends JPanel
{

    /**
     * Creates a new StockPanel object.
     */
    public StockPanel()
    {
        final StockItemsModel model = new StockItemsModel(Household.instance());

        setLayout(new BorderLayout());

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton(model.getNewAction()));
        buttonPanel.add(new JButton(model.getEditAction()));
        buttonPanel.add(new JButton(model.getDeleteAction()));
        add(buttonPanel, BorderLayout.NORTH);

        final JTable table = new JTable();
        table.setModel(new StockItemsTableModel(model.getItemSelection()));
        table.setSelectionModel(new SingleListSelectionAdapter(model
                .getItemSelection().getSelectionIndexHolder()));
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

}