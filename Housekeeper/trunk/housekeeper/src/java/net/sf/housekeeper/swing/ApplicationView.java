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
import java.io.IOException;
import java.net.URL;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import net.sf.housekeeper.util.LocalisationManager;

/**
 * The main frame of the application.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class ApplicationView extends JFrame
{

    /**
     * User choses cancel.
     */
    public static final int CANCEL = 3;

    /**
     * User choses no.
     */
    public static final int NO     = 2;

    /**
     * User choses yes.
     */
    public static final int YES    = 1;

    /**
     * Creates a new view.
     * 
     * @param supplyView The view to display the supply.
     * @param loadDataAction The action for loading data.
     * @param saveDataAction The action for saving data.
     * @param exitAction The action for exiting the application.
     * @param aboutAction The action foe showing the about dialog.
     */
    public ApplicationView(final SupplyView supplyView,
            final Action loadDataAction, final Action saveDataAction,
            final Action exitAction, final Action aboutAction)
    {
        super();

        setJMenuBar(buildMenuBar(loadDataAction, saveDataAction, exitAction,
                                 aboutAction));

        supplyView.setParentFrame(this);
        
        getContentPane().add(supplyView);

        pack();
        setLocationRelativeTo(null);

        //Use the controller for exiting the application
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /**
     * Displays the about dialog using the document at the given URL.
     * 
     * @param document The URL of the document.
     * @throws IOException if the document cannot be loaded.
     */
    public void showAboutDialog(final URL document) throws IOException
    {
        final JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        final String aboutFile = "/net/sf/housekeeper/about.html";
        final URL helpURL = ApplicationPresenter.class.getResource(aboutFile);
        editorPane.setPage(document);
        editorPane.setPreferredSize(new Dimension(400, 200));
        JOptionPane.showMessageDialog(this, editorPane);
    }

    /**
     * Asks a user to confirm something.
     * 
     * @param message The message to show to the user.
     * @return Can be one of {@link #YES},{@link #NO},{@link #CANCEL}
     */
    public int showConfirmDialog(String message)
    {
        final int option = JOptionPane.showConfirmDialog(this, message);

        int answer;
        switch (option)
        {
            case JOptionPane.YES_OPTION:
                answer = YES;
                break;
            case JOptionPane.NO_OPTION:
                answer = NO;
                break;
            default:
                answer = CANCEL;
                break;
        }
        return answer;
    }

    /**
     * Shows an error dialog with the given message.
     * 
     * @param message The message to display.
     */
    public void showErrorDialog(final String message)
    {
        final String error = LocalisationManager.INSTANCE.getText("gui.error");
        JOptionPane.showMessageDialog(this, message, error,
                                      JOptionPane.ERROR_MESSAGE);
    }

    private JMenuBar buildMenuBar(final Action loadDataAction,
                                  final Action saveDataAction,
                                  final Action exitAction,
                                  final Action aboutAction)
    {
        final JMenuBar menuBar = new JMenuBar();

        //File Menu
        final String fileMenuLabel = LocalisationManager.INSTANCE
                .getText("gui.menu.file");
        final JMenu menuFile = new JMenu(fileMenuLabel);
        menuBar.add(menuFile);

        menuFile.add(new JMenuItem(loadDataAction));
        menuFile.add(new JMenuItem(saveDataAction));
        menuFile.addSeparator();
        menuFile.add(new JMenuItem(exitAction));

        //Help Menu
        final String helpMenuString = LocalisationManager.INSTANCE
                .getText("gui.menu.help");
        final JMenu menuHelp = new JMenu(helpMenuString);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuHelp);

        menuHelp.add(new JMenuItem(aboutAction));

        return menuBar;
    }
}