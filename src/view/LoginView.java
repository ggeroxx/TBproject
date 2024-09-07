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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginView extends JFrame implements FieldsView{
	
	private JPanel contentPane;
	private JTextField textUsername;
	private JPasswordField passwordField;
	private JLabel lblError;
	private JButton btnLogin;
	private JButton btnSignUp;
	private JLabel lblClose;
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


	
	public LoginView() 
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
		        LoginView.this.setLocation(x - xx, y - xy);
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
		lblImg.setIcon(new ImageIcon(LoginView.class.getResource("/img/login.jpg")));
		panel.add(lblImg);
		
		btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(SystemColor.textHighlight);
		btnLogin.setBounds(35, 360, 326, 41);
		contentPane.add(btnLogin);
		
		textUsername = new JTextField();
		textUsername.setHorizontalAlignment(SwingConstants.CENTER);
		textUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		textUsername.setBounds(35, 119, 326, 48);
		contentPane.add(textUsername);
		textUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setBounds(35, 96, 74, 13);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(35, 190, 74, 13);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
                    btnLogin.doClick();
                }
			}
		});
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(35, 213, 326, 48);
		contentPane.add(passwordField);
		
		lblClose = new JLabel("X");
		lblClose.setBackground(new Color(240, 240, 240));
		lblClose.setBounds(767, 0, 35, 27);
		contentPane.add(lblClose);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		
		lblError = new JLabel("");
		lblError.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.RED);
		lblError.setBackground(new Color(255, 255, 255));
		lblError.setBounds(35, 294, 326, 27);
		contentPane.add(lblError);
		
		JLabel lblNewAccount = new JLabel("New Account?");
		lblNewAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewAccount.setBounds(35, 431, 326, 13);
		contentPane.add(lblNewAccount);
		
		btnSignUp = new JButton("Sign Up");
		btnSignUp.setForeground(Color.WHITE);
		btnSignUp.setBackground(SystemColor.textHighlight);
		btnSignUp.setBounds(35, 454, 326, 41);
		contentPane.add(btnSignUp);
	}
	
    public String getUsername() 
    {
        return textUsername.getText();
    }

    public String getPassword() 
    {
        return new String(passwordField.getPassword());
    }
    
    public void setTextUsername(String message)
    {
    	textUsername.setText(message);
    }
    
    public void setPasswordField(String message)
    {
    	passwordField.setText(message);
    }

    public void setMessage(String message) 
    {
    	lblError.setText(message);
    }

	public JButton getLoginButton() 
	{
        return btnLogin;
    }

    public JButton getSignUpButton() 
	{
        return btnSignUp;
    }

    public JLabel getCloseLabel() 
	{
        return lblClose;
    }

	@Override
	public void setMessageErrorUsername(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMessageErrorPassword(String message) {
		// TODO Auto-generated method stub
		
	}
}
