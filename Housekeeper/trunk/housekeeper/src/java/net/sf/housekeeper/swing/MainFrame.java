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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import net.sf.housekeeper.persistence.PersistenceController;
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

    /**
     * Log to be used for this class.
     */
    private static final Log             LOG      = LogFactory.getLog(MainFrame.class);

    /**
     * Initializes and packs a new main frame.
     */
    public MainFrame()
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
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e)
            {
               exitApplication();
            }
        });
    }

    /**
     * Builds the components of the frame and adds them to the content pane.
     */
    private Component buildComponents()
    {
        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Supply", new SupplyPanel(this));
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
     * Asks if data should be saved before it terminates the application.
     */
    private void exitApplication()
    {
        boolean exit = true;
        
        final int option = JOptionPane.showConfirmDialog(this, "Save modifications before exiting?");
        if (option == JOptionPane.YES_OPTION)
        {
            try
            {
                PersistenceController.saveDomainData();
            } catch (IOException e)
            {
                exit = false;
                showSavingErrorDialog();
                e.printStackTrace();
            }
        } else if (option == JOptionPane.CANCEL_OPTION)
        {
            exit = false;
        }
        
        if (exit)
        {
            ApplicationController.instance().exit();
        }
    }
    
    /**
     * Shows a dialog which informs the user that saving was not succesful.
     *
     */
    private void showSavingErrorDialog()
    {
        JOptionPane
        .showMessageDialog(
                           this,
                           "There was a problem saving the data.\n" +
                           "Your modifications have NOT been saved.",
                           "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Action for exiting the application.
     */
    private class ExitAction extends AbstractAction
    {

        private ExitAction()
        {
            super("Exit");
        }

        public void actionPerformed(ActionEvent e)
        {
            exitApplication();
        }
    }

    /**
     * Action to load the data.
     */
    private class LoadDataAction extends AbstractAction
    {

        private LoadDataAction()
        {
            super("Load Data");
        }

        public void actionPerformed(ActionEvent e)
        {
            try
            {
                PersistenceController.replaceDomainWithSaved();
            } catch (FileNotFoundException exception)
            {
                JOptionPane.showMessageDialog(MainFrame.this,
                                              "No data has been saved yet.",
                                              "Error",
                                              JOptionPane.ERROR_MESSAGE);
            } catch (Exception exception)
            {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(MainFrame.this, exception
                        .getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Action to persistently save the data.
     */
    private class SaveDataAction extends AbstractAction
    {

        private SaveDataAction()
        {
            super("Save Data");
        }

        public void actionPerformed(ActionEvent e)
        {
            try
            {
                PersistenceController.saveDomainData();
            } catch (IOException e1)
            {
                e1.printStackTrace();
                showSavingErrorDialog();
            }
        }
    }

    private class AboutDialogAction extends AbstractAction
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
            JOptionPane.showMessageDialog(MainFrame.this, editorPane);

        }
    }
}