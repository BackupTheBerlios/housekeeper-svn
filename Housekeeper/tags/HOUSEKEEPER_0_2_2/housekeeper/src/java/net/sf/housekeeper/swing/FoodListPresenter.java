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

import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.housekeeper.domain.Food;
import net.sf.housekeeper.swing.util.EventObjectListener;
import net.sf.housekeeper.util.LocalisationManager;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TextFilterList;

/**
 * Presents a list of {@link Food}objects. It is presented as a table which is
 * sortable. It is also possible to filter after {@link Food}categories.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class FoodListPresenter
{

    private static final String       EXPIRY_HEADER       = LocalisationManager.INSTANCE
                                                                  .getText("domain.food.expiry");

    private static final String       NAME_HEADER         = LocalisationManager.INSTANCE
                                                                  .getText("domain.food.name");

    private static final String       QUANTITY_HEADER     = LocalisationManager.INSTANCE
                                                                  .getText("domain.food.quantity");

    /**
     * The JavaBean properties of Food which shall be used as values for the
     * columns of an entry.
     */
    private static final String[]     ITEM_PROPERTIES     = {
            Food.PROPERTYNAME_NAME, Food.PROPERTYNAME_QUANTITY,
            Food.PROPERTYNAME_EXPIRY                     };

    /**
     * Defines which columns should be editable directly in the table.
     */
    private static final boolean[]    PROPERTY_MODIFYABLE = { false, false,
            false                                        };

    /**
     * The labels for the table headers.
     */
    private static final String[]     TABLE_HEADERS       = { NAME_HEADER,
            QUANTITY_HEADER, EXPIRY_HEADER               };

    private final String              category;

    private final List                selectionListeners;

    private final EventSelectionModel selectionModel;

    private final FoodListView        view;

    /**
     * Creates a new Presenter with a default view.
     * 
     * @param model A list of {@link Food}items.
     * @param category The {@link Food}category this presenter shall filter
     *            after.
     */
    public FoodListPresenter(final EventList model, final String category)
    {
        this.category = category;
        this.selectionListeners = new LinkedList();

        //Make sorted list
        final SortedList sortedList = new SortedList(model, null);

        //Make filtered list
        final TextFilterList textFiltered = new TextFilterList(sortedList,
                new String[] { Food.PROPERTYNAME_CATEGORY });
        textFiltered.getFilterEdit().setText(category);

        //Make selection model
        selectionModel = new EventSelectionModel(textFiltered);
        selectionModel.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e)
            {
                notifyListeners();
            }
        });

        //Make table model
        final EventTableModel tableModel = new EventTableModel(textFiltered,
                ITEM_PROPERTIES, TABLE_HEADERS, PROPERTY_MODIFYABLE);

        view = new FoodListView(sortedList, tableModel, selectionModel,
                EXPIRY_HEADER);
    }

    /**
     * Add a listener which gets informed if the selection changes.
     * 
     * @param listener The listener to add.
     */
    public void addTableSelectionListener(final EventObjectListener listener)
    {
        selectionListeners.add(listener);
    }

    /**
     * Clears the current selection.
     *  
     */
    public void clearSelection()
    {
        selectionModel.clearSelection();
    }

    /**
     * Returns the item's category this presenter filters after.
     * 
     * @return The category. Is not null.
     */
    public String getCategory()
    {
        return category;
    }

    /**
     * Returns the selected object.
     * 
     * @return The selected object.
     */
    public Food getSelected()
    {
        if (!hasSelection())
        {
            return null;
        }

        final EventList selectionList = selectionModel.getEventList();
        return (Food) selectionList.get(0);
    }

    /**
     * Returns the view of this Presenter.
     * 
     * @return the view of this Presenter.
     */
    public FoodListView getView()
    {
        return view;
    }

    /**
     * Returns if a row in the table is currently selected.
     * 
     * @return True if a row has been selected, false otherwise.
     */
    public boolean hasSelection()
    {
        return !selectionModel.isSelectionEmpty();
    }

    /**
     * Notifes the listeners of a selection change.
     *  
     */
    private void notifyListeners()
    {
        final Iterator iter = selectionListeners.iterator();
        while (iter.hasNext())
        {
            EventObjectListener l = (EventObjectListener) iter.next();
            l.handleEvent(new EventObject(this));
        }
    }

}