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

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import net.sf.housekeeper.domain.Category;

import org.springframework.richclient.form.support.AbstractCustomPropertyEditor;


/**
 * @author
 * @version $Revision$, $Date$
 */
public class CategoryChooserEditor extends AbstractCustomPropertyEditor
{

    private final CategoryTree tree;
    
    public CategoryChooserEditor(List categories)
    {
        tree = new CategoryTree();
        tree.setCategories(categories);
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e)
            {
                final Category selectedCategory = tree.getSelectedCategory();
                setValueInternal(selectedCategory);
            }
        });
    }
    
    /* (non-Javadoc)
     * @see org.springframework.richclient.form.support.AbstractCustomPropertyEditor#propertyValueChanged()
     */
    protected void propertyValueChanged()
    {
        final Category value = (Category)getValue();
        tree.setSelectedCategory(value);
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.form.support.AbstractCustomPropertyEditor#createCustomEditor()
     */
    protected JComponent createCustomEditor()
    {
        final JScrollPane scroll = new JScrollPane(tree);
        return scroll;
    }

}
