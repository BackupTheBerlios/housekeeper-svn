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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 * A default Action for closing a specified dialog.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @since 0.1
 */
public final class DefaultCancelButtonAction extends AbstractAction
{

    /** The dialog this Listener should act upon. */
    private final JDialog dialog;

    /**
     * Creates a new DefaultCancelButtonActionListener object.
     * 
     * @param dialog The dialog this Listener should act upon. Must not be null.
     */
    public DefaultCancelButtonAction(final JDialog dialog)
    {
        super("Cancel");
        assert dialog != null : "Parameter dialog must not be null";
        
        this.dialog = dialog;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#
     * actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(final ActionEvent e)
    {
        dialog.dispose();
    }
}