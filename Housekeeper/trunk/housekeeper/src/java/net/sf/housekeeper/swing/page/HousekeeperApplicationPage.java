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

package net.sf.housekeeper.swing.page;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import net.sf.housekeeper.swing.util.UIFSplitPane;

import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.application.PageComponent;
import org.springframework.richclient.application.PageComponentListener;
import org.springframework.richclient.application.PageComponentPane;
import org.springframework.richclient.application.PageDescriptor;
import org.springframework.richclient.application.support.DefaultApplicationPage;
import org.springframework.richclient.control.SimpleInternalFrame;

/**
 * An ApplicationPage which lays out the Housekeeper views.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class HousekeeperApplicationPage extends DefaultApplicationPage
{

    private final UIFSplitPane rootSplitPane;

    /**
     * Creates a new page
     * 
     * @param window
     * @param pageDescriptor
     */
    public HousekeeperApplicationPage(ApplicationWindow window,
            PageDescriptor pageDescriptor)
    {
        super(window, pageDescriptor);
        rootSplitPane = new UIFSplitPane();
        rootSplitPane.setDividerLocation(200);
        rootSplitPane.setOneTouchExpandable(false);
        rootSplitPane.setDividerSize(3);

        addPageComponentListener(new PageComponentListener() {

            public void componentOpened(PageComponent component)
            {

            }

            public void componentFocusGained(PageComponent component)
            {
                final SimpleInternalFrame frame = (SimpleInternalFrame) component
                        .getContext().getPane().getControl();
                frame.setSelected(true);
            }

            public void componentFocusLost(PageComponent component)
            {
                final SimpleInternalFrame frame = (SimpleInternalFrame) component
                        .getContext().getPane().getControl();
                frame.setSelected(false);

            }

            public void componentClosed(PageComponent component)
            {

            }
        });
        
        getControl().add(rootSplitPane);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.support.AbstractApplicationPage#giveFocusTo(org.springframework.richclient.application.PageComponent)
     */
    protected boolean giveFocusTo(PageComponent pageComponent)
    {
        PageComponentPane pane = pageComponent.getContext().getPane();
        //pane.requestFocusInWindow();

        fireFocusGained(pageComponent);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.richclient.application.support.AbstractApplicationPage#addPageComponent(org.springframework.richclient.application.PageComponent)
     */
    protected void addPageComponent(final PageComponent pageComponent)
    {
        final String componentID = pageComponent.getId();

        final SimpleInternalFrame pageControl = (SimpleInternalFrame) pageComponent
                .getContext().getPane().getControl();
        pageControl.setSelected(false);
        addMouseListener(pageControl, new MouseAdapter() {

            public void mousePressed(MouseEvent e)
            {
                if (getActiveComponent() != pageComponent)
                {
                    showView(componentID);
                }

            }

        });

        if (componentID.equals("supplyView"))
        {
            rootSplitPane.setRightComponent(pageControl);
        } else if (componentID.equals("categoryView"))
        {
            rootSplitPane.setLeftComponent(pageControl);
        }

        super.addPageComponent(pageComponent);
    }

    /**
     * Adds a listener to a Component and all its children.
     * 
     * @param comp The component.
     * @param listener The listener.
     */
    private void addMouseListener(final Component comp,
                                  final MouseListener listener)
    {
        comp.addMouseListener(listener);

        if (comp instanceof Container)
        {
            final Component[] children = ((Container) comp).getComponents();
            for (int i = 0; i < children.length; i++)
            {
                addMouseListener(children[i], listener);
            }

        }

    }

}
