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

package net.sf.housekeeper.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.richclient.application.config.UIManagerConfigurer;
import org.springframework.util.Assert;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticTheme;

/**
 * Configures the Look&Feel depending on the platform.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class PlatformAwareUIConfigurer implements InitializingBean
{

    private static final String OS_NAME = System.getProperty("os.name");

    private UIManagerConfigurer configurer;

    private static final String MAC_OS_X = "Mac OS X";

    private static final Log LOG = LogFactory
            .getLog(PlatformAwareUIConfigurer.class);

    /**
     * The configurer to use for selecting the L&F.
     * 
     * @param configurer the configurer.
     */
    public void setConfigurer(final UIManagerConfigurer configurer)
    {
        this.configurer = configurer;
    }

    /**
     * The theme to use for the JGoodies L&F. If omitted, the default one is
     * used.
     * 
     * @param theme != null.
     */
    public void setTheme(PlasticTheme theme)
    {
        Assert.notNull(theme);
        PlasticLookAndFeel.setMyCurrentTheme(theme);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception
    {
        if (configurer == null)
        {
            configurer = new UIManagerConfigurer();
        }

        if (LookUtils.IS_OS_WINDOWS)
        {
            LOG.info("Windows detected");
            setLookAndFeel(Options.JGOODIES_WINDOWS_NAME);
        } else if (isOS((MAC_OS_X)))
        {
            LOG.info("Macintosh OS X detected");
            setLookAndFeel(Options.getSystemLookAndFeelClassName());
        } else
        {
            setLookAndFeel(Options.PLASTICXP_NAME);
        }
    }

    private boolean isOS(final String os)
    {
        return OS_NAME.startsWith(os);
    }

    private void setLookAndFeel(String name)
    {
        if (configurer == null)
        {
            configurer = new UIManagerConfigurer();
        }

        LOG.info("Using Look&Feel: " + name);
        configurer.setLookAndFeel(name);
    }

}