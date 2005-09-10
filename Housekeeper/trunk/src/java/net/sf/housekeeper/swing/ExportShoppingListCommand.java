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

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JFileChooser;

import net.sf.housekeeper.persistence.ImportExportController;
import net.sf.housekeeper.swing.util.ErrorDialog;

import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.richclient.command.ActionCommand;
import org.springframework.richclient.command.support.ActionCommandInterceptorAdapter;
import org.springframework.richclient.command.support.ApplicationWindowAwareCommand;

/**
 * Lets the user select a file and exports the current shopping list.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ExportShoppingListCommand extends
        ApplicationWindowAwareCommand implements MessageSourceAware
{

    private final JFileChooser chooser;

    private Exception exception;

    private ImportExportController importExportController;

    private MessageSource messageSource;

    public ExportShoppingListCommand()
    {
        super("exportShoppingListCommand");
        setEnabled(true);

        chooser = new JFileChooser();
        chooser.setSelectedFile(new File("shopping_list.txt"));

        addCommandInterceptor(new ErrorInterceptor());
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
     * @see org.springframework.context.MessageSourceAware#setMessageSource(org.springframework.context.MessageSource)
     */
    public void setMessageSource(MessageSource arg0)
    {
        this.messageSource = arg0;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.command.ActionCommand#doExecuteCommand()
     */
    protected void doExecuteCommand()
    {
        final int returnVal = chooser.showSaveDialog(getApplicationWindow()
                .getControl());

        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            final File file = chooser.getSelectedFile();
            try
            {
                importExportController.exportShoppingListAsText(file);
                final String message = getMessage("exportSuccessful");
                getApplicationWindow().getStatusBar()
                        .setMessage(message + file);
            } catch (IOException e)
            {
                exception = e;
            }
        }
    }

    private String getMessage(final String code)
    {
        return messageSource.getMessage(code, null, code, Locale.getDefault());
    }

    private class ErrorInterceptor extends ActionCommandInterceptorAdapter
    {

        public void postExecution(ActionCommand arg0)
        {
            if (exception != null)
            {
                LogFactory.getLog(getClass()).error("Could not export data",
                        exception);
                final ErrorDialog dialog = new ErrorDialog(
                        "gui.mainFrame.saveError", exception);
                dialog.showDialog();
                exception = null;
            }
        }
    }

}
