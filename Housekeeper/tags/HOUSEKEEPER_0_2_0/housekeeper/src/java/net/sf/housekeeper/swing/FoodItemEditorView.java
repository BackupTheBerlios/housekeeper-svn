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

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import org.apache.commons.lang.time.DateUtils;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Displays the attributes of a {@link net.sf.housekeeper.domain.FoodItem}and
 * allows modifications to them. This class plays the <code>View</code> role
 * in the <code>Model-View-Presenter</code> Pattern. You should not use this
 * View directly. Please use
 * {@link net.sf.housekeeper.swing.FoodItemEditorPresenter}.
 * 
 * @see <a href="http://martinfowler.com/eaaDev/ModelViewPresenter.html">
 *      Model-View-Presenter </a>
 * @see net.sf.housekeeper.swing.FoodItemEditorPresenter
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
class FoodItemEditorView
{

    final JDialog    dialog;

    final JTextField nameField;

    final JTextField quantityField;

    final JSpinner   dateSpinner;

    final JCheckBox  checkbox;

    final JButton    cancelButton;

    final JButton    okButton;

    /**
     * Initializes a new View. It will be shown centered on the
     * <code>owner</code>.
     * 
     * @param owner The owner of this View.
     */
    FoodItemEditorView(final Frame owner)
    {
        dialog = new JDialog(owner, "Item Editor", true);

        nameField = new JTextField(20);
        quantityField = new JTextField(10);
        dateSpinner = createDateSpinner();
        checkbox = createExpiryEnabledCheckbox();

        okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");

        initDialog();
    }

    /**
     * Sets the item's name.
     * 
     * @param text the item's name.
     */
    public void setNameText(final String text)
    {
        nameField.setText(text);
    }

    /**
     * Returns the item's name.
     * 
     * @return the item's name.
     */
    public String getNameText()
    {
        return nameField.getText();
    }

    /**
     * Sets the item's quantity.
     * 
     * @param text the item's quantity.
     */
    public void setQuantityText(final String text)
    {
        quantityField.setText(text);
    }

    /**
     * Returns the item's quantity.
     * 
     * @return the item's quantity.
     */
    public String getQuantityText()
    {
        return quantityField.getText();
    }

    /**
     * Sets the expiry date.
     * 
     * @param expiry The date to set.
     */
    public void setExpiryDate(final Date expiry)
    {
        dateSpinner.setValue(expiry);
    }

    /**
     * Returns the expiry date.
     * 
     * @return the expiry date.
     */
    public Date getExpiryDate()
    {
        return (Date) dateSpinner.getValue();
    }

    /**
     * Allows or disallows the setting of an expiry date.
     * 
     * @param enabled If true setting of an expiry date is allowed, if false it
     *            is not.
     */
    public void setExpiryEnabled(final boolean enabled)
    {
        checkbox.setSelected(enabled);
        dateSpinner.setEnabled(enabled);
    }

    /**
     * Returns whether the setting of an expiry date is currently enabled.
     * 
     * @return True if it is enabled, false otherwise.
     */
    public boolean isExpiryEnabled()
    {
        return checkbox.isSelected();
    }

    /**
     * Adds a listener for canceling the changes.
     * 
     * @param listener The listener to add.
     */
    public void addCancelActionListener(final ActionListener listener)
    {
        cancelButton.addActionListener(listener);
    }

    /**
     * Adds a listener for changes on the expiry date enablement.
     * 
     * @param listener The listener to add.
     */
    public void addExpiryEnabledActionListener(final ActionListener listener)
    {
        checkbox.addActionListener(listener);
    }

    /**
     * Adds a listener for accepting the changes.
     * 
     * @param listener The listener to add.
     */
    public void addOKActionListener(final ActionListener listener)
    {
        okButton.addActionListener(listener);
    }

    /**
     * Shows this View centered on its owner. This method does not return until
     * it has been closed.
     */
    public void show()
    {
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getOwner());
        dialog.show();
    }

    /**
     * Closes this View.
     */
    public void close()
    {
        dialog.dispose();
    }

    /**
     * Initializes the dialog's properties and builds its content pane and. The
     * dialog is then ready to be opened.
     */
    private void initDialog()
    {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(createEditorPanel(), BorderLayout.CENTER);
        panel.add(buildButtonBar(), BorderLayout.SOUTH);
        panel.setBorder(Borders.DIALOG_BORDER);
        dialog.setContentPane(panel);
        dialog.setResizable(false);
    }

    /**
     * Builds a bar with OK and Cancel buttons.
     * 
     * @return The button bar.
     */
    private JPanel buildButtonBar()
    {
        JPanel bar = ButtonBarFactory.buildOKCancelBar(okButton, cancelButton);
        bar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
        return bar;
    }

    /**
     * Creates the checkbox for enabling or disabling the setting of an expiry
     * date.
     * 
     * @return The created checkbox.
     */
    private JCheckBox createExpiryEnabledCheckbox()
    {
        final JCheckBox checkBox = new JCheckBox();
        checkBox.setToolTipText("Check to enable the setting "
                + "of an expiry date. Uncheck to disable it");

        return checkBox;
    }

    /**
     * Creates a JSpinner which uses the current locale's short format for
     * displaying a date. It's default date date is set to today's date.
     * 
     * @return The created spinner.
     */
    private JSpinner createDateSpinner()
    {
        final SpinnerDateModel model = new SpinnerDateModel();

        //Need to truncate the current date for correct spinner operation
        model.setStart(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
        model.setCalendarField(Calendar.DAY_OF_MONTH);

        final JSpinner spinner = new JSpinner(model);

        //Set the spinner's editor to use the current locale's short date
        // format
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.SHORT);
        final String formatPattern = dateFormat.toPattern();
        spinner.setEditor(new JSpinner.DateEditor(spinner, formatPattern));

        return spinner;
    }

    /**
     * Creats a panel which which holds the components for editing an item's
     * attributes.
     */
    private JPanel createEditorPanel()
    {
        final FormLayout layout = new FormLayout(
                "right:pref, 3dlu, fill:default:grow, 3dlu, default");
        final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();
        builder.setRowGroupingEnabled(true);

        builder.append("Name:", nameField);
        builder.nextLine();
        builder.append("Quantity:", quantityField);
        builder.nextLine();
        builder.append("Expiry:", dateSpinner, checkbox);

        return builder.getPanel();
    }
}