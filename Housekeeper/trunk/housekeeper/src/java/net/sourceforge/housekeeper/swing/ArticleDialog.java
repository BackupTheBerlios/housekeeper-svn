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


import com.jgoodies.forms.extras.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

import net.sourceforge.housekeeper.model.Article;
import net.sourceforge.housekeeper.storage.StorageFactory;

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

    /** TODO DOCUMENT ME! */
    private ActionListener okButtonListener;

    /** TODO DOCUMENT ME! */
    private Article    article;

    /** TODO DOCUMENT ME! */
    private JButton    buttonCancel;

    /** TODO DOCUMENT ME! */
    private JButton    buttonOK;

    /** TODO DOCUMENT ME! */
    private JTextField nameField;

    /** TODO DOCUMENT ME! */
    private JTextField priceField;

    /** TODO DOCUMENT ME! */
    private JTextField quantityField;

    /** TODO DOCUMENT ME! */
    private JTextField storeField;

    /** TODO DOCUMENT ME! */
    private JTextField unitField;

    //~ Constructors -----------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    ArticleDialog()
    {
        super(MainFrame.INSTANCE, true);

        article          = new Article();
        okButtonListener = new LocalActionListener();

        setTitle("New Article");
        init();
    }

    /**
     * Creates a new ArticleDialog object.
    
     *
     * @param article DOCUMENT ME!
     */
    ArticleDialog(Article article)
    {
        super(MainFrame.INSTANCE, true);

        this.article     = article;
        okButtonListener = new ModifyActionListener();

        setTitle("Modify Article");
        init();
    }

    //~ Methods ----------------------------------------------------------------

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
    private void init()
    {
        initComponents();
        buildLayout();
        pack();
        center();
    }

    /**
     * TODO DOCUMENT ME!
     */
    private void initComponents()
    {
        nameField     = new JTextField(article.getName());
        storeField    = new JTextField(article.getStore());
        priceField    = new JTextField("" + article.getPrice());
        quantityField = new JTextField("" + article.getQuantity());
        unitField     = new JTextField(article.getQuantityUnit());

        buttonOK = new JButton("OK");
        buttonOK.addActionListener(okButtonListener);

        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new DefaultCancelButtonActionListener());
    }

    /**
     * TODO DOCUMENT ME!
     */
    private void readFields()
    {
        article.setName(nameField.getText());
        article.setStore(storeField.getText());
        article.setPrice(Double.parseDouble(priceField.getText()));
        article.setQuantity(Integer.parseInt(quantityField.getText()));
        article.setQuantityUnit(unitField.getText());
    }

    //~ Inner Classes ----------------------------------------------------------

    private class LocalActionListener implements ActionListener
    {
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            readFields();

            StorageFactory.getCurrentStorage()
                          .add(article);

            dispose();
        }
    }

    private class ModifyActionListener implements ActionListener
    {
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            readFields();

            StorageFactory.getCurrentStorage()
                          .update();

            dispose();
        }
    }
}
