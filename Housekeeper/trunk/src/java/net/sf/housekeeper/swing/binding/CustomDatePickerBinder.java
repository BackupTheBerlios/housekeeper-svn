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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.JComponent;

import org.jdesktop.swingx.JXDatePicker;
import org.springframework.binding.form.FormModel;
import org.springframework.richclient.form.binding.Binding;
import org.springframework.richclient.form.binding.support.AbstractBinder;
import org.springframework.richclient.form.binding.support.CustomBinding;

public class CustomDatePickerBinder extends AbstractBinder
{

    public CustomDatePickerBinder()
    {
        super(Date.class);
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.form.binding.support.AbstractBinder#createControl(java.util.Map)
     */
    protected JComponent createControl(Map context)
    {
        final JXDatePicker picker =  new JXDatePicker();
        final DateFormat[] formats = { DateFormat.getDateInstance() };
        picker.setFormats(formats);
        return picker;
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.form.binding.support.AbstractBinder#doBind(javax.swing.JComponent, org.springframework.binding.form.FormModel, java.lang.String, java.util.Map)
     */
    protected Binding doBind(JComponent control, FormModel formModel,
            String formPropertyPath, Map context)
    {
        final JXDatePicker datePicker = (JXDatePicker) control;
        return new CustomBinding(formModel, formPropertyPath, Date.class)
        {

            protected JComponent doBindControl()
            {
                datePicker.setDate((Date) getValue());
                datePicker.getEditor().addPropertyChangeListener("value",
                        new PropertyChangeListener()
                        {

                            public void propertyChange(PropertyChangeEvent evt)
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