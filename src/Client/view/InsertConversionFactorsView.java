package Client.view;

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
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;


public class InsertConversionFactorsView extends JFrame{

	private JPanel contentPane;
	private int xx, xy;
	private JTextField textValue;
	private JLabel lblRange;
	private JButton btnInsertValue;
	private ButtonGroup group;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JLabel lblError;
	private JLabel lblNewLabel;
	private JLabel lblClose;
	
	public InsertConversionFactorsView() 
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
		        InsertConversionFactorsView.this.setLocation(x - xx, y - xy);
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
					btnInsertValue.doClick();
                }
			}
		});
		
		
		
		textValue.setBounds(555, 598, 156, 19);
		contentPane.add(textValue);
		textValue.setColumns(10);
		
		btnInsertValue = new JButton("Insert Value");
		btnInsertValue.setBackground(new Color(0, 0, 255));
		btnInsertValue.setBounds(555, 645, 156, 21);
		contentPane.add(btnInsertValue);
		
		lblRange = new JLabel("");
		lblRange.setHorizontalAlignment(SwingConstants.CENTER);
		lblRange.setBounds(555, 575, 156, 13);
		contentPane.add(lblRange);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 43, 1156, 522);
		contentPane.add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);	
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		lblError = new JLabel("");
		lblError.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setBounds(555, 626, 156, 13);
		contentPane.add(lblError);
		
		lblNewLabel = new JLabel("CONVERSION FACTORS");
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
		
		group = new ButtonGroup();
	}
	
	public void addRadioButton(JRadioButton radioButton) 
	 {
		radioButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
		group.add(radioButton);
		panel.add(radioButton);
		contentPane.revalidate(); 
		contentPane.repaint();     
	 }
	
	public ButtonGroup getGroup()
	{
		return group;
	}
	
	public JScrollPane getScrollPane()
	{
		return scrollPane;
	}
	
	public JButton getInsertValueButton()
	{
		return btnInsertValue;
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
	
	public void setlblRange(String message)
	{
		lblRange.setText(message);
	}
	
	public void setlblError(String message)
	{
		lblError.setText(message);
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
	
	public void resetFields()
	{
		getPanel().removeAll();
		getPanel().revalidate();
		getPanel().repaint();
        setlblRange("");
        setTextValue("");
        setlblError("");
	}
	
    public JRadioButton addRadioButton( String info ) 
    {
        JRadioButton radioButton = new JRadioButton( info );
        addRadioButton( radioButton );
        return radioButton;
    }
   
	
}
