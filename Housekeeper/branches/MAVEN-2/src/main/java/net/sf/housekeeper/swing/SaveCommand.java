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

import net.sf.housekeeper.event.HousekeeperEvent;
import net.sf.housekeeper.persistence.ImportExportController;
import net.sf.housekeeper.swing.util.MessageFactory;
import net.sf.housekeeper.util.ApplicationEventHelper;

import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.richclient.command.ActionCommand;
import org.springframework.richclient.command.support.ActionCommandInterceptorAdapter;
import org.springframework.richclient.command.support.ApplicationWindowAwareCommand;
import org.springframework.richclient.dialog.MessageDialog;
import org.springframework.util.Assert;

/**
 * Command for saving data to persistent storage.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class SaveCommand extends ApplicationWindowAwareCommand implements
        ApplicationListener
{

    private static final String ID = "saveCommand";

    private Exception exception;

    private ImportExportController importExportController;

    /**
     * Creates a new Command for saving. If the action fails, an error dialog is
     * shown.
     */
    public SaveCommand()
    {
        super(ID);
        setEnabled(false);

        addCommandInterceptor(new ErrorInterceptor());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ApplicationEvent arg0)
    {
        if (arg0 instanceof HousekeeperEvent)
        {
            final HousekeeperEvent e = (HousekeeperEvent) arg0;
            if (e.isEventType(HousekeeperEvent.DATA_REPLACED))
            {
                setEnabled(false);
            } else if (e.isEventType(HousekeeperEvent.ADDED)
                    || e.isEventType(HousekeeperEvent.MODIFIED)
                    || e.isEventType(HousekeeperEvent.REMOVED))
            {
                setEnabled(true);
            }
        }

    }

    /**
     * @param importExportController The importExportController to set.
     */
    public void setImportExportController(
            ImportExportController importExportController)
    {
        this.importExportController = importExportController;
    }

    /**
     * @param eventHelper The eventHelper to set.
     */
    public void setEventHelper(ApplicationEventHelper eventHelper)
    {
        eventHelper.addListener(this);
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
            importExportController.exportData();
            setEnabled(false);
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
                final MessageDialog dialog = MessageFactory.INSTANCE
                        .createErrorMessageDialog("gui.mainFrame.saveError",
                                exception);
                dialog.showDialog();
                exception = null;
            } else
            {
                LogFactory.getLog(getClass()).info("Data successfully saved");
            }
        }
    }

}
