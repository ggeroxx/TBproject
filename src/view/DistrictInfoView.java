package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;

public class DistrictInfoView extends JFrame{

	private JPanel contentPane;
	private int xx, xy;
	private JLabel lblClose;
	private JMenuBar menuBar;
	private JPanel panel;
	private JTextArea textAreaMuniciplaities;
	private JLabel lblDistrictName;

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
					DistrictInfoView frame = new DistrictInfoView();
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


	
	public DistrictInfoView() 
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
		        DistrictInfoView.this.setLocation(x - xx, y - xy);
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
		
		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 32, 426, 535);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblImg = new JLabel("");
		lblImg.setBounds(10, 23, 379, 474);
		panel.add(lblImg);
		lblImg.setBackground(new Color(255, 255, 255));
		lblImg.setIcon(new ImageIcon(ChangeCredentialsConfiguratorView.class.getResource("/img/login.jpg")));
		
		lblClose = new JLabel("X");
		lblClose.setBackground(new Color(240, 240, 240));
		lblClose.setBounds(787, -5, 35, 27);
		contentPane.add(lblClose);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 795, 22);
		contentPane.add(menuBar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(446, 139, 349, 342);
		contentPane.add(scrollPane);
		
		textAreaMuniciplaities = new JTextArea();
		textAreaMuniciplaities.setFont(new Font("Monospaced", Font.BOLD, 14));
		textAreaMuniciplaities.setEnabled(false);
		textAreaMuniciplaities.setEditable(false);
		scrollPane.setViewportView(textAreaMuniciplaities);
		
		lblDistrictName = new JLabel("");
		lblDistrictName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDistrictName.setForeground(new Color(0, 0, 255));
		lblDistrictName.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistrictName.setBounds(520, 106, 205, 13);
		contentPane.add(lblDistrictName);
		
	}
	
	 public void addButton(JButton button) 
	 {
		contentPane.add(button);
		contentPane.revalidate(); 
		contentPane.repaint();     
	 }
	
	public JMenuBar getmenuBar()
	{
		return menuBar;
	}
	
	public JLabel getCloseLabel() 
	{
        return lblClose;
    }
	
	public void setLblDistrictName(String message)
	{
		lblDistrictName.setText(message);
	}
	
	public void setTextAreaMuniciplaities(String Message)
	{
		textAreaMuniciplaities.setText(Message);
	}
	
	public void createDistrictButton( String districtName, String municipalities ) 
    {
        JButton button = new JButton(districtName);
        button.setPreferredSize(new Dimension(300, button.getPreferredSize().height));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {

	            setLblDistrictName( districtName );
	            setTextAreaMuniciplaities( municipalities );
            }
        });
        
        addButton(button);
        getmenuBar().add(button);
        //button.doClick();
    }
}
