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
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.sf.housekeeper.HousekeeperEvent;
import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.CategoryManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.tree.BeanTreeCellRenderer;

/**
 * Shows a tree of categories.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoriesView extends AbstractView implements
        ApplicationListener
{
    
    private static final Log LOG = LogFactory.getLog(CategoriesView.class);

    private CategoryManager categoryManager;

    private JTree           tree;

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
        tree = new JTree();

        tree.getSelectionModel()
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        final BeanTreeCellRenderer renderer = new BeanTreeCellRenderer(
                Category.class, "name");
        renderer.setOpenIcon(null);
        renderer.setClosedIcon(null);
        renderer.setLeafIcon(null);
        tree.setCellRenderer(renderer);
        tree.setRootVisible(true);

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

        refresh();
        return tree;
    }

    private void refresh()
    {
        LOG.debug("Refreshing view");
        final Category root = (Category)categoryManager
        .getCategories().get(0);
        final DefaultMutableTreeNode rootNode = createNode(root);
        final DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        tree.setModel(treeModel);
        tree.setSelectionRow(0);
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

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ApplicationEvent e)
    {
        if (e instanceof HousekeeperEvent)
        {
            final HousekeeperEvent le = (HousekeeperEvent) e;
            if (le.getEventType() == HousekeeperEvent.CATEGORIES_MODIFIED)
            {
                LOG.debug("Received CATEGORIES_MODIFIED event");
                SwingUtilities.invokeLater(new Runnable() {

                    public void run()
                    {
                        refresh();
                    }
                });
            }
        }

    }

}