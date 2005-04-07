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

package net.sf.housekeeper.persistence;

/**
 * Signals that the format version of a data source is not supported.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class UnsupportedFileVersionException extends Exception
{

    /**
     * The unsupported version.
     */
    private final int version;

    /**
     * Constructs an exception using a default error message.
     * 
     * @param version The unsupported file version.
     */
    public UnsupportedFileVersionException(final int version)
    {
        super("File version is not supported: " + version);

        this.version = version;
    }

    /**
     * Returns the unsupported version.
     * 
     * @return The unsupported version.
     */
    public int getVersion()
    {
        return version;
    }

}