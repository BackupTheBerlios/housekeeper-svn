package net.sourceforge.housekeeper.swing;

import javax.swing.UIManager;

import com.jgoodies.plaf.Options;
import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;


/**
 * @author Adrian Gygax
 */
public final class MainFrame extends ExtendedFrame
{

	public static final MainFrame INSTANCE = new MainFrame();
	
	/**
	 * 
	 * 
	 */
	private MainFrame()
	{
		super();

		initLookAndFeel();
		
		setSize(400, 400);
		setTitle("Housekeeper");
		center();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		}
		catch (Exception e)
		{
			System.err.println("Failed to set the Look and Feel");
		}
		UIManager.put("Application.useSystemFontSettings", Boolean.TRUE);
		UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, Boolean.TRUE);
	}

}
