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

package net.sf.housekeeper;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.housekeeper.swing.MainFrame;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.LogFactory;

/**
 * Class containing the <code>main</code> method.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class Housekeeper
{

    /** The version of the last release. */
    public static final String VERSION = "0.1.2";

    /**
     * Prevents instances of this class.
     */
    private Housekeeper(String[] args)
    {
        if (args.length > 0 && args[0].equals("--debug"))
        {
            enableDebugMessages();
        }
        
        LogFactory.getLog(Housekeeper.class).info("Starting Housekeeper " + VERSION);
        
        MainFrame.INSTANCE.show();
    }
    
    /**
     * Configures the logging component for printing debug messages to the
     * console.
     */
    private void enableDebugMessages()
    {
        final Handler[] h = Logger.getLogger("").getHandlers();
        h[0].setLevel(Level.ALL);
        Logger.getLogger("").setLevel(Level.ALL);
    }

    /**
     * Starts the Housekeeper application with a Swing GUI.
     * 
     * @param args "--debug" enables debugging messages.
     */
    public static void main(final String[] args)
    {
        if(!SystemUtils.isJavaVersionAtLeast(140))
        {
            System.err.println("You need at least JRE 1.4 to run Housekeeper!");
            System.exit(1);
        }
        new Housekeeper(args);
    }

}