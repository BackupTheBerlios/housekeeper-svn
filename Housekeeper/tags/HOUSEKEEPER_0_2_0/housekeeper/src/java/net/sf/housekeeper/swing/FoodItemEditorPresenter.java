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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sf.housekeeper.domain.FoodItem;

/**
 * Shows an editor for {@link net.sf.housekeeper.domain.FoodItem}objects. This
 * class plays the <code>Presentation</code> role in the
 * <code>Model-View-Presenter</code> Pattern.
 * 
 * @see <a href="http://martinfowler.com/eaaDev/ModelViewPresenter.html">
 *      Model-View-Presenter </a>
 * @see net.sf.housekeeper.swing.FoodItemEditorView
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class FoodItemEditorPresenter
{

    /**
     * Canceled state of the action.
     */
    private boolean                  canceled;

    /**
     * The model to present.
     */
    private final FoodItem           model;

    /**
     * The view to synchronsize with the model.
     */
    private final FoodItemEditorView view;

    /**
     * Creates and initializes a new editor for a {@link FoodItem}.
     * 
     * @param view The View to be used for presenting the model.
     * @param model The Model to present.
     */
    public FoodItemEditorPresenter(final FoodItemEditorView view,
            final FoodItem model)
    {
        this.view = view;
        this.model = model;
        canceled = true;

        updateViewFromModel();
        registerListeners(view);
    }

    /**
     * Presents the model by showing the view. This method does not return until
     * the user has decided to cancel or accept the modifications.
     * 
     * @return True if the user has canceled the editing or false if he has
     *         accepted it.
     */
    public boolean show()
    {
        view.show();
        return canceled;
    }

    /**
     * Registers listeners which react to the user's actions in the view.
     * 
     * @param view The view to add the listeners to.
     */
    private void registerListeners(final FoodItemEditorView view)
    {
        view.addOKActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                updateModelFromView();
                canceled = false;
                view.close();
            }
        });

        view.addCancelActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                view.close();
            }
        });

        view.addExpiryEnabledActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                view.setExpiryEnabled(view.isExpiryEnabled());
            }
        });
    }

    /**
     * Updates the attribute values of the model with the entries in the view.
     *  
     */
    private void updateModelFromView()
    {
        model.setName(view.getNameText());
        model.setQuantity(view.getQuantityText());
        if (view.isExpiryEnabled())
        {
            model.setExpiry(view.getExpiryDate());
        } else
        {
            model.setExpiry(null);
        }
    }

    /**
     * Updates the display of the model's attributes in the view.
     *  
     */
    private void updateViewFromModel()
    {
        view.setNameText(model.getName());
        view.setQuantityText(model.getQuantity());
        if (model.getExpiry() != null)
        {
            view.setExpiryDate(model.getExpiry());
            view.setExpiryEnabled(true);
        } else
        {
            view.setExpiryEnabled(false);
        }

    }

}