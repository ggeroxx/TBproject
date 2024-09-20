package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Enumeration;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JButton;

public class HierarchyView extends JFrame {

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
	private JLabel lblHierarchy;
	private JButton btnOk;

	public HierarchyView() {
		
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
		        HierarchyView.this.setLocation(x - xx, y - xy);
		    }
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1098, 725);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1061, 22);
		contentPane.add(menuBar);
		
		mnMenuCategories = new JMenu("Categories") {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Impostare il colore di sfondo
                g.setColor(new Color(240,240,240));  // Cambia il colore qui
                // Riempire lo sfondo con il colore
                g.fillRect(0, 0, getWidth(), getHeight());
                
                g.setColor(Color.BLUE); // Colore del testo
                g.setFont(getFont());    // Usa il font del JMenu
                FontMetrics metrics = g.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = (getHeight() + metrics.getAscent()) / 2 - 2; // Centrato verticalmente
                
                g.drawString(getText(), x, y);

            }
        };
		menuBar.add(mnMenuCategories);
		mnMenuCategories.setFont(new Font("Tahoma", Font.BOLD, 14));
		mnMenuCategories.setToolTipText("Click to see categories");
		LineBorder normalBorder = new LineBorder(Color.BLUE, 2);
        mnMenuCategories.setBorder(normalBorder);
        
      /*mnMenuCategories.setHorizontalAlignment(SwingConstants.LEFT);
        mnMenuCategories.setForeground(Color.BLUE);
        LineBorder blinkingBorder = new LineBorder(new Color(240,240,240), 2);
        Timer timer = new Timer(2500, new ActionListener() {
            private boolean isNormal = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isNormal) {
                    mnMenuCategories.setBorder(blinkingBorder); // Cambia il bordo
                } else {
                    mnMenuCategories.setBorder(normalBorder); // Torna al bordo originale
                }
                isNormal = !isNormal; // Inverti lo stato
                mnMenuCategories.repaint(); // Rinfresca il componente
            }
        });
        
        timer.start();*/
		
		lblClose = new JLabel("X");
		lblClose.setBounds(1055, 0, 29, 19);
		lblClose.setHorizontalAlignment(SwingConstants.CENTER);
		lblClose.setForeground(Color.RED);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClose.setBackground(SystemColor.menu);
		contentPane.add(lblClose);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(591, 32, 470, 619);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Monospaced", Font.BOLD, 13));
		scrollPane.setViewportView(textArea);
		textArea.setEnabled(false);
		textArea.setEditable(false);
		
		lblHierarchy = new JLabel("HIERARCHY");
		lblHierarchy.setForeground(new Color(0, 0, 255));
		lblHierarchy.setHorizontalAlignment(SwingConstants.CENTER);
		lblHierarchy.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblHierarchy.setBounds(10, 26, 593, 13);
		contentPane.add(lblHierarchy);
		
		scrollPaneCategory = new JScrollPane();
		scrollPaneCategory.setBounds(30, 51, 538, 321);
		contentPane.add(scrollPaneCategory);
		
		panelCategory = new JPanel();
		scrollPaneCategory.setViewportView(panelCategory);
		panelCategory.setLayout(new BoxLayout(panelCategory, BoxLayout.Y_AXIS));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(30, 417, 538, 230);
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
		lblInformations.setBounds(10, 394, 593, 13);
		contentPane.add(lblInformations);
		
		btnOk = new JButton("OK");
		btnOk.setForeground(Color.WHITE);
		btnOk.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnOk.setBackground(Color.BLUE);
		btnOk.setBounds(533, 657, 85, 21);
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
	
	public JButton getOkButton()
	{
		return btnOk;
	}
	
	public JLabel getCloseLabel() 
	{
        return lblClose;
    }
	
	public void setLblHierarchy (String message )
	{
		lblHierarchy.setText(message);
	}
	
	public void setLblInformations (String message)
	{
		lblInformations.setText(message);
	}
	
	public void init()
	{
		setLblHierarchy ("HIERARCHY");
		getPanelCategory().removeAll();
		getPanelCategory().revalidate();
		getPanelCategory().repaint();
		mnMenuCategories.removeAll();
		setTextArea("");
		setTextAreaInfo("");
		setLblInformations ( "INFORMATIONS" );
        setUndecorated(true);
        setVisible(true);

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
