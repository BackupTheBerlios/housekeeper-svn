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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.SpinnerDateModel;

import com.jgoodies.binding.value.ValueModel;

/**
 * This adapter can be used as a {@link javax.swing.SpinnerDateModel}for a
 * {@link javax.swing.JSpinner}. This model's current date is synchronized
 * bidrectionally with a {@link com.jgoodies.binding.value.ValueModel}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class SpinnerDateModelAdapter extends SpinnerDateModel implements
        PropertyChangeListener
{

    /**
     * The subject to be used for value synchronization.
     */
    private final ValueModel subject;

    /**
     * Creates a new adapter which synchronizes with <code>subject</code>.
     * The initial date is set to the subject's value. All other settings use
     * the defaults from {@link SpinnerDateModel#SpinnerDateModel()}.
     * 
     * @param subject The subject to synchronize the date with. Must not be null
     *            and must provide {@link Date}objects as values.
     */
    public SpinnerDateModelAdapter(final ValueModel subject)
    {
        super();

        this.subject = subject;
        setValue(subject.getValue());
        subject.addValueChangeListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.SpinnerModel#setValue(java.lang.Object)
     */
    public void setValue(Object value)
    {
        subject.setValue(value);

        //A SpinnerDateModel must not provide a null value. Because of this
        //setting to null is ignored.
        if (value != null)
        {
            super.setValue(value);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    public void propertyChange(PropertyChangeEvent evt)
    {
        final Object eventValue = evt.getNewValue();
        
        //A SpinnerDateModel must not provide a null value. Because of this
        //setting to null is ignored.
        if (eventValue != null)
        {
            super.setValue(eventValue);
        }
    }
}