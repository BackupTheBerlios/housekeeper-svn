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

package net.sf.housekeeper.swing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JComponent;

import net.sf.housekeeper.swing.util.CalendarDateChooserEditor;

import org.springframework.binding.form.FormModel;
import org.springframework.richclient.forms.AbstractForm;
import org.springframework.richclient.forms.BeanFormBuilder;
import org.springframework.richclient.forms.JGoodiesBeanFormBuilder;

import com.jgoodies.forms.layout.FormLayout;

/**
 * A Form for editing the properties of an {@link net.sf.housekeeper.domain.ExpiringItem}
 * object.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ExpiringItemPropertiesForm extends AbstractForm
{

    /**
     * Creates a new Form using the given model.
     * 
     * @param formModel The model to use. Must not be null.
     */
    public ExpiringItemPropertiesForm(final FormModel formModel)
    {
        super(formModel, "foodPropertiesForm");
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.SHORT);
        final String formatPattern = dateFormat.toPattern();

        getFormModel().registerCustomEditor(
                                            "expiry",
                                            new CalendarDateChooserEditor(
                                                    formatPattern));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.forms.AbstractForm#createFormControl()
     */
    protected JComponent createFormControl()
    {
        final FormLayout layout = new FormLayout(
                "right:pref, 3dlu, fill:default:grow");
        BeanFormBuilder formBuilder = new JGoodiesBeanFormBuilder(
                getFormModel(), layout);
        formBuilder.add("name");
        formBuilder.add("description");
        formBuilder.add("expiry");
        return formBuilder.getForm();
    }

}