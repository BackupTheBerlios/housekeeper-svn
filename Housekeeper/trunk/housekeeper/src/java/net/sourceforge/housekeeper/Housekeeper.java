package net.sourceforge.housekeeper;

import javax.swing.JFrame;

import net.sourceforge.housekeeper.swing.MainFrame;

/**
 * @author Adrian Gygax
 */
public class Housekeeper
{

	public static void main(String[] args)
	{
		JFrame mainFrame = MainFrame.INSTANCE;
		
		mainFrame.show();
	}
}
