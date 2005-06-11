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
 * http://housekeeper.berlios.de
 */

package net.sf.housekeeper.persistence.jibx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.sf.housekeeper.persistence.UnsupportedFileVersionException;
import net.sf.housekeeper.testutils.DataGenerator;

import org.custommonkey.xmlunit.XMLTestCase;

/**
 * Tests loading and saving of XML files with JiBX.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class JiBXPersistenceTest extends XMLTestCase
{

    private JiBXPersistence     persistence;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        persistence = new JiBXPersistence();
    }

    /*
     * Loads an XML file which contains data in the current file format version
     * and resaves it to another file. Then both XML files are compared for
     * equality.
     * 
     * @throws Exception if any error occurs.
     *
    public void testLoadSaveXML() throws Exception
    {
        //Read data from resources and parse it
        final InputStream dataStream = getClass()
                .getResourceAsStream(DataGenerator.VERSION3_DATA);
        final Household household = jdomPersistence.loadData(dataStream);

        //Save data to a temporary file
        final File tempFile = createTempFile();
        final OutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(tempFile));
        jdomPersistence.saveData(household, outputStream);
        outputStream.flush();
        outputStream.close();

        //Reopen the two XML files and test for equality
        final InputStream originalStream = getClass()
                .getResourceAsStream(DataGenerator.VERSION3_DATA);
        final Reader originalReader = new InputStreamReader(originalStream);
        final Reader savedReader = new FileReader(tempFile);
        assertXMLEqual(originalReader, savedReader);

        originalReader.close();
        savedReader.close();
    }*/

    /**
     * Tests if an {@link IllegalArgumentException}is thrown if it is tried to
     * parse a stream which is not a valid Housekeeper document.
     * 
     * @throws IOException
     * @throws UnsupportedFileVersionException
     */
    public void testLoadNotAnXmlFile() throws IOException,
            UnsupportedFileVersionException
    {
        //Read data from resources and parse it
        final InputStream dataStream = getClass()
                .getResourceAsStream(DataGenerator.NOT_AN_XML_FILE);

        try
        {
            persistence.loadData(dataStream);
        } catch (IllegalArgumentException e)
        {
            //Expected behaviour
            return;
        }
        fail("Loading a file which is not even an XML file"
                + "must throw an IllegalArgumentException");
    }

    /**
     * Creates a temporary file which gets deleted on VM exit.
     * 
     * @return A file which didn't exist before.
     * @throws IOException If any error occurs.
     */
    public File createTempFile() throws IOException
    {
        final File temp = File.createTempFile("hok", null);
        temp.deleteOnExit();
        return temp;
    }
}