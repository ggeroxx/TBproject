package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.GRASPController.ConversionFactorsGRASPController;
import model.Category;
import model.ConversionFactor;
import model.ConversionFactors;
import model.util.Constants;
import repository.ConversionFactorsRepository;
import view.AllConversionFactorsView;
import view.ConversionFactorsOfCategoryView;
import view.ConversionFactorsView;
import view.InsertConversionFactorsView;

public class ConversionFactorsController extends Controller {
    
    private InsertConversionFactorsView insertConversionFactorsView;
    private AllConversionFactorsView allConversionFactorsView;
    private ConversionFactorsOfCategoryView conversionFactorsOfCategoryView;
    private ConversionFactorsView conversionFactorsView;
    private ConversionFactorController conversionFactorController;
    private CategoryController categoryController;
    private ConversionFactorsGRASPController controllerGRASP; 
    private HashMap<JRadioButton, ConversionFactor> radioButtonObjectMap = new HashMap<>();

    public ConversionFactorsController ( AllConversionFactorsView allConversionFactorsView, InsertConversionFactorsView insertConversionFactorsView, ConversionFactorsOfCategoryView conversionFactorsOfCategoryView, ConversionFactorsView conversionFactorsView, ConversionFactorController conversionFactorController, CategoryController categoryController, ConversionFactorsGRASPController controllerGRASP)
    {
        super( conversionFactorsView );
        this.conversionFactorsView = conversionFactorsView;
        this.conversionFactorController = conversionFactorController;
        this.categoryController = categoryController;
        this.controllerGRASP = controllerGRASP;

        this.insertConversionFactorsView = insertConversionFactorsView;
        this.allConversionFactorsView = allConversionFactorsView;
        this.conversionFactorsOfCategoryView = conversionFactorsOfCategoryView;

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
                            try 
                            {
                                calculate( getIndexForConversionFactor(conversionFactor), Double.parseDouble(insertConversionFactorsView.getTextValue()) );
                            } 
                            catch (NumberFormatException | SQLException e1) 
                            {
                                e1.printStackTrace();
                            }
                            insertConversionFactorsView.getPanel().removeAll();
                            insertConversionFactorsView.setlblRange("");
                            insertConversionFactorsView.setTextValue("");
                            insertConversionFactorsView.setlblError("");
                            insertConversionFactorsView.dispose();
                            radioButtonObjectMap = new HashMap<>();
                            try {
                                startInsertConversionFactorsView();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                        else
                        {
                            insertConversionFactorsView.setlblError(Constants.OUT_OF_RANGE_ERROR);
                        }
                    }
                    catch(NumberFormatException ex)
                    {

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
                        insertConversionFactorsView.dispose();
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
                allConversionFactorsView.dispose();
			}
		});

        this.conversionFactorsOfCategoryView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                conversionFactorsOfCategoryView.dispose();
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

    public void startAllConversionFactorsView() throws SQLException
    {
        populate();
        this.allConversionFactorsView.setUndecorated(true);
        this.allConversionFactorsView.setVisible(true);
        for ( Entry<Integer, ConversionFactor> entry : getConversionFactors().getList().entrySet() )
        {
            addLabelInAllConversionFactorsView( entry.getValue() );
        }
    }

    public void startConversionFactorsOfCategoryView() throws SQLException
    {
        conversionFactorsOfCategoryView.getPanel().removeAll();
        conversionFactorsOfCategoryView.setLblCategory("CONVERSION FACTORS OF CATEGORY");
        this.conversionFactorsOfCategoryView.setUndecorated(true);
        this.conversionFactorsOfCategoryView.setVisible(true);
        for ( Category toPrint : categoryController.getCategoryRepository().getAllSavedLeaf() ) 
        {
            addMenuItem( toPrint, " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + categoryController.getCategoryRepository().getRootByLeaf( toPrint ).getName() + " ]  " );
        }
        for ( Category toPrint : categoryController.getCategoryRepository().getAllNotSavedLeaf() )
        {
            addMenuItem( toPrint, " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + categoryController.getCategoryRepository().getRootByLeaf( toPrint ).getName() + " ]  " + Constants.NOT_SAVED );
        } 
    }

    public ConversionFactorsRepository getConversionFactorsRepository () 
    {
        return this.controllerGRASP.getConversionFactorsRepository();
    }

    public ConversionFactors getConversionFactors ()
    {
        return this.controllerGRASP.getConversionFactors();
    }

    public void resetConversionFactors ()
    {
        this.controllerGRASP.resetConversionFactors();
    }

    public void listAll () throws SQLException
    {
        conversionFactorsView.print( "\n" );

        for ( Entry<Integer, ConversionFactor> entry : getConversionFactors().getList().entrySet() )
        {
            conversionFactorsView.print( " " + entry.getKey() + ". " + super.padRight( Integer.toString( entry.getKey() ), 5 ) );
            conversionFactorController.print( entry.getValue() );
        }

        conversionFactorsView.print( "\n" );
    }

    public void listConversionFactorsByLeaf ( Category leaf ) throws SQLException
    {
        populate();

        for ( Entry<Integer, ConversionFactor> entry : getConversionFactors().getList().entrySet() )
            if ( entry.getValue().getLeaf_1().getID() == leaf.getID() || entry.getValue().getLeaf_2().getID() == leaf.getID() )
                conversionFactorController.print( entry.getValue() );
    }

    public void listConversionFactorsOfLeaf () throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorsView.print( Constants.LEAF_CATEGORY_LIST );

        if ( categoryController.getCategoryRepository().getAllLeaf().isEmpty() )
        {
            conversionFactorsView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        categoryController.listAllLeafs();
        int leafID = categoryController.enterLeafID();
        if ( leafID == 0 ) return;

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorsView.println( Constants.CONVERSION_FACTORS_LIST );

        this.listConversionFactorsByLeaf( categoryController.getCategoryRepository().getCategoryByID( leafID ) );

        conversionFactorsView.enterString( "\n" + Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public int enterID ()
    {
        return super.readInt( Constants.ENTER_CHOICE_PAIR, Constants.NOT_EXIST_MESSAGE, ( input ) -> !getConversionFactors().getList().containsKey( (Integer) input ) || !(getConversionFactors().getList().get( (Integer) input ).getValue() == null ) );
    }

    public Double enterValue ( double[] range )
    {
        Double value = null;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                String rangeToPrint = "[ " + range[ 0 ] + ", " + range[ 1 ] + " ]: ";
                value = conversionFactorsView.enterDouble( Constants.ENTER_VALUE_CONVERSION_FACTOR + rangeToPrint );
                if ( value < range[ 0 ] || value > range[ 1 ] ) conversionFactorsView.print( Constants.OUT_OF_RANGE_ERROR );
            }
            catch ( InputMismatchException e )
            {
                conversionFactorsView.println( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || value < range[ 0 ] || value > range[ 1 ] );

        return value;
    }

    public void listAllConversionFactors () throws SQLException
    {
        populate();

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorsView.print( Constants.CONVERSION_FACTORS_LIST );

        if ( getConversionFactors().getList().isEmpty() )
        {
            conversionFactorsView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        this.listAll();

        conversionFactorsView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public void enterConversionFactors () throws SQLException
    {
        populate();

        if ( isComplete() )
        {
            this.listAllConversionFactors();
            return;
        }

        do
        {
            super.clearConsole( Constants.TIME_SWITCH_MENU );
            conversionFactorsView.print( "\n" );
            this.listAll();

            int index = this.enterID();
            double[] range = calculateRange( index );
            Double value = this.enterValue( range );

            calculate( index, value );
        } while ( !isComplete() );

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorsView.print( "\n" );
        this.listAll();
        conversionFactorsView.print( "\n" );
        conversionFactorsView.println( Constants.OPERATION_COMPLETED );
        super.clearConsole( Constants.TIME_MESSAGE );
    }
    
    public void saveConversionFactors () throws SQLException
    {
        this.controllerGRASP.saveConversionFactors();
    }

    public void populate () throws SQLException
    {
        this.controllerGRASP.populate();
    }

    public void calculate ( int index, Double value ) throws SQLException
    {
        this.controllerGRASP.calculate(index, value);
    }

    public void calculateEq ( int index, Double value, boolean check, HashMap<Integer, ConversionFactor> copyCFs, List<String> equations )
    {
        this.controllerGRASP.calculateEq(index, value, check, copyCFs, equations);
    }

    public double[] calculateRange ( int index )
    {
        return this.controllerGRASP.calculateRange(index);
    }

    public boolean isComplete () throws SQLException
    {
        return this.controllerGRASP.isComplete();
    }

    private void addRadioButton( ConversionFactor conversionFactor ) throws SQLException 
    {
        JRadioButton radioButton = new JRadioButton( conversionFactorController.infoConversionFactor(conversionFactor) );

        insertConversionFactorsView.addRadioButton( radioButton );

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

    private void addLabelInAllConversionFactorsView( ConversionFactor conversionFactor ) throws SQLException 
    {
        JLabel label = new JLabel( conversionFactorController.infoConversionFactor(conversionFactor) );
        allConversionFactorsView.addlblConversionFactor( label );
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
        JMenuItem categoryItem = new JMenuItem( info );
        conversionFactorsOfCategoryView.addMenuCategory(categoryItem);

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
                            JLabel label = new JLabel( conversionFactorController.infoConversionFactor( entry.getValue() ) );
                            conversionFactorsOfCategoryView.addlblConversionFactor( label );
                            
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                
            }
        });
        
    }

}
