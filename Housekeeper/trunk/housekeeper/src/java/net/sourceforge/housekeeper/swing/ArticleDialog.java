package net.sourceforge.housekeeper.swing;

import javax.swing.JTextField;

import com.jgoodies.forms.builder.PanelBuilder;
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
	}
	
	private void buildLayout()
	{
		FormLayout layout = new FormLayout(
			"right:pref, 3dlu, 30dlu, 30dlu", // columns
			"p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p");
		
		PanelBuilder builder = new PanelBuilder(layout);
		builder.setDefaultDialogBorder();
		
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Name",       cc.xy  (1,  1));
		builder.add(nameField,         cc.xywh(3,  1, 2, 1));
		builder.addLabel("Geschäft",       cc.xy  (1,  3));
		builder.add(storeField,         cc.xywh(3,  3, 2, 1));
		
		setContentPane(builder.getPanel());
	}

}
