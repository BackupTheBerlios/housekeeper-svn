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


import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

import net.sourceforge.housekeeper.entities.ArticleDescription;
import net.sourceforge.housekeeper.entities.Purchase;
import net.sourceforge.housekeeper.entities.PurchasedArticle;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @see
 * @since
 */
final class PurchaseDialog extends JDialog
{
    //~ Instance fields --------------------------------------------------------

    private Collection purchasedArticles;
    private JButton buttonAddArticle;
    private JButton buttonCancel;
    private JButton buttonOK;
    private JTable articleList;
    private JTextField bfeField;
    private JTextField dateField;
    private Purchase purchase;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new PurchaseDialog object.
     */
    PurchaseDialog()
    {
        super(MainFrame.INSTANCE, true);

        initComponents();
        buildLayout();
        pack();
        SwingUtils.centerOnComponent(this, getParent());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * TODO DOCUMENT ME!
     *
     * @param title DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    Purchase show(String title)
    {
        setTitle(title);
        super.show();

        return purchase;
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private JPanel buildButtonPanel()
    {
        return ButtonBarFactory.buildOKCancelBar(buttonOK, buttonCancel);
    }

    /**
     * TODO DOCUMENT ME!
     */
    private void buildLayout()
    {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(buildLeftPanel(), BorderLayout.WEST);
        contentPane.add(buildRightPanel(), BorderLayout.EAST);
        contentPane.add(buildButtonPanel(), BorderLayout.SOUTH);
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private JPanel buildLeftPanel()
    {
        FormLayout layout = new FormLayout("right:pref, 3dlu, 80dlu, 120dlu", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.appendSeparator("Purchase");
        builder.nextLine();
        builder.append("Date", dateField);

        builder.appendSeparator("Purchased Articles");
        builder.append(new JScrollPane(new JTable()), 4);

        return builder.getPanel();
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private JPanel buildRightPanel()
    {
        FormLayout layout = new FormLayout("right:pref, 3dlu, 80dlu, 3dlu, 120dlu",
                                           "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.appendSeparator("Available Article Descriptions");
        builder.nextLine();

        builder.append(new JScrollPane(articleList), 5);

        builder.append("Best before end", bfeField);
        builder.append(ButtonBarFactory.buildRightAlignedBar(buttonAddArticle));

        return builder.getPanel();
    }

    /**
     * TODO DOCUMENT ME!
     */
    private void initComponents()
    {
        dateField = new JTextField();
        bfeField = new JTextField();
        articleList = new JTable(ArticleDescriptionTableModel.getInstance());

        purchasedArticles = new LinkedList();

        buttonOK = new JButton("OK");
        buttonOK.addActionListener(new OKButtonActionListener());

        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new DefaultCancelButtonActionListener(this));

        buttonAddArticle = new JButton("Add Article");
        buttonAddArticle.addActionListener(new AddArticleActionListener());
    }

    //~ Inner Classes ----------------------------------------------------------

    private class AddArticleActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            PurchasedArticle purchasedArticle = new PurchasedArticle();
            int selectedRow = articleList.getSelectedRow();
            ArticleDescriptionTableModel tableModel = (ArticleDescriptionTableModel)articleList.getModel();
            purchasedArticle.setArticle((ArticleDescription)tableModel.getObjectAtRow(selectedRow));
            purchasedArticle.setBestBeforeEnd(new Date());
            purchasedArticles.add(purchasedArticle);
        }
    }

    private class OKButtonActionListener implements ActionListener
    {
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            if (purchase == null)
            {
                purchase = new Purchase();
            }


            purchase.setDate(new Date());
            purchase.add(purchasedArticles);

            dispose();
        }
    }
}
