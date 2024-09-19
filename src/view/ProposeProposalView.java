package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;

import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ProposeProposalView extends JFrame{

	private JPanel contentPane;
	private int xx, xy;
	private JTextField textValue;
	private JButton btnConfirm;
	private ButtonGroup groupRequestedCategory;
	private ButtonGroup groupOfferedCategory;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLabel lblClose;
	private JButton btnRequestCategory;
	private JLabel lblErrorValue;
	private JButton btnCancel;
	private JLabel lblValue;
	private JButton btnOfferCategory;
	private JButton btnBack;
	
	public ProposeProposalView() 
	{

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
		        ProposeProposalView.this.setLocation(x - xx, y - xy);
		    }
		});
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1206, 705);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textValue = new JTextField();
		textValue.setFont(new Font("Tahoma", Font.BOLD, 10));
		textValue.setHorizontalAlignment(SwingConstants.CENTER);
		textValue.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					btnConfirm.doClick();
                }
			}
		});
		
		
		
		textValue.setBounds(467, 575, 346, 19);
		contentPane.add(textValue);
		textValue.setColumns(10);
		
		btnConfirm = new JButton("CONFIRM");
		btnConfirm.setForeground(Color.WHITE);
		btnConfirm.setBackground(new Color(0, 0, 255));
		btnConfirm.setBounds(467, 637, 174, 21);
		contentPane.add(btnConfirm);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 43, 1156, 509);
		contentPane.add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);	
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		lblNewLabel = new JLabel("PROPOSE PROPOSAL");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setForeground(new Color(0, 0, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(555, 10, 156, 23);
		contentPane.add(lblNewLabel);
		
		lblClose = new JLabel("X");
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setBackground(SystemColor.menu);
		lblClose.setBounds(1170, 0, 35, 27);
		contentPane.add(lblClose);
		
		btnRequestCategory = new JButton("CONFIRM REQUEST CATEGORY ");
		btnRequestCategory.setForeground(Color.WHITE);
		btnRequestCategory.setBackground(Color.BLUE);
		btnRequestCategory.setBounds(467, 617, 346, 21);
		contentPane.add(btnRequestCategory);
		
		lblErrorValue = new JLabel("");
		lblErrorValue.setForeground(Color.RED);
		lblErrorValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorValue.setBounds(467, 594, 346, 19);
		contentPane.add(lblErrorValue);
		
		btnCancel = new JButton("CANCEL");
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setBackground(Color.RED);
		btnCancel.setBounds(639, 637, 174, 21);
		contentPane.add(btnCancel);
		
		lblValue = new JLabel("VALUE");
		lblValue.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblValue.setBounds(467, 562, 346, 13);
		contentPane.add(lblValue);
		
		btnOfferCategory = new JButton("CONFIRM OFFER CATEGORY ");
		btnOfferCategory.setForeground(Color.WHITE);
		btnOfferCategory.setBackground(Color.BLUE);
		btnOfferCategory.setBounds(467, 617, 346, 21);
		contentPane.add(btnOfferCategory);
		
		btnBack = new JButton("<");
		btnBack.setForeground(Color.WHITE);
		btnBack.setBackground(Color.BLUE);
		btnBack.setBounds(26, 617, 52, 21);
		contentPane.add(btnBack);
		
		groupRequestedCategory = new ButtonGroup();
		groupOfferedCategory = new ButtonGroup();
	}
	
	public void addRadioButton(JRadioButton radioButton, boolean requestedCategory) 
	 {
		radioButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
		if ( requestedCategory ) groupRequestedCategory.add(radioButton);
		else groupOfferedCategory.add(radioButton);
		panel.add(radioButton);
		contentPane.revalidate(); 
		contentPane.repaint();     
	 }
	
	public ButtonGroup getOfferedCategoryGroup()
	{
		return groupOfferedCategory;
	}
	
	public ButtonGroup getRequestedCategoryGroup()
	{
		return groupRequestedCategory;
	}
	
	public JScrollPane getScrollPane()
	{
		return scrollPane;
	}
	
	public JButton getConfirmButton()
	{
		return btnConfirm;
	}
	
	public JButton getRequestCategoryButton()
	{
		return btnRequestCategory;
	}
	
	public JButton getOfferCategoryButton()
	{
		return btnOfferCategory;
	}
	
	public JButton getCancelButton()
	{
		return btnCancel;
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	public String getTextValue()
	{
		return textValue.getText();
	}
	
	public JTextField getTextFieldValue()
	{
		return textValue;
	}
	
	public void setTextValue(String message)
	{
		textValue.setText(message);
	}
	
	public JLabel getCloseLabel() 
	{
        return lblClose;
    }
	
	public JLabel getLblValue() 
	{
        return lblValue;
    }
	
	public void setLblErrorValue (String message)
	{
		lblErrorValue.setText(message);
	}
	
	public JButton getBackButton()
	{
		return btnBack;
	}
	
	public void init()
	{
		getTextFieldValue().setVisible(false);
		getConfirmButton().setVisible(false);
		getOfferCategoryButton().setVisible(false);
		getCancelButton().setVisible(false);
		getLblValue().setVisible(false);
		getRequestCategoryButton().setVisible(true);
		lblErrorValue.setText("");
		this.setUndecorated(true);
		this.setVisible(true);
		getBackButton().setVisible(true);
	}
	
	public void resetFields()
	{
		getPanel().removeAll();
		getPanel().revalidate();
		getPanel().repaint();
        setTextValue("");
        lblErrorValue.setText("");
	}
	
    public JRadioButton addRadioButton( String info, boolean requestCategory ) 
    {
        JRadioButton radioButton = new JRadioButton( info );
        addRadioButton( radioButton, requestCategory );
        return radioButton;
    }
    
    public JRadioButton getSelectedRadioButton (boolean requestedCategory) 
	{
    	Enumeration<AbstractButton> buttons;
	    if ( requestedCategory )  buttons = getRequestedCategoryGroup().getElements();
	    else buttons = getOfferedCategoryGroup().getElements();
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
    
    public void addLblCategory( String info ) 
	{
		JLabel label = new JLabel( info );
		label.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel.add(label);
		contentPane.revalidate(); 
		contentPane.repaint();     
	}
    
    public void removeAllRadioButtons( boolean requestedCategory ) 
	{
		
    	Enumeration<AbstractButton> buttons;
	    if (requestedCategory )  buttons = getRequestedCategoryGroup().getElements();
	    else buttons = getOfferedCategoryGroup().getElements();
	    while (buttons.hasMoreElements()) 
	    {
	        AbstractButton button = buttons.nextElement();
	        if (requestedCategory )  getRequestedCategoryGroup().remove(button);
		    else getOfferedCategoryGroup().remove(button);
	    }
	    
	    getPanel().removeAll(); 
	    getPanel().revalidate();
	    getPanel().repaint(); 
	    
	}
}
