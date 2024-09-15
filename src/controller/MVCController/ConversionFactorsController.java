package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.AbstractButton;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import java.awt.event.MouseEvent;
import model.Category;
import model.ConversionFactor;
import model.ConversionFactors;
import model.util.Constants;
import service.ControlPatternService;
import service.ConversionFactorsService;
import view.AllConversionFactorsView;
import view.ConversionFactorsOfCategoryView;
import view.InsertConversionFactorsView;

public class ConversionFactorsController  {
    
    private InsertConversionFactorsView insertConversionFactorsView;
    private AllConversionFactorsView allConversionFactorsView;
    private ConversionFactorsOfCategoryView conversionFactorsOfCategoryView;
    private ConversionFactorController conversionFactorController;
    private CategoryController categoryController;
    private ConversionFactorsService conversionFactorsService; 
    private HashMap<JRadioButton, ConversionFactor> radioButtonObjectMap = new HashMap<>();

    public ConversionFactorsController ( AllConversionFactorsView allConversionFactorsView, InsertConversionFactorsView insertConversionFactorsView, ConversionFactorsOfCategoryView conversionFactorsOfCategoryView, ConversionFactorController conversionFactorController, CategoryController categoryController, ConversionFactorsService conversionFactorsService)
    {
        this.insertConversionFactorsView = insertConversionFactorsView;
        this.allConversionFactorsView = allConversionFactorsView;
        this.conversionFactorsOfCategoryView = conversionFactorsOfCategoryView;
        this.conversionFactorController = conversionFactorController;
        this.categoryController = categoryController;
        this.conversionFactorsService = conversionFactorsService;

        insertConversionFactorsView.getInsertValueButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {

                if(getSelectedRadioButton() != null && !insertConversionFactorsView.getTextValue().isEmpty())
                {
                    ConversionFactor conversionFactor = radioButtonObjectMap.get(getSelectedRadioButton());
                    double[] range = calculateRange( getIndexForConversionFactor(conversionFactor) );

                    try
                    {
                        Double.parseDouble(insertConversionFactorsView.getTextValue());
                    
                        if( ( ( Double.parseDouble(insertConversionFactorsView.getTextValue()) >= range[0] ) && ( Double.parseDouble(insertConversionFactorsView.getTextValue()) <= range[1] ) ) )
                        {
                            
                            calculate( getIndexForConversionFactor(conversionFactor), Double.parseDouble(insertConversionFactorsView.getTextValue()) );
                            closeInsertConversionFactorsView();
                            startInsertConversionFactorsView();
                        
                        }
                        else
                        {
                            insertConversionFactorsView.setlblError(Constants.OUT_OF_RANGE_ERROR);
                        }
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
                
        });

        this.insertConversionFactorsView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                try {
                    if(isComplete())
                    {
                        closeInsertConversionFactorsView();
                    }
                } catch (SQLException e1) 
                {
                    e1.printStackTrace();
                }
			}
		});

        this.allConversionFactorsView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                closeAllConversionFactorsView();
			}
		});

        this.conversionFactorsOfCategoryView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                closeConversioFactorsOfCategoryView();
			}
		});

    }

    public void startInsertConversionFactorsView() throws SQLException
    {
        if ( isComplete() )
        {
            startAllConversionFactorsView();
        }
        else
        {
            populate();
            insertConversionFactorsView.setUndecorated(true);
            insertConversionFactorsView.setVisible(true);
            for ( Entry<Integer, ConversionFactor> entry : getConversionFactors().getList().entrySet() )
            {
                addRadioButton( entry.getValue() );
            }
        }

    }

    public void closeInsertConversionFactorsView ()
    {
        insertConversionFactorsView.resetFields();
        insertConversionFactorsView.dispose();
    }

    public void startAllConversionFactorsView() throws SQLException
    {
        populate();
        this.allConversionFactorsView.setUndecorated(true);
        this.allConversionFactorsView.setVisible(true);
        for ( Entry<Integer, ConversionFactor> entry : getConversionFactors().getList().entrySet() )
        {
            allConversionFactorsView.addlblConversionFactor( conversionFactorController.infoConversionFactor( entry.getValue() ) );
        }
    }

    public void closeAllConversionFactorsView ()
    {
        allConversionFactorsView.resetFields();
        allConversionFactorsView.dispose();
    }

    public void startConversionFactorsOfCategoryView() throws SQLException
    {
        conversionFactorsOfCategoryView.init();

        for ( Category toPrint : categoryController.getAllSavedLeaf() ) 
        {
            addMenuItem( toPrint, " " + toPrint.getID() + ". " + ControlPatternService.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + ControlPatternService.padRight( toPrint.getName() , 50 ) + "  [ " + categoryController.getCategoryRepository().getRootByLeaf( toPrint ).getName() + " ]  " );
        }
        for ( Category toPrint : categoryController.getAllNotSavedLeaf() )
        {
            addMenuItem( toPrint, " " + toPrint.getID() + ". " + ControlPatternService.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + ControlPatternService.padRight( toPrint.getName() , 50 ) + "  [ " + categoryController.getCategoryRepository().getRootByLeaf( toPrint ).getName() + " ]  " + Constants.NOT_SAVED );
        } 
    }

    public void closeConversioFactorsOfCategoryView ()
    {
        conversionFactorsOfCategoryView.resetFields();
        conversionFactorsOfCategoryView.dispose();
    }

    public Double getConversionFactorValue ( Category requested, Category offered) throws SQLException 
    {
        return this.conversionFactorsService.getConversionFactorValue( requested, offered );
    }

    public ConversionFactors getConversionFactors ()
    {
        return this.conversionFactorsService.getConversionFactors();
    }

    public void resetConversionFactors ()
    {
        this.conversionFactorsService.resetConversionFactors();
    }
   
    public void saveConversionFactors () throws SQLException
    {
        this.conversionFactorsService.saveConversionFactors();
    }

    public void populate () throws SQLException
    {
        this.conversionFactorsService.populate();
    }

    public void calculate ( int index, Double value ) throws SQLException
    {
        this.conversionFactorsService.calculate(index, value);
    }

    public void calculateEq ( int index, Double value, boolean check, HashMap<Integer, ConversionFactor> copyCFs, List<String> equations )
    {
        this.conversionFactorsService.calculateEq(index, value, check, copyCFs, equations);
    }

    public double[] calculateRange ( int index )
    {
        return this.conversionFactorsService.calculateRange(index);
    }

    public boolean isComplete () throws SQLException
    {
        return this.conversionFactorsService.isComplete();
    }

    private void addRadioButton( ConversionFactor conversionFactor ) throws SQLException 
    {
        
        JRadioButton radioButton = insertConversionFactorsView.addRadioButton( conversionFactorController.infoConversionFactor(conversionFactor ) );
        radioButtonObjectMap.put( radioButton, conversionFactor );

        if(conversionFactor.getValue() != null)
        {
            radioButton.setEnabled( false );
        }

        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if (radioButton.isSelected()) 
                {
                    double[] range = calculateRange( getIndexForConversionFactor(conversionFactor) );
                    insertConversionFactorsView.setlblRange("[ " + range[0] +" - " + range[1]+ " ]");
                        
                } 
            }
        });
    }

    public Integer getIndexForConversionFactor(ConversionFactor conversionFactor) 
    {
        for (Map.Entry<Integer, ConversionFactor> entry : getConversionFactors().getList().entrySet()) 
        {
            if (entry.getValue().equals(conversionFactor)) 
            {
                return entry.getKey();
            }
        }
        return null;
    }

    private JRadioButton getSelectedRadioButton() 
	{
	    Enumeration<AbstractButton> buttons = insertConversionFactorsView.getGroup().getElements();
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

    public void addMenuItem( Category category, String info)
    {
        JMenuItem categoryItem = conversionFactorsOfCategoryView.addMenuItem(info);
        categoryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conversionFactorsOfCategoryView.getPanel().removeAll();
                conversionFactorsOfCategoryView.setLblCategory(category.getName());
                try {
                    populate();
                    for ( Entry<Integer, ConversionFactor> entry : getConversionFactors().getList().entrySet() )
                    {
                        if ( entry.getValue().getLeaf_1().getID() == category.getID() || entry.getValue().getLeaf_2().getID() == category.getID() )
                        {
                            conversionFactorsOfCategoryView.addlblConversionFactor( conversionFactorController.infoConversionFactor( entry.getValue() ) );      
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                
            }
        });
        
    }

}
