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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.XMLTestCase;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.SAXException;

/**
 * Tests the converter.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public class Version1To2ConverterTest extends XMLTestCase
{

    private static final String VERSION1to2_DATA = "data_v1_to_v2.xml";

    private static final String VERSION1_DATA    = "data_v1.xml";

    /**
     * Converts a version 1 document to a version 2 document and compares this
     * to a previously saved target document, which has been converted manually.
     * 
     * @throws JDOMException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public final void testConvert() throws JDOMException, SAXException, IOException, ParserConfigurationException
    {
        //Get root elements
        final org.jdom.Document convertedJDOMDoc = getXMLDoc(VERSION1_DATA);

        //Convert original v1 doc
        final DocumentVersionConverter converter = new Version1To2Converter();
        converter.convert(convertedJDOMDoc);

        final File tempFile = createTempFile();
        final Format format = Format.getPrettyFormat();
        final XMLOutputter serializer = new XMLOutputter(format);
        serializer.output(convertedJDOMDoc, new FileWriter(tempFile));
        
        //Compare the converted document with the target document
        final Reader targetFile = new InputStreamReader(getClass().getResourceAsStream(VERSION1to2_DATA));
        final Reader convertedFile = new FileReader(tempFile);
        
        assertXMLEqual(targetFile, convertedFile);
        
        targetFile.close();
        convertedFile.close();
    }

    private org.jdom.Document getXMLDoc(final String doc) throws JDOMException,
            IOException
    {
        final InputStream stream = getClass().getResourceAsStream(doc);
        final SAXBuilder builder = new SAXBuilder();
        final org.jdom.Document document = builder.build(stream);
        return document;
    }
    
    /**
     * Creates a temporary file which gets deleted on VM exit.
     * 
     * @return A file which didn't exist before.
     * @throws IOException If any error occurs.
     */
    private File createTempFile() throws IOException
    {
        final File temp = File.createTempFile("hok", null);
        temp.deleteOnExit();
        return temp;
    }

}