package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import controller.ClientServer.Client;
import controller.ClientServer.SomeRequestCategory;
import model.util.Constants;
import view.HierarchyView;
import view.InsertNewHierarchyView;
import view.NavigateHierarchyView;

public class CategoryController {
     
    private InsertNewHierarchyView insertNewHierarchyView;
    private HierarchyView hierarchyView;
    private NavigateHierarchyView navigateHierarchyView;
    private SessionController sessionController;
    private ControlPatternController controlPatternController;
    private HashMap<JRadioButton, Integer> radioButtonObjectMapForParentiID = new HashMap<>();
    private HashMap<JRadioButton, Integer> radioButtonObjectMapForCategory = new HashMap<>();
    private HashMap<JRadioButton, String> radioButtonObjectMapForFields = new HashMap<>();
    private ArrayList<Integer> history = new ArrayList<Integer>();

    private Client client;
    private SomeRequestCategory requestCategory;

    public CategoryController ( InsertNewHierarchyView insertNewHierarchyView, HierarchyView hierarchyView, NavigateHierarchyView navigateHierarchyView, SessionController sessionController, ControlPatternController controlPatternController, Client client)
    {
        this.insertNewHierarchyView = insertNewHierarchyView;
        this.sessionController = sessionController;
        this.navigateHierarchyView = navigateHierarchyView;
        this.hierarchyView = hierarchyView;
        this.controlPatternController = controlPatternController;
        this.client = client;

        insertNewHierarchyView.getChckbxLeafCategory().addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) 
                {
                    insertNewHierarchyView.chckbxLeafCategoryDeselected();
                }
                else
                {
                    if (e.getStateChange() == ItemEvent.SELECTED) 
                    {
                        insertNewHierarchyView.chckbxLeafCategorySelected();
                    }
                }
            }
        });

        this.navigateHierarchyView.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) 
            {
                if( !history.isEmpty() && !(history.size() == 1)) 
                {
                    history.remove( history.size() - 1 );
                    try 
                    {
                        addRadioButtonFieladsOfCategory( history.get( history.size() - 1 ), false );
                    } 
                    catch (ClassNotFoundException | IOException e1) 
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });

        this.navigateHierarchyView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                closeNavigateHierarchyView();
                try 
                {
                    client.close();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
                System.exit(0);
			}
		});

        this.insertNewHierarchyView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
                /*try {
                    if (!(getCategoryRepository().getCategoriesWithoutChild().size() > 0 ))
                    {
                        closeInsertNewHierarchy();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }*/
			}
		});

        this.hierarchyView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                closeHierarchyView();
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

        this.hierarchyView.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeHierarchyView();
            }
		});

        this.insertNewHierarchyView.getEndButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeInsertNewHierarchy();
            }
		});

        this.insertNewHierarchyView.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeInsertNewHierarchy();
            }
		});

        this.navigateHierarchyView.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                closeNavigateHierarchyView();
            }
		});

    }

    public void startInsertNewHierarchyView( ConfiguratorController configuratorController)
    {
        insertNewHierarchyView.initInsertRoot();
        if (insertNewHierarchyView.getInsertRootButton().getActionListeners().length == 0) 
        {
            this.insertNewHierarchyView.getInsertRootButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    try
                    {
                        String categoryName = insertNewHierarchyView.getTextCategory();
                        String field = insertNewHierarchyView.getTextField();
                        
                        if(validateRootFileds(categoryName, field))
                        {
                            requestCategory = new SomeRequestCategory("INSERT_ROOT", 0, categoryName, field,null, false, 0);
                            client.sendRequest(requestCategory);
                            client.receiveResponse();
                            setCategory(getRootCategoryByName(categoryName));
                            //configuratorController.createCategory( categoryName, field, null, true, null );
                            insertNewHierarchyView.afterInsertRoot();
                            continueInsertCategoryAfterRoot(configuratorController, getCategoryID());
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
    }

    public void closeInsertNewHierarchy ()
    { 
        for (ActionListener al : insertNewHierarchyView.getInsertCategoryButton().getActionListeners()) 
        {
            insertNewHierarchyView.getInsertCategoryButton().removeActionListener(al);
        }
        insertNewHierarchyView.setTextArea("");
        insertNewHierarchyView.removeAllRadioButtons();
        radioButtonObjectMapForParentiID.clear();
        insertNewHierarchyView.dispose();
    }

    public void startHierarchyView() throws ClassNotFoundException, IOException
    {
        hierarchyView.init();

        for ( int toPrint : getAllRootID() ) 
        {
            addMenuItemForHierarchyView( toPrint, " " + toPrint + ". " + controlPatternController.padRight( Integer.toString( toPrint ) , 3 ) + getCategoryNameByID(toPrint) + controlPatternController.padRight( getCategoryNameByID(toPrint) , 50 )  );
        } 
    }

    public void closeHierarchyView()
    {
        hierarchyView.dispose();
    }

    public void startNavigateHierarchyView () throws ClassNotFoundException, IOException
    {
        navigateHierarchyView.init();

        for ( int toPrint : getAllRootID() ) 
        {
            addMenuItemForNavigateHierarchyView( toPrint, " " + toPrint + ". " + controlPatternController.padRight( Integer.toString( toPrint ) , 3 ) + getCategoryNameByID(toPrint) + controlPatternController.padRight( getCategoryNameByID(toPrint) , 50 )  );
        } 
    }

    public void closeNavigateHierarchyView ()
    {
        navigateHierarchyView.dispose();
    }

    public void continueInsertCategoryAfterRoot(ConfiguratorController configuratorController, int rootID) throws  ClassNotFoundException, IOException
    {
        if ((getNumberOfCategoriesWithoutChild()> 0 )) insertNewHierarchyView.getEndButton().setVisible(false);
        else insertNewHierarchyView.getEndButton().setVisible(true);
        
        insertNewHierarchyView.initInsertCategoryAfterRoot();
        insertNewHierarchyView.setTextArea( buildHierarchy( rootID ));
        this.insertNewHierarchyView.getInsertCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {

                    String categoryName = insertNewHierarchyView.getTextCategory();
                    String field = insertNewHierarchyView.getTextField();
                    String description = insertNewHierarchyView.getTextDescription();
                    
                    if( validateCategoryFields( categoryName, field, description, rootID ) )
                    {
                        if(!insertNewHierarchyView.getChckbxLeafCategory().isSelected())
                        {
                            createCategory( categoryName, field, description, false, rootID );
                        }
                        else
                        {
                            createCategory( categoryName, null, description, false, rootID );
                        }
                        insertNewHierarchyView.setLblDescriptionError("");
                        insertNewHierarchyView.getInsertCategoryButton().removeActionListener(insertNewHierarchyView.getInsertCategoryButton().getActionListeners()[0]);
                        continueInsertWithFiledType(configuratorController, rootID);
                    }
                                   
                }
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }

            }
                
        });
    }

    public void continueInsertWithFiledType(ConfiguratorController configuratorController, int rootID) throws ClassNotFoundException, IOException
    {
        insertNewHierarchyView.initInsertFiledTypeAndParentID();

        if (insertNewHierarchyView.getInsertParentIdButton().getActionListeners().length == 0) 
        {
            for ( int toPrint : getParentCategoriesID( rootID ) ) addRadioButton( toPrint );

            this.insertNewHierarchyView.getInsertParentIdButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    try
                    {
                        String fieldType = insertNewHierarchyView.getTextFieldType();
                        if(radioButtonObjectMapForParentiID.get(insertNewHierarchyView.getSelectedRadioButton()) != null)
                        {
                            //int parentID = radioButtonObjectMapForParentiID.get(insertNewHierarchyView.getSelectedRadioButton()).getID();
                            int parentID = radioButtonObjectMapForParentiID.get(insertNewHierarchyView.getSelectedRadioButton());

                            if( validateFiledType(fieldType, parentID) )
                            {
                                createRelationship( parentID, fieldType );
                                insertNewHierarchyView.afterInsertParentIDAndFiledType();
                                insertNewHierarchyView.getSelectedRadioButton().setSelected(false);
                                insertNewHierarchyView.getGroup().clearSelection();
                                continueInsertCategoryAfterRoot(configuratorController, rootID);
                            }
                        } 
                    }
                    catch (ClassNotFoundException e1) 
                    {
                        e1.printStackTrace();
                    } catch (IOException e1) 
                    {
                        e1.printStackTrace();
                    }
                }
            });
        }
            
    }

    public boolean validateRootFileds( String categoryName, String field) throws ClassNotFoundException, IOException
    {
        boolean wrongPatternCategoryName = controlPatternController.checkPattern( categoryName, 0, 50 );
        boolean existRootCategory =  (getRootCategoryByName( categoryName ) != null);
        boolean wrongPatternField = controlPatternController.checkPattern( field, 0, 25 );
        if( wrongPatternCategoryName )
        {
            insertNewHierarchyView.setLblCategoryError(Constants.ERROR_PATTERN_NAME);
        }
        else
        {
            if( existRootCategory)
            {
                insertNewHierarchyView.setLblCategoryError(Constants.ROOT_CATEGORY_ALREADY_PRESENT);
            }
            else
            {
                insertNewHierarchyView.setLblCategoryError("");
            }
        }
       
        if(wrongPatternField)
        {
            insertNewHierarchyView.setLblFieldError(Constants.ERROR_PATTERN_FIELD);
        }
        else
        {
            insertNewHierarchyView.setLblFieldError("");
        }

        return (!wrongPatternCategoryName && ! existRootCategory && !wrongPatternCategoryName) ? true: false;  
    }

    public boolean validateCategoryFields ( String categoryName, String field, String description, int rootID) throws ClassNotFoundException, IOException
    {
        boolean wrongPatternCategoryName = controlPatternController.checkPattern( categoryName, 0, 50 );
        boolean isPresentInternalCategory = isPresentInternalCategory( rootID, categoryName );
        boolean wrongPatternDescription = controlPatternController.checkPattern( description, -1, 100 );

        if( wrongPatternCategoryName )
        {
            insertNewHierarchyView.setLblCategoryError(Constants.ERROR_PATTERN_NAME);
        }
        else
        {
            if( isPresentInternalCategory)
            {
                insertNewHierarchyView.setLblCategoryError(Constants.INTERNAL_CATEGORY_ALREADY_PRESENT);
            }
            else
            {
                insertNewHierarchyView.setLblCategoryError("");
            }
        }

        if(wrongPatternDescription)
        {
            insertNewHierarchyView.setLblDescriptionError(Constants.ERROR_PATTERN_DESCRIPTION);
        }
        else
        {
            insertNewHierarchyView.setLblDescriptionError("");
        }
       
        if(!insertNewHierarchyView.getChckbxLeafCategory().isSelected())
        {
            boolean wrongPatternField = controlPatternController.checkPattern( field, 0, 25 );
            if(wrongPatternField)
            {
                insertNewHierarchyView.setLblFieldError(Constants.ERROR_PATTERN_FIELD);
            }
            else
            {
                insertNewHierarchyView.setLblFieldError("");
            }
            return ( !wrongPatternCategoryName && !isPresentInternalCategory && !wrongPatternDescription && !wrongPatternField );
        }
        return ( !wrongPatternCategoryName && !isPresentInternalCategory && !wrongPatternDescription );

    }

    public boolean validateFiledType ( String fieldType, int parentID ) throws ClassNotFoundException, IOException
    {
        boolean wrongPatternFiledType = (controlPatternController.checkPattern( fieldType, 0, 25 ) || fieldType.equals( "<" ) ) || ( existValueOfField( fieldType, parentID ) );
        if( wrongPatternFiledType )
        {
            insertNewHierarchyView.setLblFieldTypeError(Constants.ERROR_FIELD_VALUE);
        }
        else
        {
            insertNewHierarchyView.setLblFieldTypeError("");
        }
        return !wrongPatternFiledType;
    }

    private void addRadioButton( int categoryID ) throws ClassNotFoundException, IOException 
    {
        String info = "  " + categoryID + ". " + getCategoryNameByID(categoryID);
        JRadioButton radioButton = insertNewHierarchyView.addRadioButton( info );
        radioButtonObjectMapForParentiID.put( radioButton, categoryID );
    }

    private void addRadioButtonCategoryForInformations( int categoryID ) throws ClassNotFoundException, IOException 
    {       
        JRadioButton radioButton = hierarchyView.addRadioButton( getCategoryNameByID(categoryID) );
        radioButtonObjectMapForCategory.put( radioButton, categoryID );

        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if (radioButton.isSelected()) 
                {
                    try 
                    {
                        hierarchyView.setLblInformations( getCategoryNameByID(categoryID));
                        hierarchyView.setTextAreaInfo( info( radioButtonObjectMapForCategory.get( getSelectedRadioButton() ) ) );
                    }
                    catch (ClassNotFoundException e1) 
                    {
                        e1.printStackTrace();
                    } catch (IOException e1) 
                    {
                        e1.printStackTrace();
                    }
                } 
            }
        });
    }

    private void addRadioButtonFieladsOfCategory( int categoryID, boolean addHistory ) throws ClassNotFoundException, IOException
    {
        navigateHierarchyView.getBackButton().setVisible(true);
        navigateHierarchyView.reset();
        navigateHierarchyView.setLblInformations(getCategoryNameByID(categoryID));
        navigateHierarchyView.setTextAreaInfo(info( categoryID ) );
        if ( addHistory) history.add(categoryID);

        for (String field : getFieldValuesFromParentID(categoryID) )
        {
            JRadioButton radioButton = navigateHierarchyView.addRadioButton( field );
            radioButtonObjectMapForFields.put( radioButton, field );

            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed( ActionEvent e ) {
                    if (radioButton.isSelected()) 
                    {
                        try 
                        {
                            addRadioButtonFieladsOfCategory( getChildCategoryIDByFieldAndParentID(field, categoryID), true);
                        } catch (ClassNotFoundException e1) 
                        {
                            e1.printStackTrace();
                        } catch (IOException e1)
                        {
                            e1.printStackTrace();
                        }
                    } 
                }
            });
        }
    }

    public void addMenuItemForHierarchyView( int rootID, String info)
    {
        JMenuItem categoryItem = hierarchyView.addMenuItem(info);
        categoryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try
                {
                    hierarchyView.getPanelCategory().removeAll();
                    hierarchyView.setTextArea( buildHierarchy( rootID ));
                    hierarchyView.setTextAreaInfo("");
                    hierarchyView.setLblInformations("INFORMATIONS");
                    hierarchyView.setLblHierarchy(getCategoryNameByID(rootID));
                    for ( int toPrint : getAllCategoriesIDFromRootID(rootID) )
                    {
                        addRadioButtonCategoryForInformations( toPrint);
                    }
                }
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
            }
        });
        
    }

    public void addMenuItemForNavigateHierarchyView( int rootID, String info)
    {
        JMenuItem categoryItem = navigateHierarchyView.addMenuItem(info);
        categoryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try
                {
                    history.clear();
                    navigateHierarchyView.getBackButton().setVisible(false);
                    navigateHierarchyView.getPanelCategory().removeAll();
                    navigateHierarchyView.setTextArea( buildHierarchy( rootID ) );
                    navigateHierarchyView.setTextAreaInfo("");
                    navigateHierarchyView.setLblInformations("INFORMATIONS");
                    navigateHierarchyView.setLblFields("FIELDS");
                    addRadioButtonFieladsOfCategory(rootID, true);
                    
                }
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
            }
        });
        
    }

    private JRadioButton getSelectedRadioButton() 
	{
	    Enumeration<AbstractButton> buttons = hierarchyView.getGroup().getElements();
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

    public String getCategoryNameByID (int categoryID) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("GET_CATEGORY_NAME_BY_ID", categoryID, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (String) client.receiveResponse();
    }

    public String info( int categoryID ) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("INFO", categoryID, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (String) client.receiveResponse();
    }

    public List<String> getFieldValuesFromParentID(int categoryID) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("GET_FIELD_VALUES_FROM_PARENT_ID", categoryID, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (List<String>) client.receiveResponse();
    }

    public int getChildCategoryIDByFieldAndParentID ( String field, int parentID) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("GET_CHILD_CATEGORY_ID_BY_FIELD_AND_PARENT_ID", parentID, field, field, field, false, 0);
        client.sendRequest(requestCategory);
        return (int) client.receiveResponse();
    }

    public int[] getAllRootID() throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("GET_ALL_ROOT_ID", 0, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (int[]) client.receiveResponse();
    }

    public int[] getAllCategoriesIDFromRootID (int rootID) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("GET_ALL_CATEGORIES_ID_FROM_ROOT_ID", rootID, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (int[]) client.receiveResponse();
    }

    public void setCategory ( int categoryID ) throws IOException, ClassNotFoundException 
    {
        requestCategory = new SomeRequestCategory("SET_CATEGORY", categoryID, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        client.receiveResponse();
        //this.categoryService.setCategory(category);
    }

    public int getCategoryID () throws ClassNotFoundException, IOException 
    {
        requestCategory = new SomeRequestCategory("GET_CATEGORY_ID", 0, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (int) client.receiveResponse();
        //return this.categoryService.getCategory();
    }

    /*public CategoryRepository getCategoryRepository()
    {
        return this.categoryService.getCategoryRepository();
    }*/

    private boolean existValueOfField ( String field, int parentID ) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("EXIST_VALUE_OF_FIELD", parentID, field, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (boolean) client.receiveResponse();

        //return this.categoryService.existValueOfField(field,parent);
    }

    /*public Category getRootByLeaf ( Category category ) throws SQLException
    {
        return categoryService.getRootByLeaf(category);
    }*/

    public void saveCategories () throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("SAVE_CATEGPRIES", 0, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        client.receiveResponse();
        //this.categoryService.saveCategories();
    }

    public void createRelationship ( int parentID, String fieldType ) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("CREATE_RELATIONSHIP", parentID, null, fieldType, null, false, 0);
        client.sendRequest(requestCategory);
        client.receiveResponse();
        //this.categoryService.createRelationship(parentID, fieldType);
    }

    public boolean isPresentInternalCategory ( int rootID, String nameToCheck ) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("IS_PRESENT_INTERNAL_CATEGORY", rootID, nameToCheck, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (boolean) client.receiveResponse();

       //return this.categoryService.isPresentInternalCategory(root, nameToCheck);
    }

    public boolean isValidParentID ( int rootID, int IDToCheck ) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("IS_VALID_PARENT_ID", IDToCheck, null, null, null, false, rootID);
        client.sendRequest(requestCategory);
        return (boolean) client.receiveResponse();
        //return this.categoryService.isValidParentID(root, IDToCheck);
    }

    public int[] getAllSavedLeafID () throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("GET_ALL_SAVED_LEAF_ID", 0, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (int[]) client.receiveResponse();
        //return categoryService.getAllSavedLeaf();
    }
    
    public int[] getAllNotSavedLeafID () throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("GET_ALL_NOT_SAVED_LEAF_ID", 0, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (int[]) client.receiveResponse();
        //return categoryService.getAllNotSavedLeaf();
    }

    /*public Category getCategoryByID ( int categoryID ) throws SQLException
    {
        return categoryService.getCategoryByID ( categoryID);
    }*/

    public Integer getRootCategoryByName (String name) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("GET_ROOT_CATEGORY_ID_BY_NAME", 0, name, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (Integer) client.receiveResponse();
        //return this.categoryService.getRootCategoryByName(name);
    }

    public int getNumberOfEqualsCategories( int categoryID) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("GET_NUMBER_OF_EQUALS_CATEGORY", categoryID, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (int) client.receiveResponse();
    }

    public String getRootNameByLeaf (int leafID) throws IOException, ClassNotFoundException
    {
        requestCategory = new SomeRequestCategory("GET_ROOT_NAME_BY_LEAF", leafID, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (String) client.receiveResponse();
    }

    public void createCategory ( String name, String field, String description, boolean isRoot, Integer hierarchyID ) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("CREATE_CATEGORY", 0, name, field, description, isRoot, hierarchyID);
        client.sendRequest(requestCategory);
        client.receiveResponse();
        //this.configuratorService.createCategory(name, field, description, isRoot, hierarchyID);
    }

    public int[] getParentCategoriesID (int rootID) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("GET_PARENT_CATEGORIES_ID", rootID, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (int[]) client.receiveResponse();
    }

    public String buildHierarchy ( int IDToPrint) throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("BUILD_HIERARCHY", IDToPrint, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (String) client.receiveResponse();
    }

    public int getNumberOfCategoriesWithoutChild () throws ClassNotFoundException, IOException
    {
        requestCategory = new SomeRequestCategory("GET_NUMBER_OF_CATEGORIES_WITHOUT_CHILD", 0, null, null, null, false, 0);
        client.sendRequest(requestCategory);
        return (int) client.receiveResponse();
    }

}