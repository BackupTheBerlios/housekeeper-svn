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

import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.CategoryManager;

import org.springframework.binding.form.FormModel;
import org.springframework.binding.value.support.ValueHolder;
import org.springframework.richclient.form.binding.Binding;
import org.springframework.richclient.form.binding.support.AbstractBinder;
import org.springframework.richclient.form.binding.swing.ComboBoxBinding;
import org.springframework.util.Assert;

/**
 * A binder for {@link net.sf.housekeeper.domain.Category}objects.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoryBinder extends AbstractBinder
{

    private CategoryManager catMan;

    /**
     * Creates a new binder.
     */
    public CategoryBinder()
    {
        super(Category.class);
    }

    /**
     * Sets the manager to get the available categories from.
     * 
     * @param catMan !=
     *            null
     */
    public void setCategoryManager(CategoryManager catMan)
    {
        this.catMan = catMan;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.form.binding.support.AbstractBinder#createControl(java.util.Map)
     */
    protected JComponent createControl(Map context)
    {
        return getComponentFactory().createComboBox();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.form.binding.support.AbstractBinder#doBind(javax.swing.JComponent,
     *      org.springframework.binding.form.FormModel, java.lang.String,
     *      java.util.Map)
     */
    protected Binding doBind(JComponent control, FormModel formModel,
            String formPropertyPath, Map context)
    {
        Assert.isTrue(control instanceof JComboBox, formPropertyPath);
        Assert.notNull(catMan);

        ComboBoxBinding binding = new ComboBoxBinding((JComboBox) control,
                formModel, formPropertyPath);

        final List categories = catMan.getAllCategories();

        binding.setSelectableItemsHolder(new ValueHolder(categories));
        return binding;
    }

}
