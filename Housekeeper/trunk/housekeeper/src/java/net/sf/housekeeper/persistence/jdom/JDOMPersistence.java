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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.PersistenceService;
import net.sf.housekeeper.persistence.UnsupportedFileVersionException;

import org.apache.commons.logging.Log;
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
    public static final String  FILE_VERSION_ATTRIBUTE = "version";

    /**
     * Name of the root element.
     */
    public static final String  ROOT_ELEMENT           = "household";

    /**
     * The logger for this class.
     */
    private static final Log     LOG                    = LogFactory
                                                                .getLog(JDOMPersistence.class);
    

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
     * @see net.sf.housekeeper.persistence.PersistenceService#loadData(java.io.InputStream)
     */
    public Household loadData(final InputStream location) throws IOException,
            UnsupportedFileVersionException, IllegalArgumentException
    {
        final SAXBuilder builder = new SAXBuilder();

        try
        {
            final Document document = builder.build(location);
            final Element root = document.getRootElement();
            final Household household = convertToDomain(root);

            return household;
        } catch (JDOMException e)
        {
            final String message = "The data stream is not a valid Housekeeper XML document.";
            LOG.error(message, e);
            throw new IllegalArgumentException(message);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.PersistenceService#saveData(net.sf.housekeeper.domain.Household,
     *      java.io.OutputStream)
     */
    public void saveData(final Household household, final OutputStream location)
            throws IOException
    {
        final Element root = modelConverter.convertDomainToXML(household);
        final Document document = new Document(root);

        final Format format = Format.getPrettyFormat();
        final XMLOutputter serializer = new XMLOutputter(format);

        serializer.output(document, location);
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.persistence.PersistenceService#maxVersion()
     */
    public int maxVersion()
    {
        return 1;
    }
    
    /* (non-Javadoc)
     * @see net.sf.housekeeper.persistence.PersistenceService#minVersion()
     */
    public int minVersion()
    {
        return 1;
    }
    
    /**
     * Converts a DOM into a domain model. Package private for Unit Testing.
     * 
     * @param root The root element of the data structure.
     * @return The converted domain model.
     * @throws UnsupportedFileVersionException if the file version of the data
     *             cannot be detected or is not supported.
     * @throws IllegalArgumentException if the given element is not a valid root
     *             element.
     */
    Household convertToDomain(final Element root)
            throws UnsupportedFileVersionException, IllegalArgumentException
    {
        if (!root.getName().equals(ROOT_ELEMENT))
        {
            throw new IllegalArgumentException(
                    "This is no Housekeeper root element: " + root.getName());
        }
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
     */
    private int getFileVersionFor(Element root)
    {
        final String versionString = root
                .getAttributeValue(FILE_VERSION_ATTRIBUTE);
        final int version = Integer.parseInt(versionString);
        return version;
    }

    private boolean isVersionSupported(int version)
    {
        return version >= minVersion() && version <= maxVersion();
    }

}