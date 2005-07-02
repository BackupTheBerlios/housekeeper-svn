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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.springframework.richclient.core.Message;
import org.springframework.richclient.dialog.DefaultMessageAreaModel;
import org.springframework.richclient.dialog.Messagable;
import org.springframework.richclient.dialog.MessagePane;
import org.springframework.richclient.factory.AbstractControlFactory;
import org.springframework.richclient.image.EmptyIcon;
import org.springframework.util.StringUtils;

/**
 * A MessagePane which uses a {@link javax.swing.JTextArea}to show the message.
 * Supports word wrapping.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class TextAreaPane extends AbstractControlFactory implements
        MessagePane, PropertyChangeListener
{

    private JTextArea messageTextArea;

    private Icon defaultIcon = EmptyIcon.SMALL;

    private DefaultMessageAreaModel messageAreaModel;

    /**
     * 
     */
    public TextAreaPane()
    {
        init(this);
    }

    /**
     * @param delegate
     */
    public TextAreaPane(Messagable delegate)
    {
        init(delegate);
    }

    private void init(Messagable delegate)
    {
        this.messageAreaModel = new DefaultMessageAreaModel(delegate);
        this.messageAreaModel.addPropertyChangeListener(this);
    }

    /**
     * @param defaultIcon
     */
    public void setDefaultIcon(Icon defaultIcon)
    {
        this.defaultIcon = defaultIcon;
    }

    protected JComponent createControl()
    {
        if (messageTextArea == null)
        {
            this.messageTextArea = createTextArea();
            this.messageAreaModel.renderMessage(messageTextArea);
        }

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        final JLabel iconLabel = new JLabel(getDefaultIcon());
        panel.add(iconLabel);

        panel.add(Box.createHorizontalStrut(10));

        final JScrollPane messageScroll = new JScrollPane(messageTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        messageScroll.setBorder(BorderFactory.createEmptyBorder());
        panel.add(messageScroll);

        return panel;
    }

    private Icon getDefaultIcon()
    {
        return defaultIcon;
    }

    public Message getMessage()
    {
        return messageAreaModel.getMessage();
    }

    public void setMessage(Message message)
    {
        messageAreaModel.setMessage(message);
        if (messageTextArea != null)
        {
            messageAreaModel.renderMessage(messageTextArea);
        }
    }

    /**
     * 
     */
    public void clearMessage()
    {
        messageAreaModel.setMessage(null);
    }

    public boolean isMessageShowing()
    {
        if (messageTextArea == null)
        {
            return false;
        }
        return StringUtils.hasText(messageTextArea.getText())
                && messageTextArea.isVisible();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        messageAreaModel.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener)
    {
        messageAreaModel.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        messageAreaModel.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener)
    {
        messageAreaModel.removePropertyChangeListener(propertyName, listener);
    }

    public void propertyChange(PropertyChangeEvent evt)
    {
        if (messageTextArea == null)
        {
            messageTextArea = createTextArea();
        }
        messageAreaModel.getMessage().renderMessage(messageTextArea);
    }

    private JTextArea createTextArea()
    {
        final JTextArea messageTextArea = new JTextArea();
        messageTextArea.setOpaque(false);
        messageTextArea.setEditable(false);
        messageTextArea.setLineWrap(true);
        messageTextArea.setWrapStyleWord(true);
        return messageTextArea;
    }

}