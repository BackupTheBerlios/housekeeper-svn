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

package net.sf.housekeeper.swing.stock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import net.sf.housekeeper.domain.StockItem;
import net.sf.housekeeper.swing.DefaultCancelButtonAction;
import net.sf.housekeeper.swing.MainFrame;
import net.sf.housekeeper.swing.util.ComponentCenterer;
import net.sf.housekeeper.swing.util.FormBuilder;

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

        /*
         * The next few lines just create a Date object with the current date
         * not the exact current time. This prevents the problem that the date
         * in the spinner can't be set to today, once it has been modified.
         */
        final Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        final Calendar todayCal = new GregorianCalendar(now.get(Calendar.YEAR),
                now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        final Date today = todayCal.getTime();

        spinnerModel = new SpinnerDateModel();
        spinnerModel.setStart(today);
        final JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setEditor(new DateEditor(spinner));

        final JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(new OKButtonActionListener());
        final JButton buttonCancel = new JButton(new DefaultCancelButtonAction(this));

        final FormBuilder builder = new FormBuilder(80);
        builder.append("Name", nameField);
        builder.append("Best Before End", spinner);
        builder.setButtons(buttonOK, buttonCancel);
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
     * @param title Title of the dialog's window. If null an empty string is
     *  used.
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
     * @param title Title of the dialog's window. If null an empty string is
     *  used.
     * @param stockItem Object that should be modified. Must not be null.
     * 
     * @return The modified object if the user confirms his entry, null if he
     *         aborts.
     */
    public StockItem show(final String title, final StockItem stockItem)
    {
        assert stockItem != null : "Parameter stockItem must not be null";
        
        setTitle(title);

        this.item = stockItem;
        nameField.setText(stockItem.getName());

        super.show();

        return stockItem;
    }

    /**
     * Updates the underlying StockItem object and closes the dialog.
     */
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
    
    /**
     * An editor for a JSpinner which formats dates.
     */
    private class DateEditor extends JSpinner.DefaultEditor
    {
        
        /**
         * Creates a DateEditor using the current locale.
         * 
         * @param spinner The spinner for this editor.
         */
        private DateEditor(final JSpinner spinner)
        {
            super(spinner);

            final SpinnerDateModel model = (SpinnerDateModel) spinner.getModel();
            final DateFormatter formatter = new DateFormatter();
            final DefaultFormatterFactory factory = new DefaultFormatterFactory(
                    formatter);
            final JFormattedTextField ftf = getTextField();
            ftf.setEditable(true);
            ftf.setFormatterFactory(factory);

            try
            {
                String maxString = formatter.valueToString(model.getStart());
                String minString = formatter.valueToString(model.getEnd());
                ftf.setColumns(Math.max(maxString.length(), minString
                                .length()));
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    }
}