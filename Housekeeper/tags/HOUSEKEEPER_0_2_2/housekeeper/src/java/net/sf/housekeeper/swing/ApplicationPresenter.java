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

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.UIManager;

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.PersistenceController;
import net.sf.housekeeper.persistence.UnsupportedFileVersionException;
import net.sf.housekeeper.swing.util.IconGenerator;
import net.sf.housekeeper.util.ConfigurationManager;
import net.sf.housekeeper.util.LocalisationManager;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;

/**
 * Presenter for the main frame of the Housekeeper application GUI.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ApplicationPresenter
{

    /**
     * Log to be used for this class.
     */
    private static final Log      LOG = LogFactory.getLog(ApplicationPresenter.class);

    /**
     * The domain of this application.
     */
    private final Household       household;

    /**
     * The view of this main frame.
     */
    private final ApplicationView view;

    /**
     * Initializes and packs a new main frame.
     * 
     * @param household The domain for this application.
     */
    public ApplicationPresenter(final Household household)
    {
        super();
        this.household = household;

        initLookAndFeel();

        final SupplyPresenter supplyPresenter = new SupplyPresenter(household
                .getFoodManager());


        view = new ApplicationView(supplyPresenter.getView(),
                new LoadDataAction(), new SaveDataAction(), new ExitAction(),
                new AboutDialogAction());
        final String version = ConfigurationManager.INSTANCE.getConfiguration()
        .getString(ConfigurationManager.HOUSEKEEPER_VERSION);
        view.setTitle("Housekeeper " + version);

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
            view.showErrorDialog(e1.getLocalizedMessage());
        }
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
            final int option = view.showConfirmDialog(question);

            //If user choses yes try to save. If that fails do not exit.
            if (option == ApplicationView.YES)
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
            } else if (option == ApplicationView.CANCEL)
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
            final String nodata = LocalisationManager.INSTANCE
                    .getText("gui.mainFrame.nodata");
            view.showErrorDialog(nodata);
        } catch (Exception exception)
        {
            LOG.error("Could not load domain data", exception);
            view.showErrorDialog(exception.getLocalizedMessage());
        }
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
            LOG.error("Error while saving.", e1);
            showSavingErrorDialog();
        }
    }

    /**
     * Shows the About dialog for the application.
     */
    private void showAboutDialog()
    {
        final String aboutFile = "/net/sf/housekeeper/about.html";
        final URL helpURL = ApplicationPresenter.class.getResource(aboutFile);
        if (helpURL != null)
        {
            try
            {
                view.showAboutDialog(helpURL);
            } catch (IOException ex)
            {
                LogFactory.getLog(ApplicationPresenter.AboutDialogAction.class)
                        .error("Attempted to read a bad URL: " + helpURL, ex);
            }
        } else
        {
            LogFactory.getLog(ApplicationPresenter.AboutDialogAction.class)
                    .error("Could not find file: " + aboutFile);
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
        view.showErrorDialog(message);
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
            putValue(Action.SMALL_ICON, IconGenerator.getIcon("fileexit.png"));
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
            putValue(Action.SMALL_ICON, IconGenerator.getIcon("fileopen.png"));
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
            putValue(Action.SMALL_ICON, IconGenerator.getIcon("filesave.png"));
        }

        public void actionPerformed(ActionEvent e)
        {
            saveDomainData();
        }
    }
}