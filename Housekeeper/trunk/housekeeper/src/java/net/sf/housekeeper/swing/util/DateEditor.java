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

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

/**
 * An editor for a {@link javax.swing.JSpinner} which formats dates in a locale
 * dependent manner.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class DateEditor extends JSpinner.DateEditor
{

    /**
     * Creates a DateEditor using the current locale.
     * 
     * @param spinner The spinner for this editor.
     */
    public DateEditor(final JSpinner spinner)
    {
        super(spinner);

        final SpinnerDateModel model = (SpinnerDateModel) spinner.getModel();
        final DateFormatter formatter = new DateFormatter();
        final DefaultFormatterFactory factory = new DefaultFormatterFactory(
                formatter);
        final JFormattedTextField ftf = getTextField();
        ftf.setEditable(true);
        ftf.setFormatterFactory(factory);

        try
        {
            String maxString = formatter.valueToString(model.getStart());
            String minString = formatter.valueToString(model.getEnd());
            ftf.setColumns(Math.max(maxString.length(), minString.length()));
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
    }
}