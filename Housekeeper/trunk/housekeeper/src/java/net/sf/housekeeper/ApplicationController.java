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

import java.io.File;
import java.io.IOException;

import net.sf.housekeeper.swing.MainFrame;
import net.sf.housekeeper.util.ConfigurationManager;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.LogFactory;

/**
 * Class containing the <code>main</code> method.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ApplicationController
{

    /**
     * The singleton instance.
     */
    private static ApplicationController instance;
    
    /**
     * Prevents instances of this class.
     * 
     * @throws IOException
     * @throws ConfigurationException
     */
    private ApplicationController(String[] args) throws ConfigurationException,
            IOException
    {
        initializeConfigurationManager(); 
    }
    
    /**
     * Starts the application.
     *
     */
    public void start()
    {
        logVersionInfo();

        final MainFrame main = new MainFrame();
        main.show();
    }
    
    /**
     * Exits the application.
     *
     */
    public void exit()
    {
        LogFactory.getLog(ApplicationController.class).info("Exiting Housekeeper...");
        System.exit(0);
    }
    
    /**
     * Returns the application controller Singleton.
     * 
     * @return The application controller instance.
     */
    public static ApplicationController instance()
    {
        return instance;
    }

    /**
     * Initializes the {@link ConfigurationManager}.
     * 
     * @throws ConfigurationException
     * @throws IllegalStateException
     * @throws IOException
     */
    private void initializeConfigurationManager()
            throws ConfigurationException, IllegalStateException, IOException
    {
        final String homeDirString = System.getProperty("user.home");
        final File hkDir = new File(homeDirString, ".housekeeper");
        hkDir.mkdir();
        ConfigurationManager.INSTANCE.init(hkDir);
    }

    /**
     * Prints an application starting message including a version information.
     *  
     */
    private void logVersionInfo()
    {
        final String version = ConfigurationManager.INSTANCE.getConfiguration()
                .getString(ConfigurationManager.HOUSEKEEPER_VERSION);
        LogFactory.getLog(ApplicationController.class).info(
                                                  "Starting Housekeeper "
                                                          + version);
    }

    /**
     * Starts the Housekeeper application with a Swing GUI.
     * 
     * @param args
     * @throws IOException
     * @throws ConfigurationException
     */
    public static void main(final String[] args) throws ConfigurationException,
            IOException
    {
        if (!SystemUtils.isJavaVersionAtLeast(140))
        {
            LogFactory.getLog(ApplicationController.class)
                    .fatal("You need at least JRE 1.4 to run Housekeeper!");
            System.exit(1);
        }
        instance = new ApplicationController(args);
        instance.start();
    }

}