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

import java.awt.Image;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.Icon;

import org.springframework.richclient.application.PageDescriptor;
import org.springframework.richclient.application.PageLayoutBuilder;


/**
 * @author
 * @version $Revision$, $Date$
 */
public class BorderLayoutPageDescriptor implements PageDescriptor
{

    private String id;
    
    private Collection views;
    
    /* (non-Javadoc)
     * @see org.springframework.richclient.application.PageDescriptor#getId()
     */
    public String getId()
    {
        return id;
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.application.PageDescriptor#buildInitialLayout(org.springframework.richclient.application.PageLayoutBuilder)
     */
    public void buildInitialLayout(PageLayoutBuilder pageLayout)
    {
        final BorderLayoutPageLayoutBuilder borderLayoutBuilder = (BorderLayoutPageLayoutBuilder)pageLayout;
        final Iterator viewIterator = views.iterator();
        while (viewIterator.hasNext())
        {
            ViewLocationDescriptor view = (ViewLocationDescriptor) viewIterator.next();
            borderLayoutBuilder.addView(view.getViewDescriptorID(), view.getLocation());
        }        
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.core.DescribedElement#getDisplayName()
     */
    public String getDisplayName()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.core.DescribedElement#getCaption()
     */
    public String getCaption()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.core.DescribedElement#getDescription()
     */
    public String getDescription()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.core.VisualizedElement#getImage()
     */
    public Image getImage()
    {
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.richclient.core.VisualizedElement#getIcon()
     */
    public Icon getIcon()
    {
        return null;
    }
    
    /**
     * @param descriptors
     */
    public void setViews(Collection descriptors) {
        views = descriptors;
    }
    
    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

}
