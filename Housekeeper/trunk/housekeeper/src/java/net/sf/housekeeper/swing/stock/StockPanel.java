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
 * Lets the user manage the {@link net.sf.housekeeper.domain.StockItem}objects.
 * It provides access to displaying, adding and modifying items.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class StockPanel extends JPanel
{

    /** Singleton instance */
    private static final StockPanel INSTANCE = new StockPanel();

    /** The table showing the items. */
    private ListTable               table;

    /**
     * Creates a new StockPanel object.
     */
    private StockPanel()
    {
        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton(new NewStockItemAction()));
        buttonPanel.add(new JButton(new DeleteStockItemAction()));

        final EventList items = StorageFactory.getCurrentStorage()
                .getAllStockItems();
        final SortedList sortedList = new SortedList(items, null);
        table = new ListTable(sortedList, new StockTableCell());
        new TableComparatorChooser(table, sortedList, false);

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(table.getTableScrollPane(), BorderLayout.CENTER);
    }

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
     * Returns the selected item from the table.
     * 
     * @return The selected Article Description or null if no table row is
     *         selected.
     */
    public StockItem getSelectedItem()
    {
        return (StockItem) table.getSelected();
    }

    /**
     * Deletes the currently selected item.
     */
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

    /**
     * Shows a dialog for adding a new item.
     */
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