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

package net.sf.housekeeper.swing.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import net.sf.housekeeper.swing.MainFrame;

/**
 * A class for creating standard dialogs for entering data.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class StandardDialog extends JDialog
{

    /** State when the when the entries have been approved. */
    public static final int APPROVE = 1;

    /** State when the entries should be discarded. */
    public static final int DISCARD = 0;

    private FormBuilder     builder;

    /** The validity of the users entries. */
    private int             state   = DISCARD;

    /**
     * Creates a new standard dialog centered on the {@link MainFrame}.
     * 
     * @param title The title of the dialog's window.
     */
    public StandardDialog(final String title)
    {
        super(MainFrame.INSTANCE, title, true);
        builder = new FormBuilder();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Adds a new row to the form. The label is placed to the left of the
     * component.
     * 
     * @param label The label for the component. Must not be null.
     * @param comp The component to be added. Must not be null.
     * @param length The length of this component. Must not be negative.
     */
    public void addElement(final String label, final Component comp,
                           final int length)
    {
        builder.append(label, comp, length);
    }

    /**
     * Displays the dialog and returns the validity of the dialog's entries.
     * 
     * @return {@link #APPROVE} if the user has approved his entries,
     *         {@link #DISCARD} if he has discarded his entried or has closed
     *         the window.
     */
    public int display()
    {
        final JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {

                state = APPROVE;
                dispose();
            }
        });

        final JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                state = DISCARD;
                dispose();
            }
        });

        builder.setButtons(ok, cancel);
        getContentPane().add(builder.getPanel());
        pack();
        ComponentCenterer.centerOnComponent(this, getParent());
        show();
        return state;
    }
}