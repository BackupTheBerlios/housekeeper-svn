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
 * Copyright 2003-2004, Adrian Gygax http://housekeeper.sourceforge.net
 */

package net.sf.housekeeper.swing.stock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import net.sf.housekeeper.domain.StockItem;
import net.sf.housekeeper.swing.DefaultCancelButtonAction;
import net.sf.housekeeper.swing.MainFrame;
import net.sf.housekeeper.swing.util.ComponentCenterer;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Dialog for showing, modifying and adding items to stock.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @since 0.1
 */
public final class StockItemDialog extends JDialog
{
    /** The item to be processed. */
    private StockItem        item;

    private JTextField       nameField;

    private SpinnerDateModel spinnerModel;

    /**
     * Creates a new AssortimentItemDialog.
     */
    public StockItemDialog()
    {
        super(MainFrame.INSTANCE, true);

        nameField = new JTextField();

        spinnerModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd.MM.yyyy"));

        JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(new OKButtonActionListener());
        JButton buttonCancel = new JButton(new DefaultCancelButtonAction(this));

        //build layout
        FormLayout layout = new FormLayout("right:pref, 3dlu, 80dlu", // columns
                "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();
        builder.append("Name", nameField);
        builder.append("Best Before End", spinner);
        builder.appendSeparator();
        builder.append(ButtonBarFactory
                .buildOKCancelBar(buttonOK, buttonCancel), 3);
        setContentPane(builder.getPanel());

        pack();
        ComponentCenterer.centerOnComponent(this, getParent());
    }

    //~ Methods
    // ----------------------------------------------------------------

    /**
     * Shows a dialog for entering a new Article Description. The new object is
     * returned by this method.
     * 
     * @param title Title of the dialog's window.
     * 
     * @return The new object if the user confirms his entry, null if he aborts.
     */
    public StockItem show(final String title)
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
     * @param stockItem Object that should be modified.
     * 
     * @return The modified object if the user confirms his entry, null if he
     *         aborts.
     */
    public StockItem show(final String title, final StockItem stockItem)
    {
        setTitle(title);

        this.item = stockItem;
        nameField.setText(stockItem.getName());

        super.show();

        return stockItem;
    }

    private class OKButtonActionListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            if (item == null)
            {
                item = new StockItem();
            }

            item.setName(nameField.getText());
            item.setBestBeforeEnd(spinnerModel.getDate());

            dispose();
        }
    }
}