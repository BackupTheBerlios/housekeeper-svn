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
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import net.sf.housekeeper.domain.FoodItem;
import net.sf.housekeeper.swing.util.BoundComponentFactory;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.value.BufferedValueModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

/**
 * A dialog for editing a FoodItem. The buttons for accepting or canceling the
 * edit trigger a commit or flush in the underlying PresentationModel.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class FoodItemEditorDialog extends JDialog
{

    /**
     * True if the users pressed the cancel button. False otherwise.
     */
    private boolean                 canceled;

    /**
     * Holds the edited item and vends ValueModels that adapt item properties.
     */
    private final PresentationModel presentationModel;

    /**
     * Creates an editor dialog for the specified item.
     * 
     * @param owner The owner of this dialog. The dialog will be opened centered
     *            on the owner frame.
     * @param item the item to be edited.
     */
    public FoodItemEditorDialog(final Frame owner, final FoodItem item)
    {
        super(owner, "Item Editor", true);
        this.presentationModel = new PresentationModel(item);
        canceled = false;
    }

    /**
     * Closes the dialog: makes it invisible and disposes it, which in turn
     * releases all required OS resources.
     */
    public void close()
    {
        dispose();
    }

    /**
     * Checks and answers whether the dialog has been canceled.
     * 
     * @return true indicates that the dialog has been canceled
     */
    public boolean hasBeenCanceled()
    {
        return canceled;
    }

    /**
     * Opens the dialog.
     */
    public void open()
    {
        build();
        canceled = false;
        setVisible(true);
    }

    /**
     * Builds the dialog's content pane and locates it on the screen. The dialog
     * is then ready to be opened.
     */
    private void build()
    {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.add(buildEditorPanel(), BorderLayout.CENTER);
        panel.add(buildButtonBar(), BorderLayout.SOUTH);
        panel.setBorder(Borders.DIALOG_BORDER);
        setContentPane(panel);

        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    /**
     * Builds a bar with OK and Cancel buttons.
     * 
     * @return The button bar.
     */
    private JComponent buildButtonBar()
    {
        JPanel bar = ButtonBarFactory.buildOKCancelBar(new JButton(
                new OKAction()), new JButton(new CancelAction()));
        bar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
        return bar;
    }

    /**
     * Builds a panel with a form for editing an item's properties.
     * 
     * @return a panel with a form for editing an item's properties.
     */
    private JComponent buildEditorPanel()
    {
        //Create name field
        final JTextField nameField = BasicComponentFactory
                .createTextField(presentationModel
                        .getBufferedModel(FoodItem.PROPERTYNAME_NAME));
        nameField.setColumns(20);

        //Create quantity field
        final JTextField quantityField = BasicComponentFactory
                .createTextField(presentationModel
                        .getBufferedModel(FoodItem.PROPERTYNAME_QUANTITY));
        quantityField.setColumns(10);

        //Create expiry date selection spinner
        final JSpinner dateSpinner = BoundComponentFactory
                .createDateSpinner(presentationModel
                        .getBufferedModel(FoodItem.PROPERTYNAME_EXPIRY));

        //Create checkbox which controls if date selection is enabled or
        //disabled.
        final JCheckBox checkbox = new JCheckBox();
        checkbox.setToolTipText("Check to enable the setting "
                + "of an expiry date. Uncheck to disable it");
        final Object expiryValue = presentationModel
                .getBufferedModel(FoodItem.PROPERTYNAME_EXPIRY).getValue();
        checkbox.setSelected(expiryValue != null);
        dateSpinner.setEnabled(expiryValue != null);
        checkbox.addItemListener(new CheckBoxListener(dateSpinner));

        //Build layout
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

    /**
     * Listens for selection state changes of a checkbox. If it becomes
     * deselected, the domain object's expiry date value is set to null. Date
     * selection is disabled but the spinner model caches the last selected
     * date. If the checkbox becomes selected date selection is enabled and the
     * domain object's expiry date is set to the spinner model's cached date.
     */
    private class CheckBoxListener implements ItemListener
    {

        private final JSpinner spinner;

        private CheckBoxListener(JSpinner spinner)
        {
            this.spinner = spinner;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
         */
        public void itemStateChanged(ItemEvent e)
        {
            final boolean hasBeenSelected = e.getStateChange() == ItemEvent.SELECTED;
            final BufferedValueModel expiryValueModel = presentationModel
                    .getBufferedModel(FoodItem.PROPERTYNAME_EXPIRY);

            spinner.setEnabled(hasBeenSelected);

            if (hasBeenSelected)
            {
                expiryValueModel.setValue(spinner.getValue());
            } else
            {
                expiryValueModel.setValue(null);
            }
        }
    }

    /**
     * Action for disarding any changes.
     */
    private class CancelAction extends AbstractAction
    {

        private CancelAction()
        {
            super("Cancel");
        }

        public void actionPerformed(ActionEvent e)
        {
            presentationModel.triggerFlush();
            canceled = true;
            close();
        }
    }

    /**
     * Action for accepting any changes.
     */
    private class OKAction extends AbstractAction
    {

        private OKAction()
        {
            super("OK");
        }

        public void actionPerformed(ActionEvent e)
        {
            presentationModel.triggerCommit();
            canceled = false;
            close();
        }
    }
}