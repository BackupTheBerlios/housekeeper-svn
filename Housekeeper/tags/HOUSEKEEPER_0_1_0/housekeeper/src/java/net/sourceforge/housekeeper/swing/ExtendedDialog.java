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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


/**
 * Abstract Scenic dialog. Provides constants. Copyright (c) 2003 Java Magazin,
 * Steffen Mueller. All Rights Reserved.
 */
abstract class ExtendedDialog extends JDialog
{
    //~ Constructors -----------------------------------------------------------

    // Class variables ******************************************************************
    // Constructors *********************************************************************
    public ExtendedDialog(Frame   owner,
                          boolean modal)
    {
        super(owner, modal);
    }

    //~ Methods ----------------------------------------------------------------

    // Class methods ********************************************************************

    /**
     * Centers the dialog based on its parent.
     */
    protected void center()
    {
        Point     parentFrameLocation = this.getParent()
                                            .getLocation();
        Dimension parentFrameSize = this.getParent()
                                        .getSize();
        Dimension thisDialogSize  = this.getSize();

        // Calculate locations
        int xLocation = (parentFrameLocation.x + (parentFrameSize.width / 2)) -
                        (thisDialogSize.width / 2);
        int yLocation = (parentFrameLocation.y + (parentFrameSize.height / 2)) -
                        (thisDialogSize.height / 2);

        // Do not set location outside of screen
        if (xLocation < 0)
        {
            xLocation = 15;
        }


        if (yLocation < 0)
        {
            yLocation = 15;
        }


        // Do not cover parent frame
        if (xLocation <= (parentFrameLocation.x + 15))
        {
            xLocation = parentFrameLocation.x + 15;
        }


        if (yLocation <= (parentFrameLocation.y + 15))
        {
            yLocation = parentFrameLocation.y + 15;
        }


        this.setLocation(xLocation, yLocation);
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
        JOptionPane.showMessageDialog(ExtendedDialog.this, message, title,
                                      messageType);
    }

    //~ Inner Classes ----------------------------------------------------------

    // Standard listeners *************************************************************
    class DefaultCancelButtonActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            dispose();
        }
    }
}
