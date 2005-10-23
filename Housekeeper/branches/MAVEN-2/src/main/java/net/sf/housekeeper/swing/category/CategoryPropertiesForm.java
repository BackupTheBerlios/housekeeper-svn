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

package net.sf.housekeeper.swing.category;

import java.util.List;

import javax.swing.JComponent;

import net.sf.housekeeper.domain.Category;

import org.springframework.richclient.form.AbstractForm;
import org.springframework.richclient.form.binding.swing.SwingBindingFactory;
import org.springframework.richclient.form.builder.TableFormBuilder;

/**
 * A form for editing the properties of a
 * {@link net.sf.housekeeper.domain.Category}.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoryPropertiesForm extends AbstractForm
{

    private final List possibleParents;

    /**
     * Creates a new Form.
     * 
     * @param object The model to use. Must not be null.
     */
    public CategoryPropertiesForm(Category object, List possibleParents)
    {
        super(object);
        setId("categoryPropertiesForm");

        this.possibleParents = possibleParents;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.forms.AbstractForm#createFormControl()
     */
    protected JComponent createFormControl()
    {
        TableFormBuilder formBuilder = new TableFormBuilder(getBindingFactory());
        formBuilder.add("name");
        formBuilder.row();
        SwingBindingFactory bindingFactory = (SwingBindingFactory) getBindingFactory();
        formBuilder.add(bindingFactory.createBoundComboBox("parent",
                possibleParents));
        return formBuilder.getForm();
    }

}
