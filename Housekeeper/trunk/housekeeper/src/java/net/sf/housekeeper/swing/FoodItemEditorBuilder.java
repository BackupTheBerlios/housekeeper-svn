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

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import net.sf.housekeeper.domain.FoodItem;
import net.sf.housekeeper.swing.util.BoundComponentFactory;
import net.sf.housekeeper.swing.util.DateEditor;
import net.sf.housekeeper.swing.util.FormBuilder;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;

/**
 * Builds a form to display and edit StockItems.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class FoodItemEditorBuilder
{

    /**
     * Component for the item's expiry date.
     */
    private JSpinner     dateSpinner;

    /**
     * Component for the item's name.
     */
    private JTextField              nameField;

    /**
     * Holds the edited item and vends ValueModels that adapt item properties.
     */
    private final PresentationModel presentationModel;

    /**
     * Component for the item's quantity.
     */
    private JTextField              quantityField;

    /**
     * Creates a StockItemEditorBuilder for the given PresentationModel.
     * 
     * @param presentationModel provides the adapting ValueModels.
     */
    public FoodItemEditorBuilder(final PresentationModel presentationModel)
    {
        this.presentationModel = presentationModel;
    }

    /**
     * Builds the pane.
     * 
     * @return the built panel
     */
    public JComponent build()
    {
        initComponents();

        final FormBuilder formBuilder = new FormBuilder();
        formBuilder.append("Name", nameField, 80);
        formBuilder.append("Quantity", quantityField, 80);
        formBuilder.append("Expiry", dateSpinner, 80);
        return formBuilder.getPanel();
    }

    /**
     * Creates and intializes the UI components.
     */
    private void initComponents()
    {
        nameField = BasicComponentFactory.createTextField(presentationModel
                .getBufferedModel(FoodItem.PROPERTYNAME_NAME));
        quantityField = BasicComponentFactory.createTextField(presentationModel
                .getBufferedModel(FoodItem.PROPERTYNAME_QUANTITY));
        dateSpinner = BoundComponentFactory.createDateSpinner(presentationModel
                .getBufferedModel(FoodItem.PROPERTYNAME_EXPIRY));
        //final SimpleDateFormat dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance();
        //final String formatPattern = dateFormat.toPattern();
        //dateSpinner.setEditor(new DateEditor(dateSpinner));
        
        System.out.println(((JSpinner.DateEditor)dateSpinner.getEditor()).getFormat().toPattern());
    }
}