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

package net.sourceforge.housekeeper.swing.articledescription;


import net.sourceforge.housekeeper.domain.ArticleDescription;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;


/**
 * Shows all Article Descriptions as a table and offers manipulation (Create,
 * modify, delete) on them.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
public final class ArticleDescriptionPanel extends JPanel
{
    //~ Static fields/initializers ---------------------------------------------

    /** Singleton instance */
    private static final ArticleDescriptionPanel INSTANCE = new ArticleDescriptionPanel();

    //~ Instance fields --------------------------------------------------------

    private JPanel      buttonPanel;
    private JScrollPane scrollPane;
    private JTable      table;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ArticleDescriptionPanel object.
     */
    private ArticleDescriptionPanel()
    {
        table       = new JTable(ArticleDescriptionTableModel.getInstance());
        scrollPane  = new JScrollPane(table);
        buttonPanel = new JPanel();

        buttonPanel.add(new JButton(NewArticleDescriptionAction.INSTANCE));
        buttonPanel.add(new JButton(ModifyArticleDescriptionAction.INSTANCE));

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Returns the Singleton instance of this class.
     *
     * @return The Singleton instance.
     */
    public static ArticleDescriptionPanel getInstance()
    {
        return INSTANCE;
    }

    /**
     * Returns the selected Article Description from the table.
     *
     * @return The selected Article Description or null if no table row is
     *         selected.
     */
    public ArticleDescription getSelectedArticle()
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
}
