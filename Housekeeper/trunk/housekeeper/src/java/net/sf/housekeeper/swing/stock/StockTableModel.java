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
 * Copyright 2003-2004, Adrian Gygax
 * http://housekeeper.sourceforge.net
 */

package net.sf.housekeeper.swing.stock;


import net.sf.housekeeper.domain.StockItem;
import net.sf.housekeeper.swing.TableModelTemplate;


/**
 * DOCUMENT ME!
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class StockTableModel extends TableModelTemplate
{
    //~ Static fields/initializers ---------------------------------------------

    /** TODO DOCUMENT ME! */
    private static final String[] HEADERS = 
                                            {
                                                "Item",
                                                "Best before end"
                                            };

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new StockTableModel object.
     */
    public StockTableModel()
    {
        super(HEADERS);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * TODO DOCUMENT ME!
     *
     * @param arg0 DOCUMENT ME!
     * @param arg1 DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Object getValueAt(int arg0, int arg1)
    {
        StockItem item = (StockItem)getObjectAtRow(arg0);

        switch (arg1)
        {
            case 0:
                return item.getName();

            case 1:
                return item.getBestBeforeEnd();

            default:
                return null;
        }
    }
}
