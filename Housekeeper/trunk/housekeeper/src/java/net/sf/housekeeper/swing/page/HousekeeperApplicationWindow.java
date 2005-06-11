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

package net.sf.housekeeper.swing.page;

import org.springframework.richclient.application.ApplicationPage;
import org.springframework.richclient.application.PageDescriptor;
import org.springframework.richclient.application.support.DefaultApplicationWindow;

/**
 * An {@link org.springframework.richclient.application.ApplicationWindow} using
 * {@link net.sf.housekeeper.swing.page.HousekeeperApplicationPage}s.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class HousekeeperApplicationWindow extends
        DefaultApplicationWindow
{

    protected ApplicationPage createPage(PageDescriptor descriptor)
    {
        return new HousekeeperApplicationPage(this, descriptor);

    }

}