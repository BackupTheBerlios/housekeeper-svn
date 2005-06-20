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
 * http://housekeeper.berlios.de
 */

package net.sf.housekeeper.swing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.JFileChooser;

import net.sf.housekeeper.domain.ItemManager;

import org.springframework.richclient.command.support.ApplicationWindowAwareCommand;

/**
 * @author
 * @version $Revision$, $Date$
 */
public class ExportShoppingListCommand extends ApplicationWindowAwareCommand
{

    private ItemManager shoppingListManager;

    /**
     *  
     */
    public ExportShoppingListCommand()
    {
        super("exportShoppingListCommand");
    }

    /**
     * @param shoppingListManager The shoppingListManager to set.
     */
    public void setShoppingListManager(ItemManager shoppingListManager)
    {
        this.shoppingListManager = shoppingListManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.command.ActionCommand#doExecuteCommand()
     */
    protected void doExecuteCommand()
    {
        final JFileChooser chooser = new JFileChooser();
        final int returnVal = chooser.showSaveDialog(getApplicationWindow()
                .getControl());

        if (returnVal == JFileChooser.APPROVE_OPTION)
            ;
        {
            final String itemsAsText = shoppingListManager.getItemsAsText();
            try
            {
                final File file = chooser.getSelectedFile();
                final Writer writer = new BufferedWriter(new FileWriter(file,
                        false));
                writer.write(itemsAsText);
                writer.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}
