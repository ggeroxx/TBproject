package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JScrollPane;

public class ProposalOfUserView extends JFrame{

	private JPanel contentPane;
	private int xx, xy;
	private JScrollPane scrollPane;
	private JPanel panel;

	private JLabel lblProposal;
	private JLabel lblClose;

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
					ProposalOfCategoryView frame = new ProposalOfCategoryView();
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


	
	public ProposalOfUserView() 
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
		        ProposalOfUserView.this.setLocation(x - xx, y - xy);
		    }
		});
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1288, 580);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 70, 1241, 395);
		contentPane.add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);	
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		lblProposal = new JLabel("PROPOSALS");
		lblProposal.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProposal.setForeground(new Color(0, 0, 255));
		lblProposal.setHorizontalAlignment(SwingConstants.CENTER);
		lblProposal.setBounds(85, 37, 1021, 23);
		contentPane.add(lblProposal);
		
		lblClose = new JLabel("X");
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setBackground(SystemColor.menu);
		lblClose.setBounds(1253, 0, 35, 27);
		contentPane.add(lblClose);
		

	}
	
	public void addlblProposal( String info ) 
	 {
		JLabel label = new JLabel( info );
		label.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel.add(label);
		contentPane.revalidate(); 
		contentPane.repaint();     
	 }
	
	public JScrollPane getScrollPane()
	{
		return scrollPane;
	}
	
	
	public JPanel getPanel()
	{
		return panel;
	}
	

	public void setLblProposal( String message )
	{
		lblProposal.setText(message);
	}
	
	public JLabel getCloseLabel() 
	{
        return lblClose;
    }
	
	public void init ()
	{
		getPanel().removeAll();
		getPanel().revalidate();
		getPanel().repaint();
		setLblProposal("PROPOSALS");
        setUndecorated(true);
        setVisible(true);
	}
	
	
	public void resetFields()
	{
		getPanel().removeAll();
		getPanel().revalidate();
		getPanel().repaint();
	}
	

}

