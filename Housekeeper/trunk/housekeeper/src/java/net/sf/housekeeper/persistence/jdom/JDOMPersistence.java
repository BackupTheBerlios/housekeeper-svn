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

import net.sf.housekeeper.domain.Household;
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
 * Uses JDOM to convert data between an XML file and domain objects.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class JDOMPersistence implements PersistenceService
{

    /**
     * Name of the attribute wich specifies the file format version.
     */
    private static final String  FILE_VERSION_ATTRIBUTE = "version";

    /**
     * Object used for converting between DOM and domain model.
     */
    private final ModelConverter modelConverter;

    /**
     * Initializes a JDOM persistence service.
     *
     */
    public JDOMPersistence()
    {
        modelConverter = new ModelConverter();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.PersistenceService#loadData(java.io.File)
     */
    public Household loadData(final File location) throws IOException,
            UnsupportedFileVersionException
    {
        final SAXBuilder builder = new SAXBuilder();
        LogFactory.getLog(getClass()).debug("Trying to load file: " + location);
        try
        {
            final Document document = builder.build(location);
            final Element root = document.getRootElement();
            final Household household = convertToDomain(root);

            return household;
        } catch (JDOMException e)
        {
            e.printStackTrace();
            throw new IOException(
                    "There was an error parsing the XML document: " + location);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.PersistenceService#saveData(net.sf.housekeeper.domain.Household,
     *      java.io.File)
     */
    public void saveData(final Household household, final File location)
            throws IOException
    {
        final Element root = modelConverter.convertDomainToXML(household);
        final Document document = new Document(root);

        final Format format = Format.getPrettyFormat();
        final XMLOutputter serializer = new XMLOutputter(format);
        final FileWriter fileWriter = new FileWriter(location);
        serializer.output(document, fileWriter);
        fileWriter.close();
    }

    /**
     * Converts a DOM into a domain model.
     * 
     * @param root The root element of the data structure.
     * @return The converted domain model.
     * @throws UnsupportedFileVersionException if the file version of the data
     *             cannot be detected or is not supported.
     */
    private Household convertToDomain(final Element root)
            throws UnsupportedFileVersionException
    {
        //Extract version number
        final int version = getFileVersionFor(root);
        LogFactory.getLog(getClass())
        .debug("Detected file version: " + version);
        
        //Check if version is supported
        if (!isVersionSupported(version))
        {
            throw new UnsupportedFileVersionException(version);
        }

        //Convert the DOM
        final Household household = modelConverter.convertToDomain(root);
        return household;
    }

    /**
     * Determines the file version for the given DOM.
     * 
     * @param root The root element of the saved data.
     * @return The version of the data structure.
     * @throws UnsupportedFileVersionException if the version cannot be detected.
     */
    private int getFileVersionFor(Element root)
            throws UnsupportedFileVersionException
    {
        final String versionString = root
                .getAttributeValue(FILE_VERSION_ATTRIBUTE);
        if (versionString == null)
        {
            throw new UnsupportedFileVersionException(
                    "Could not detect version of the file structure.");
        }
        final int version = Integer.parseInt(versionString);
        return version;
    }

    private boolean isVersionSupported(int version)
    {
        return version == 1;
    }

}