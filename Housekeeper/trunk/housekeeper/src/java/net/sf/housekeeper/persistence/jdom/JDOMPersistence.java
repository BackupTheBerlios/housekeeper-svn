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

    /* (non-Javadoc)
     * @see net.sf.housekeeper.persistence.PersistenceService#loadData(java.io.File)
     */
    public Household loadData(final File location) throws IOException, UnsupportedFileVersionException
    {
        final SAXBuilder builder = new SAXBuilder();
        LogFactory.getLog(getClass()).debug(
                                            "Trying to load file: "
                                                    + location);
        try
        {
            final Document document = builder.build(location);
            final Element root = document.getRootElement();
            final Household household = DomainConverterProxy.instance().convertToDomain(root);
            
            return household;
        } catch (JDOMException e)
        {
            e.printStackTrace();
            throw new IOException(
                    "There was an error parsing the XML document: " + location);
        }
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.persistence.PersistenceService#saveData(net.sf.housekeeper.domain.Household, java.io.File)
     */
    public void saveData(final Household household, final File location) throws IOException
    {
        final Element root = DomainConverterProxy.instance()
                .convertDomainToXML(household);
        final Document document = new Document(root);

        final Format format = Format.getPrettyFormat();
        final XMLOutputter serializer = new XMLOutputter(format);
        final FileWriter fileWriter = new FileWriter(location);
        serializer.output(document, fileWriter);
        fileWriter.close();
    }

}