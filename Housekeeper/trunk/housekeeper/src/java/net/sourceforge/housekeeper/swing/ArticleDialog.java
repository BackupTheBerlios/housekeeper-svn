package net.sourceforge.housekeeper.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import net.sourceforge.housekeeper.model.Article;
import net.sourceforge.housekeeper.storage.StorageFactory;

import com.jgoodies.forms.extras.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author Adrian Gygax
 */
public class ArticleDialog extends ExtendedDialog
{
	private JTextField nameField;
	private JTextField storeField;
	private JTextField priceField;
	private JTextField quantityField;
	private JTextField unitField;
	
	private JButton buttonOK;
	private JButton buttonCancel;
	
	/**
	 * 
	 * @param owner
	 * @param modal
	 */
	public ArticleDialog()
	{
		super(MainFrame.INSTANCE, true);
		
		initComponents();
		buildLayout();
		pack();
		center();
	}
	
	private void initComponents()
	{
		nameField = new JTextField();
		storeField = new JTextField();
		priceField = new JTextField();
		quantityField = new JTextField();
		unitField = new JTextField();
		
		buttonOK = new JButton("OK");
		buttonOK.addActionListener(new LocalActionListener());
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new DefaultCancelButtonActionListener());
		
	}
	
	private void buildLayout()
	{

		FormLayout layout = new FormLayout(
					"right:pref, 3dlu, 40dlu, 80dlu", // columns
					"");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		
		builder.setDefaultDialogBorder();
		
		builder.appendTitle("New Article");
		builder.nextLine();
		
		builder.append("Name", nameField, 2);
		builder.append("Store", storeField, 2);
		builder.append("Price", priceField);
		builder.append("Quantity", quantityField);
		builder.append("Unit", unitField);
		
		builder.appendSeparator();
		
		builder.append(buildButtonBar(), 4);
		
		setContentPane(builder.getPanel());
	}
	
	private Component buildButtonBar()
	{
		return ButtonBarFactory.buildOKCancelBar(buttonOK, buttonCancel);
	}
	
	private class LocalActionListener implements ActionListener
	{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e)
		{
			Article article = new Article();
			article.setName(nameField.getText());
			article.setStore(storeField.getText());
			article.setPrice(Double.parseDouble(priceField.getText()));
			article.setQuantity(Integer.parseInt(quantityField.getText()));
			article.setQuantityUnit(unitField.getText());
			
			StorageFactory.getCurrentStorage().addArticle(article);
			StorageFactory.getCurrentStorage().saveData();
			
			dispose();
		}
		
	}

}
