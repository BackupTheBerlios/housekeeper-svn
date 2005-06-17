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
 * http://housekeeper.berlios.de
 */

package net.sf.housekeeper.swing.util;

import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.toedter.calendar.JDateChooser;

/**
 * A date chooser which supports null dates. By default, a date is presented in
 * the short format of the current locale.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class DisableableJDateChooser extends JPanel
{

    private final JDateChooser chooser   = new JDateChooser();

    private final JCheckBox    enableBox = new JCheckBox();

    /**
     * Creates a new instance.
     */
    public DisableableJDateChooser()
    {
        enableBox.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e)
            {
                enableDate(enableBox.isSelected());
            }
        });
        enableBox.setSelected(false);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(chooser);
        add(Box.createHorizontalStrut(5));
        add(enableBox);

        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.SHORT);
        final String formatPattern = dateFormat.toPattern();
        chooser.setDateFormatString(formatPattern);
    }

    /**
     * Sets the date. If the date is null, the date chooser is disabled and the
     * checkbox gets unselected.
     * 
     * @param date The date to set.
     */
    public void setDate(Date date)
    {
        final boolean isNullDate = date == null;

        enableDate(!isNullDate);

        if (isNullDate)
        {
            date = new Date();
        }
        chooser.setDate(date);
    }

    /**
     * Gets the date from the chooser. If the checkbox is unselected, "null"
     * returned instead.
     * 
     * @return The chosen date or null.
     */
    public Date getDate()
    {
        if (chooser.isEnabled())
        {
            return chooser.getDate();
        }

        return null;
    }

    /**
     * Sets the format for the date chooser.
     * 
     * @param format
     * @see JDateChooser#setDateFormatString(java.lang.String)
     */
    public void setDateFormatString(final String format)
    {
        chooser.setDateFormatString(format);
    }

    /**
     * Adds a listener which gets notified if the chosen value changes.
     * 
     * @param listener The listener to add
     */
    public void addChangeListener(final ChangeListener listener)
    {
        chooser.getSpinner().addChangeListener(listener);
        enableBox.addChangeListener(listener);
    }

    /**
     * Allows or disallows the setting of an expiry date.
     * 
     * @param enabled If true setting of an expiry date is allowed, if false it
     *            is not.
     */
    private void enableDate(final boolean enabled)
    {
        enableBox.setSelected(enabled);
        chooser.setEnabled(enabled);
        final Component[] comps = chooser.getComponents();
        for (int i = 0; i < comps.length; i++)
        {
            comps[i].setEnabled(enabled);
        }
    }

}