package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JButton;

public class NavigateHierarchyView extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	private JScrollPane scrollPaneCategory;
	private JPanel panelCategory;
	private ButtonGroup group;
	private int xx, xy;
	private JLabel lblClose;
	private JMenuBar menuBar;
	private JMenu mnMenuCategories;
	private JScrollPane scrollPane_1;
	private JTextArea textAreaInfo;
	private JLabel lblInformations;
	private JLabel lblFields;
	private JButton btnBack;
	private JButton btnOk;

	public NavigateHierarchyView() {
		
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
		        NavigateHierarchyView.this.setLocation(x - xx, y - xy);
		    }
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1098, 716);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1061, 22);
		contentPane.add(menuBar);
		
		mnMenuCategories = new JMenu("Categories");
		menuBar.add(mnMenuCategories);
		
		lblClose = new JLabel("X");
		lblClose.setBounds(1055, 0, 29, 19);
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setBackground(SystemColor.menu);
		contentPane.add(lblClose);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(591, 32, 470, 612);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Monospaced", Font.BOLD, 13));
		scrollPane.setViewportView(textArea);
		textArea.setEnabled(false);
		textArea.setEditable(false);
		
		lblFields = new JLabel("FIELDS");
		lblFields.setForeground(new Color(0, 0, 255));
		lblFields.setHorizontalAlignment(SwingConstants.CENTER);
		lblFields.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFields.setBounds(0, 379, 593, 13);
		contentPane.add(lblFields);
		
		scrollPaneCategory = new JScrollPane();
		scrollPaneCategory.setBounds(30, 402, 538, 242);
		contentPane.add(scrollPaneCategory);
		
		panelCategory = new JPanel();
		scrollPaneCategory.setViewportView(panelCategory);
		panelCategory.setLayout(new BoxLayout(panelCategory, BoxLayout.Y_AXIS));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(30, 65, 538, 230);
		contentPane.add(scrollPane_1);
		
		textAreaInfo = new JTextArea();
		scrollPane_1.setViewportView(textAreaInfo);
		textAreaInfo.setForeground(Color.BLACK);
		textAreaInfo.setFont(new Font("Monospaced", Font.BOLD, 13));
		textAreaInfo.setEnabled(false);
		textAreaInfo.setEditable(false);
		
		lblInformations = new JLabel("INFORMATIONS");
		lblInformations.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformations.setForeground(Color.BLUE);
		lblInformations.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblInformations.setBounds(0, 32, 593, 13);
		contentPane.add(lblInformations);
		
		btnBack = new JButton("<");
		btnBack.setBackground(Color.BLUE);
		btnBack.setForeground(Color.WHITE);
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnBack.setBounds(30, 654, 46, 21);
		contentPane.add(btnBack);
		
		btnOk = new JButton("OK");
		btnOk.setForeground(Color.WHITE);
		btnOk.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnOk.setBackground(Color.BLUE);
		btnOk.setBounds(514, 654, 138, 21);
		contentPane.add(btnOk);
		
		group = new ButtonGroup();
	}
	
	public JRadioButton addRadioButton( String info ) 
	 {
		JRadioButton radioButton = new JRadioButton( info );
		radioButton.setFont(new Font("Monospaced", Font.PLAIN, 12));
		group.add(radioButton);
		panelCategory.add(radioButton);
		contentPane.revalidate(); 
		contentPane.repaint(); 
		return radioButton;
	 }
	
	public void setTextArea( String Message )
	{
		textArea.setText(Message);
	}
	
	
	public JPanel getPanelCategory()
	{
		return panelCategory;
	}
	
	
	public ButtonGroup getGroup()
	{
		return group;
	}
	
	public JScrollPane getScrollPaneParentID()
	{
		return scrollPaneCategory;
	}
	
	public void setTextAreaInfo ( String message )
	{
		textAreaInfo.setText(message);
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
	
	public JLabel getCloseLabel() 
	{
        return lblClose;
    }
	
	public void setLblFields (String message )
	{
		lblFields.setText(message);
	}
	
	public void setLblInformations (String message)
	{
		lblInformations.setText(message);
	}
	
	public JButton getBackButton()
	{
		return btnBack;
	}
	
	public JButton getOkButton()
	{
		return btnOk;
	}
	
	public void init()
	{
		setLblFields ("FIELDS");
		getPanelCategory().removeAll();
		getPanelCategory().revalidate();
		getPanelCategory().repaint();
		mnMenuCategories.removeAll();
		setTextArea("");
		setTextAreaInfo("");
		setLblInformations ( "INFORMATIONS" );
		getBackButton().setVisible(false);
        setUndecorated(true);
        setVisible(true);
	}
	
	public void reset()
	{
		getPanelCategory().removeAll();
		getPanelCategory().revalidate();
		getPanelCategory().repaint();
	}
	
	public void addMenuCategory(JMenuItem categoryItem) 
	 {
		categoryItem.setFont(new Font("Monospaced", Font.PLAIN, 12));
		mnMenuCategories.add(categoryItem);
		contentPane.revalidate(); 
		contentPane.repaint();     
	 }
	
	public JMenuItem addMenuItem (String info)
	{
		JMenuItem categoryItem = new JMenuItem( info );
        addMenuCategory(categoryItem);
        return categoryItem;
	}
		
}
