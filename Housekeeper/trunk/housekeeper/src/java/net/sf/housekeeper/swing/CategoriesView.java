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

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.housekeeper.HousekeeperEvent;
import net.sf.housekeeper.domain.Category;

import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.list.BeanPropertyValueListRenderer;

/**
 * @author
 * @version $Revision$, $Date$
 */
public final class CategoriesView extends AbstractView
{

    private JList list;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.support.AbstractView#createControl()
     */
    protected JComponent createControl()
    {
        final Category[] listItems = { Category.CONVENIENCE, Category.MISC };
        list = new JList(listItems);
        list.getSelectionModel()
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new BeanPropertyValueListRenderer("name"));

        list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e)
            {
                getApplicationContext()
                        .publishEvent(
                                      new HousekeeperEvent(
                                              HousekeeperEvent.SELECTED, list
                                                      .getSelectedValue()));
            }
        });

        list.setSelectedIndex(0);
        return list;
    }

}