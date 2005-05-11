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

package net.sf.housekeeper.persistence.jibx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.housekeeper.domain.Household;
import net.sf.housekeeper.persistence.PersistenceService;
import net.sf.housekeeper.persistence.UnsupportedFileVersionException;

import org.apache.commons.logging.LogFactory;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;

/**
 * Provides persistence using XML and the JiBX data mapper.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
public final class JiBXPersistence implements PersistenceService
{

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
            IBindingFactory bfact = BindingDirectory
                    .getFactory(Household.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            Household obj = (Household) uctx
                    .unmarshalDocument(dataStream, null);
            return obj;
        } catch (JiBXException e)
        {
            LogFactory.getLog(getClass()).error("Error while JiBIxing", e);
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
        try
        {
            IBindingFactory bfact = BindingDirectory
                    .getFactory(Household.class);
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(2);
            mctx.marshalDocument(household, "UTF-8", Boolean.TRUE, dataStream);
        } catch (JiBXException e)
        {
            LogFactory.getLog(getClass()).error("Error while JiBIxing", e);
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
        return 4;
    }

}