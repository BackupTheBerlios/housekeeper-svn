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

package net.sf.housekeeper.swing.util;

import org.springframework.binding.validation.Severity;
import org.springframework.richclient.application.support.ApplicationServicesAccessor;
import org.springframework.richclient.core.Message;
import org.springframework.richclient.dialog.MessageDialog;

/**
 * Factory for creating localized
 * {@link org.springframework.richclient.core.Message}s and {@link org.springframework.richclient.dialog.MessageDialog}s from message IDs.
 * 
 * @author Adrian Gygax
 */
public final class MessageFactory extends ApplicationServicesAccessor
{

    /**
     * Singleton instance
     */
    public static final MessageFactory INSTANCE = new MessageFactory();

    /**
     * Message ID for getting the localized string fot the dialog title.
     */
    private static final String DIALOG_TITLE_MESSAGE_ID = "gui.error";

    private MessageFactory()
    {

    }

    /**
     * Creates a localized {@link Message} of severity {@link Severity#ERROR} for a given message ID.
     * 
     * @param messageID Message ID of the localized message.
     * @param exception If != null, the exception's message is added.
     * @return != null
     */
    public Message createErrorMessage(String messageID, Exception exception)
    {
        final String messageString;
        final StringBuffer messageBuffer = new StringBuffer();
        if (messageID != null)
        {
            messageString = getMessage(messageID);

            messageBuffer.append(messageString);
        } else
        {
            messageString = "";
        }

        if (exception != null)
        {
            if (!messageString.equals(""))
            {
                messageBuffer.append("\n");
            }
            messageBuffer.append(exception.getLocalizedMessage());
        }

        final Message errorMessage = new Message(messageBuffer.toString(),
                Severity.ERROR);
        return errorMessage;
    }

    /**
     * Creates a {@link MessageDialog} for an Exception.
     * 
     * @param exception The exception to create the dialog from.
     * @return != null
     */
    public MessageDialog createErrorMessageDialog(Exception exception)
    {
        return createErrorMessageDialog(null, exception);
    }

    /**
     *  Creates a {@link MessageDialog} for with a localized message and the message of an Exception.
     * 
     * @param messageID The ID of the localized message.
     * @param exception The exception.
     * @return != null
     */
    public MessageDialog createErrorMessageDialog(String messageID,
            Exception exception)
    {
        final Message message = createErrorMessage(messageID, exception);

        final String title = getMessage(DIALOG_TITLE_MESSAGE_ID);
        final MessageDialog dialog = new MessageDialog(title, message);
        return dialog;
    }

}
