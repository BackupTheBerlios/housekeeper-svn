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

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Locates icons in the classpath and generates {@link ImageIcon}obejcts.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class IconGenerator
{

    private static final String ICON_LOCATION = "/net/sf/housekeeper/images/";

    private IconGenerator()
    {

    }

    /**
     * Locates an icon with the given file name and creates an {@link ImageIcon}
     * object for it.
     * 
     * @param fileName The name of the icon file. Must not be null.
     * @return The icon. Is not null.
     * @throws IllegalArgumentException if the icon cannot be located.
     */
    public static ImageIcon getIcon(final String fileName)
            throws IllegalArgumentException
    {
        final URL url = IconGenerator.class.getResource(ICON_LOCATION
                + fileName);
        if (url == null)
        {
            throw new IllegalArgumentException("No icon found at " + url);
        }
        return new ImageIcon(url);
    }
}