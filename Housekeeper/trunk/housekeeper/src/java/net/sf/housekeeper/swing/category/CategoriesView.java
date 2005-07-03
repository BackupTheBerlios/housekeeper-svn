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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.CategoryManager;
import net.sf.housekeeper.event.HousekeeperEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.richclient.application.PageComponentContext;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.command.support.GlobalCommandIds;
import org.springframework.richclient.dialog.FormBackedDialogPage;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.tree.TreeUtils;
import org.springframework.richclient.util.PopupMenuMouseListener;

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

    private CategoryTree tree;

    private final PropertyCommandExecutor propertyCommand = new PropertyCommandExecutor();

    private final DeleteCommandExecutor deleteCommand = new DeleteCommandExecutor();

    /**
     * Sets the category manager to be used.
     * 
     * @param manager
     *            the category manager to be used.
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
        final String rootNodeTitle = getMessage("category.allCategories");
        tree = new CategoryTree(rootNodeTitle);

        tree.addTreeSelectionListener(new SelectionHandler());
        tree.addMouseListener(new DoubleClickListener());
        tree.addMouseListener(new PopupTriggerListener(createContextMenu()));
        refresh();
        TreeUtils.expandAll(tree, true);

        final JScrollPane scrollPane = new JScrollPane(tree);
        return scrollPane;
    }

    private void refresh()
    {
        LOG.debug("Refreshing view");

        final Collection cats = categoryManager.getTopLevelCategories();
        tree.setCategories(cats);
    }

    private void publishSelectionEvent(Category cat)
    {
        final Object source;
        if (cat == null)
        {
            source = Category.NULL_OBJECT;
        } else
        {
            source = cat;
        }
        getApplicationContext().publishEvent(
                new HousekeeperEvent(HousekeeperEvent.SELECTED, source));
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

            if (le.objectIs(Category.class))
            {
                if (le.isEventType(HousekeeperEvent.ADDED))
                {
                    tree.addCategory((Category) e.getSource());
                    tree.setSelectedCategory((Category) e.getSource());
                } else if (le.isEventType(HousekeeperEvent.REMOVED))
                {
                    tree.removeCategory((Category) e.getSource());
                } else if (le.isEventType(HousekeeperEvent.MODIFIED))
                {
                    tree.removeCategory((Category) e.getSource());
                    tree.addCategory((Category) e.getSource());
                    tree.setSelectedCategory((Category) e.getSource());
                }
            } else if (le.isEventType(HousekeeperEvent.DATA_REPLACED))
            {
                refresh();
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.support.AbstractView#registerLocalCommandExecutors(org.springframework.richclient.application.PageComponentContext)
     */
    protected void registerLocalCommandExecutors(PageComponentContext context)
    {
        context.register(GlobalCommandIds.PROPERTIES, propertyCommand);
        context.register(GlobalCommandIds.DELETE, deleteCommand);
    }

    /**
     * Enables and disables Actions (and thus the buttons) depending on the
     * current selection state.
     */
    private void updateActionEnablement()
    {
        boolean hasSelection = tree.isCategorySelected();
        propertyCommand.setEnabled(hasSelection);
        deleteCommand.setEnabled(hasSelection);
    }

    private JPopupMenu createContextMenu()
    {
        CommandGroup convCommandGroup = getWindowCommandManager()
                .createCommandGroup(
                        "categoryPopupCommandGroup",
                        new Object[] { "newCategoryCommand",
                                GlobalCommandIds.PROPERTIES,
                                GlobalCommandIds.DELETE });
        return convCommandGroup.createPopupMenu();
    }

    private final class SelectionHandler implements TreeSelectionListener
    {

        public void valueChanged(TreeSelectionEvent e)
        {
            Category cat = tree.getSelectedCategory();
            Category.setSelectedCategory(cat);
            publishSelectionEvent(cat);
            updateActionEnablement();
        }
    }

    /**
     * Shows a dialog for adding a new item.
     */
    private class PropertyCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final Category category = tree.getSelectedCategory();
            final Category oldParent = category.getParent();

            final CategoryPropertiesForm form = new CategoryPropertiesForm(
                    category, categoryManager.getAllCategoriesInclusiveNullExcept(category));

            final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
                    new FormBackedDialogPage(form))
            {

                protected void onAboutToShow()
                {
                    setEnabled(true);
                }

                protected boolean onFinish()
                {
                    form.commit();
                    categoryManager.update(category, oldParent);
                    return true;
                }
            };
            dialog.showDialog();
        }
    }

    private class DeleteCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final Category selectedItem = tree.getSelectedCategory();
            categoryManager.remove(selectedItem);
        }
    }

    private final class DoubleClickListener extends MouseAdapter
    {

        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2)
            {
                if (tree.getSelectedCategory().isLeaf())
                {
                    getWindowCommandManager().getActionCommand(
                            "propertiesCommand").execute();
                }
            }
        }
    }

    private class PopupTriggerListener extends PopupMenuMouseListener
    {

        private PopupTriggerListener(final JPopupMenu menu)
        {
            super(menu);
        }

        /**
         * Selects the row beneath the cursor before showing the popup menu
         */
        protected boolean onAboutToShow(MouseEvent e)
        {
            final int row = tree.getRowForLocation((int) e.getPoint().getX(),
                    (int) e.getPoint().getY());
            tree.setSelectionRow(row);
            return super.onAboutToShow(e);
        }
    }
}
