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

import java.io.FileNotFoundException;

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.PersistenceController;
import net.sf.housekeeper.swing.util.ErrorDialog;

import org.springframework.richclient.command.support.ApplicationWindowAwareCommand;

/**
 * Command for loading data from persistent storage.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class LoadCommand extends ApplicationWindowAwareCommand
{

    private static final String ID = "loadCommand";

    private final Household     household;

    /**
     * Creates a new LoadCommand.
     * 
     * @param household
     */
    public LoadCommand(final Household household)
    {
        super(ID);
        this.household = household;
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
            PersistenceController.instance().replaceDomainWithSaved(household);
        } catch (FileNotFoundException exception)
        {
            //If called during startup, the application window is not yet
            //initialized. So we can check for that to not show the "no data
            //found" error on startup.
            if (getApplicationWindow().getControl() != null)
            {
                final ErrorDialog dialog = new ErrorDialog(
                        "gui.mainFrame.nodata", exception);
                dialog.showDialog();
            }
        } catch (Exception exception)
        {
            final ErrorDialog dialog = new ErrorDialog(exception);
            dialog.showDialog();
        }
    }

}