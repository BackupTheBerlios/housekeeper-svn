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

package net.sourceforge.housekeeper.swing.articledescription;


import net.sourceforge.housekeeper.domain.ArticleDescription;
import net.sourceforge.housekeeper.swing.TableModelTemplate;


/**
 * A table model for display of all Article Descriptions in a table.
 *
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 *
 * @since 0.1
 */
public final class ArticleDescriptionTableModel extends TableModelTemplate
{

    /** Header names for the table */
    private static final String[] COLUMNHEADERS = 
                                                  {
                                                      "Name",
                                                      "Dealer",
                                                      "Quantity",
                                                      "Unit",
                                                      "Price"
                                                  };


    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ArticleDescriptionModel object.
     */
    public ArticleDescriptionTableModel()
    {
        super(COLUMNHEADERS);
    }

    //~ Methods ----------------------------------------------------------------


    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ArticleDescription article = (ArticleDescription)getObjectAtRow(rowIndex);

        switch (columnIndex)
        {
            case 0:
                return article.getName();

            case 1:
                return article.getStore();

            case 2:
                return new Integer(article.getQuantity());

            case 3:
                return article.getUnit();

            case 4:
                return new Double(article.getPrice());

            default:
                return null;
        }
    }
}
