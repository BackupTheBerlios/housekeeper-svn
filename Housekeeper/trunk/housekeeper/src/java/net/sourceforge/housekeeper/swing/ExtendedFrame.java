package net.sourceforge.housekeeper.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract Scenic frame. Provides language support.
 *
 * Copyright (c) 2003 Java Magazin, Steffen Mueller.
 *
 * All Rights Reserved.
 */

abstract class ExtendedFrame extends JFrame {

    // Class variables ******************************************************************


    // Constructors *********************************************************************

    public ExtendedFrame() throws HeadlessException {
        super();
    }


    // Abstract methods *****************************************************************


    // Class methods ********************************************************************

    /**
     * Moves the frame into the screen center.
     */
    protected void center() {
        // Center frame
        Dimension paneSize = getSize();
        Dimension screenSize = getToolkit().getScreenSize();

        // Calculate locations
        int xLocation = (screenSize.width - paneSize.width) / 2;
        int yLocation = (screenSize.height - paneSize.height) / 2;

        // Set to 0 in case they are negative
        if (xLocation < 0) xLocation = 0;
        if (yLocation < 0) yLocation = 0;

        setLocation(xLocation, yLocation);
    }


	/**
	 * Shows a message dialog.
	 * @param message The message
	 * @param title The dialog's title
	 * @param messageType The message's type
	 */
	protected void showMessageDialog(final String message, final String title, final int messageType) {
		JOptionPane.showMessageDialog(ExtendedFrame.this, message, title, messageType);
	}


	/**
	 * Shows a message dialog. Runnable.
	 * @param message The message
	 * @param title The dialog's title
	 * @param messageType The message's type
	 */
	protected void showMessageDialogRunnable(final String message, final String title, final int messageType) {
		Runnable runnable = new Runnable() {
			public void run() {
				showMessageDialog(message, title, messageType);
			}
		};
		try {
			SwingUtilities.invokeAndWait(runnable);
		} catch (Exception e) {
		}
	}
}
