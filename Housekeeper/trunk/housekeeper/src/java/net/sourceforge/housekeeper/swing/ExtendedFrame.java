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


import java.awt.*;

import javax.swing.*;


/**
 * Abstract Scenic frame. Provides language support. Copyright (c) 2003 Java
 * Magazin, Steffen Mueller. All Rights Reserved.
 */
abstract class ExtendedFrame extends JFrame
{
    //~ Constructors -----------------------------------------------------------

    // Class variables ******************************************************************
    // Constructors *********************************************************************
    public ExtendedFrame() throws HeadlessException
    {
        super();
    }

    //~ Methods ----------------------------------------------------------------

    // Abstract methods *****************************************************************
    // Class methods ********************************************************************

    /**
     * Moves the frame into the screen center.
     */
    protected void center()
    {
        // Center frame
        Dimension paneSize   = getSize();
        Dimension screenSize = getToolkit()
                                   .getScreenSize();

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


        setLocation(xLocation, yLocation);
    }

    /**
     * Shows a message dialog.
     *
     * @param message The message
     * @param title The dialog's title
     * @param messageType The message's type
     */
    protected void showMessageDialog(final String message,
                                     final String title,
                                     final int    messageType)
    {
        JOptionPane.showMessageDialog(ExtendedFrame.this, message, title,
                                      messageType);
    }

    /**
     * Shows a message dialog. Runnable.
     *
     * @param message The message
     * @param title The dialog's title
     * @param messageType The message's type
     */
    protected void showMessageDialogRunnable(final String message,
                                             final String title,
                                             final int    messageType)
    {
        Runnable runnable = new Runnable()
        {
            public void run()
            {
                showMessageDialog(message, title, messageType);
            }
        };


        try
        {
            SwingUtilities.invokeAndWait(runnable);
        }
        catch (Exception e)
        {
        }
    }
}
