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
    public static final String   FILE_VERSION_ATTRIBUTE = "version";

    /**
     * Name of the root element.
     */
    public static final String   ROOT_ELEMENT           = "household";

    /**
     * The current file version.
     */
    private static final int     CURRENT_FILE_VERSION   = 3;

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
            final Household household = convertToDomain(document.getRootElement());

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
        root.setAttribute("version", "" + CURRENT_FILE_VERSION);
        final Document document = new Document(root);

        final Format format = Format.getPrettyFormat();
        final XMLOutputter serializer = new XMLOutputter(format);

        serializer.output(document, location);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.PersistenceService#maxVersion()
     */
    public int maxVersion()
    {
        return CURRENT_FILE_VERSION;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.PersistenceService#minVersion()
     */
    public int minVersion()
    {
        return 1;
    }

    /**
     * Converts a DOM into a domain model. Package private for Unit Testing.
     * 
     * @param root The root element of the DOM. Must not be null.
     * @return The converted domain model.
     * @throws UnsupportedFileVersionException if the file version of the data
     *             cannot be detected or is not supported.
     * @throws IllegalArgumentException if the given element is not a valid root
     *             element.
     */
    Household convertToDomain(final Element root)
            throws UnsupportedFileVersionException, IllegalArgumentException
    {
        final boolean isHousekeeperDoc = root.getName().equals(ROOT_ELEMENT);
        if (!isHousekeeperDoc)
        {
            throw new IllegalArgumentException(
                    "This is no Housekeeper root element: "
                            + root.getName());
        }

        //Extract version number
        final int version = getFileVersionFor(root);
        LogFactory.getLog(getClass())
                .debug("Detected file version: " + version);

        final Element newRoot = convertToLatestVersion(root);

        final Household household = modelConverter.convertToDomain(newRoot);
        return household;
    }

    /**
     * Converts the document to the latest version, if necessary.
     * 
     * @param root The root of the document to convert. Must not be null.
     * @return The converted DOM.
     * @throws UnsupportedFileVersionException If the document cannot be
     *             converted.
     */
    private Element convertToLatestVersion(Element root)
            throws UnsupportedFileVersionException
    {
        //Extract version number
        final int version = getFileVersionFor(root);
        LogFactory.getLog(getClass())
                .debug("Detected file version: " + version);

        final DocumentVersionConverter versionConverter = getConverterForVersion(version);
        if (versionConverter != null)
        {
            return versionConverter.convert(root);
        } else {
            return root;
        }
    }

    /**
     * Returns the converter which converts from the specified version to the
     * latest one.
     * 
     * @param version The version from which to convert.
     * @return The converter object or null, if no conversion is necessary.
     * @throws UnsupportedFileVersionException If the document cannot be
     *             converted.
     */
    private DocumentVersionConverter getConverterForVersion(int version)
            throws UnsupportedFileVersionException
    {
        DocumentVersionConverter docConverter = null;
        switch (version)
        {
            case 1:
                docConverter = new Version2To3Converter(new Version1To2Converter());
                break;
            case 2:
                docConverter = new Version2To3Converter();
                break;
            case CURRENT_FILE_VERSION:
                break;
            default:
                throw new UnsupportedFileVersionException(version);
        }
        return docConverter;
    }

    /**
     * Determines the file version for the given DOM.
     * 
     * @param root The root element. Must not be null.
     * @return The version of the data structure.
     */
    private int getFileVersionFor(Element root)
    {
        final String versionString = root.getAttributeValue(FILE_VERSION_ATTRIBUTE);
        final int version = Integer.parseInt(versionString);
        return version;
    }

}