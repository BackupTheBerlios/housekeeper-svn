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

package net.sf.housekeeper.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import net.sf.housekeeper.ApplicationController;
import net.sf.housekeeper.persistence.PersistenceServiceFactory;
import net.sf.housekeeper.util.ConfigurationManager;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;

/**
 * The Main Frame of the Housekeeper application.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class MainFrame extends JFrame
{

    /** Singleton instance. */
    public static final MainFrame INSTANCE = new MainFrame();

    /**
     * Log to be used for this class.
     */
    private final Log             LOG      = LogFactory.getLog(MainFrame.class);

    /**
     * Creates a new MainFrame object.
     */
    private MainFrame()
    {
        super();
        initLookAndFeel();
        
        final String version = ConfigurationManager.INSTANCE.getConfiguration()
                .getString(ConfigurationManager.HOUSEKEEPER_VERSION);
        setTitle("Housekeeper " + version);

        setJMenuBar(buildMenuBar());
        getContentPane().add(buildComponents());

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Builds the components of the frame and adds them to the content pane.
     */
    private Component buildComponents()
    {
        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Supply", new SupplyPanel());
        return tabbedPane;
    }

    /**
     * Builds the menus.
     */
    private JMenuBar buildMenuBar()
    {
        final JMenuBar menuBar = new JMenuBar();

        //File Menu
        final JMenu menuFile = new JMenu("File");
        menuBar.add(menuFile);

        menuFile.add(new JMenuItem(new LoadDataAction()));
        menuFile.add(new JMenuItem(new SaveDataAction()));
        menuFile.addSeparator();
        menuFile.add(new JMenuItem(new ExitAction()));

        //Help Menu
        final JMenu menuHelp = new JMenu("Help");
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuHelp);

        menuHelp.add(new JMenuItem(new AboutDialogAction()));

        return menuBar;
    }

    /**
     * Initializes the Look and Feel.
     */
    private void initLookAndFeel()
    {

        if (SystemUtils.IS_OS_MAC_OSX)
        {
            try
            {
                UIManager.setLookAndFeel(UIManager
                        .getSystemLookAndFeelClassName());
            } catch (Exception e)
            {
                //Do nothing if setting the Look and Feel fails.
            }
        } else
        {
            try
            {
                UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
            } catch (Exception e)
            {
                //Do nothing if setting the Look and Feel fails.
            }
        }

        LOG.debug("Using Look and Feel: "
                + UIManager.getLookAndFeel().getName());
    }

    /**
     * Action for exiting the application.
     */
    private static class ExitAction extends AbstractAction
    {

        private ExitAction()
        {
            super("Exit");
        }

        public void actionPerformed(ActionEvent e)
        {
            ApplicationController.instance().exit();
        }
    }

    /**
     * Action to load the data.
     */
    private static class LoadDataAction extends AbstractAction
    {

        private LoadDataAction()
        {
            super("Load Data");
        }

        public void actionPerformed(ActionEvent e)
        {
            try
            {
                PersistenceServiceFactory.getCurrentService().loadData();
            } catch (FileNotFoundException exception)
            {
                JOptionPane.showMessageDialog(MainFrame.INSTANCE,
                                              "No data has been saved yet.",
                                              "Error",
                                              JOptionPane.ERROR_MESSAGE);
            } catch (Exception exception)
            {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(MainFrame.INSTANCE, exception
                        .getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Action to persistently save the data.
     */
    private static class SaveDataAction extends AbstractAction
    {

        private SaveDataAction()
        {
            super("Save Data");
        }

        public void actionPerformed(ActionEvent e)
        {
            try
            {
                PersistenceServiceFactory.getCurrentService().saveData();
            } catch (IOException e1)
            {
                e1.printStackTrace();
                JOptionPane
                        .showMessageDialog(
                                           MainFrame.INSTANCE,
                                           "There was a problem saving the data.\nYour modifications have NOT been saved.",
                                           "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static class AboutDialogAction extends AbstractAction
    {

        private AboutDialogAction()
        {
            super("About");
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {

            final JEditorPane editorPane = new JEditorPane();
            editorPane.setEditable(false);
            final String aboutFile = "/net/sf/housekeeper/about.html";
            final URL helpURL = MainFrame.class.getResource(aboutFile);
            if (helpURL != null)
            {
                try
                {
                    editorPane.setPage(helpURL);
                } catch (IOException ex)
                {
                    LogFactory.getLog(MainFrame.AboutDialogAction.class)
                            .error("Attempted to read a bad URL: " + helpURL,
                                   ex);
                }
            } else
            {
                LogFactory.getLog(MainFrame.AboutDialogAction.class)
                        .error("Could not find file: " + aboutFile);
            }

            editorPane.setPreferredSize(new Dimension(400, 200));
            JOptionPane.showMessageDialog(MainFrame.INSTANCE, editorPane);

        }
    }
}