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

package net.sf.housekeeper.swing.category;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.CategoryManager;

import org.springframework.binding.form.FormModel;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.forms.SwingFormModel;


/**
 * Shows a dialog for adding a {@link net.sf.housekeeper.domain.Category}.
 */
public final class NewCategoryCommandExecutor extends
        AbstractActionCommandExecutor
{
    
    private CategoryManager categoryManager;

    public NewCategoryCommandExecutor()
    {
        setEnabled(true);
    }

    public void execute()
    {
        final Category newCategory = new Category();
        final Category parentCategory = Category.getSelectedCategory();
        newCategory.setParent(parentCategory);

        final FormModel formModel = SwingFormModel
                .createFormModel(newCategory);
        final CategoryPropertyForm form = new CategoryPropertyForm(
                formModel);

        final FormBackedDialogPage dialogPage = new FormBackedDialogPage(form);
        final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
dialogPage) {

            protected void onAboutToShow()
            {
                setEnabled(true);
            }

            protected boolean onFinish()
            {
                formModel.commit();
                categoryManager.add(newCategory);
                return true;
            }
        };
        dialog.showDialog();
    }
    
    
    public void setCategoryManager(CategoryManager categoryManager)
    {
        this.categoryManager = categoryManager;
    }
}
