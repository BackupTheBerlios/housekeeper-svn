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

import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
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

    private Frame        parentFrame;

    /**
     * Creates a new view.
     *  
     */
    public SupplyView()
    {
        setLayout(new GridLayout(1, 0));
    }

    /**
     * Adds a new panel to the right of the existing panels, creating a titled
     * border with the panel's name around it.
     * 
     * @param panel The panel to add.
     */
    void addPanel(JPanel panel)
    {
        panel.setBorder(BorderFactory.createTitledBorder(panel.getName()));
        add(panel);
    }

    /**
     * Sets the parent frame for this panel. Used for dialog display.
     * 
     * @param parent The parent frame for this panel.
     */
    public void setParentFrame(Frame parent)
    {
        this.parentFrame = parent;
    }

    Frame getParentFrame()
    {
        return parentFrame;
    }

}