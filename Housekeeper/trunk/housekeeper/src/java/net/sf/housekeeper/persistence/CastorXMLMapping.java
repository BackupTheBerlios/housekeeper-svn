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

package net.sf.housekeeper.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import net.sf.housekeeper.domain.Household;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;


/**
 * Uses <a href="http://www.castor.org">Castor</a> for XML binding.
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 */
final class CastorXMLMapping implements PersistenceLayer
{
    
    private static final File DATAFILE = new File("HK_castor.xml");
    
    private final Mapping mapping;

    CastorXMLMapping()
    {
        mapping = new Mapping();
        try
        {
            mapping.loadMapping("castor_mapping.xml");
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (MappingException e)
        {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.storage.Storage#loadData()
     */
    public void loadData() throws IOException
    {
        final Reader reader = new FileReader(DATAFILE);
        try
        {
            final Unmarshaller unmarshaller = new Unmarshaller(mapping);
            final Household household = (Household)unmarshaller.unmarshal(reader);
            Household.setINSTANCE(household);
        } catch (MarshalException e)
        {
            throw new IOException(e.getMessage());
        } catch (ValidationException e)
        {
            throw new IOException(e.getMessage());
        } catch (MappingException e)
        {
            e.printStackTrace();
            throw new IOException();
        }

    }

    /* (non-Javadoc)
     * @see net.sf.housekeeper.storage.Storage#saveData()
     */
    public void saveData() throws IOException
    {
        final FileWriter writer = new FileWriter(DATAFILE);
        final Mapping map = new Mapping();
        
        try
        {
            map.loadMapping("castor_mapping.xml");
            final Marshaller marshaller = new Marshaller(writer);
            marshaller.setMapping(map);
            marshaller.marshal(Household.instance());
        } catch (MarshalException e)
        {
            throw new IOException(e.getMessage());
        } catch (ValidationException e)
        {
            throw new IOException(e.getMessage());
        } catch (MappingException e)
        {
            e.printStackTrace();
            throw new IOException();
        }
    }

}
