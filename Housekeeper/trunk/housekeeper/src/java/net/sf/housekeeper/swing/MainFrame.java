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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import net.sf.housekeeper.Housekeeper;
import net.sf.housekeeper.persistence.PersistenceLayerFactory;

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

    private JTabbedPane           tabbedPane;

    /**
     * Creates a new MainFrame object.
     */
    private MainFrame()
    {
        super();

        initLookAndFeel();
        setJMenuBar(buildMenuBar());
        buildComponents();

        setTitle("Housekeeper " + Housekeeper.VERSION);
        pack();
        setLocationRelativeTo(null);
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
                PersistenceLayerFactory.getCurrentLayer().loadData();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Action to cause the permanent saving of the data.
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
                PersistenceLayerFactory.getCurrentLayer().saveData();
            } catch (IOException e1)
            {
                e1.printStackTrace();
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
            final URL helpURL = MainFrame.class
                    .getResource("about.html");
            if (helpURL != null)
            {
                try
                {
                    editorPane.setPage(helpURL);
                } catch (IOException ex)
                {
                    System.err.println("Attempted to read a bad URL: "
                            + helpURL);
                }
            } else
            {
                System.err.println("Couldn't find file: about.html");
            }

            editorPane.setPreferredSize(new Dimension(450, 500));
            final JScrollPane scroll = new JScrollPane(editorPane);

            JOptionPane.showMessageDialog(MainFrame.INSTANCE, scroll);

        }
    }
}