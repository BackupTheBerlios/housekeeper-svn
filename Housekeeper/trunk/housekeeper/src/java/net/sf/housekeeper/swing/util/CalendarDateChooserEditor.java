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

import com.toedter.calendar.JDateChooser;


/**
 * @author
 * @version $Revision$, $Date$
 */
public class CalendarDateChooserEditor extends CustomDateEditor
{
    private SimpleDateFormat dateFormat;
    private JDateChooser dateChooser = new JDateChooser();

    public CalendarDateChooserEditor(String format) {
        super(new SimpleDateFormat(format), true);
        dateChooser.setDateFormatString(format);
        dateChooser.getSpinner().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                firePropertyChange();
            }
        });
    }

    public boolean supportsCustomEditor() {
        return true;
    }

    public Component getCustomEditor() {
        return dateChooser;
    }

    public void setValue(Object value) {
        super.setValue(value);
        dateChooser.setDate((Date) value);
    }
    
    public Object getValue() {
        return dateChooser.getDate();
    }
}
