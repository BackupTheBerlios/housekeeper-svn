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
 * Copyright 2003, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper.swing;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


/**
 * DOCUMENT ME!
 *
 * @author <a href="notizklotz@gmx.net">Adrian Gygax</a>
 */
final class Exit extends AbstractAction
{
    //~ Static fields/initializers ---------------------------------------------

    /** TODO DOCUMENT ME! */
    static final Exit INSTANCE = new Exit();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new Exit object.
    
     */
    private Exit()
    {
        super("Exit");
    }

    //~ Methods ----------------------------------------------------------------

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        System.exit(0);
    }
}
