package net.sourceforge.housekeeper.swing;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import net.sourceforge.housekeeper.model.Article;
import net.sourceforge.housekeeper.storage.StorageFactory;

/**
 * @author Adrian Gygax
 */
public class ArticlePanel extends JPanel
{
	private JTable table;
	private JScrollPane scrollPane;

	public ArticlePanel()
	{
		table = new JTable(new ArticleModel());
		scrollPane = new JScrollPane(table);

		add(scrollPane);
	}

	private class ArticleModel extends AbstractTableModel implements Observer
	{

		private ArticleModel()
		{
			StorageFactory.getCurrentStorage().addObserver(this);
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount()
		{
			return 5;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount()
		{
			return StorageFactory.getCurrentStorage().getArticles().size();
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int rowIndex, int columnIndex)
		{
			List articles = StorageFactory.getCurrentStorage().getArticles();
			Article article = (Article)articles.get(rowIndex);

			switch (columnIndex)
			{
				case 0 :
					return article.getName();

				case 1 :
					return article.getStore();

				case 2 :
					return new Integer(article.getQuantity());

				case 3 :
					return article.getQuantityUnit();

				case 4 :
					return new Double(article.getPrice());

				default :
					break;
			}
			System.err.println("Null");
			return null;
		}

		/* (non-Javadoc)
		 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
		 */
		public void update(Observable o, Object arg)
		{
			fireTableDataChanged();
		}

	}
}
