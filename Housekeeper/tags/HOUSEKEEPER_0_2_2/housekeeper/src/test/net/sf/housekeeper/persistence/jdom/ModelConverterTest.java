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

package net.sf.housekeeper.persistence.jdom;

import org.jdom.Element;

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.testutils.DataGenerator;
import junit.framework.TestCase;

/**
 * Test if {@link net.sf.housekeeper.persistence.jdom.ModelConverter}correctly
 * converts between domain and DOM objects.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class ModelConverterTest extends TestCase
{

    /**
     * Converts a {@link Household}to an XML DOM and then converts that DOM
     * back to a {@link Household}. Then it compares if the new Household is
     * equal to the original Household.
     */
    public void testModelConverter()
    {
        final Household originalDomain = DataGenerator.createHousehold();
        final ModelConverter converter = new ModelConverter();

        final Element xmlDOM = converter.convertDomainToXML(originalDomain);
        final Household transformedDomain = converter.convertToDomain(xmlDOM);

        assertEquals(originalDomain, transformedDomain);
    }

}