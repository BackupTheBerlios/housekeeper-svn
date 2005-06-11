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

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.sf.housekeeper.domain.Category;

import org.springframework.richclient.tree.BeanTreeCellRenderer;
import org.springframework.util.Assert;

/**
 * Shows a list of {@link net.sf.housekeeper.domain.Category}objects as a tree.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class CategoryTree extends JTree
{
    private final String rootNodeTitle;
    
    private List categories;

    /**
     * Creates an empty tree. The root node has a default label.
     *  
     */
    public CategoryTree()
    {
        this("------");

    }
    
    /**
     * Creates an empty tree.
     * 
     * @param rootNodeTitle The title for the root node.
     */
    public CategoryTree(final String rootNodeTitle)
    {
        this.rootNodeTitle = rootNodeTitle;
        
        getSelectionModel()
                .setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        final BeanTreeCellRenderer renderer = new BeanTreeCellRenderer(
                Category.class, "name");
        renderer.setOpenIcon(null);
        renderer.setClosedIcon(null);
        renderer.setLeafIcon(null);
        setCellRenderer(renderer);

        final boolean showRootNode = rootNodeTitle != null;
        setRootVisible(true);
    }

    /**
     * Adds a category to this tree.
     * 
     * @param category The category to add.
     */
    public void addCategory(Category category)
    {
        final DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) getModel()
                .getRoot();
        if (rootNode.isLeaf())
        {
            refresh();
        } else
        {
            DefaultMutableTreeNode parentNode;
            if (category.isTopLevel())
            {
                parentNode = rootNode;

            } else
            {
                parentNode = findNode(category.getParent());
            }

            final DefaultMutableTreeNode node = createNode(category);
            getCastedModel().insertNodeInto(node, parentNode,
                                            parentNode.getChildCount());
        }
    }

    private DefaultTreeModel getCastedModel()
    {
        return (DefaultTreeModel) getModel();
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
     * Returns if any Category is selected.
     * 
     * @return true if a Category is selected. False if nothing or no Category
     *         is selected.
     */
    public boolean isCategorySelected()
    {
        if (hasSelection())
        {
            if (getSelectedCategory() != null)
            {
                return true;
            }
        }
        return false;
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

    /**
     * Sets the selection in this tree to <code>selectCategory</code>
     * 
     * @param selectCategory The category to select or null to clear the
     *            selection.
     */
    public void setSelectedCategory(Category selectCategory)
    {
        if (selectCategory == null)
        {
            clearSelection();
        }

        DefaultMutableTreeNode nodeToSelect = findNode(selectCategory);

        if (nodeToSelect != null)
        {
            final TreePath path = new TreePath(nodeToSelect.getPath());
            setSelectionPath(path);
        }
    }

    private DefaultMutableTreeNode findNode(Category selectCategory)
    {
        final DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) getModel()
                .getRoot();
        final Enumeration children = rootNode.breadthFirstEnumeration();

        DefaultMutableTreeNode nodeToFind = null;
        while (children.hasMoreElements())
        {
            DefaultMutableTreeNode element = (DefaultMutableTreeNode) children
                    .nextElement();
            if (element.getUserObject() == selectCategory)
            {
                nodeToFind = element;
                break;
            }
        }
        return nodeToFind;
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

    private boolean hasSelection()
    {
        return getSelectionCount() > 0;
    }

    private void refresh()
    {
        final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
                rootNodeTitle);

        final Iterator iter = categories.iterator();
        while (iter.hasNext())
        {
            Category element = (Category) iter.next();
            final DefaultMutableTreeNode node = createNode(element);
            rootNode.add(node);
        }

        final DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

        setModel(treeModel);
        setSelectionRow(0);
    }

    /**
     * @param category
     */
    public void removeCategory(Category category)
    {
        final DefaultMutableTreeNode node = findNode(category);
        getCastedModel().removeNodeFromParent(node);
    }
}
