package net.sourceforge.housekeeper.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Abstract Scenic dialog. Provides constants.
 *
 * Copyright (c) 2003 Java Magazin, Steffen Mueller.
 *
 * All Rights Reserved.
 */

abstract class ExtendedDialog extends JDialog {

    // Class variables ******************************************************************


    // Constructors *********************************************************************

    public ExtendedDialog(Frame owner, boolean modal) {
        super(owner, modal);
    }


    // Class methods ********************************************************************

    /**
     * Centers the dialog based on its parent.
     */
    protected void center() {
        Point parentFrameLocation = this.getParent().getLocation();
        Dimension parentFrameSize = this.getParent().getSize();
        Dimension thisDialogSize = this.getSize();

        // Calculate locations
        int xLocation = parentFrameLocation.x + parentFrameSize.width / 2 - thisDialogSize.width / 2;
        int yLocation = parentFrameLocation.y + parentFrameSize.height / 2 - thisDialogSize.height / 2;

        // Do not set location outside of screen
        if (xLocation < 0) xLocation = 15;
        if (yLocation < 0) yLocation = 15;

        // Do not cover parent frame
        if (xLocation <= parentFrameLocation.x + 15) xLocation = parentFrameLocation.x + 15;
        if (yLocation <= parentFrameLocation.y + 15) yLocation = parentFrameLocation.y + 15;

        this.setLocation(xLocation, yLocation);
    }


    /**
     * Shows a message dialog.
     * @param message The message
     * @param title The dialog's title
     * @param messageType The message's type
     */
    protected void showMessageDialog(final String message, final String title, final int messageType) {
		JOptionPane.showMessageDialog(ExtendedDialog.this, message, title, messageType);
    }


	// Standard listeners *************************************************************

	class DefaultCancelButtonActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}
