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
import java.io.FileWriter;
import java.io.IOException;

import net.sf.housekeeper.ConfigurationManager;
import net.sf.housekeeper.persistence.PersistenceService;
import net.sf.housekeeper.persistence.UnsupportedFileVersionException;

import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Uses JDOM to save the data to an XML file.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class JDOMPersistence implements PersistenceService
{

    /**
     * File used for loading and saving
     */
    private final File dataFile;

    public JDOMPersistence()
    {
        final File dataDir = (File)ConfigurationManager.INSTANCE
                .getConfiguration().getProperty(ConfigurationManager.DATA_DIRECTORY);

        dataFile = new File(dataDir, "data.xml");
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.PersistenceService#loadData()
     */
    public void loadData() throws IOException, UnsupportedFileVersionException
    {
        final SAXBuilder builder = new SAXBuilder();
        try
        {
            LogFactory.getLog(getClass()).debug(
                                                "Trying to load file: "
                                                        + dataFile);
            final Document document = builder.build(dataFile);
            final Element root = document.getRootElement();

            DomainConverterProxy.getInstance().replaceDomain(root);
        } catch (JDOMException e)
        {
            e.printStackTrace();
            throw new IOException(
                    "There was an error parsing the XML document: " + dataFile);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.PersistenceService#saveData()
     */
    public void saveData() throws IOException
    {
        final Element root = DomainConverterProxy.getInstance()
                .convertDomainToXML();
        final Document document = new Document(root);

        final Format format = Format.getPrettyFormat();
        final XMLOutputter serializer = new XMLOutputter(format);
        final FileWriter fileWriter = new FileWriter(dataFile);
        serializer.output(document, fileWriter);
        fileWriter.close();
    }

}