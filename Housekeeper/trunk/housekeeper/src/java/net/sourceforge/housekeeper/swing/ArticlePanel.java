/*
 * This file is part of Housekeeper.
 *
 * Housekeeper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Housekeeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Housekeeper; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA  02111-1307  USA
 *
 * Copyright 2003, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper.swing;


import net.sourceforge.housekeeper.entities.ArticleDescription;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 */
final class ArticlePanel extends JPanel
{
    //~ Static fields/initializers ---------------------------------------------

    /** TODO DOCUMENT ME! */
    private static final ArticlePanel INSTANCE = new ArticlePanel();

    //~ Instance fields --------------------------------------------------------

    /** TODO DOCUMENT ME! */
    private JPanel buttonPanel;

    /** TODO DOCUMENT ME! */
    private JScrollPane scrollPane;

    /** TODO DOCUMENT ME! */
    private JTable table;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ArticlePanel object.
     */
    private ArticlePanel()
    {
        table = new JTable(ArticleDescriptionTableModel.getInstance());
        scrollPane = new JScrollPane(table);
        buttonPanel = new JPanel();

        buttonPanel.add(new JButton(NewArticleAction.INSTANCE));
        buttonPanel.add(new JButton(ModifyArticleAction.INSTANCE));

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static ArticlePanel getInstance()
    {
        return INSTANCE;
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    ArticleDescription getSelectedArticle()
    {
        int selectedRow = table.getSelectedRow();

        if (selectedRow > -1)
        {
            ArticleDescriptionTableModel tableModel = (ArticleDescriptionTableModel)table.getModel();

            return tableModel.getObjectAtRow(selectedRow);
        }
        else
        {
            return null;
        }
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    JTable getTable()
    {
        return table;
    }
}
