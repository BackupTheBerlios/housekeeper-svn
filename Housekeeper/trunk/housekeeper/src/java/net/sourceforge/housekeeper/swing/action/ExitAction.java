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

package net.sourceforge.housekeeper.swing.action;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


/**
 * Action for exiting the application.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
public final class ExitAction extends AbstractAction
{
    //~ Static fields/initializers ---------------------------------------------

    /** Singleton instance of this object. */
    public static final ExitAction INSTANCE = new ExitAction();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ExitAction object.
     */
    private ExitAction()
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
