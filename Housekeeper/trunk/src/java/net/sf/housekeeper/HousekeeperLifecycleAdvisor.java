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

package net.sf.housekeeper;

import java.io.IOException;

import javax.swing.JOptionPane;

import net.sf.housekeeper.persistence.ImportExportController;

import org.apache.commons.logging.LogFactory;
import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.application.config.ApplicationWindowConfigurer;
import org.springframework.richclient.application.config.DefaultApplicationLifecycleAdvisor;
import org.springframework.richclient.command.ActionCommand;

/**
 * Executes stuff if the lifecycle state of the application changes. Loads the
 * data upon startup and opens a dialog asking the user if he wants to save
 * before exiting.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class HousekeeperLifecycleAdvisor extends
        DefaultApplicationLifecycleAdvisor
{

    private ImportExportController importExportController;

    /**
     * Ask if user wants to save before exiting.
     */
    public boolean onPreWindowClose(ApplicationWindow window)
    {
        boolean exit = true;

        final ActionCommand saveCommand = window.getCommandManager()
                .getActionCommand("saveCommand");

        // Only show dialog for saving before exiting if any data has been
        // changed
        if (saveCommand.isEnabled())
        {
            final String question = getApplicationServices().getMessages()
                    .getMessage("gui.mainFrame.saveModificationsQuestion");
            final int option = JOptionPane.showConfirmDialog(window
                    .getControl(), question);

            // If user choses yes try to save. If that fails do not exit.
            if (option == JOptionPane.YES_OPTION)
            {
                saveCommand.execute();

            } else if (option == JOptionPane.CANCEL_OPTION)
            {
                exit = false;
            }
        }
        return exit;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.config.ApplicationLifecycleAdvisor#onWindowOpened(org.springframework.richclient.application.ApplicationWindow)
     */
    public void onWindowOpened(ApplicationWindow window)
    {
        window.getControl().toFront();
        super.onWindowOpened(window);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.config.ApplicationLifecycleAdvisor#onPreWindowOpen(org.springframework.richclient.application.config.ApplicationWindowConfigurer)
     */
    public void onPreWindowOpen(ApplicationWindowConfigurer configurer)
    {
        try
        {
            importExportController.importData();
        } catch (IOException e)
        {
            LogFactory.getLog(getClass()).info(
                    "No saved data found, starting with no objects loaded");
        }
        super.onPreWindowOpen(configurer);
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
}
