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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import net.sf.housekeeper.domain.Household;

import org.custommonkey.xmlunit.XMLTestCase;

/**
 * Tests if XML files are not changed semantically on load/save.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class JDOMPersistenceTest extends XMLTestCase
{

    private static final String VERSION1_DATA = "data_v1.xml";

    /**
     * Loads an XML file which contains data in the current file format version
     * and resaves it to another file. Then both XML files are compared for
     * equality.
     * 
     * @throws Exception if any error occurs.
     */
    public void testLoadSaveXML() throws Exception
    {
        final JDOMPersistence pers = new JDOMPersistence();

        //Read data from resources and parse it
        final InputStream dataStream = getClass()
                .getResourceAsStream(VERSION1_DATA);
        final Household household = pers.loadData(dataStream);

        //Save data to a temporary file
        final File tempFile = createTempFile();
        final OutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(tempFile));
        pers.saveData(household, outputStream);
        outputStream.flush();
        outputStream.close();

        //Reopen the two XML files and test for equality
        final InputStream originalStream = getClass()
                .getResourceAsStream(VERSION1_DATA);
        final Reader originalReader = new InputStreamReader(originalStream);
        final Reader savedReader = new FileReader(tempFile);
        assertXMLEqual(originalReader, savedReader);

        originalReader.close();
        savedReader.close();
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