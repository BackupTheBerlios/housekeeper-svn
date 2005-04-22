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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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
import org.springframework.binding.form.FormModel;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.richclient.application.PageComponentContext;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.command.support.GlobalCommandIds;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.forms.SwingFormModel;
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

    private static final Log              LOG             = LogFactory
                                                                  .getLog(CategoriesView.class);

    private CategoryManager               categoryManager;

    private CategoryTree                  tree;

    private final NewCommandExecutor      newCommand      = new NewCommandExecutor();

    private final PropertyCommandExecutor propertyCommand = new PropertyCommandExecutor();

    private final DeleteCommandExecutor   deleteCommand   = new DeleteCommandExecutor();

    /**
     * Creates a new instance.
     *  
     */
    public CategoriesView()
    {
        newCommand.setEnabled(true);
    }

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
        tree = new CategoryTree();

        tree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e)
            {
                Category cat = tree.getSelectedCategory();
                publishSelectionEvent(cat);
                updateActionEnablement();
            }
        });
        tree.addMouseListener(new DoubleClickListener());
        tree.addMouseListener(new PopupTriggerListener(createContextMenu()));
        refresh();

        final JScrollPane scrollPane = new JScrollPane(tree);
        return scrollPane;
    }

    private void refresh()
    {
        LOG.debug("Refreshing view");

        final List cats = categoryManager.getTopLevelCategories();
        tree.setCategories(cats);
    }

    private void publishSelectionEvent(Category cat)
    {
        final Object source;
        if (cat == null)
        {
            source = new Object();
        } else
        {
            source = cat;
        }
        getApplicationContext().publishEvent(
                                             new HousekeeperEvent(
                                                     HousekeeperEvent.SELECTED,
                                                     source));
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
            if (le.isEventType(HousekeeperEvent.ADDED))
            {
                LOG.debug("Received event: " + le.toString());
                tree.addCategory((Category) e.getSource());
            } else if (le.isEventType(HousekeeperEvent.REMOVED))
            {
                LOG.debug("Received event: " + le.toString());
                tree.removeCategory((Category) e.getSource());
            } else if (le.isEventType(HousekeeperEvent.DATA_REPLACED))
            {
                LOG.debug("Received event: " + le.toString());
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
        context.register("newCommand", newCommand);
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
                                    new Object[] { "newCommand",
                                            GlobalCommandIds.PROPERTIES,
                                            GlobalCommandIds.DELETE });
        return convCommandGroup.createPopupMenu();
    }

    /**
     * Shows a dialog for adding a new item.
     */
    private class NewCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final Category newCategory = new Category();
            final Category parentCategory = tree.getSelectedCategory();
            newCategory.setParent(parentCategory);

            final FormModel formModel = SwingFormModel
                    .createFormModel(newCategory);
            final CategoryPropertyForm form = new CategoryPropertyForm(
                    formModel);

            final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
                    form, getWindowControl()) {

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
    }

    /**
     * Shows a dialog for adding a new item.
     */
    private class PropertyCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final Category newCategory = tree.getSelectedCategory();

            final FormModel formModel = SwingFormModel
                    .createFormModel(newCategory);
            final CategoryPropertyForm form = new CategoryPropertyForm(
                    formModel);

            final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
                    form, getWindowControl()) {

                protected void onAboutToShow()
                {
                    setEnabled(true);
                }

                protected boolean onFinish()
                {
                    formModel.commit();
                    categoryManager.update(newCategory);
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
                    getWindowCommandManager()
                            .getActionCommand("propertiesCommand").execute();
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
