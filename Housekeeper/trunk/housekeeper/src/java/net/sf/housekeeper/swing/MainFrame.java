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
import java.io.FileNotFoundException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import net.sf.housekeeper.Housekeeper;
import net.sf.housekeeper.storage.StorageFactory;
import net.sf.housekeeper.swing.util.ComponentCenterer;

import com.jgoodies.plaf.Options;
import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;

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

    private JMenu                 menuFile;

    private JMenuBar              menuBar;

    private JTabbedPane           tabbedPane;

    /**
     * Creates a new MainFrame object.
     */
    private MainFrame()
    {
        super();

        initLookAndFeel();
        buildMenus();
        buildComponents();

        setTitle("Housekeeper " + Housekeeper.VERSION);
        pack();
        ComponentCenterer.centerOnScreen(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Builds the components of the frame and adds them to the content pane.
     */
    private void buildComponents()
    {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Stock", StockPanel.getInstance());
        getContentPane().add(tabbedPane);
    }

    /**
     * Builds the menus.
     */
    private void buildMenus()
    {
        //Menubar
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //File Menu
        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        menuFile.add(new JMenuItem(new LoadDataAction()));
        menuFile.add(new JMenuItem(new SaveDataAction()));
        menuFile.addSeparator();
        menuFile.add(new JMenuItem(new ExitAction()));

    }

    /**
     * Initializes the Look and Feel.
     */
    private void initLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
        } catch (Exception e)
        {
            System.err.println("Failed to set the Look and Feel");
        }

        UIManager.put("Application.useSystemFontSettings", Boolean.TRUE);
        UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, Boolean.TRUE);
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
            System.exit(0);
        }
    }

    /**
     * Action to cause the permanent saving of the data.
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
                StorageFactory.getCurrentStorage().loadData();
            } catch (FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Action to cause the permanent saving of the data.
     */
    public final class SaveDataAction extends AbstractAction
    {

        private SaveDataAction()
        {
            super("Save Data");
        }

        public void actionPerformed(ActionEvent e)
        {
            try
            {
                StorageFactory.getCurrentStorage().saveData();
            } catch (FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
        }
    }

}