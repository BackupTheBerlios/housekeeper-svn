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
 * Copyright 2003-2004, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper.swing.assortimentItem;


import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

import net.sourceforge.housekeeper.domain.AssortimentItem;
import net.sourceforge.housekeeper.swing.MainFrame;
import net.sourceforge.housekeeper.swing.SwingUtils;
import net.sourceforge.housekeeper.swing.action.*;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;


/**
 * Dialog for showing, modifying and adding ArticleDescriptions.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
public final class AssortimentItemDialog extends JDialog
{
    //~ Instance fields --------------------------------------------------------

    private AssortimentItem item;
    private JButton         buttonCancel;
    private JButton         buttonOK;
    private JTextField      nameField;
    private JTextField      priceField;
    private JTextField      quantityField;
    private JTextField      storeField;
    private JTextField      unitField;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new AssortimentItemDialog.
     */
    public AssortimentItemDialog()
    {
        super(MainFrame.INSTANCE, true);

        nameField     = new JTextField();
        storeField    = new JTextField();
        priceField    = new JTextField();
        quantityField = new JTextField();
        unitField     = new JTextField();

        buttonOK = new JButton("OK");
        buttonOK.addActionListener(new OKButtonActionListener());

        buttonCancel = new JButton(new DefaultCancelButtonActionListener(this));

        buildLayout();
        pack();
        SwingUtils.centerOnComponent(this, getParent());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Shows a dialog for entering a new Article Description. The new object is
     * returned by this method.
     *
     * @param title Title of the dialog's window.
     *
     * @return The new object if the user confirms his entry, null if he
     *         aborts.
     */
    public AssortimentItem show(String title)
    {
        setTitle(title);
        super.show();

        return item;
    }

    /**
     * Shows a dialog for modifying an existing Article Description. The
     * modified object is returned by this method.
     *
     * @param title Title of the dialog's window.
     * @param articleDescription Object that should be modified.
     *
     * @return The modified object if the user confirms his entry, null if he
     *         aborts.
     */
    public AssortimentItem show(String title, AssortimentItem articleDescription)
    {
        setTitle(title);

        this.item = articleDescription;
        nameField.setText(articleDescription.getName());
        storeField.setText(articleDescription.getStore());
        priceField.setText("" + articleDescription.getPrice());
        quantityField.setText("" + articleDescription.getQuantity());
        unitField.setText(articleDescription.getUnit());

        super.show();

        return articleDescription;
    }

    /**
     * Builds the button bar.
     *
     * @return The created button bar.
     */
    private Component buildButtonBar()
    {
        return ButtonBarFactory.buildOKCancelBar(buttonOK, buttonCancel);
    }

    /**
     * Builds the layout of the dialog.
     */
    private void buildLayout()
    {
        FormLayout         layout = new FormLayout("right:pref, 3dlu, 40dlu, 80dlu", // columns
                                                   "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);

        builder.setDefaultDialogBorder();

        builder.appendTitle("Assortiment Item");
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

    //~ Inner Classes ----------------------------------------------------------

    private class OKButtonActionListener implements ActionListener
    {
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            if (item == null)
            {
                item = new AssortimentItem();
            }


            item.setName(nameField.getText());
            item.setStore(storeField.getText());
            item.setPrice(Double.parseDouble(priceField.getText()));
            item.setQuantity(Integer.parseInt(quantityField.getText()));
            item.setUnit(unitField.getText());

            dispose();
        }
    }
}
