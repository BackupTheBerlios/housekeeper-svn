package net.sourceforge.housekeeper.storage;

/**
 * @author Adrian Gygax
 */
public class StorageFactory
{
	private static Storage currentStorage;
	
	private StorageFactory()
	{
	}
	
	public static Storage getCurrentStorage()
	{
		if (currentStorage == null)
		{
			currentStorage = new XMLStorage();
		}
		
		return currentStorage;
	}
}
