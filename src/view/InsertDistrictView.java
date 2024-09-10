package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import javax.swing.Timer;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;

public class InsertDistrictView extends JFrame{

	private JPanel contentPane;
	private JTextField textMunicipality;
	private JLabel lblErrorMunicipality;
	private int xx, xy;
	private JLabel lblClose;
	private JButton btnAddDistrict;
	private JTextArea txtMunicipalities; 
	private JTextField textNameDistrict;
	private JLabel lblMunicipality;
	private JScrollPane scrollPane;
	private JButton btnAddmunicipality;
	private JLabel lblErrorNameDistrict;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					InsertDistrictView frame = new InsertDistrictView();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}


	
	public InsertDistrictView() 
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
		        InsertDistrictView.this.setLocation(x - xx, y - xy);
		    }
		});
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 819, 593);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 10, 426, 666);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblImg = new JLabel("");
		lblImg.setBounds(38, 23, 351, 512);
		panel.add(lblImg);
		lblImg.setBackground(new Color(255, 255, 255));
		lblImg.setIcon(new ImageIcon(ChangeCredentialsConfiguratorView.class.getResource("/img/login.jpg")));
		
		btnAddDistrict = new JButton("AddDistrict");
		btnAddDistrict.setForeground(Color.WHITE);
		btnAddDistrict.setBackground(SystemColor.textHighlight);
		btnAddDistrict.setBounds(446, 414, 326, 41);
		contentPane.add(btnAddDistrict);
		
		textMunicipality = new JTextField();
		textMunicipality.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					btnAddmunicipality.doClick();
                }
			}
		});
		textMunicipality.setHorizontalAlignment(SwingConstants.CENTER);
		textMunicipality.setFont(new Font("Tahoma", Font.BOLD, 12));
		textMunicipality.setBounds(446, 319, 326, 48);
		contentPane.add(textMunicipality);
		textMunicipality.setColumns(10);
		textMunicipality.setVisible(false);
		
		lblMunicipality = new JLabel("MUNICIPALITY");
		lblMunicipality.setBounds(446, 296, 103, 13);
		contentPane.add(lblMunicipality);
		lblMunicipality.setVisible(false);
		
		lblClose = new JLabel("X");
		lblClose.setBackground(new Color(240, 240, 240));
		lblClose.setBounds(783, 0, 35, 27);
		contentPane.add(lblClose);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		
		lblErrorMunicipality = new JLabel("");
		lblErrorMunicipality.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblErrorMunicipality.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorMunicipality.setForeground(Color.RED);
		lblErrorMunicipality.setBackground(new Color(255, 255, 255));
		lblErrorMunicipality.setBounds(446, 365, 326, 27);
		contentPane.add(lblErrorMunicipality);
		lblErrorMunicipality.setVisible(false);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(446, 186, 326, 90);
		contentPane.add(scrollPane);
		scrollPane.setVisible(false);
		
		txtMunicipalities = new JTextArea();
		txtMunicipalities.setFont(new Font("Monospaced", Font.BOLD, 13));
		txtMunicipalities.setEnabled(false);
		txtMunicipalities.setEditable(false);
		scrollPane.setViewportView(txtMunicipalities);
		txtMunicipalities.setVisible(false);
		
		JLabel lblDistrict = new JLabel("DISTRICT");
		lblDistrict.setBounds(446, 77, 103, 13);
		contentPane.add(lblDistrict);
		
		textNameDistrict = new JTextField();
		textNameDistrict.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					btnAddDistrict.doClick();
                }
			}
		});
		textNameDistrict.setHorizontalAlignment(SwingConstants.CENTER);
		textNameDistrict.setFont(new Font("Tahoma", Font.BOLD, 12));
		textNameDistrict.setColumns(10);
		textNameDistrict.setBounds(446, 97, 326, 48);
		contentPane.add(textNameDistrict);
		
		lblErrorNameDistrict = new JLabel("");
		lblErrorNameDistrict.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorNameDistrict.setForeground(Color.RED);
		lblErrorNameDistrict.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblErrorNameDistrict.setBackground(Color.WHITE);
		lblErrorNameDistrict.setBounds(446, 149, 326, 27);
		contentPane.add(lblErrorNameDistrict);
		
		btnAddmunicipality = new JButton("AddMunicipality");
		btnAddmunicipality.setForeground(Color.WHITE);
		btnAddmunicipality.setBackground(SystemColor.textHighlight);
		btnAddmunicipality.setBounds(446, 477, 326, 41);
		contentPane.add(btnAddmunicipality);
		btnAddmunicipality.setVisible(false);
		
		
	}
	
	public JButton getAddDistrictAddButton()
	{
		return btnAddDistrict;
	}
	
	public String getTextMunicipality()
	{
		return textMunicipality.getText();
	}

	public JLabel getlblMunicipality()
	{
		return lblMunicipality;
	}
	
    public JLabel getCloseLabel() 
	{
        return lblClose;
    }
    
    public JLabel getlblErrorMunicipality()
    {
    	return lblErrorMunicipality;
    }
    
    public JScrollPane getScrollPane()
    {
    	return scrollPane;
    }
    
    public JTextArea getTxtMunicipalities()
    {
    	return txtMunicipalities;
    }
    
    public JTextField getTextFiledMunicipality()
    {
    	return textMunicipality;
    }
    
    public JTextField getTextFiledNameDistrict()
    {
    	return textNameDistrict;
    }
    
    public String getTextNameDistrict()
    {
    	return textNameDistrict.getText();
    }
    
    
    public JButton getAddmunicipalityButton()
    {
    	return btnAddmunicipality;
    }
    
    public void setTextNameDistrict(String message)
    {
    	textNameDistrict.setText(message);
    }
    
    public void setLblErrorMunicipality(String message)
    {
    	lblErrorMunicipality.setText(message);
    }
   
    public void setTextMunicipalities(String municipalities)
    {
    	txtMunicipalities.setText(municipalities);
    }
    
    public void setTxtMunicipality(String municipalities)
    {
    	textMunicipality.setText(municipalities);
    }
    
    public void setLblErrorNameDistrict(String message)
    {
    	lblErrorNameDistrict.setText(message);
    }
    
    public void blockAddDistrictAnEnableAddMunicipality( String nameDistrict )
    {
    	setLblErrorNameDistrict("");
        setTextNameDistrict( nameDistrict );
        getTextFiledNameDistrict().setEnabled(false);
        getAddDistrictAddButton().setVisible(false);
        getlblMunicipality().setVisible(true);
        getTextFiledMunicipality().setVisible(true);
        getlblErrorMunicipality().setVisible(true);
        getAddmunicipalityButton().setVisible(true);
        revalidate();
        repaint();
    }
    
    public void addedSuccesfullMunicipality( String succefullMessage, String municipalities)
    {
    	setLblErrorMunicipality( succefullMessage );
        setTextMunicipalities("");
        setTextMunicipalities( municipalities );
    	getScrollPane().setVisible(true);
        getTxtMunicipalities().setVisible(true);   
        getlblErrorMunicipality().setForeground(Color.GREEN);

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                setLblErrorMunicipality("");
                getlblErrorMunicipality().setForeground(Color.RED); 
                setTxtMunicipality("");
            }
        });
        timer.setRepeats(false); 
        timer.start();
    }
    
    
    public void resetFields()
    {
        setTextNameDistrict("");
        getTextFiledNameDistrict().setEnabled(true);
        getAddDistrictAddButton().setVisible(true);
        getScrollPane().setVisible(false);
        getTxtMunicipalities().setVisible(false);
        getlblMunicipality().setVisible(false);
        getTextFiledMunicipality().setVisible(false);
        getlblErrorMunicipality().setVisible(false);
        getAddmunicipalityButton().setVisible(false);
        setTextMunicipalities("");
        setTxtMunicipality("");
        setLblErrorMunicipality("");
        setLblErrorNameDistrict("");
        revalidate();
        repaint();
    }
}
