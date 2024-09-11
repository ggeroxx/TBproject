package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JButton;
import java.awt.SystemColor;

public class ConfiguratorMenuView extends JFrame {
	
	private JPanel contentPane;
	private JLabel lblClose;
	private int xx, xy;
	private JButton btnInsertNewDistrict;
	private JButton btnViewDistrict;
	private JButton btnInsertConversionFactors;
	private JButton btnViewAllConversion;
	private JButton btnViewConversionFactorsOfCategory;
	private JButton btnInsertNewHierarchy;
	private JButton btnSaveAll;
	private JButton btnLogout;
	private JButton btnViewHierarchy;

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
					ConfiguratorMenuView frame = new ConfiguratorMenuView();
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


	
	public ConfiguratorMenuView() 
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
		        ConfiguratorMenuView.this.setLocation(x - xx, y - xy);
		    }
		});
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 824, 600);
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
		lblImg.setBounds(27, 27, 351, 512);
		lblImg.setBackground(new Color(255, 255, 255));
		lblImg.setIcon(new ImageIcon(LoginView.class.getResource("/img/login.jpg")));
		panel.add(lblImg);
		
		lblClose = new JLabel("X");
		lblClose.setBackground(new Color(240, 240, 240));
		lblClose.setBounds(787, 0, 35, 27);
		contentPane.add(lblClose);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		
		JLabel lblNewLabel = new JLabel("CONFIGURATOR MENU");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(104, 33, 194, 13);
		contentPane.add(lblNewLabel);
		
		btnInsertNewDistrict = new JButton("INSERT NEW DISTRICT");
		btnInsertNewDistrict.setBackground(new Color(240, 240, 240));
		btnInsertNewDistrict.setForeground(new Color(0, 0, 255));
		btnInsertNewDistrict.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInsertNewDistrict.setBounds(10, 97, 372, 35);
		contentPane.add(btnInsertNewDistrict);
		
		btnInsertNewHierarchy = new JButton("INSERT NEW HIERARCHY");
		btnInsertNewHierarchy.setForeground(Color.BLUE);
		btnInsertNewHierarchy.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInsertNewHierarchy.setBackground(SystemColor.menu);
		btnInsertNewHierarchy.setBounds(10, 142, 372, 35);
		contentPane.add(btnInsertNewHierarchy);
		
		btnInsertConversionFactors = new JButton("INSERT CONVERSION FACTORS");
		btnInsertConversionFactors.setForeground(Color.BLUE);
		btnInsertConversionFactors.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInsertConversionFactors.setBackground(SystemColor.menu);
		btnInsertConversionFactors.setBounds(10, 187, 372, 35);
		contentPane.add(btnInsertConversionFactors);
		
		btnSaveAll = new JButton("SAVE ALL");
		btnSaveAll.setForeground(Color.BLUE);
		btnSaveAll.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSaveAll.setBackground(SystemColor.menu);
		btnSaveAll.setBounds(10, 232, 372, 35);
		contentPane.add(btnSaveAll);
		
		btnViewDistrict = new JButton("VIEW DISTRICT");
		btnViewDistrict.setForeground(Color.BLUE);
		btnViewDistrict.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnViewDistrict.setBackground(SystemColor.menu);
		btnViewDistrict.setBounds(10, 277, 372, 35);
		contentPane.add(btnViewDistrict);
		
		btnViewHierarchy = new JButton("VIEW HIERARCHY");
		btnViewHierarchy.setForeground(Color.BLUE);
		btnViewHierarchy.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnViewHierarchy.setBackground(SystemColor.menu);
		btnViewHierarchy.setBounds(10, 322, 372, 35);
		contentPane.add(btnViewHierarchy);
		
		btnViewAllConversion = new JButton("VIEW ALL CONVERSION FACTORS");
		btnViewAllConversion.setForeground(Color.BLUE);
		btnViewAllConversion.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnViewAllConversion.setBackground(SystemColor.menu);
		btnViewAllConversion.setBounds(10, 367, 372, 35);
		contentPane.add(btnViewAllConversion);
		
		btnViewConversionFactorsOfCategory = new JButton("VIEW CONVERSION FACTORS OF A CATEGORY");
		btnViewConversionFactorsOfCategory.setForeground(Color.BLUE);
		btnViewConversionFactorsOfCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnViewConversionFactorsOfCategory.setBackground(SystemColor.menu);
		btnViewConversionFactorsOfCategory.setBounds(10, 412, 372, 35);
		contentPane.add(btnViewConversionFactorsOfCategory);
		
		JButton btnViewProposalOfCategory = new JButton("VIEW PROPOSALS OF A CATEGORY");
		btnViewProposalOfCategory.setForeground(Color.BLUE);
		btnViewProposalOfCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnViewProposalOfCategory.setBackground(SystemColor.menu);
		btnViewProposalOfCategory.setBounds(10, 457, 372, 35);
		contentPane.add(btnViewProposalOfCategory);
		
		btnLogout = new JButton("LOGOUT");
		btnLogout.setForeground(Color.BLUE);
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogout.setBackground(SystemColor.menu);
		btnLogout.setBounds(10, 502, 372, 35);
		contentPane.add(btnLogout);
	}
	
	public JLabel getCloseLabel() 
	{
        return lblClose;
    }
	
	public JButton getInsertNewDistrictButton()
	{
		return btnInsertNewDistrict;
	}
	
	public JButton getViewAllConversionFactorsButton()
	{
		return btnViewAllConversion;
	}
	
	public JButton getViewDistrictButton()
	{
		return btnViewDistrict;
	}
	
	public JButton getInsertConversionFactorsButton()
	{
		return btnInsertConversionFactors;
	}
	
	public JButton getViewConversionFactorsOfCategoryButton()
	{
		return btnViewConversionFactorsOfCategory;
	}
	 
	public JButton getInsertNewHierarchyButton()
	{
		return btnInsertNewHierarchy;
	}
	
	public JButton getLogoutButton()
	{
		return btnLogout;
	}
	
	public JButton getViewHierarchyButton()
	{
		return btnViewHierarchy;
	}
	
}
