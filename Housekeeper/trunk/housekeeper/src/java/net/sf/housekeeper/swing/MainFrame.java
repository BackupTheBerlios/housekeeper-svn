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
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.PersistenceController;
import net.sf.housekeeper.persistence.UnsupportedFileVersionException;
import net.sf.housekeeper.util.ConfigurationManager;
import net.sf.housekeeper.util.LocalisationManager;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;

/**
 * The main frame of the Housekeeper application GUI.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class MainFrame
{

    /**
     * Log to be used for this class.
     */
    private static final Log LOG = LogFactory.getLog(MainFrame.class);

    /**
     * The view of this main frame.
     */
    private final JFrame     view;

    /**
     * The domain of this application.
     */
    private final Household  household;

    /**
     * Initializes and packs a new main frame.
     * 
     * @param household The domain for this application.
     */
    public MainFrame(final Household household)
    {
        super();
        this.household = household;

        view = new JFrame();

        initLookAndFeel();

        final String version = ConfigurationManager.INSTANCE.getConfiguration()
                .getString(ConfigurationManager.HOUSEKEEPER_VERSION);
        view.setTitle("Housekeeper " + version);

        view.setJMenuBar(buildMenuBar());
        view.getContentPane().add(buildComponents());

        view.pack();
        view.setLocationRelativeTo(null);

        //Use the controller for exiting the application
        view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        view.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e)
            {
                exitApplication();
            }
        });
    }

    /**
     * Displays this main frame.
     */
    public void show()
    {
        view.show();
        try
        {
            PersistenceController.instance().replaceDomainWithSaved(household);
        } catch (IOException e1)
        {
            //Nothing wrong about that
        } catch (UnsupportedFileVersionException e1)
        {
            LOG.error("Unsupported file format: " + e1.getVersion(), e1);
            final String error = LocalisationManager.INSTANCE
                    .getText("gui.error");
            JOptionPane.showMessageDialog(view, e1.getLocalizedMessage(),
                                          error, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Builds the components of the frame and adds them to the content pane.
     * 
     * @return The created components. Is not null.
     */
    private Component buildComponents()
    {
        final JTabbedPane tabbedPane = new JTabbedPane();
        final String supplyTabName = LocalisationManager.INSTANCE
                .getText("domain.food");
        tabbedPane.addTab(supplyTabName, new SupplyPanel(view, household
                .getFoodManager()));
        return tabbedPane;
    }

    /**
     * Builds the menus.
     * 
     * @return The created menu bar. Is not null.
     */
    private JMenuBar buildMenuBar()
    {
        final JMenuBar menuBar = new JMenuBar();

        //File Menu
        final String fileMenuLabel = LocalisationManager.INSTANCE
                .getText("gui.menu.file");
        final JMenu menuFile = new JMenu(fileMenuLabel);
        menuBar.add(menuFile);

        menuFile.add(new JMenuItem(new LoadDataAction()));
        menuFile.add(new JMenuItem(new SaveDataAction()));
        menuFile.addSeparator();
        menuFile.add(new JMenuItem(new ExitAction()));

        //Help Menu
        final String helpMenuString = LocalisationManager.INSTANCE
                .getText("gui.menu.help");
        final JMenu menuHelp = new JMenu(helpMenuString);
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

        //Only show dialog for saving before exiting if any data has been
        // changed
        if (household.hasChanged())
        {
            final String question = LocalisationManager.INSTANCE
                    .getText("gui.mainFrame.saveModificationsQuestion");
            final int option = JOptionPane.showConfirmDialog(view, question);

            //If user choses yes try to save. If that fails do not exit.
            if (option == JOptionPane.YES_OPTION)
            {
                try
                {
                    PersistenceController.instance().saveDomainData(household);
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
        }

        if (exit)
        {
            System.exit(0);
        }
    }

    /**
     * Shows a dialog which informs the user that saving was not succesful.
     *  
     */
    private void showSavingErrorDialog()
    {
        final String message = LocalisationManager.INSTANCE
                .getText("gui.mainFrame.saveError");
        final String error = LocalisationManager.INSTANCE.getText("gui.error");
        JOptionPane.showMessageDialog(view, message, error,
                                      JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows the About dialog for the application.
     */
    private void showAboutDialog()
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
                        .error("Attempted to read a bad URL: " + helpURL, ex);
            }
        } else
        {
            LogFactory.getLog(MainFrame.AboutDialogAction.class)
                    .error("Could not find file: " + aboutFile);
        }

        editorPane.setPreferredSize(new Dimension(400, 200));
        JOptionPane.showMessageDialog(view, editorPane);
    }

    /**
     * Saves the current domain objects to a persistent storage.
     */
    private void saveDomainData()
    {
        try
        {
            PersistenceController.instance().saveDomainData(household);
        } catch (IOException e1)
        {
            e1.printStackTrace();
            showSavingErrorDialog();
        }
    }

    /**
     * Replaces the current domain objects from the persistent storage.
     */
    private void loadDomainData()
    {
        try
        {
            PersistenceController.instance().replaceDomainWithSaved(household);
        } catch (FileNotFoundException exception)
        {
            LOG.error("Could not load domain data", exception);
            final String error = LocalisationManager.INSTANCE
                    .getText("gui.error");
            final String nodata = LocalisationManager.INSTANCE
                    .getText("gui.mainFrame.nodata");
            JOptionPane.showMessageDialog(view, nodata, error,
                                          JOptionPane.ERROR_MESSAGE);
        } catch (Exception exception)
        {
            LOG.error("Could not load domain data", exception);
            final String error = LocalisationManager.INSTANCE
                    .getText("gui.error");
            JOptionPane.showMessageDialog(view,
                                          exception.getLocalizedMessage(),
                                          error, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Action for exiting the application.
     */
    private class ExitAction extends AbstractAction
    {

        private ExitAction()
        {
            super();
            final String name = LocalisationManager.INSTANCE
                    .getText("gui.mainFrame.exit");
            putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent e)
        {
            exitApplication();
        }
    }

    /**
     * Action for loading the data.
     */
    private class LoadDataAction extends AbstractAction
    {

        private LoadDataAction()
        {
            super();
            final String name = LocalisationManager.INSTANCE
                    .getText("gui.mainFrame.load");
            putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent e)
        {
            loadDomainData();
        }

    }

    /**
     * Action for persistently saving the data.
     */
    private class SaveDataAction extends AbstractAction
    {

        private SaveDataAction()
        {
            super();
            final String name = LocalisationManager.INSTANCE
                    .getText("gui.mainFrame.save");
            putValue(Action.NAME, name);
        }

        public void actionPerformed(ActionEvent e)
        {
            saveDomainData();
        }
    }

    /**
     * Action for displaying the about dialog.
     */
    private class AboutDialogAction extends AbstractAction
    {

        private AboutDialogAction()
        {
            super();
            final String name = LocalisationManager.INSTANCE
                    .getText("gui.mainFrame.about");
            putValue(Action.NAME, name);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e)
        {
            showAboutDialog();
        }
    }
}