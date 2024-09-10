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
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;

public class RegistrationUserView extends JFrame {

	private JPanel contentPane;
	private JTextField textUsername;
	private JPasswordField passwordField;
	private JLabel lblErrorUsername;
	private JLabel lblErrorPassword;
	private int xx, xy;
	private JTextField textEmail;
	private JComboBox comboBoxDistrict;
	private JLabel lblClose;
	private JButton btnSignUp;
	private JTextArea txtMunicipalities; 
	private JLabel lblErrorEmail;
	private JButton btnLogin;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		/*EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					RegistrationUserView frame = new RegistrationUserView(null, null);
					frame.setUndecorated(true);
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});*/
	}


	
	public RegistrationUserView() 
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
		        RegistrationUserView.this.setLocation(x - xx, y - xy);
		    }
		});
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 820, 682);
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
		lblImg.setBounds(30, 84, 351, 512);
		panel.add(lblImg);
		lblImg.setBackground(new Color(255, 255, 255));
		lblImg.setIcon(new ImageIcon(ChangeCredentialsConfiguratorView.class.getResource("/img/login.jpg")));
		
		btnSignUp = new JButton("Sign Up");
		btnSignUp.setForeground(Color.WHITE);
		btnSignUp.setBackground(SystemColor.textHighlight);
		btnSignUp.setBounds(446, 515, 326, 41);
		contentPane.add(btnSignUp);
		
		textUsername = new JTextField();
		textUsername.setHorizontalAlignment(SwingConstants.CENTER);
		textUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		textUsername.setBounds(446, 203, 326, 48);
		contentPane.add(textUsername);
		textUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("USERNAME");
		lblUsername.setBounds(446, 180, 103, 13);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(446, 397, 103, 13);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(446, 420, 326, 48);
		contentPane.add(passwordField);
		
		lblClose = new JLabel("X");
		lblClose.setBackground(new Color(240, 240, 240));
		lblClose.setBounds(783, 0, 35, 27);
		contentPane.add(lblClose);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		
		lblErrorUsername = new JLabel("");
		lblErrorUsername.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblErrorUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorUsername.setForeground(Color.RED);
		lblErrorUsername.setBackground(new Color(255, 255, 255));
		lblErrorUsername.setBounds(446, 249, 326, 27);
		contentPane.add(lblErrorUsername);
		
		lblErrorPassword = new JLabel("");
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				{
					btnSignUp.doClick();
                }
			}
		});
		lblErrorPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorPassword.setForeground(Color.RED);
		lblErrorPassword.setFont(new Font("Tahoma", Font.BOLD, 8));
		lblErrorPassword.setBackground(Color.WHITE);
		lblErrorPassword.setBounds(446, 478, 326, 27);
		contentPane.add(lblErrorPassword);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(446, 68, 326, 90);
		contentPane.add(scrollPane);
		
		txtMunicipalities = new JTextArea();
		txtMunicipalities.setFont(new Font("Monospaced", Font.BOLD, 13));
		txtMunicipalities.setEnabled(false);
		txtMunicipalities.setEditable(false);
		scrollPane.setViewportView(txtMunicipalities);
		
		textEmail = new JTextField();
		textEmail.setHorizontalAlignment(SwingConstants.CENTER);
		textEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		textEmail.setColumns(10);
		textEmail.setBounds(446, 309, 326, 48);
		contentPane.add(textEmail);
		
		lblErrorEmail = new JLabel("");
		lblErrorEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorEmail.setForeground(Color.RED);
		lblErrorEmail.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblErrorEmail.setBackground(Color.WHITE);
		lblErrorEmail.setBounds(446, 360, 326, 27);
		contentPane.add(lblErrorEmail);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(446, 286, 103, 13);
		contentPane.add(lblEmail);
		
		comboBoxDistrict = new JComboBox();
		comboBoxDistrict.setBackground(Color.WHITE);
		comboBoxDistrict.setBounds(522, 41, 250, 21);
		contentPane.add(comboBoxDistrict);
		
		JLabel lblDistrict = new JLabel("DISTRICT");
		lblDistrict.setBounds(446, 45, 103, 13);
		contentPane.add(lblDistrict);
		
		btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(SystemColor.textHighlight);
		btnLogin.setBounds(446, 594, 326, 41);
		contentPane.add(btnLogin);
		
		lblNewLabel = new JLabel("have an account?");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(446, 571, 326, 13);
		contentPane.add(lblNewLabel);
		
		
	}
	
    public String getUsername() 
    {
        return textUsername.getText();
    }

    public String getPassword() 
    {
        return new String(passwordField.getPassword());
    }
    
    public String getEmail() 
    {
        return textEmail.getText();
    }

    public void setTextUsername(String message)
    {
    	textUsername.setText(message);
    }
    
    public void setPasswordField(String message)
    {
    	passwordField.setText(message);
    }
    
    public void setTextEmail(String message)
    {
    	textEmail.setText(message);
    }
    
    public void setMessageErrorUsername(String message) 
    {
    	lblErrorUsername.setText(message);
    }
    
    public void setMessageErrorPassword(String message) 
    {
    	lblErrorPassword.setText(message);
    }
    
    public void setMessageErrorEmail(String message) 
    {
    	lblErrorEmail.setText(message);
    }

    public JLabel getCloseLabel() 
	{
        return lblClose;
    }
    
    public JComboBox<String> getComboBoxDistrict() 
    {
        return comboBoxDistrict;
    }
    
    public JButton getSignUpButton()
    {
    	return btnSignUp;
    }
    
    public void setTextMunicipalities(String municipalities)
    {
    	txtMunicipalities.setText(municipalities);
    }
    
    public JButton getLoginButton()
    {
    	return btnLogin;
    }
    
    public void activeComboBox()
    {
    	getComboBoxDistrict().setEnabled(true);
    }
    
    public void disactiveComboBox()
    {
    	getComboBoxDistrict().setEnabled(false);
    }
    
    public void resetFiled ()
    {
        setTextUsername("");
        setMessageErrorUsername("");
        setPasswordField("");
        setMessageErrorPassword("");
        setTextEmail("");
        setMessageErrorEmail("");
        getComboBoxDistrict().removeAllItems();
        
    }
    
    
}
