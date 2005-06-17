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
package net.sf.housekeeper.swing.binding;

import java.util.Date;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.housekeeper.swing.util.DisableableJDateChooser;

import org.springframework.binding.form.FormModel;
import org.springframework.richclient.form.binding.Binding;
import org.springframework.richclient.form.binding.support.AbstractBinder;
import org.springframework.richclient.form.binding.support.CustomBinding;

/**
 * Uses a {@link net.sf.housekeeper.swing.util.DisableableJDateChooser}for
 * binding {@link java.util.Date}s.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CustomDatePickerBinder extends AbstractBinder
{

    public CustomDatePickerBinder()
    {
        super(Date.class);
    }

    protected JComponent createControl(Map context)
    {
        return new DisableableJDateChooser();
    }

    protected Binding doBind(JComponent control, FormModel formModel,
                             String formPropertyPath, Map context)
    {
        final DisableableJDateChooser datePicker = (DisableableJDateChooser) control;
        return new CustomBinding(formModel, formPropertyPath, Date.class) {

            protected JComponent doBindControl()
            {
                datePicker.setDate((Date) getValue());
                datePicker.addChangeListener(new ChangeListener() {

                    public void stateChanged(ChangeEvent e)
                    {
                        controlValueChanged(datePicker.getDate());
                    }
                });
                return datePicker;
            }

            protected void readOnlyChanged()
            {
                datePicker.setEnabled(isEnabled() && !isReadOnly());
            }

            protected void enabledChanged()
            {
                datePicker.setEnabled(isEnabled() && !isReadOnly());
            }

            protected void valueModelChanged(Object newValue)
            {
                datePicker.setDate((Date) newValue);
            }
        };
    }
}
