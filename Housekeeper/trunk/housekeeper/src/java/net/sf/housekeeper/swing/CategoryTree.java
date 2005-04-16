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
import java.util.List;

import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.sf.housekeeper.domain.Category;

import org.springframework.richclient.tree.BeanTreeCellRenderer;
import org.springframework.util.Assert;


/**
 * Shows a list of {@link net.sf.housekeeper.domain.Category} objects as a
 * tree.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoryTree extends JTree
{
    private List categories;
    
    /**
     * Creates an empty tree.
     *
     */
    public CategoryTree()
    {
        getSelectionModel()
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        final BeanTreeCellRenderer renderer = new BeanTreeCellRenderer(
                Category.class, "name");
        renderer.setOpenIcon(null);
        renderer.setClosedIcon(null);
        renderer.setLeafIcon(null);
        setCellRenderer(renderer);
        setRootVisible(true);
    }
    
    /**
     * Returns the selected {@link Category}.
     * 
     * @return The selected category or null, if none is selected.
     */
    public Category getSelectedCategory()
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();
        
        if (node == null)
            return null;

        Object nodeInfo = node.getUserObject();
        return nodeInfo instanceof Category ? (Category) nodeInfo : null;
    }
    
    /**
     * Show another list of objects in this tree.
     * 
     * @param categories != null
     */
    public void setCategories(final List categories)
    {
        Assert.notNull(categories);
        
        this.categories = categories;
        refresh();
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
    
    private void refresh()
    {
        final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("All Categories");
        
        final Iterator iter = categories.iterator();
        while (iter.hasNext())
        {
            Category element = (Category) iter.next();
            final DefaultMutableTreeNode node = createNode(element);
            rootNode.add(node);
        }

        final DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        
        final Runnable r = new Runnable() {

            public void run()
            {
                setModel(treeModel);
                setSelectionRow(0);
            }
        };
        SwingUtilities.invokeLater(r);

    }
}
