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

import java.util.Date;

import javax.swing.JSpinner;

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
     * synchronized bidirectionally.
     * 
     * @param valueModel the model that provides the value. Must not be null and
     *            must provide {@link Date}objects.
     * @return A spinner whose value is bound to <code>valueModel</code>.
     */
    public static JSpinner createDateSpinner(final ValueModel valueModel)
    {
        assert valueModel != null : "Parameter valueModel must not be null";
        assert valueModel.getValue().getClass().equals(Date.class) : "valueModel must provide Date objects as values";

        final JSpinner spinner = new JSpinner();
        spinner.setModel(new SpinnerDateModelAdapter(valueModel));
        return spinner;
    }

}