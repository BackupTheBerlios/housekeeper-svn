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

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import net.sf.housekeeper.domain.StockItem;
import net.sf.housekeeper.swing.util.FormBuilder;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;

/**
 * Builds a form to display and edit StockItems.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class StockItemEditorBuilder
{

    /**
     * TextField for the item's date.
     */
    private JFormattedTextField     dateField;

    /**
     * TextField for the item's name.
     */
    private JTextField              nameField;

    /**
     * Holds the edited item and vends ValueModels that adapt item properties.
     */
    private final PresentationModel presentationModel;

    /**
     * TextField for the item's quantity.
     */
    private JTextField              quantityField;

    /**
     * Creates a StockItemEditorBuilder for the given PresentationModel.
     * 
     * @param presentationModel provides the adapting ValueModels.
     */
    public StockItemEditorBuilder(final PresentationModel presentationModel)
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
        formBuilder.append("Expiry", dateField, 80);
        return formBuilder.getPanel();
    }

    /**
     * Creates and intializes the UI components.
     */
    private void initComponents()
    {
        nameField = BasicComponentFactory.createTextField(presentationModel
                .getBufferedModel(StockItem.PROPERTYNAME_NAME));
        quantityField = BasicComponentFactory.createTextField(presentationModel
                .getBufferedModel(StockItem.PROPERTYNAME_QUANTITY));
        dateField = BasicComponentFactory.createDateField(presentationModel
                .getBufferedModel(StockItem.PROPERTYNAME_EXPIRY));
    }
}