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

import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import net.sf.housekeeper.HousekeeperEvent;
import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.CategoryManager;

import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.tree.BeanTreeCellRenderer;

/**
 * @author
 * @version $Revision$, $Date$
 */
public final class CategoriesView extends AbstractView
{

    private CategoryManager categoryManager;

    /**
     * Sets the category manager to be used.
     * 
     * @param manager the category manager to be used.
     */
    public void setCategoryManager(CategoryManager manager)
    {
        this.categoryManager = manager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.support.AbstractView#createControl()
     */
    protected JComponent createControl()
    {
        final DefaultMutableTreeNode rootNode = createNode(categoryManager
                .getRootCategory());

        final JTree tree = new JTree(rootNode);
        tree.getSelectionModel()
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        final BeanTreeCellRenderer renderer = new BeanTreeCellRenderer(
                Category.class, "name");
        renderer.setOpenIcon(null);
        renderer.setClosedIcon(null);
        renderer.setLeafIcon(null);
        tree.setCellRenderer(renderer);

        tree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e)
            {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();

                if (node == null)
                    return;

                Object nodeInfo = node.getUserObject();
                Category cat = (Category) nodeInfo;
                publishSelectionEvent(cat);
            }
        });

        tree.setRootVisible(true);

        tree.setSelectionRow(0);
        return tree;
    }

    private DefaultMutableTreeNode createNode(final Category cat)
    {
        final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(cat);

        final Iterator catIter = cat.getChildrenIterator();
        while (catIter.hasNext())
        {
            Category element = (Category) catIter.next();
            rootNode.add(createNode(element));
        }

        return rootNode;
    }

    private void publishSelectionEvent(Category cat)
    {
        getApplicationContext().publishEvent(
                                             new HousekeeperEvent(
                                                     HousekeeperEvent.SELECTED,
                                                     cat));
    }

}