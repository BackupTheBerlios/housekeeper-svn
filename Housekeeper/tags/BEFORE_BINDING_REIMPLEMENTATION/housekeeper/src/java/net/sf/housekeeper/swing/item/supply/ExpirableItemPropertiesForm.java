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

package net.sf.housekeeper.swing.item.supply;

import javax.swing.JComponent;

import net.sf.housekeeper.domain.CategoryManager;
import net.sf.housekeeper.domain.ExpirableItem;
import net.sf.housekeeper.swing.category.CategoryChooserEditor;

import org.springframework.richclient.form.builder.TableFormBuilder;
import org.springframework.richclient.forms.AbstractForm;

/**
 * A Form for editing the properties of an
 * {@link net.sf.housekeeper.domain.ExpirableItem}object.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ExpirableItemPropertiesForm extends AbstractForm
{

    /**
     * Creates a new Form.
     * 
     * @param object The item to create the form for. Must not be null.
     */
    public ExpirableItemPropertiesForm(final ExpirableItem object)
    {
        super(object);
        setId("expirableItemPropertiesForm");

        final CategoryManager catMan = (CategoryManager) getApplicationContext()
                .getBean("categoryManager");

        getFormModel().registerCustomEditor(
                                            "category",
                                            new CategoryChooserEditor(catMan
                                                    .getTopLevelCategories()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.forms.AbstractForm#createFormControl()
     */
    protected JComponent createFormControl()
    {
        TableFormBuilder formBuilder = new TableFormBuilder(getFormModel());
        formBuilder.add("name");
        formBuilder.row();
        formBuilder.add("description");
        formBuilder.row();
        formBuilder.add("expiry");
        formBuilder.row();
        formBuilder.add("category");
        return formBuilder.getForm();
    }

}
