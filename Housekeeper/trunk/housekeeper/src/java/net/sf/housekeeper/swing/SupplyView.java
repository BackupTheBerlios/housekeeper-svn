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
import java.awt.GridLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A panel for display and manipulation of the items on hand. It consists of a
 * table and several buttons for adding and manipulation items.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class SupplyView extends JPanel
{

    private final JPanel contentPanel;

    private final JPanel buttonPanel;

    SupplyView(Action newItemAction, Action duplicateItemAction,
            Action editItemAction, Action deleteItemAction)
    {
        setLayout(new BorderLayout());

        contentPanel = new JPanel(new GridLayout(1, 0));

        buttonPanel = new JPanel();
        buttonPanel.add(new JButton(newItemAction));
        buttonPanel.add(new JButton(duplicateItemAction));
        buttonPanel.add(new JButton(editItemAction));
        buttonPanel.add(new JButton(deleteItemAction));

        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.NORTH);
    }

    void addPanel(JPanel panel)
    {
        contentPanel.add(panel);
    }

}