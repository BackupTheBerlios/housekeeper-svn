package net.sourceforge.housekeeper.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * @author <a href="notizklotz@gmx.net">Adrian Gygax</a>
 */
final class Exit extends AbstractAction
{
	static final Exit INSTANCE = new Exit();
	
	private Exit()
	{
		super("Exit");
	}
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		System.exit(0);
	}

}
