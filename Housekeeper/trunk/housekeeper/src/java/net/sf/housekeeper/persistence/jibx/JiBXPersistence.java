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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.PersistenceService;
import net.sf.housekeeper.persistence.UnsupportedFileVersionException;

import org.apache.commons.logging.LogFactory;
import org.jibx.extras.BindingSelector;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.springframework.util.Assert;

/**
 * Provides persistence using XML and the JiBX data mapper.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class JiBXPersistence implements PersistenceService
{

    /**
     * The supported versions with the latest one first.
     */
    private static final String[] VERSION_TEXTS          = { "5", "4" };

    /**
     * The bindings related to the version texts.
     */
    private static final String[] VERSION_BINDINGS       = { "jibx_binding_v5",
            "jibx_binding_v4"                           };

    private static final String   VERSION_ATTRIBUTE_NAME = "version";

    private static final String   WRONG_VERSION_MESSAGE  = "The version attribute of Household must be equal to the latest supported version";

    private static final String   ENCODING               = "UTF-8";

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.PersistenceService#loadData(java.io.InputStream)
     */
    public Household loadData(InputStream dataStream) throws IOException,
            UnsupportedFileVersionException, IllegalArgumentException
    {

        try
        {
            BindingSelector select = new BindingSelector(null,
                    VERSION_ATTRIBUTE_NAME, VERSION_TEXTS, VERSION_BINDINGS);
            IUnmarshallingContext context = select.getContext();
            context.setDocument(dataStream, null);
            Household obj = (Household) select
                    .unmarshalVersioned(Household.class);
            return obj;
        } catch (JiBXException e)
        {
            LogFactory.getLog(getClass()).error("Error while JiBXing", e);
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.PersistenceService#saveData(net.sf.housekeeper.domain.Household,
     *      java.io.OutputStream)
     */
    public void saveData(Household household, OutputStream dataStream)
            throws IOException
    {
        Assert.isTrue(household.version.equals(VERSION_TEXTS[0]),
                      WRONG_VERSION_MESSAGE);

        try
        {
            BindingSelector select = new BindingSelector(null,
                    VERSION_ATTRIBUTE_NAME, VERSION_TEXTS, VERSION_BINDINGS);
            select.setIndent(2);
            select.setOutput(dataStream, ENCODING);
            select.marshalVersioned(household, VERSION_TEXTS[0]);
        } catch (JiBXException e)
        {
            LogFactory.getLog(getClass()).error("Error while JiBXing", e);
            throw new IOException(e.getLocalizedMessage());
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.PersistenceService#minVersion()
     */
    public int minVersion()
    {
        return 4;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.housekeeper.persistence.PersistenceService#maxVersion()
     */
    public int maxVersion()
    {
        return 5;
    }

}
