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

package net.sf.housekeeper.swing.item;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.housekeeper.domain.Category;
import net.sf.housekeeper.domain.ExpirableItem;
import net.sf.housekeeper.domain.Item;
import net.sf.housekeeper.domain.ItemManager;
import net.sf.housekeeper.event.HousekeeperEvent;
import net.sf.housekeeper.swing.util.SortableTable;

import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.richclient.application.PageComponentContext;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.command.CommandGroup;
import org.springframework.richclient.command.support.AbstractActionCommandExecutor;
import org.springframework.richclient.command.support.GlobalCommandIds;
import org.springframework.richclient.dialog.TitledPageApplicationDialog;
import org.springframework.richclient.form.Form;
import org.springframework.richclient.util.PopupMenuMouseListener;
import org.springframework.util.Assert;

/**
 * A customizable view for any type of {@link net.sf.housekeeper.domain.Item}s.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class ItemsView extends AbstractView implements ApplicationListener
{

    private Category category;

    private List customPopupMenuEntries;

    private final DeleteCommandExecutor deleteExecutor = new DeleteCommandExecutor();

    private final DuplicateCommandExecutor duplicateExecutor = new DuplicateCommandExecutor();

    private final EditCommandExecutor editExecutor = new EditCommandExecutor();

    private Class itemClass;

    private ItemManager itemManager;

    private SortableTable table;

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
            this);

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

            if (le.objectIs(itemClass))
            {
                setCategory(category);
            } else if (le.objectIs(Category.class)
                    && le.isEventType(HousekeeperEvent.SELECTED))
            {
                final Category cat;
                if (le.getSource() != Category.NULL_OBJECT)
                {
                    cat = (Category) le.getSource();
                } else
                {
                    cat = null;
                }

                SwingUtilities.invokeLater(new Runnable()
                {

                    public void run()
                    {
                        setCategory(cat);
                    }
                });
            } else if (le.isEventType(HousekeeperEvent.DATA_REPLACED))
            {
                SwingUtilities.invokeLater(new Runnable()
                {

                    public void run()
                    {
                        refresh();
                    }
                });
            }
        }
    }

    /**
     * @param customPopupMenuEntries
     *            The customPopupMenuEntries to set.
     */
    public void setCustomPopupMenuEntries(List customPopupMenuEntries)
    {
        this.customPopupMenuEntries = customPopupMenuEntries;
    }

    /**
     * @param itemClass
     *            The itemClass to set.
     */
    public void setItemClass(String itemClass)
    {
        try
        {
            this.itemClass = Class.forName(itemClass);
            LogFactory.getLog(getClass()).debug(
                    "Listening for events related to class: "
                            + this.itemClass.getName());
        } catch (ClassNotFoundException e)
        {
            LogFactory.getLog(getClass()).error(
                    "Class to filter after set to null", e);
        }
    }

    /**
     * Sets the manager which holds the data to display.
     * 
     * @param manager
     */
    public void setItemManager(final ItemManager manager)
    {
        this.itemManager = manager;
    }

    /**
     * Sets the table this view shall use.
     * 
     * @param table !=
     *            null
     */
    public void setTable(SortableTable table)
    {
        this.table = table;
    }

    /**
     * Returns the selected item in this view.
     * 
     * @return The selected item or null if none is selected.
     */
    public Item getSelectedItem()
    {
        return (Item) table.getSelected();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.factory.AbstractControlFactory#createControl()
     */
    protected JComponent createControl()
    {
        Assert.notNull(table);

        configureTable(table);
        refresh();

        final JPanel panel = new JPanel();
        // Without that, the tables won't grow and shrink with the window's
        // size.
        panel.setLayout(new GridLayout());
        panel.add(new JScrollPane(table));

        return panel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.support.AbstractView#registerLocalCommandExecutors(org.springframework.richclient.application.PageComponentContext)
     */
    protected void registerLocalCommandExecutors(PageComponentContext context)
    {
        context.register(GlobalCommandIds.DELETE, deleteExecutor);
        context.register(GlobalCommandIds.PROPERTIES, editExecutor);

        // TODO Quick Fix for #004362
        if (itemClass.equals(ExpirableItem.class))
        {
            context.register("duplicateCommand", duplicateExecutor);
        }

    }

    private void configureTable(JTable table)
    {
        table.getSelectionModel().addListSelectionListener(
                new TableSelectionListener());
        table.getSelectionModel().setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new PopupTriggerListener(createContextMenu()));
        table.addMouseListener(new DoubleClickListener());
    }

    private JPopupMenu createContextMenu()
    {
        final List commands = new ArrayList();
        commands.addAll(customPopupMenuEntries);
        // TODO Quick Fix for #004362
        if (itemClass.equals(ExpirableItem.class))
        {
            commands.add("duplicateCommand");
        }

        commands.add(GlobalCommandIds.PROPERTIES);
        commands.add(GlobalCommandIds.DELETE);

        CommandGroup convCommandGroup = getWindowCommandManager()
                .createCommandGroup("tablePopupCommandGroup",
                        commands.toArray());
        return convCommandGroup.createPopupMenu();
    }

    private void refresh()
    {
        final List items = itemManager.getItemsForCategory(category);
        table.replaceAll(items);
    }

    private void setCategory(Category category)
    {
        this.category = category;
        refresh();
    }

    /**
     * Enables and disables Actions (and thus the buttons) depending on the
     * current selection state.
     */
    private void updateActionEnablement()
    {
        boolean hasSelection = table.hasSelection();
        duplicateExecutor.setEnabled(hasSelection);
        editExecutor.setEnabled(hasSelection);
        deleteExecutor.setEnabled(hasSelection);
    }

    private class DeleteCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final Item selectedItem = (Item) table.getSelected();
            itemManager.remove(selectedItem);
        }
    }

    private final class DoubleClickListener extends MouseAdapter
    {

        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2)
            {
                getWindowCommandManager().getActionCommand(
                        GlobalCommandIds.PROPERTIES).execute();
            }
        }
    }

    /**
     * Duplicates the selected item.
     */
    private class DuplicateCommandExecutor extends
            AbstractActionCommandExecutor
    {

        public void execute()
        {
            final ExpirableItem selectedItem = (ExpirableItem) getSelectedItem();
            itemManager.duplicate(selectedItem);
        }
    }

    /**
     * Shows a dialog for modifying the currently selected item and updates it.
     */
    private class EditCommandExecutor extends AbstractActionCommandExecutor
    {

        public void execute()
        {
            final Item foodObject = (Item) table.getSelected();
            final Form form = Factory.createForm(foodObject);

            final TitledPageApplicationDialog dialog = new TitledPageApplicationDialog(
                    form, getWindowControl())
            {

                protected void onAboutToShow()
                {
                    setEnabled(true);
                }

                protected boolean onFinish()
                {
                    form.commit();
                    itemManager.update(foodObject);
                    return true;
                }
            };
            dialog.showDialog();
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
            final int row = table.rowAtPoint(e.getPoint());
            table.getSelectionModel().setSelectionInterval(row, row);
            return super.onAboutToShow(e);
        }
    }

    private final class TableSelectionListener implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            if (!e.getValueIsAdjusting())
            {
                updateActionEnablement();
                propertyChangeSupport.firePropertyChange("selection", null,
                        table.getSelected());
            }

        }
    }
}
