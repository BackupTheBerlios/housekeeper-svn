/*
 * This file is part of Housekeeper.
 *
 * Housekeeper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Housekeeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Housekeeper; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA  02111-1307  USA
 *
 * Copyright 2003, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper;


import net.sourceforge.housekeeper.swing.MainFrame;

import javax.swing.JFrame;


/**
 * Class containing the <code>main</code> method.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
public final class Housekeeper
{
    //~ Static fields/initializers ---------------------------------------------

    /** The version of the last release. */
    public static final String VERSION = "0.1.1";

    //~ Constructors -----------------------------------------------------------

    /**
     * Prevents instances of this class
     */
    private Housekeeper()
    {
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Starts the Housekeeper application with a Swing GUI.
     *
     * @param args Not used
     */
    public static void main(String[] args)
    {
        JFrame mainFrame = MainFrame.INSTANCE;

        mainFrame.show();
    }
}
