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

package net.sf.housekeeper.util;

import java.util.Comparator;
import java.util.Date;

/**
 * Comparator for expiry dates. Treats null values as being later than any
 * date.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class ExpiryDateComparator implements Comparator
{

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object o1, Object o2)
    {
        if (o1 == null && o2 == null)
        {
            return 0;
        }

        //Null is treated as being greater as any non-null expiry date.
        //This has the effect of displaying items without an expiry
        //At the end of a least-expiry-first table of items.
        if (o1 == null)
        {
            return 1;
        }
        if (o2 == null)
        {
            return -1;
        }

        final Date date1 = (Date) o1;
        final Date date2 = (Date) o2;
        return date1.compareTo(date2);
    }
}
