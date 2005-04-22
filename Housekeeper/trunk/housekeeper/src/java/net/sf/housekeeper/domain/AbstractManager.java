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

package net.sf.housekeeper.domain;


/**
 * Supertype for all managers.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
abstract class AbstractManager
{

    /**
     * Flag indicating if any data has been changed.
     */
    private boolean hasChanged;

    /**
     * Resets the status of the "has changed" attribute. After calling this,
     * {@link #hasChanged()}returns false.
     */
    public void resetChangedStatus()
    {
        hasChanged = false;
    }

    /**
     * Returns true if any data has been changed since the last call to
     * {@link #resetChangedStatus()}.
     * 
     * @return True if any dat has changed, false otherwise.
     */
    public boolean hasChanged()
    {
        return hasChanged;
    }
    
    /**
     * Set this manager to "has changed".
     */
    protected void setChanged()
    {
        hasChanged = true;
    }

}
