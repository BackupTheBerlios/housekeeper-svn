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

package net.sf.housekeeper.swing.util;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.UIManager;

import org.springframework.richclient.command.AbstractCommand;
import org.springframework.richclient.core.Message;
import org.springframework.richclient.dialog.ApplicationDialog;
import org.springframework.rules.reporting.Severity;
import org.springframework.util.Assert;

/**
 * A default dialog for showing an error message.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ErrorDialog extends ApplicationDialog
{

    private static final String ERROR_MESSAGE_ID = "gui.error";

    private static final String ERRORICON        = "OptionPane.errorIcon";

    private final String        message;
    
    private final Exception exception;

    /**
     * Creates a dialog for showing an error message.
     * 
     * @param messageID The ID of the message to show. Can be null.
     * @param e The exception associated with the error. Can be null.
     */
    public ErrorDialog(final String messageID, final Exception e)
    {
        Assert.notNull(messageID);
        exception = e;
        
        message = getMessage(messageID);

        final String title = getMessage(ERROR_MESSAGE_ID);
        setTitle(title);
    }
    
    /**
     * Creates a dialog which gets the message from an Exception.
     * 
     * @param e The exception to get the message from.
     */
    public ErrorDialog(final Exception e)
    {
        this("", e);
    }

    /**
     * Creates a dialog for showing an error message.
     * 
     * @param messageID The ID of the message to show.
     */
    public ErrorDialog(final String messageID)
    {
        this(messageID, null);
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.dialog.ApplicationDialog#createDialogContentPane()
     */
    protected JComponent createDialogContentPane()
    {
        final TextAreaPane messagePane = new TextAreaPane();
        final Icon icon = UIManager.getIcon(ERRORICON);
        messagePane.setDefaultIcon(icon);
        
        final StringBuffer messageBuffer = new StringBuffer();
        messageBuffer.append(message);

        if (exception != null)
        {
            if (!message.equals(""))
            {
                messageBuffer.append("\n\n");
            }
            messageBuffer.append(exception.getLocalizedMessage());
        }

        messagePane.setMessage(new Message(messageBuffer.toString(), Severity.ERROR));
        final JComponent paneConrol = messagePane.getControl();
        paneConrol.setPreferredSize(new Dimension(400, 100));
        return messagePane.getControl();
    }

    /**
     * Show only the OK button.
     */
    protected Object[] getCommandGroupMembers()
    {
        return new AbstractCommand[] { getFinishCommand() };
    }

    /**
     * Always completes successfully.
     */
    protected boolean onFinish()
    {
        return true;
    }

}