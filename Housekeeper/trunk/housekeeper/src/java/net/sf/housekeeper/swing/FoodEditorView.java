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
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.housekeeper.util.LocalisationManager;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;

/**
 * Displays the attributes of a {@link net.sf.housekeeper.domain.Food}and
 * allows modifications to them. This class plays the <code>View</code> role
 * in the <code>Model-View-Presenter</code> Pattern. You should not use this
 * View directly. Please use
 * {@link net.sf.housekeeper.swing.FoodEditorPresenter}.
 * 
 * @see <a href="http://martinfowler.com/eaaDev/ModelViewPresenter.html">
 *      Model-View-Presenter </a>
 * @see net.sf.housekeeper.swing.FoodEditorPresenter
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class FoodEditorView
{

    private final JDialog    dialog;

    private final JTextField nameField;

    private final JTextField quantityField;

    private final JDateChooser   dateSpinner;

    private final JCheckBox  checkbox;

    private final JButton    cancelButton;

    private final JButton    okButton;

    /**
     * Initializes a new View. It will be shown centered on the
     * <code>owner</code>.
     * 
     * @param owner The owner of this View.
     */
    FoodEditorView(final Frame owner)
    {
        final String dialogTitle = LocalisationManager.INSTANCE
                .getText("gui.foodEditor.dialogTitle");
        dialog = new JDialog(owner, dialogTitle, true);

        nameField = new JTextField(20);
        quantityField = new JTextField(10);
        dateSpinner = createDateSpinner();
        checkbox = createExpiryEnabledCheckbox();

        final String okButtonText = LocalisationManager.INSTANCE
                .getText("gui.okButton");
        okButton = new JButton(okButtonText);
        okButton.setActionCommand("OK");

        final String cancelButtonText = LocalisationManager.INSTANCE
                .getText("gui.cancelButton");
        cancelButton = new JButton(cancelButtonText);
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
     * Sets the item's description.
     * 
     * @param text the item's description.
     */
    public void setDescriptionText(final String text)
    {
        quantityField.setText(text);
    }

    /**
     * Returns the item's description.
     * 
     * @return the item's description.
     */
    public String getDescriptionText()
    {
        return quantityField.getText();
    }

    /**
     * Sets the expiry date.
     * 
     * @param expiry The date to set.
     */
    public void setExpiryDate(Date expiry)
    {
        if (expiry == null)
        {
            expiry = new Date();
        }
        dateSpinner.setDate(expiry);
    }

    /**
     * Returns the expiry date.
     * 
     * @return the expiry date.
     */
    public Date getExpiryDate()
    {
        return dateSpinner.getDate();
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
        final Component[] comps = dateSpinner.getComponents();
        for (int i = 0; i < comps.length; i++)
        {
            comps[i].setEnabled(enabled);
        }
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
        final String toolTip = LocalisationManager.INSTANCE
                .getText("gui.foodEditor.expiryCheckBoxToolTip");
        checkBox.setToolTipText(toolTip);

        return checkBox;
    }

    /**
     * Creates a JSpinner which uses the current locale's short format for
     * displaying a date. It's default date date is set to today's date.
     * 
     * @return The created spinner.
     */
    private JDateChooser createDateSpinner()
    {
        //Set the spinner's editor to use the current locale's short date
        // format
        final SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat
                .getDateInstance(DateFormat.SHORT);
        final String formatPattern = dateFormat.toPattern();
        final JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString(formatPattern);

        return dateChooser;
    }

    /**
     * Creats a panel which which holds the components for editing an item's
     * attributes.
     * 
     * @return The created panel. Is not null.
     */
    private JPanel createEditorPanel()
    {
        final FormLayout layout = new FormLayout(
                "right:pref, 3dlu, fill:default:grow, 3dlu, default");
        final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();
        builder.setRowGroupingEnabled(true);

        final String nameLabel = LocalisationManager.INSTANCE
                .getText("food.name")
                + ":";
        builder.append(nameLabel, nameField);
        builder.nextLine();

        final String quantityLabel = LocalisationManager.INSTANCE
                .getText("food.description")
                + ":";
        builder.append(quantityLabel, quantityField);
        builder.nextLine();

        final String expiryLabel = LocalisationManager.INSTANCE
                .getText("food.expiry")
                + ":";
        builder.append(expiryLabel, dateSpinner, checkbox);

        return builder.getPanel();
    }
}