package net.sourceforge.housekeeper.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 * @author <a href="notizklotz@gmx.net">Adrian Gygax</a>
 */
final class NewArticle extends AbstractAction
{
	static final NewArticle INSTANCE = new NewArticle();

	/**
	 * 
	 * 
	 */
	private NewArticle()
	{
		super("New Article");
	}

	public void actionPerformed(ActionEvent e)
	{
		JDialog articleDialog = new ArticleDialog();
		articleDialog.show();
	}

}
