package net.sourceforge.housekeeper.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.sourceforge.housekeeper.storage.StorageFactory;

/**
 * @author <a href="notizklotz@gmx.net">Adrian Gygax</a>
 */
final class SaveData extends AbstractAction
{
	static final SaveData INSTANCE = new SaveData();
	
	private SaveData()
	{
		super("Save Data");
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		StorageFactory.getCurrentStorage().saveData();
	}

}
