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
final class ArticlePanel extends JPanel
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
		
		public Class getColumnClass(int columnIndex)
		{
			switch (columnIndex)
			{
				case 0 :
					return new String().getClass();
				
				case 1 :
					return new String().getClass();
				
				case 2:
					return new Integer(0).getClass();
				
				case 3:
					return new String().getClass();
					
				case 4:
					return new Double(0).getClass();

				default :
					return null;
			}
		}
		
		public String getColumnName(int columnIndex)
		{
			switch (columnIndex)
			{
				case 0 :
					return "Name";
				
				case 1 :
					return "Vendor";
				
				case 2:
					return "Quantity";
				
				case 3:
					return "Unit";
					
				case 4:
					return "Price";

				default :
					return null;
			}
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
					return null;
			}
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
