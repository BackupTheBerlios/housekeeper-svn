package net.sourceforge.housekeeper.swing.purchase;

import javax.swing.JPanel;
import javax.swing.JTable;

import net.sourceforge.housekeeper.swing.articledescription.ArticleDescriptionTableModel;


class ArticleSelection extends JPanel
{

    private JTable table;

	ArticleSelection()
    {
		table = new JTable(ArticleDescriptionTableModel.getInstance());
		add(table);
    }
    
}