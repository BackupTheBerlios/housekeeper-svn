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

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.UIManager;

import org.springframework.richclient.command.AbstractCommand;
import org.springframework.richclient.core.Message;
import org.springframework.richclient.dialog.ApplicationDialog;
import org.springframework.richclient.dialog.DefaultMessageAreaPane;
import org.springframework.rules.reporting.Severity;
import org.springframework.util.Assert;


/**
 * A default dialog for showing an error message.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class ErrorDialog extends ApplicationDialog
{
    private final String message;
    
    /**
     * Creates a dialog for showing an error message.
     * 
     * @param messageID The ID of the message to show.
     */
    public ErrorDialog(final String messageID) {
        super();
        Assert.notNull(messageID);
        
        message = getMessage(messageID);
        
        final String title = getMessage("gui.error");
        setTitle(title);
    }

    /** 
     * Always completes successfully.
     */
    protected boolean onFinish()
    {
        return true;
    }
    

    /**
     * Show only the OK button.
     */
    protected Object[] getCommandGroupMembers() {
        return new AbstractCommand[] { getFinishCommand() };
    }


    /**
     * Shows the error message.
     */
    protected JComponent createDialogContentPane()
    {
        final DefaultMessageAreaPane messagePane = new DefaultMessageAreaPane();
        Icon icon = UIManager.getIcon("OptionPane.errorIcon");
        messagePane.setDefaultIcon(icon);
        messagePane.setMessage(new Message(message, Severity.ERROR));
        return messagePane.getControl();
    }

}
