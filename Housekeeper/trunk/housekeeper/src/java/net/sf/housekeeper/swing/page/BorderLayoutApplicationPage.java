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

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.application.PageComponent;
import org.springframework.richclient.application.PageComponentDescriptor;
import org.springframework.richclient.application.PageComponentPane;
import org.springframework.richclient.application.PageDescriptor;
import org.springframework.richclient.application.support.AbstractApplicationPage;
import org.springframework.richclient.application.support.DefaultViewContext;

/**
 * @author
 * @version $Revision$, $Date$
 */
public final class BorderLayoutApplicationPage extends AbstractApplicationPage
        implements BorderLayoutPageLayoutBuilder
{

    private JPanel control;

    
    /**
     * @param window
     * @param pageDescriptor
     */
    public BorderLayoutApplicationPage(ApplicationWindow window,
            PageDescriptor pageDescriptor)
    {
        super(window, pageDescriptor);
    }

    public JComponent getControl()
    {
        if (control == null)
        {
            this.control = new JPanel(new BorderLayout());
            this.getPageDescriptor().buildInitialLayout(this);
            setActiveComponent();
        }
        return control;
    }

    protected boolean giveFocusTo(PageComponent pageComponent)
    {
        PageComponentPane pane = pageComponent.getContext().getPane();
        pane.requestFocusInWindow();

        fireFocusGained(pageComponent);

        return true;
    }

    protected PageComponent createPageComponent(
                                                PageComponentDescriptor pageComponentDescriptor)
    {
        PageComponent pageComponent = pageComponentDescriptor
                .createPageComponent();
        pageComponent.setContext(new DefaultViewContext(this,
                new PageComponentPane(pageComponent)));

        // trigger the createControl method of the PageComponent, so if a
        // PageComponentListener is added
        // in the createControl method, the componentOpened event is received.
        pageComponent.getControl();

        return pageComponent;
    }

    // Initial Application Page Layout Builder methods
    public void addView(String viewDescriptorId)
    {
        addView(viewDescriptorId, BorderLayout.EAST);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.swing.page.BorderLayoutPageLayoutBuilder#addView(java.lang.String,
     *      java.lang.String)
     */
    public void addView(String descriptorID, String location)
    {
        PageComponent component = createPageComponent(getViewDescriptor(descriptorID));
        control.add(component.getContext().getPane().getControl(), location);
        addPageComponent(component);
        showView(descriptorID);
    }

}