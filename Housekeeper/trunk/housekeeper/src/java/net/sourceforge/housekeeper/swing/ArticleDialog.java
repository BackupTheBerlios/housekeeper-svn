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

import net.sourceforge.housekeeper.model.ArticleDescription;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 */
class ArticleDialog extends ExtendedDialog
{
    //~ Instance fields --------------------------------------------------------

    private ArticleDescription article;
    private JButton buttonCancel;
    private JButton buttonOK;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTextField storeField;
    private JTextField unitField;

    //~ Constructors -----------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    ArticleDialog()
    {
        super(MainFrame.INSTANCE, true);

        initComponents();
        buildLayout();
        pack();
        center();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * TODO DOCUMENT ME!
     *
     * @param title DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    ArticleDescription show(String title)
    {
        setTitle(title);
        super.show();

        return article;
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @param title DOCUMENT ME!
     * @param article DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    ArticleDescription show(String title, ArticleDescription article)
    {
        this.article = article;
        setTitle(title);

        nameField.setText(article.getName());
        storeField.setText(article.getStore());
        priceField.setText("" + article.getPrice());
        quantityField.setText("" + article.getQuantity());
        unitField.setText(article.getQuantityUnit());

        super.show();

        return article;
    }

    /**
     * TODO DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    private Component buildButtonBar()
    {
        return ButtonBarFactory.buildOKCancelBar(buttonOK, buttonCancel);
    }

    /**
     * TODO DOCUMENT ME!
     */
    private void buildLayout()
    {
        FormLayout layout = new FormLayout("right:pref, 3dlu, 40dlu, 80dlu", // columns
                                           "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);

        builder.setDefaultDialogBorder();

        builder.appendTitle("Article");
        builder.nextLine();

        builder.append("Name", nameField, 2);
        builder.append("Store", storeField, 2);
        builder.append("Quantity", quantityField);
        builder.append("Unit", unitField);
        builder.append("Price", priceField);

        builder.appendSeparator();

        builder.append(buildButtonBar(), 4);

        setContentPane(builder.getPanel());
    }

    /**
     * TODO DOCUMENT ME!
     */
    private void initComponents()
    {
        nameField = new JTextField();
        storeField = new JTextField();
        priceField = new JTextField();
        quantityField = new JTextField();
        unitField = new JTextField();

        buttonOK = new JButton("OK");
        buttonOK.addActionListener(new OKButtonActionListener());

        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new DefaultCancelButtonActionListener());
    }

    //~ Inner Classes ----------------------------------------------------------

    private class OKButtonActionListener implements ActionListener
    {
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            if (article == null)
            {
                article = new ArticleDescription();
            }

            article.setName(nameField.getText());
            article.setStore(storeField.getText());
            article.setPrice(Double.parseDouble(priceField.getText()));
            article.setQuantity(Integer.parseInt(quantityField.getText()));
            article.setQuantityUnit(unitField.getText());

            dispose();
        }
    }
}
