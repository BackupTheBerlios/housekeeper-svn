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

import java.io.IOException;

import javax.swing.JOptionPane;

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.PersistenceController;
import net.sf.housekeeper.util.LocalisationManager;

import org.springframework.richclient.command.support.ApplicationWindowAwareCommand;


/**
 * Command for saving data to persistent storage.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class SaveCommand extends ApplicationWindowAwareCommand
{

    private static final String ID = "saveCommand";

    private final Household household;
    
    /**
     * Creates a new Command for saving.
     * 
     * @param household
     */
    public SaveCommand(final Household household)
    {
        super(ID);
        this.household = household;
    }
    
    
    /* (non-Javadoc)
     * @see org.springframework.richclient.command.ActionCommand#doExecuteCommand()
     */
    protected void doExecuteCommand()
    {
        try
        {
            PersistenceController.instance().saveDomainData(household);
        } catch (IOException e)
        {
            e.printStackTrace();
            final String message = LocalisationManager.INSTANCE
            .getText("gui.mainFrame.saveError");
            showErrorDialog(message);
        }
        
    }
    
    private void showErrorDialog(String message)
    {
        final String error = LocalisationManager.INSTANCE.getText("gui.error");
        JOptionPane.showMessageDialog(getParentWindowControl(), message, error,
                                      JOptionPane.ERROR_MESSAGE);
    }
}
