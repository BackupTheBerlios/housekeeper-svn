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

import java.io.IOException;

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.PersistenceController;
import net.sf.housekeeper.swing.util.ErrorDialog;

import org.apache.commons.logging.LogFactory;
import org.springframework.richclient.command.ActionCommand;
import org.springframework.richclient.command.support.ActionCommandInterceptorAdapter;

/**
 * Command for saving data to persistent storage.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class SaveCommand extends ActionCommand
{

    private static final String   ID = "saveCommand";

    private Household             household;

    private PersistenceController persistenceController;

    private Exception             exception;

    /**
     * Creates a new Command for saving. If the action fails, an error dialog is
     * shown.
     */
    public SaveCommand()
    {
        super(ID);

        addCommandInterceptor(new ErrorInterceptor());
    }

    /**
     * Sets the household property.
     * 
     * @param household the value to set.
     */
    public void setHousehold(Household household)
    {
        this.household = household;
    }

    /**
     * Sets the persistenceController property.
     * 
     * @param controller the value to set.
     */
    public void setPersistenceController(PersistenceController controller)
    {
        this.persistenceController = controller;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.command.ActionCommand#doExecuteCommand()
     */
    protected void doExecuteCommand()
    {
        try
        {
            persistenceController.saveDomainData(household);
        } catch (IOException e)
        {
            exception = e;
        }

    }

    private class ErrorInterceptor extends ActionCommandInterceptorAdapter
    {

        public void postExecution(ActionCommand arg0)
        {
            if (exception != null)
            {
                LogFactory.getLog(getClass()).error("Could not save data",
                                                    exception);
                final ErrorDialog dialog = new ErrorDialog(
                        "gui.mainFrame.saveError", exception);
                dialog.showDialog();
                exception = null;
            } else
            {
                LogFactory.getLog(getClass()).info("Data successfully saved");
            }
        }
    }

}