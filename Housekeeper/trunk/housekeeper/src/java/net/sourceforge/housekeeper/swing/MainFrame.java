/*
 * This file is part of Housekeeper.
 *
 * Housekeeper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Housekeeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Housekeeper; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 * MA  02111-1307  USA
 *
 * Copyright 2003, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sourceforge.housekeeper.swing;


import com.jgoodies.plaf.Options;
import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;

import net.sourceforge.housekeeper.Housekeeper;
import net.sourceforge.housekeeper.swing.action.*;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 */
public final class MainFrame extends JFrame
{
    //~ Static fields/initializers ---------------------------------------------

    /** TODO DOCUMENT ME! */
    public static final MainFrame INSTANCE = new MainFrame();

    //~ Instance fields --------------------------------------------------------

    /** TODO DOCUMENT ME! */
    private JMenu menuArticles;

    /** TODO DOCUMENT ME! */
    private JMenu menuFile;

    /** TODO DOCUMENT ME! */
    private JMenuBar menuBar;

    /** TODO DOCUMENT ME! */
    private JTabbedPane tabbedPane;

    //~ Constructors -----------------------------------------------------------

    /**
     *
     *
     */
    private MainFrame()
    {
        super();

        initLookAndFeel();
        buildMenus();
        buildComponents();

        setTitle("Housekeeper " + Housekeeper.VERSION);
        pack();
        SwingUtils.centerOnScreen(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * TODO DOCUMENT ME!
     */
    private void buildComponents()
    {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Articles", ArticleDescriptionPanel.getInstance());

        //tabbedPane.addTab("Purchases", PurchasePanel.getInstance());
        getContentPane().add(tabbedPane);
    }

    /**
     * TODO DOCUMENT ME!
     */
    private void buildMenus()
    {
        //Menubar
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //File Menu
        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        menuFile.add(new JMenuItem(LoadDataAction.INSTANCE));
        menuFile.add(new JMenuItem(SaveDataAction.INSTANCE));
        menuFile.addSeparator();
        menuFile.add(new JMenuItem(ExitAction.INSTANCE));

        //Articles menu
        menuArticles = new JMenu("Articles");
        menuBar.add(menuArticles);

        menuArticles.add(new JMenuItem(NewArticleDescriptionAction.INSTANCE));
    }

    /**
     * TODO DOCUMENT ME!
     */
    private void initLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
        }
        catch (Exception e)
        {
            System.err.println("Failed to set the Look and Feel");
        }


        UIManager.put("Application.useSystemFontSettings", Boolean.TRUE);
        UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, Boolean.TRUE);
    }
}
