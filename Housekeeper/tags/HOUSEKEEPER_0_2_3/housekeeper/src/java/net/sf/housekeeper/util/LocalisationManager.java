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

package net.sf.housekeeper.util;

import java.util.ResourceBundle;

/**
 * Provides access to text translations for the current locale.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class LocalisationManager
{

    /**
     * Singleton instance.
     */
    public static final LocalisationManager INSTANCE = new LocalisationManager();

    /**
     * Holds the translations for being used in the user interface.
     */
    private final ResourceBundle            resourceBundle;

    /**
     * Initializes the ResourceBundle with translations suitable for the current
     * locale.
     */
    private LocalisationManager()
    {
        final String baseName = "net.sf.housekeeper.translations";
        resourceBundle = ResourceBundle.getBundle(baseName);
    }

    /**
     * Returns the translated text for the specified key. A list of valid keys
     * can be found in the default translation file.
     * 
     * @param key The key for looking up the translated text.
     * @return The translated text.
     */
    public String getText(final String key)
    {
        return resourceBundle.getString(key);
    }

}