package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RetireProposalView extends JFrame{

	private JPanel contentPane;
	private int xx, xy;
	private JScrollPane scrollPane;
	private JPanel panel;
	private ButtonGroup group;
	private JLabel lblProposal;
	private JLabel lblClose;
	private JButton btnRetireProposal;

	public RetireProposalView() 
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
		        RetireProposalView.this.setLocation(x - xx, y - xy);
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
		
		btnRetireProposal = new JButton("RETIRE PROPOSAL");
		btnRetireProposal.setBackground(Color.BLUE);
		btnRetireProposal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRetireProposal.setBounds(466, 487, 304, 21);
		contentPane.add(btnRetireProposal);
		
		group = new ButtonGroup();

	}
	
	public JRadioButton addRadioButton( String info ) 
	{
		JRadioButton radioButton = new JRadioButton( info );
		radioButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
		group.add(radioButton);
		panel.add(radioButton);
		contentPane.revalidate(); 
		contentPane.repaint(); 
		return radioButton;
	}
	
	public JRadioButton getSelectedRadioButton () 
	{
	    Enumeration<AbstractButton> buttons = getGroup().getElements();
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
	
	public ButtonGroup getGroup()
	{
		return group;
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
	
	public JButton getRetireProposalButton()
	{
		return btnRetireProposal;
	}
	

}

