package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;

public class InsertNewHierarchyView extends JFrame {

	private JPanel contentPane;
	private JTextField textCategory;
	private JTextField textField;
	private JTextArea textArea;
	private JLabel lblCategoryError;
	private JLabel lblFieldError;
	private JButton btnInsertRoot;
	private JCheckBox chckbxLeafCategory;
	private JTextField textDescription;
	private JLabel lblDescriptionError;
	private JLabel lblDescription;
	private JLabel lblParentID;
	private JScrollPane scrollPaneParentID;
	private JPanel panelParentID;
	private JTextField textFieldType;
	private JLabel lblFieldType;
	private JLabel lblFieldTypeError;
	private ButtonGroup group;
	private JButton btnInsertCategory;
	private int xx, xy;
	private JButton btnInsertParentId;
	private JLabel lblClose;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertNewHierarchyView frame = new InsertNewHierarchyView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InsertNewHierarchyView() {
		
		addMouseListener(new MouseAdapter() 
		{
		    @Override
		    public void mousePressed(MouseEvent e) 
		    {
		        xx = e.getX();
		        xy = e.getY();
		    }
		});

		addMouseMotionListener(new MouseMotionAdapter() 
		{
		    @Override
		    public void mouseDragged(MouseEvent e) 
		    {
		        int x = e.getXOnScreen();
		        int y = e.getYOnScreen(); 
		        InsertNewHierarchyView.this.setLocation(x - xx, y - xy);
		    }
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1098, 716);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblClose = new JLabel("X");
		lblClose.setBounds(1071, 0, 19, 19);
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setBackground(SystemColor.menu);
		contentPane.add(lblClose);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(588, 20, 470, 619);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Monospaced", Font.BOLD, 13));
		scrollPane.setViewportView(textArea);
		textArea.setEnabled(false);
		textArea.setEditable(false);
		
		textCategory = new JTextField();
		textCategory.setHorizontalAlignment(SwingConstants.CENTER);
		textCategory.setBounds(140, 73, 342, 37);
		contentPane.add(textCategory);
		textCategory.setColumns(10);
		
		JLabel lblCategory = new JLabel("CATEGORY NAME");
		lblCategory.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblCategory.setBounds(10, 50, 593, 13);
		contentPane.add(lblCategory);
		
		JLabel lblHierarchyInsertion = new JLabel("HIERARCHY INSERTION");
		lblHierarchyInsertion.setForeground(new Color(0, 0, 255));
		lblHierarchyInsertion.setHorizontalAlignment(SwingConstants.CENTER);
		lblHierarchyInsertion.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHierarchyInsertion.setBounds(10, 20, 593, 13);
		contentPane.add(lblHierarchyInsertion);
		
		lblCategoryError = new JLabel("");
		lblCategoryError.setForeground(Color.RED);
		lblCategoryError.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategoryError.setBounds(10, 109, 593, 19);
		contentPane.add(lblCategoryError);
		
		JLabel lblField = new JLabel("FIELD");
		lblField.setHorizontalAlignment(SwingConstants.CENTER);
		lblField.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblField.setBounds(10, 138, 593, 13);
		contentPane.add(lblField);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);
		textField.setBounds(140, 153, 342, 37);
		contentPane.add(textField);
		
		lblFieldError = new JLabel("");
		lblFieldError.setForeground(Color.RED);
		lblFieldError.setHorizontalAlignment(SwingConstants.CENTER);
		lblFieldError.setBounds(10, 189, 593, 19);
		contentPane.add(lblFieldError);
		
		btnInsertRoot = new JButton("INSERT ROOT CATEGORY");
		btnInsertRoot.setBackground(new Color(0, 0, 255));
		btnInsertRoot.setForeground(new Color(0, 0, 0));
		btnInsertRoot.setBounds(140, 241, 342, 31);
		contentPane.add(btnInsertRoot);
		
		chckbxLeafCategory = new JCheckBox("Leaf Category?");
		chckbxLeafCategory.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxLeafCategory.setFont(new Font("Tahoma", Font.BOLD, 10));
		chckbxLeafCategory.setBackground(new Color(255, 255, 255));
		chckbxLeafCategory.setBounds(10, 214, 572, 21);
		contentPane.add(chckbxLeafCategory);
		
		textDescription = new JTextField();
		textDescription.setHorizontalAlignment(SwingConstants.CENTER);
		textDescription.setColumns(10);
		textDescription.setBounds(140, 274, 342, 37);
		contentPane.add(textDescription);
		
		lblDescriptionError = new JLabel("");
		lblDescriptionError.setForeground(Color.RED);
		lblDescriptionError.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescriptionError.setBounds(10, 311, 593, 13);
		contentPane.add(lblDescriptionError);
		
		lblDescription = new JLabel("DESCRIPTION");
		lblDescription.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblDescription.setBounds(10, 259, 593, 13);
		contentPane.add(lblDescription);
		
		lblParentID = new JLabel("PARENT ID");
		lblParentID.setHorizontalAlignment(SwingConstants.CENTER);
		lblParentID.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblParentID.setBounds(10, 353, 593, 13);
		contentPane.add(lblParentID);
		
		scrollPaneParentID = new JScrollPane();
		scrollPaneParentID.setBounds(140, 375, 342, 121);
		contentPane.add(scrollPaneParentID);
		
		panelParentID = new JPanel();
		scrollPaneParentID.setViewportView(panelParentID);
		panelParentID.setLayout(new BoxLayout(panelParentID, BoxLayout.Y_AXIS));
		
		textFieldType = new JTextField();
		textFieldType.setColumns(10);
		textFieldType.setBounds(140, 527, 342, 37);
		contentPane.add(textFieldType);
		
		lblFieldType = new JLabel("FIELD TYPE");
		lblFieldType.setHorizontalAlignment(SwingConstants.CENTER);
		lblFieldType.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblFieldType.setBounds(10, 512, 593, 13);
		contentPane.add(lblFieldType);
		
		lblFieldTypeError = new JLabel("");
		lblFieldTypeError.setForeground(Color.RED);
		lblFieldTypeError.setHorizontalAlignment(SwingConstants.CENTER);
		lblFieldTypeError.setBounds(10, 564, 593, 19);
		contentPane.add(lblFieldTypeError);
		
		btnInsertCategory = new JButton("INSERT CATEGORY");
		btnInsertCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnInsertCategory.setForeground(Color.BLACK);
		btnInsertCategory.setBackground(Color.BLUE);
		btnInsertCategory.setBounds(140, 335, 342, 31);
		contentPane.add(btnInsertCategory);
		
		btnInsertParentId = new JButton("INSERT PARENT ID & FIELD TYPE");
		btnInsertParentId.setForeground(Color.BLACK);
		btnInsertParentId.setBackground(Color.BLUE);
		btnInsertParentId.setBounds(140, 593, 342, 31);
		contentPane.add(btnInsertParentId);
		
		group = new ButtonGroup();
	}
	
	public JRadioButton addRadioButton( String info ) 
	 {
		JRadioButton radioButton = new JRadioButton( info );
		radioButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
		group.add(radioButton);
		panelParentID.add(radioButton);
		contentPane.revalidate(); 
		contentPane.repaint(); 
		return radioButton;
	 }
	
	public void setTextArea( String Message )
	{
		textArea.setText(Message);
	}
	
	public JTextField getTextFieldCategory()
	{
		return textCategory;
	}
	
	public void setTextCategory(String message)
	{
		textCategory.setText(message);
	}
	
	public String getTextCategory()
	{
		return textCategory.getText();
	}
	
	public void setLblCategoryError (String message)
	{
		lblCategoryError.setText(message);
	}
	
	public String getTextField()
	{
		return textField.getText();
	}
	
	public JTextField getTextFieldField()
	{
		return textField;
	}
	
	public void setTextField( String message )
	{
		textField.setText( message );
	}
	
	public void setLblFieldError (String message)
	{
		lblFieldError.setText(message);
	}
	
	public JButton getInsertRootButton()
	{
		return btnInsertRoot;
	}
	
	public JButton getInsertCategoryButton()
	{
		return btnInsertCategory;
	}
	
	public JCheckBox getChckbxLeafCategory()
	{
		return chckbxLeafCategory;
	}
	
	public JLabel getLblDescription()
	{
		return lblDescription;
	}
	
	public JTextField getTextFieldDescription()
	{
		return textDescription;
	}
	
	public String getTextDescription()
	{
		return textDescription.getText();
	}
	
	public void setTextDescription( String message )
	{
		textDescription.setText(message);
	}
	
	public JLabel getLblDescriptionError()
	{
		return lblDescriptionError;
	}
	
	public void setLblDescriptionError (String message)
	{
		lblDescriptionError.setText(message);
	}
	
	public JLabel getLblParentID()
	{
		return lblParentID;
	}
	
	public JPanel getPanelParentID()
	{
		return panelParentID;
	}
	
	public JLabel getLblFieldType()
	{
		return lblFieldType;
	}
	
	public JTextField getTextFieldFieldType()
	{
		return textFieldType;
	}
	
	public String getTextFieldType()
	{
		return textFieldType.getText();
	}
	
	public void setTextFieldType( String message )
	{
		textFieldType.setText(message);
	}
	
	public JLabel getLblFiledTypeError()
	{
		return lblFieldTypeError;
	}
	
	public void setLblFieldTypeError (String message)
	{
		lblFieldTypeError.setText(message);
	}
	
	public ButtonGroup getGroup()
	{
		return group;
	}
	
	public JScrollPane getScrollPaneParentID()
	{
		return scrollPaneParentID;
	}
	
	public JButton getInsertParentIdButton()
	{
		return btnInsertParentId;
	}
	
	public JLabel getCloseLabel() 
	{
        return lblClose;
    }
	
	public void chckbxLeafCategoryDeselected()
	{
		getTextFieldField().setEditable(true);
        repaint();
	}
	
	public void chckbxLeafCategorySelected()
	{
		getTextFieldField().setEditable(false);
        setTextField("");
        setLblFieldError("");
        repaint();
	}
	
	public void initInsertRoot()
	{
		getInsertRootButton().setVisible(true);
		getChckbxLeafCategory().setVisible(false);
        getLblDescription().setVisible(false);
        getTextFieldDescription().setVisible(false);
        getLblDescriptionError().setVisible(false);
        getLblParentID().setVisible(false);
        getScrollPaneParentID().setVisible(false);
        getPanelParentID().setVisible(false);
        getLblFieldType().setVisible(false);
        getTextFieldFieldType().setVisible(false);
        getLblFiledTypeError().setVisible(false);
        getInsertCategoryButton().setVisible(false);
        getInsertParentIdButton().setVisible(false);
        setUndecorated(true);
        setVisible(true);
	}
	
	public void afterInsertRoot()
	{
        setTextCategory("");
        setTextField("");
        getChckbxLeafCategory().setVisible(true);
        getLblDescription().setVisible(true);
        getTextFieldDescription().setVisible(true);
        getLblDescriptionError().setVisible(true);
        getLblParentID().setVisible(true);
        getScrollPaneParentID().setVisible(true);
        getPanelParentID().setVisible(true);
        getLblFieldType().setVisible(true);
        getTextFieldFieldType().setVisible(true);
        getLblFiledTypeError().setVisible(true);
        getInsertCategoryButton().setVisible(true);
        getInsertRootButton().setVisible(false);
        dispose();
        setUndecorated(true);
        setVisible(true);
	}
	
	public void initInsertCategoryAfterRoot()
	{
		getInsertParentIdButton().setVisible(false);
        getLblParentID().setVisible(false);
        getScrollPaneParentID().setVisible(false);
        getPanelParentID().setVisible(false);
        getLblFieldType().setVisible(false);
        getTextFieldFieldType().setVisible(false);
        getLblFiledTypeError().setVisible(false);
        getTextFieldCategory().setEditable(true);
        getTextFieldField().setEditable(true);
        getTextFieldDescription().setEditable(true);
        getChckbxLeafCategory().setEnabled(true);
        getChckbxLeafCategory().setSelected(false);
        getInsertCategoryButton().setVisible(true);
        repaint();
	}
	
	public void initInsertFiledTypeAndParentID ()
	{
		getLblParentID().setVisible(true);
        getScrollPaneParentID().setVisible(true);
        getPanelParentID().setVisible(true);
        getLblFieldType().setVisible(true);
        getTextFieldFieldType().setVisible(true);
        getLblFiledTypeError().setVisible(true);
        getTextFieldCategory().setEditable(false);
        getTextFieldField().setEditable(false);
        getTextFieldDescription().setEditable(false);
        getChckbxLeafCategory().setEnabled(false);
        getInsertCategoryButton().setVisible(false);
        getInsertParentIdButton().setVisible(true);
        repaint();
	}
	
	public void afterInsertParentIDAndFiledType ()
	{
		setLblFieldTypeError("");
        setTextCategory("");
        setTextField("");
        setTextDescription("");
        setTextFieldType("");
        getPanelParentID().removeAll();
        getInsertParentIdButton().removeActionListener(getInsertParentIdButton().getActionListeners()[0]);
        repaint();
	}
	
	public void setFalseAllRadioButton ()
	{
		ButtonGroup group = getGroup();
		Enumeration<AbstractButton> buttons = group.getElements();
		while (buttons.hasMoreElements()) 
		{
		    AbstractButton button = buttons.nextElement();
		    button.setSelected(false);
		}
		getGroup().clearSelection();
	}
	
	public JRadioButton getSelectedRadioButton () 
	{
	    Enumeration<AbstractButton> buttons = getGroup().getElements();
	    while (buttons.hasMoreElements()) 
        {
	        JRadioButton button = (JRadioButton) buttons.nextElement();
	        if (button.isSelected()) 
            {
	            return button;
	        }
	    }
	    return null;
	}
	

	public void removeAllRadioButtons() 
	{
		
	    Enumeration<AbstractButton> buttons = getGroup().getElements();
	    while (buttons.hasMoreElements()) 
	    {
	        AbstractButton button = buttons.nextElement();
	        getGroup().remove(button);
	    }
	    
	    getPanelParentID().removeAll(); 
	    getPanelParentID().revalidate();
	    getPanelParentID().repaint(); 
	    
	}
		
}
