package net.sourceforge.housekeeper.swing;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.jgoodies.plaf.Options;
import com.jgoodies.plaf.plastic.Plastic3DLookAndFeel;


/**
 * @author Adrian Gygax
 */
public final class MainFrame extends ExtendedFrame
{

	public static final MainFrame INSTANCE = new MainFrame();

	private JMenuBar menuBar;
	private JMenu menuArticles;
	private JMenu menuFile;
	
	private JTabbedPane tabbedPane;
	
	/**
	 * 
	 * 
	 */
	private MainFrame()
	{
		super();

		initLookAndFeel();
		buildMenus();
		buildComponents();
		
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
	
	private void buildMenus()
	{
		//Menubar
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//File Menu
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuFile.add(new JMenuItem(LoadData.INSTANCE));
		menuFile.add(new JMenuItem(SaveData.INSTANCE));
		menuFile.addSeparator();
		menuFile.add(new JMenuItem(Exit.INSTANCE));
		
		//Articles menu
		menuArticles = new JMenu("Articles");
		menuBar.add(menuArticles);
		
		menuArticles.add(new JMenuItem(NewArticle.INSTANCE));		
	}
	
	private void buildComponents()
	{
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Articles", new ArticlePanel());

		getContentPane().add(tabbedPane);
	}

}
