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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.Assert;

/**
 * Uses a {@link net.sf.housekeeper.swing.util.DisableableJDateChooser}for
 * editing dates.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CalendarDateChooserEditor extends CustomDateEditor
{

    private DisableableJDateChooser dateChooser = new DisableableJDateChooser();

    /**
     * Creates a new editor with a specific date format.
     * 
     * @param format The format for the date.
     */
    public CalendarDateChooserEditor(String format)
    {
        super(new SimpleDateFormat(format), true);
        dateChooser.setDateFormatString(format);
        dateChooser.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e)
            {
                firePropertyChange();
            }
        });
    }

    /**
     * Returns true
     */
    public boolean supportsCustomEditor()
    {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditor#getCustomEditor()
     */
    public Component getCustomEditor()
    {
        return dateChooser;
    }

    /**
     * Sets the value for the chooser.
     * 
     * @param value A {@link Date}or null.
     */
    public void setValue(Object value)
    {
        if (value != null)
        {
            Assert.isInstanceOf(Date.class, value,
                                "Parameter must be a Date or null");
        }

        super.setValue(value);
        dateChooser.setDate((Date) value);
    }

    /**
     * Returns the date from the editor.
     * 
     * @return A date or null.
     */
    public Object getValue()
    {
        return dateChooser.getDate();
    }
}