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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import org.apache.commons.lang.time.DateUtils;

import com.jgoodies.binding.value.ValueModel;

/**
 * A Factory for creating components which are bound to a
 * {@link com.jgoodies.binding.value.ValueModel}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class BoundComponentFactory
{

    /**
     * Private to prevent instanciation of this utility class.
     *  
     */
    private BoundComponentFactory()
    {

    }

    /**
     * Creates a JSpinner whose value is bound to the given ValueModel. This
     * means, that the value of the spinner and the value of the ValueModel are
     * synchronized bidirectionally. Additionally, the spinner uses
     * the current locale's short format for displaying a date.
     * 
     * @param valueModel the model that provides the value. Must not be null and
     *            must provide {@link Date}objects.
     * @return A spinner whose value is bound to <code>valueModel</code>.
     */
    public static JSpinner createDateSpinner(final ValueModel valueModel)
    {
        final SpinnerDateModel model = new SpinnerDateModelAdapter(valueModel);
        //Need to truncate the current date for correct spinner operation
        model.setStart(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
        model.setCalendarField(Calendar.DAY_OF_MONTH);
        
        final JSpinner spinner = new JSpinner(model);
        
        //Set the spinner's editor to use the current locale's short date
        // format
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.SHORT);
        final String formatPattern = dateFormat.toPattern();
        spinner.setEditor(new JSpinner.DateEditor(spinner,
                formatPattern));
        
        return spinner;
    }

}