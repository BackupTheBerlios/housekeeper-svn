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

import javax.swing.JButton;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Generates a form which is compliant with the applications standards.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class FormBuilder
{

    /** Builder to which form building is delegated. */
    private DefaultFormBuilder builder;

    /** OK button of the button bar */
    private JButton            okButton;

    /** Cancel button of the button bar */
    private JButton            cancelButton;

    /**
     * Initializes a FormBuilder. All fields will have the length of
     * fieldLength.
     * 
     * @param fieldLength The length of all of the fields. Must not be negative.
     */
    public FormBuilder(final int fieldLength)
    {
        assert !(fieldLength<0) : "Parameter fieldLength (" + fieldLength
                + ") is less than 0";

        final FormLayout layout = new FormLayout("right:pref, 3dlu, "
                + fieldLength + "dlu", // columns
                "");
        builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();
    }

    /**
     * Adds a new row to the form. The label is placed to the left of the
     * component. The component's size is determined by the
     * {@link #FormBuilder(int)}parameter.
     * 
     * @param label The label for the component. Must not be null.
     * @param comp The component to be added. Must not be null.
     */
    public void append(final String label, final Component comp)
    {
        assert label != null : "Parameter label must not be null";
        assert comp != null : "Parameter comp must not be null";

        builder.append(label, comp);
    }

    /**
     * Sets the buttons for the button bar at the end of the form.
     * 
     * @param okButton Button for approving the entries. Must not be null.
     * @param cancelButton Buton for discarding the entries. Must not be null.
     */
    public void setButtons(final JButton okButton, final JButton cancelButton)
    {
        assert okButton != null : "Parameter okButton must not be null";
        assert cancelButton != null : "Parameter okButton must not be null";

        this.okButton = okButton;
        this.cancelButton = cancelButton;
    }

    /**
     * Builds a standards conforming JPanel containing all the added components.
     * 
     * @return A fully initialized JPanel.
     */
    public JPanel getPanel()
    {
        if (okButton != null && cancelButton != null)
        {
            builder.appendSeparator();
            builder.append(ButtonBarFactory.buildOKCancelBar(okButton,
                                                             cancelButton), 3);
        }
        return builder.getPanel();
    }
}