package net.sourceforge.housekeeper.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
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
			"right:pref, 3dlu, 40dlu, 40dlu", // columns
			"p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p");  //rows
		
		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();
		
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Name",       cc.xy  (1,  1));
		builder.add(nameField,         cc.xywh(3,  1, 2, 1));
		builder.addLabel("Store",       cc.xy  (1,  3));
		builder.add(storeField,         cc.xywh(3,  3, 2, 1));
		builder.addLabel("Price",       cc.xy  (1,  5));
		builder.add(priceField,         cc.xywh(3,  5, 1, 1));
		builder.addLabel("Quantity",       cc.xy  (1,  7));
		builder.add(quantityField,         cc.xywh(3,  7, 1, 1));
		builder.addLabel("Unit",       cc.xy  (1,  9));
		builder.add(unitField,         cc.xywh(3,  9, 1, 1));
		builder.add(buildButtonBar(), cc.xywh(1, 11, 4, 1));
		
	
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
			System.out.println("OK");
			dispose();
		}
		
	}

}
