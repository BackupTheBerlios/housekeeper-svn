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

package net.sf.housekeeper.testutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.SAXException;

import net.sf.housekeeper.domain.Food;
import net.sf.housekeeper.domain.Household;

/**
 * A Factory for various domain objects and XML documents.
 * 
 * 
 * @author Adrian Gygax.
 * @version $Revision$, $Date$
 */
public final class DataGenerator
{

    /**
     * Prefix for the test data within the classpath.
     */
    private static final String prefix           = "/net/sf/housekeeper/persistence/jdom/";

    /**
     * A file which is not XML.
     */
    public static final String  NOT_AN_XML_FILE  = prefix + "notAnXmlFile.xml";

    /**
     * Document which contains the same data as VERSION1_DATA but in the new
     * format.
     */
    public static final String  VERSION1to2_DATA = prefix + "data_v1_to_v2.xml";

    /**
     * Test data for document version 1.
     */
    public static final String  VERSION1_DATA    = prefix + "data_v1.xml";

    private DataGenerator()
    {

    }

    /**
     * Creates a JDOM document from a file located on the classpath.
     * 
     * @param docName The name of the file. Must not be null.
     * @return The document. Is not null.
     * @throws JDOMException If any parse error occurs.
     * @throws IOException If any IO error occurs.
     */
    public static Document getJDOMDocument(final String docName)
            throws JDOMException, IOException
    {
        final InputStream stream = DataGenerator.class
                .getResourceAsStream(docName);
        final SAXBuilder builder = new SAXBuilder();
        final Document document = builder.build(stream);
        return document;
    }

    /**
     * Creates a W3C document from a file located on the classpath.
     * 
     * @param docName The name of the file. Must not be null.
     * @return The document. Is not null.
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws ParserConfigurationException
     * @throws SAXException If any parse error occurs.
     * @throws IOException If any IO error occurs.
     */
    public static org.w3c.dom.Document getW3CDocument(final String docName)
            throws ParserConfigurationException, SAXException, IOException
    {
        final DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        final DocumentBuilder parser = factory.newDocumentBuilder();
        final org.w3c.dom.Document doc = parser.parse(DataGenerator.class
                .getResourceAsStream(docName));
        return doc;
    }

    /**
     * Creates a {@link Household}object which is populated with a complex and
     * a simple {@link Food}.
     * 
     * @return Not null.
     */
    public static Household createHousehold()
    {
        final Household household = new Household();
        household.getConvenienceFoodManager().add(createComplexItem());
        household.getConvenienceFoodManager().add(createSimpleItem());

        return household;
    }

    /**
     * Creates a {@link Food}object which has all attributes set to a value.
     * 
     * @return Not null.
     */
    public static Food createComplexItem()
    {
        final String itemName = "aName";
        final String itemQuantity = "aQuantity";

        final Calendar itemDate = Calendar.getInstance();
        final int day = 23;
        final int month = 5;
        final int year = 2004;
        itemDate.set(year, month, day, 15, 22);

        final Food item = new Food(itemName, itemQuantity, itemDate.getTime());

        return item;
    }

    /**
     * Creates a {@link Food}object which has only its name set.
     * 
     * @return Not null.
     */
    public static Food createSimpleItem()
    {
        final String itemName = "aName";

        final Food item = new Food(itemName, null, null);

        return item;
    }
}