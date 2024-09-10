package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;

public class ChangeCredentialsConfiguratorView extends JFrame {

	private JPanel contentPane;
	private JTextField textUsername;
	private JPasswordField passwordField;
	private JLabel lblErrorUsername;
	private JLabel lblErrorPassword;
	private JLabel lblClose;
	private JButton btnChangeCredentials;
	private int xx, xy;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Home frame = new Home();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}*/


	
	public ChangeCredentialsConfiguratorView( ) 
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
		        ChangeCredentialsConfiguratorView.this.setLocation(x - xx, y - xy);
		    }
		});
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 801, 565);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(392, 20, 410, 568);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblImg = new JLabel("");
		lblImg.setBounds(10, 10, 351, 512);
		lblImg.setBackground(new Color(255, 255, 255));
		lblImg.setIcon(new ImageIcon(ChangeCredentialsConfiguratorView.class.getResource("/img/login.jpg")));
		panel.add(lblImg);
		
		btnChangeCredentials = new JButton("Change Credentials");
		btnChangeCredentials.setForeground(Color.WHITE);
		btnChangeCredentials.setBackground(SystemColor.textHighlight);
		btnChangeCredentials.setBounds(35, 360, 326, 41);
		contentPane.add(btnChangeCredentials);
		
		textUsername = new JTextField();
		textUsername.setHorizontalAlignment(SwingConstants.CENTER);
		textUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		textUsername.setBounds(35, 119, 326, 48);
		contentPane.add(textUsername);
		textUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("NEW USERNAME");
		lblUsername.setBounds(35, 96, 103, 13);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("NEW PASSWORD");
		lblPassword.setBounds(35, 209, 103, 13);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(35, 232, 326, 48);
		contentPane.add(passwordField);
		
		lblClose = new JLabel("X");
		lblClose.setBackground(new Color(240, 240, 240));
		lblClose.setBounds(767, 0, 35, 27);
		contentPane.add(lblClose);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		
		lblErrorUsername = new JLabel("");
		lblErrorUsername.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblErrorUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorUsername.setForeground(Color.RED);
		lblErrorUsername.setBackground(new Color(255, 255, 255));
		lblErrorUsername.setBounds(35, 172, 326, 27);
		contentPane.add(lblErrorUsername);
		
		lblErrorPassword = new JLabel("");
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					btnChangeCredentials.doClick();
                }
			}
		});
		lblErrorPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorPassword.setForeground(Color.RED);
		lblErrorPassword.setFont(new Font("Tahoma", Font.BOLD, 8));
		lblErrorPassword.setBackground(Color.WHITE);
		lblErrorPassword.setBounds(35, 290, 326, 27);
		contentPane.add(lblErrorPassword);
	}
	
    public String getUsername() 
    {
        return textUsername.getText();
    }

    public String getPassword() 
    {
        return new String(passwordField.getPassword());
    }

	public JLabel getCloseLabel() 
	{
        return lblClose;
    }
	
	public JButton getButtonChangeCredentials()
	{
		return btnChangeCredentials;
	}
	
	public void setTextUsername(String message)
    {
    	textUsername.setText(message);
    }
    
    public void setPasswordField(String message)
    {
    	passwordField.setText(message);
    }

    public void setMessageErrorUsername(String message) 
    {
    	lblErrorUsername.setText(message);
    }
    public void setMessageErrorPassword(String message) 
    {
    	lblErrorPassword.setText(message);
    }
    
    public void resetFiled ()
    {
        setTextUsername("");
        setMessageErrorUsername("");
        setPasswordField("");
        setMessageErrorPassword("");
    }
    
    
    
    
	
}
