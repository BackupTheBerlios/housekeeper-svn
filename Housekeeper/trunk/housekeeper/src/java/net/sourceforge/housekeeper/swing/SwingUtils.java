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

package net.sourceforge.housekeeper.swing;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;


/**
 * Provides useful services to GUI components.
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

    /**
     * Centers a component on another component. This is particularly useful
     * for centering dialogs on its parent window.
     *
     * @param centerComponent Component to be centered on another.
     * @param onComponent Component on which centerComponent should be
     *        centered.
     */
    static void centerOnComponent(Component centerComponent,
                                  Component onComponent)
    {
        final Point     parentFrameLocation = onComponent.getLocation();
        final Dimension parentFrameSize = onComponent.getSize();
        final Dimension thisDialogSize  = centerComponent.getSize();

        // Calculate locations
        int xLocation = (parentFrameLocation.x + (parentFrameSize.width / 2)) -
                        (thisDialogSize.width / 2);
        int yLocation = (parentFrameLocation.y + (parentFrameSize.height / 2)) -
                        (thisDialogSize.height / 2);

        // Do not set location outside of screen
        xLocation = ifNegativeSetTo(xLocation, 15);
        yLocation = ifNegativeSetTo(yLocation, 15);


        // Do not cover parent frame
        if (xLocation <= (parentFrameLocation.x + 15))
        {
            xLocation = parentFrameLocation.x + 15;
        }


        if (yLocation <= (parentFrameLocation.y + 15))
        {
            yLocation = parentFrameLocation.y + 15;
        }


        centerComponent.setLocation(xLocation, yLocation);
    }

    /**
     * Centers a component on the screen. This is particularly useful for
     * centering a Frame on the screen.
     *
     * @param comp Component to be centered on the screen.
     */
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
    
    private static int ifNegativeSetTo(int from, int to)
    {
        if(from < 0)
        {
            return to;
        }
        return from;
    }
}
