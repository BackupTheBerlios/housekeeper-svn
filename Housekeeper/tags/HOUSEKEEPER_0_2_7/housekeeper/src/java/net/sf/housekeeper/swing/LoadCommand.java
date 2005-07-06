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

import java.io.FileNotFoundException;

import net.sf.housekeeper.persistence.ImportExportController;
import net.sf.housekeeper.swing.util.ErrorDialog;

import org.apache.commons.logging.LogFactory;
import org.springframework.richclient.command.support.ApplicationWindowAwareCommand;
import org.springframework.util.Assert;

/**
 * Command for loading data from persistent storage.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class LoadCommand extends ApplicationWindowAwareCommand
{

    private static final String ID = "loadCommand";

    private ImportExportController importExportController;

    /**
     * Creates a new LoadCommand.
     */
    public LoadCommand()
    {
        super(ID);
    }

    /**
     * @param importExportController
     *            The importExportController to set.
     */
    public void setImportExportController(
            ImportExportController importExportController)
    {
        this.importExportController = importExportController;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.command.ActionCommand#doExecuteCommand()
     */
    protected void doExecuteCommand()
    {
        Assert.notNull(importExportController);

        try
        {
            importExportController.importData();
            LogFactory.getLog(getClass()).info("Data sucessfully loaded");
        } catch (FileNotFoundException exception)
        {

            final ErrorDialog dialog = new ErrorDialog("gui.mainFrame.nodata",
                    exception);
            dialog.showDialog();
        } catch (Exception exception)
        {
            final ErrorDialog dialog = new ErrorDialog(exception);
            dialog.showDialog();
        }
    }

}
