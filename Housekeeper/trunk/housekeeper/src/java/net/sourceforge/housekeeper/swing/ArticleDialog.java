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
class ArticleDialog extends ExtendedDialog
{
	private JTextField nameField;
	private JTextField storeField;
	private JTextField priceField;
	private JTextField quantityField;
	private JTextField unitField;
	
	private JButton buttonOK;
	private JButton buttonCancel;
	
	private Article article;
	
	private ActionListener okButtonListener;
	
	/**
	 * 
	 * @param owner
	 * @param modal
	 */
	ArticleDialog()
	{
		super(MainFrame.INSTANCE, true);
		
		article = new Article();
		okButtonListener = new LocalActionListener();
		
		setTitle("New Article");
		init();
	}
	
	ArticleDialog(Article article)
	{
		super(MainFrame.INSTANCE, true);
		
		this.article = article;
		okButtonListener = new ModifyActionListener();
		
		setTitle("Modify Article");
		init();
	}
	
	private void init()
	{
		initComponents();
		buildLayout();
		pack();
		center();
	}
	
	private void initComponents()
	{
		nameField = new JTextField(article.getName());
		storeField = new JTextField(article.getStore());
		priceField = new JTextField("" + article.getPrice());
		quantityField = new JTextField("" + article.getQuantity());
		unitField = new JTextField(article.getQuantityUnit());
		
		buttonOK = new JButton("OK");
		buttonOK.addActionListener(okButtonListener);
		
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
		
		builder.appendTitle("Article");
		builder.nextLine();
		
		builder.append("Name", nameField, 2);
		builder.append("Store", storeField, 2);
		builder.append("Quantity", quantityField);
		builder.append("Unit", unitField);
		builder.append("Price", priceField);
		
		builder.appendSeparator();
		
		builder.append(buildButtonBar(), 4);
		
		setContentPane(builder.getPanel());
	}
	
	private Component buildButtonBar()
	{
		return ButtonBarFactory.buildOKCancelBar(buttonOK, buttonCancel);
	}
	
	private void readFields()
	{
		article.setName(nameField.getText());
		article.setStore(storeField.getText());
		article.setPrice(Double.parseDouble(priceField.getText()));
		article.setQuantity(Integer.parseInt(quantityField.getText()));
		article.setQuantityUnit(unitField.getText());
	}
	
	private class LocalActionListener implements ActionListener
	{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e)
		{
			readFields();
			
			StorageFactory.getCurrentStorage().add(article);
			
			dispose();
		}
		
	}
	
	private class ModifyActionListener implements ActionListener
	{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e)
		{
			readFields();
			
			StorageFactory.getCurrentStorage().update();
			
			dispose();
		}
		
	}

}
