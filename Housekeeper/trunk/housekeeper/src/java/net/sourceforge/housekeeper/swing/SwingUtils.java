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


import java.awt.Component;
import java.awt.Dimension;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
final class SwingUtils
{
    //~ Constructors -----------------------------------------------------------

    private SwingUtils()
    {
    }

    //~ Methods ----------------------------------------------------------------

    static void centerOnScreen(Component comp)
    {
        // Center frame
        Dimension paneSize   = comp.getSize();
        Dimension screenSize = comp.getToolkit().getScreenSize();

        // Calculate locations
        int xLocation = (screenSize.width - paneSize.width) / 2;
        int yLocation = (screenSize.height - paneSize.height) / 2;

        // Set to 0 in case they are negative
        if (xLocation < 0)
        {
            xLocation = 0;
        }


        if (yLocation < 0)
        {
            yLocation = 0;
        }


        comp.setLocation(xLocation, yLocation);
    }
}
