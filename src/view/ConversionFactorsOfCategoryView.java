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
import java.awt.MenuBar;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class ConversionFactorsOfCategoryView extends JFrame{

	private JPanel contentPane;
	private int xx, xy;
	private JScrollPane scrollPane;
	private JPanel panel;

	private JLabel lblCategory;
	private JLabel lblClose;
	private JMenuBar menuBar;
	private JMenu mnMenuCategories;

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
					ConversionFactorsOfCategoryView frame = new ConversionFactorsOfCategoryView();
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


	
	public ConversionFactorsOfCategoryView() 
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
		        ConversionFactorsOfCategoryView.this.setLocation(x - xx, y - xy);
		    }
		});
		
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1202, 731);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 70, 1156, 615);
		contentPane.add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);	
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		lblCategory = new JLabel("CONVERSION FACTORS");
		lblCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCategory.setForeground(new Color(0, 0, 255));
		lblCategory.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategory.setBounds(85, 37, 1021, 23);
		contentPane.add(lblCategory);
		
		lblClose = new JLabel("X");
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setBackground(SystemColor.menu);
		lblClose.setBounds(1163, 0, 35, 27);
		contentPane.add(lblClose);
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1167, 22);
		contentPane.add(menuBar);
		
		mnMenuCategories = new JMenu("Categories");
		menuBar.add(mnMenuCategories);
		

	}
	
	public void addlblConversionFactor( String info ) 
	 {
		JLabel label = new JLabel( info );
		label.setFont(new Font("Monospaced", Font.PLAIN, 12));
		panel.add(label);
		contentPane.revalidate(); 
		contentPane.repaint();     
	 }
	
	public void addMenuCategory(JMenuItem categoryItem) 
	 {
		categoryItem.setFont(new Font("Monospaced", Font.PLAIN, 12));
		mnMenuCategories.add(categoryItem);
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
	

	public void setLblCategory( String message )
	{
		lblCategory.setText(message);
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
		mnMenuCategories.removeAll();
        setLblCategory("CONVERSION FACTORS OF CATEGORY");
        setUndecorated(true);
        setVisible(true);
	}
	
	public JMenuItem addMenuItem (String info)
	{
		JMenuItem categoryItem = new JMenuItem( info );
        addMenuCategory(categoryItem);
        return categoryItem;
	}
	
	public void resetFields()
	{
		getPanel().removeAll();
		getPanel().revalidate();
		getPanel().repaint();
	}
	

}
