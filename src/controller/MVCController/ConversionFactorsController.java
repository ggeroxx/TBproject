package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.AbstractButton;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import controller.ClientServer.Client;
import controller.ClientServer.ConversionFactorDTO;
import controller.ClientServer.ConversionFactorsDTO;
import controller.ClientServer.SomeRequestConversionFactors;
import java.awt.event.MouseEvent;
import java.io.IOException;
import model.util.Constants;
import view.AllConversionFactorsView;
import view.ConversionFactorsOfCategoryView;
import view.InsertConversionFactorsView;

public class ConversionFactorsController  {
    
    private InsertConversionFactorsView insertConversionFactorsView;
    private AllConversionFactorsView allConversionFactorsView;
    private ConversionFactorsOfCategoryView conversionFactorsOfCategoryView;
    private ConversionFactorController conversionFactorController;
    private CategoryController categoryController;
    private ControlPatternController controlPatternController; 
    private SessionController sessionController;
    private HashMap<JRadioButton, ConversionFactorDTO> radioButtonObjectMap = new HashMap<>();

    private Client client;
    private SomeRequestConversionFactors requestConversionFactors;

    public ConversionFactorsController ( AllConversionFactorsView allConversionFactorsView, InsertConversionFactorsView insertConversionFactorsView, ConversionFactorsOfCategoryView conversionFactorsOfCategoryView, ConversionFactorController conversionFactorController, CategoryController categoryController, ControlPatternController controlPatternController, SessionController sessionController, Client client)
    {
        this.insertConversionFactorsView = insertConversionFactorsView;
        this.allConversionFactorsView = allConversionFactorsView;
        this.conversionFactorsOfCategoryView = conversionFactorsOfCategoryView;
        this.conversionFactorController = conversionFactorController;
        this.categoryController = categoryController;
        this.controlPatternController = controlPatternController;
        this.sessionController = sessionController;
        this.client = client;

        insertConversionFactorsView.getInsertValueButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {

                if(getSelectedRadioButton() != null && !insertConversionFactorsView.getTextValue().isEmpty())
                {
                    ConversionFactorDTO conversionFactorDTO = radioButtonObjectMap.get(getSelectedRadioButton());
                    try
                    {
                        double[] range = calculateRange( getIndexForConversionFactor(conversionFactorDTO) );
                        Double.parseDouble(insertConversionFactorsView.getTextValue());
                    
                        if( ( ( Double.parseDouble(insertConversionFactorsView.getTextValue()) >= range[0] ) && ( Double.parseDouble(insertConversionFactorsView.getTextValue()) <= range[1] ) ) )
                        {     
                            calculate( getIndexForConversionFactor(conversionFactorDTO), Double.parseDouble(insertConversionFactorsView.getTextValue()) );
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
                try 
                {
                    sessionController.logout();
                    client.close();
                } 
                catch (ClassNotFoundException | IOException e1) 
                {
                    e1.printStackTrace();
                }
                System.exit(0);
                /*try {
                    if(isComplete())
                    {
                        closeInsertConversionFactorsView();
                    }
                } catch (SQLException e1) 
                {
                    e1.printStackTrace();
                }*/
			}
		});

        this.allConversionFactorsView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                closeAllConversionFactorsView();
                try 
                {
                    sessionController.logout();
                    client.close();
                }
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
                System.exit(0);
			}
		});

        this.conversionFactorsOfCategoryView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                closeConversioFactorsOfCategoryView();
                try 
                {
                    sessionController.logout();
                    client.close();
                }
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
                System.exit(0);
			}
		});

        this.allConversionFactorsView.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeAllConversionFactorsView();
            }
		});

        this.conversionFactorsOfCategoryView.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeConversioFactorsOfCategoryView();
            }
		});

    }

    public void startInsertConversionFactorsView() throws ClassNotFoundException, IOException
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
            /*for ( Entry<Integer, ConversionFactor> entry : getConversionFactors().getList().entrySet() )
            {
                addRadioButton( entry.getValue() );
            }*/
            ConversionFactorsDTO conversionFactorsDTO = getConversionFactors();
            for ( Entry<Integer, ConversionFactorDTO> entry : conversionFactorsDTO.getConversionFactors().entrySet() )
            {
                ConversionFactorDTO factorDTO = entry.getValue();
                addRadioButton( factorDTO );
            }
        }

    }

    public void closeInsertConversionFactorsView ()
    {
        insertConversionFactorsView.resetFields();
        insertConversionFactorsView.dispose();
    }

    public void startAllConversionFactorsView() throws IOException, ClassNotFoundException
    {
        populate();
        this.allConversionFactorsView.setUndecorated(true);
        this.allConversionFactorsView.setVisible(true);
        ConversionFactorsDTO conversionFactorsDTO = getConversionFactors();

        for ( Entry<Integer, ConversionFactorDTO> entry : conversionFactorsDTO.getConversionFactors().entrySet() )
        {
            ConversionFactorDTO factorDTO = entry.getValue();
            allConversionFactorsView.addlblConversionFactor( conversionFactorController.infoConversionFactor( factorDTO ) );
        }
    }

    public void closeAllConversionFactorsView ()
    {
        allConversionFactorsView.resetFields();
        allConversionFactorsView.dispose();
    }

    public void startConversionFactorsOfCategoryView() throws ClassNotFoundException, IOException
    {
        conversionFactorsOfCategoryView.init();

        for ( int toPrint : categoryController.getAllSavedLeafID() ) 
        {
            addMenuItem( toPrint, " " + toPrint + ". " + controlPatternController.padRight( Integer.toString( toPrint ) , 3 ) + categoryController.getCategoryNameByID(toPrint) + controlPatternController.padRight( categoryController.getCategoryNameByID(toPrint) , 50 ) + "  [ " + categoryController.getRootNameByLeaf( toPrint ) + " ]  " );
        }
        for ( int toPrint : categoryController.getAllNotSavedLeafID() )
        {
            addMenuItem( toPrint, " " + toPrint + ". " + controlPatternController.padRight( Integer.toString( toPrint ) , 3 ) + categoryController.getCategoryNameByID(toPrint) + controlPatternController.padRight( categoryController.getCategoryNameByID(toPrint) , 50 ) + "  [ " + categoryController.getRootNameByLeaf( toPrint ) + " ]  " + Constants.NOT_SAVED );
        } 
    }

    public void closeConversioFactorsOfCategoryView ()
    {
        conversionFactorsOfCategoryView.resetFields();
        conversionFactorsOfCategoryView.dispose();
    }

    private void addRadioButton( ConversionFactorDTO conversionFactorDTO ) throws ClassNotFoundException, IOException 
    {
        
        JRadioButton radioButton = insertConversionFactorsView.addRadioButton( conversionFactorController.infoConversionFactor(conversionFactorDTO ) );
        radioButtonObjectMap.put( radioButton, conversionFactorDTO );

        if(conversionFactorDTO.getValue() != null)
        {
            radioButton.setEnabled( false );
        }

        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if (radioButton.isSelected()) 
                {
                    double[] range;
                    try 
                    {
                        range = calculateRange( getIndexForConversionFactor(conversionFactorDTO) );
                        insertConversionFactorsView.setlblRange("[ " + range[0] +" - " + range[1]+ " ]");
                    } 
                    catch (ClassNotFoundException | IOException e1) 
                    {
                        e1.printStackTrace();
                    }
                    
                        
                } 
            }
        });
    }

    public Integer getIndexForConversionFactor(ConversionFactorDTO conversionFactorDTO) throws ClassNotFoundException, IOException 
    {
        for (Map.Entry<Integer, ConversionFactorDTO> entry : getConversionFactors().getConversionFactors().entrySet()) 
        {
            if (entry.getValue().equals(conversionFactorDTO)) 
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

    public void addMenuItem( int categoryID, String info)
    {
        JMenuItem categoryItem = conversionFactorsOfCategoryView.addMenuItem(info);
        categoryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    conversionFactorsOfCategoryView.getPanel().removeAll();
                    conversionFactorsOfCategoryView.setLblCategory(categoryController.getCategoryNameByID(categoryID));
                    populate();
                    for ( Entry<Integer, ConversionFactorDTO> entry : getConversionFactors().getConversionFactors().entrySet() )
                    {
                        if ( entry.getValue().getLeaf1ID() == categoryID || entry.getValue().getLeaf2ID() == categoryID )
                        {
                            conversionFactorsOfCategoryView.addlblConversionFactor( conversionFactorController.infoConversionFactor( entry.getValue() ) );      
                        }
                    }
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                }
                
            }
        });
        
    }

    public Double getConversionFactorValue ( int requestedID, int offeredID) throws ClassNotFoundException, IOException 
    {
        requestConversionFactors = new SomeRequestConversionFactors("GET_CONVERSION_FACTOR_VALUE", requestedID, null, false, null, null, offeredID);
        client.sendRequest(requestConversionFactors);
        return (Double) client.receiveResponse();
        //return this.conversionFactorsService.getConversionFactorValue( requested, offered );
    }

    public ConversionFactorsDTO getConversionFactors () throws IOException, ClassNotFoundException
    {
        requestConversionFactors = new SomeRequestConversionFactors("GET_CONVERSION_FACTORS",0, null,  false, null, null, 0);
        client.sendRequest(requestConversionFactors);
        return (ConversionFactorsDTO) client.receiveResponse();
        //return this.conversionFactorsService.getConversionFactors();
    }

    public void resetConversionFactors () throws ClassNotFoundException, IOException
    {
        requestConversionFactors = new SomeRequestConversionFactors("RESET_CONVERSION_FACTORS",0, null,  false, null, null, 0);
        client.sendRequest(requestConversionFactors);
        client.receiveResponse();
        //this.conversionFactorsService.resetConversionFactors();
    }
   
    public void saveConversionFactors () throws ClassNotFoundException, IOException
    {
        requestConversionFactors = new SomeRequestConversionFactors("SAVE_CONVERSION_FACTORS",0,null,  false, null, null, 0);
        client.sendRequest(requestConversionFactors);
        client.receiveResponse();
        //this.conversionFactorsService.saveConversionFactors();
    }

    public void populate () throws IOException, ClassNotFoundException
    {
        requestConversionFactors = new SomeRequestConversionFactors("POPULATE",0, null,  false, null, null, 0);
        client.sendRequest(requestConversionFactors);
        client.receiveResponse();
        //this.conversionFactorsService.populate();
    }

    public void calculate ( int index, Double value ) throws ClassNotFoundException, IOException
    {
        requestConversionFactors = new SomeRequestConversionFactors("CALCULATE", index, value, false, null, null, 0);
        client.sendRequest(requestConversionFactors);
        client.receiveResponse();
        //this.conversionFactorsService.calculate(index, value);
    }

    public void calculateEq ( int index, Double value, boolean check, HashMap<Integer, ConversionFactorDTO> copyCFs, List<String> equations ) throws ClassNotFoundException, IOException
    {
        requestConversionFactors = new SomeRequestConversionFactors("CALCULATE_EQ", index, value, check, copyCFs, equations, 0);
        client.sendRequest(equations);
        client.receiveResponse();
        //this.conversionFactorsService.calculateEq(index, value, check, copyCFs, equations);
    }

    public double[] calculateRange ( int index ) throws IOException, ClassNotFoundException
    {
        requestConversionFactors = new SomeRequestConversionFactors("CALCULATE_RANGE", index, null, false, null, null, 0);
        client.sendRequest(requestConversionFactors);
        return (double[]) client.receiveResponse();
        //return this.conversionFactorsService.calculateRange(index);
    }

    public boolean isComplete () throws IOException, ClassNotFoundException 
    {
        requestConversionFactors = new SomeRequestConversionFactors("IS_COMPLETE",0,null,  false, null, null, 0);
        client.sendRequest(requestConversionFactors);
        return (boolean) client.receiveResponse();
        //return this.conversionFactorsService.isComplete();
    }

}
