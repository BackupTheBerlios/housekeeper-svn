package net.sourceforge.housekeeper.storage.xmlser;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;

import net.sourceforge.housekeeper.storage.AssortimentItemMapper;
import net.sourceforge.housekeeper.storage.DataMapperFactory;
import net.sourceforge.housekeeper.storage.StockItemMapper;
import net.sourceforge.housekeeper.storage.memory.MemoryMapperFactory;

/**
 * 
 * 
 * @author Adrian Gygax
 * @version $Revision$, $Date$
 * @see
 * @since
 */
public final class XMLSerializationFactory implements DataMapperFactory
{
    /** The path to the file that is used for data storage */
    private static final File FILE = new File(System.getProperty("user.home"),
                                              "housekeeper_ser.xml");

    //~ Instance fields --------------------------------------------------------

    private XMLDecoder xmlDecoder;
    private XMLEncoder xmlEncoder;
    
    

	public AssortimentItemMapper getAssortimentItemMapper()
	{
		return MemoryMapperFactory.INSTANCE.getAssortimentItemMapper();
	}

	public StockItemMapper getStockItemMapper()
	{
		
		return MemoryMapperFactory.INSTANCE.getStockItemMapper();
	}

	public void saveData()
	{
        try
        {
            xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILE)));

            xmlEncoder.writeObject(getAssortimentItemMapper().getAll());
            xmlEncoder.writeObject(getStockItemMapper().getAll());

            xmlEncoder.close();
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File not found");
        }
		

	}

	public void loadData()
	{
        try
        {
            xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(FILE)));

            Collection assortimentItems = (Collection)xmlDecoder.readObject();
            Collection stockItems = (Collection)xmlDecoder.readObject();

            xmlDecoder.close();

            getAssortimentItemMapper().add(assortimentItems);
            getStockItemMapper().add(stockItems);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File not found");
        }

	}

}
