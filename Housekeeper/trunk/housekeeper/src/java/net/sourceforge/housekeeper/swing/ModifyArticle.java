package net.sourceforge.housekeeper.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import net.sourceforge.housekeeper.model.Article;
import net.sourceforge.housekeeper.storage.StorageFactory;

/**
 * @author <a href="notizklotz@gmx.net">Adrian Gygax</a>
 */
public class ModifyArticle extends AbstractAction
{
	public static final ModifyArticle INSTANCE = new ModifyArticle();
	
	private ModifyArticle()
	{
		super("Modify Article");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		Article article = (Article)StorageFactory.getCurrentStorage().getArticles().get(ArticlePanel.INSTANCE.getTable().getSelectedRow());

		JDialog articleDialog = new ArticleDialog(article);
		articleDialog.show();
	}

}
