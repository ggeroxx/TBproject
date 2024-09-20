package Client.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JButton;
import java.awt.SystemColor;

public class UserMenuView extends JFrame {
	
	private JPanel contentPane;
	private JLabel lblClose;
	private int xx, xy;
	private JButton btnNavigateHierarchies;
	private JButton btnViewAllProposal;
	private JButton btnRetireProposal;
	private JButton btnProposProposal;
	private JButton btnLogout;
	
	public UserMenuView() 
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
		        UserMenuView.this.setLocation(x - xx, y - xy);
		    }
		});
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 831, 621);
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
		lblImg.setIcon(new ImageIcon(LoginView.class.getResource("/Client/img/login.jpg")));
		panel.add(lblImg);
		
		lblClose = new JLabel("X");
		lblClose.setBackground(new Color(240, 240, 240));
		lblClose.setBounds(787, 0, 35, 27);
		contentPane.add(lblClose);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		
		JLabel lblNewLabel = new JLabel("USER MENU");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(111, 81, 194, 13);
		contentPane.add(lblNewLabel);
		
		btnNavigateHierarchies = new JButton("NAVIGATE HIERARCHIES");
		btnNavigateHierarchies.setBackground(new Color(240, 240, 240));
		btnNavigateHierarchies.setForeground(new Color(0, 0, 255));
		btnNavigateHierarchies.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNavigateHierarchies.setBounds(10, 154, 372, 35);
		contentPane.add(btnNavigateHierarchies);
		
		btnProposProposal = new JButton("PROPOSE PROPOSAL");
		btnProposProposal.setForeground(Color.BLUE);
		btnProposProposal.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnProposProposal.setBackground(SystemColor.menu);
		btnProposProposal.setBounds(10, 199, 372, 35);
		contentPane.add(btnProposProposal);
		
		btnRetireProposal = new JButton("RETIRE PROPOSAL");
		btnRetireProposal.setForeground(Color.BLUE);
		btnRetireProposal.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRetireProposal.setBackground(SystemColor.menu);
		btnRetireProposal.setBounds(10, 244, 372, 35);
		contentPane.add(btnRetireProposal);
		
		btnViewAllProposal = new JButton("VIEW ALL PROPOSAL");
		btnViewAllProposal.setForeground(Color.BLUE);
		btnViewAllProposal.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnViewAllProposal.setBackground(SystemColor.menu);
		btnViewAllProposal.setBounds(10, 289, 372, 35);
		contentPane.add(btnViewAllProposal);
		
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
	
	public JButton getNavigateHierarchiesButton()
	{
		return btnNavigateHierarchies;
	}
	
	
	public JButton getViewAllProposalButton()
	{
		return btnViewAllProposal;
	}
	
	public JButton getRetireProposalButton()
	{
		return btnRetireProposal;
	}
	
	 
	public JButton getProposProposalButton()
	{
		return btnProposProposal;
	}
	
	public JButton getLogoutButton()
	{
		return btnLogout;
	}
	
	
}
