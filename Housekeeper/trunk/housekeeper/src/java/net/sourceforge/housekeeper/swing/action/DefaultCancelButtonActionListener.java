/*
 * This file is part of housekeeper.
 *
 *	housekeeper is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation; either version 2 of the License, or
 *	(at your option) any later version.
 *
 *	housekeeper is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with housekeeper; if not, write to the Free Software
 *	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.sourceforge.housekeeper.swing.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @since 0.1
 */
public final class DefaultCancelButtonActionListener implements ActionListener
{
    private final JDialog dialog;

    public DefaultCancelButtonActionListener(JDialog dialog)
    {
        this.dialog = dialog;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        dialog.dispose();
    }

}