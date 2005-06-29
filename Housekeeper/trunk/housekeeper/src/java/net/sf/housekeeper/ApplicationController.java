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

package net.sf.housekeeper;

import org.apache.commons.logging.LogFactory;
import org.springframework.core.JdkVersion;
import org.springframework.richclient.application.ApplicationLauncher;

/**
 * Has the <code>main</code> method which configures the Spring-RCP
 * <code>ApplicationLauncher</code> and starts the application.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ApplicationController
{

    /**
     * Prevents instances of this class.
     */
    private ApplicationController()
    {

    }

    /**
     * Starts the Housekeeper application with a Swing GUI.
     * 
     * @param args No parameters are used.
     */
    public static void main(final String[] args)
    {
        final int jreVersion = JdkVersion.getMajorJavaVersion();
        final int minVersion = JdkVersion.JAVA_14;
        if (jreVersion < minVersion)
        {
            LogFactory.getLog(ApplicationController.class)
                    .fatal("You need at least JRE 1.4 to run Housekeeper!");
            System.exit(1);
        }

        String rootContextDirectoryClassPath = "/net/sf/housekeeper/config/";
        String startupContextPath = rootContextDirectoryClassPath
                + "startup-context.xml";
        String applicationContextPath = rootContextDirectoryClassPath
                + "application-context.xml";
        String domainContextPath = rootContextDirectoryClassPath
                + "domain-context.xml";
        new ApplicationLauncher(startupContextPath, new String[] {
                applicationContextPath, domainContextPath });
    }

}