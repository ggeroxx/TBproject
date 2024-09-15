package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import model.util.Constants;
import model.util.Controls;
import model.Category;
import repository.CategoryRepository;
import service.CategoryService;
import service.ControlPatternService;
import view.HierarchyView;
import view.InsertNewHierarchyView;
import view.NavigateHierarchyView;

public class CategoryController {
     
    private InsertNewHierarchyView insertNewHierarchyView;
    private HierarchyView hierarchyView;
    private NavigateHierarchyView navigateHierarchyView;
    private CategoryService categoryService;
    private HashMap<JRadioButton, Category> radioButtonObjectMapForParentiID = new HashMap<>();
    private HashMap<JRadioButton, Category> radioButtonObjectMapForCategory = new HashMap<>();
    private HashMap<JRadioButton, String> radioButtonObjectMapForFields = new HashMap<>();
    ArrayList<Category> history = new ArrayList<Category>();

    public CategoryController ( InsertNewHierarchyView insertNewHierarchyView, HierarchyView hierarchyView, NavigateHierarchyView navigateHierarchyView, CategoryService categoryService)
    {
        this.insertNewHierarchyView = insertNewHierarchyView;
        this.categoryService = categoryService;
        this.navigateHierarchyView = navigateHierarchyView;
        this.hierarchyView = hierarchyView;

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
            public void actionPerformed( ActionEvent e ) {
                    try 
                    {
                        if( !history.isEmpty() && !(history.size() == 1)) 
                        {
                            history.remove( history.size() - 1 );
                            addRadioButtonFieladsOfCategory( history.get( history.size() - 1 ), false );
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
            }
        });

        this.navigateHierarchyView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                closeNavigateHierarchyView();
			}
		});

        this.insertNewHierarchyView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                try {
                    if (!(getCategoryRepository().getCategoriesWithoutChild().size() > 0 ))
                    {
                        closeInsertNewHierarchy();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
			}
		});

        this.hierarchyView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                closeHierarchyView();
			}
		});

    }

    public void setCategory ( Category category ) 
    {
        this.categoryService.setCategory(category);
    }

    public Category getCategory () 
    {
        return this.categoryService.getCategory();
    }


    public CategoryRepository getCategoryRepository()
    {
        return this.categoryService.getCategoryRepository();
    }

    /*public void viewHierarchy () throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        categoryView.print( Constants.HIERARCHIES_LIST );

        if ( getCategoryRepository().getAllRoot().isEmpty() )
        {
            categoryView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        this.listAllRoots();
        int hierarchyID = this.enterRootID();
        if( hierarchyID == 0 ) return;

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        categoryView.println( "\n" );
        this.viewBuiltHierarchy( hierarchyID );

        int categoryID = this.enterID( hierarchyID );

        categoryView.println( "\n" );
        this.viewInfo( getCategoryRepository().getCategoryByID( categoryID ) );

        categoryView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }
    
    public void viewBuiltHierarchy ( int hierarchyID ) throws SQLException
    {
        categoryView.println( this.controllerGRASP.buildHierarchy( hierarchyID, new StringBuffer(), new StringBuffer() ) );
    }

    public String enterName ()
    {
        return super.readString( Constants.ENTER_CATEGORY_NAME, Constants.ERROR_PATTERN_NAME + "\n", ( str ) -> Controls.checkPattern( str, 0, 50 ) );
    }

    public String enterYesOrNo ( String msg )
    {
        return super.readString( msg, Constants.INVALID_OPTION, ( str ) -> !str.equals( Constants.NO_MESSAGE ) && !str.equals( Constants.YES_MESSAGE ) );
    }

    public String enterField ()
    {
        return super.readString( Constants.ENTER_FIELD, Constants.ERROR_PATTERN_FIELD, ( str ) -> Controls.checkPattern( str, 0, 25 ) );
    }

    public String enterDescription ()
    {
        return super.readString( Constants.ENTER_DESCRIPTION, Constants.ERROR_PATTERN_DESCRIPTION, ( str ) -> str.length() <= -1 || str.length() >= 100 );
    }

    public void listAllPossibleDad ( Category category ) throws SQLException
    {
        for ( Category toPrint : getCategoryRepository().getParentCategories( category.getHierarchyID() ) ) categoryView.println( "  " + Constants.YELLOW + toPrint.getID() + ". " + Constants.RESET + toPrint.getName() );
    }

    public void listAllRoots () throws SQLException
    {
        for ( Category toPrint : getCategoryRepository().getAllSavedRoot() ) categoryView.println( " " + toPrint.getID() + ". " + toPrint.getName() );
        for ( Category toPrint : getCategoryRepository().getAllNotSavedRoot() ) categoryView.println( " " + toPrint.getID() + ". " + toPrint.getName() + Constants.NOT_SAVED );
    }

    public void listAllLeafs () throws SQLException
    {
        for ( Category toPrint : getCategoryRepository().getAllSavedLeaf() ) categoryView.println( " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + getCategoryRepository().getRootByLeaf( toPrint ).getName() + " ]  " );
        for ( Category toPrint : getCategoryRepository().getAllNotSavedLeaf() ) categoryView.println( " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + getCategoryRepository().getRootByLeaf( toPrint ).getName() + " ]  " + Constants.NOT_SAVED );
    }

    public String allLeafs () throws SQLException
    {
        StringBuffer toString = new StringBuffer();
        for ( Category toPrint : getCategoryRepository().getAllSavedLeaf() ) toString.append( " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + getCategoryRepository().getRootByLeaf( toPrint ).getName() + " ]  " );
        for ( Category toPrint : getCategoryRepository().getAllNotSavedLeaf() ) toString.append( " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + getCategoryRepository().getRootByLeaf( toPrint ).getName() + " ]  " + Constants.NOT_SAVED );
        return toString.toString();
    }

    public void listAllLeafsWithout ( Category toRemoved ) throws SQLException
    {
        for ( Category toPrint : getCategoryRepository().getAllSavedLeaf() ) 
            if ( !toPrint.equals( toRemoved ) && !toPrint.getName().equals( toRemoved.getName() ) )
                categoryView.println( " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + getCategoryRepository().getRootByLeaf( toPrint ).getName() + " ]  " );

        for ( Category toPrint : getCategoryRepository().getAllNotSavedLeaf() ) 
            if ( !toPrint.equals( toRemoved ) && !toPrint.getName().equals( toRemoved.getName() ) )
                categoryView.println( " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + getCategoryRepository().getRootByLeaf( toPrint ).getName() + " ]  " + Constants.NOT_SAVED );
    }

    public void viewInfo ( Category category ) throws SQLException
    {
        categoryView.println( controllerGRASP.info(category));
    }

    public int enterRootID ()
    {
        return super.readIntWithExit( Constants.ENTER_HIERARCHY_ID, Constants.NOT_EXIST_MESSAGE, ( input ) -> {
            try 
            {
                return getCategoryRepository().getRootCategoryByID( (Integer) input ) == null;
            } 
            catch ( SQLException e ) 
            {
                return false;
            }
        } );
    }
        
    public int enterLeafID ()
    {
        return super.readIntWithExit( Constants.ENTER_CATEGORY_ID_WITH_EXIT, Constants.NOT_EXIST_MESSAGE, ( input ) -> {
            try 
            {
                return ( getCategoryRepository().getCategoryByID( (Integer) input ) ) == null || !( getCategoryRepository().getCategoryByID( (Integer) input ) ).isLeaf();
            } 
            catch ( SQLException e ) 
            {
                return false;
            }
        } );
    }

    public int enterParentID ( Category root ) throws SQLException
    {
        int parentID = 0;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                categoryView.print( "\n" + Constants.CATEGORY_LIST );
                listAllPossibleDad( getCategory() );
                parentID = categoryView.enterInt( Constants.ENTER_DAD_MESSAGE );
                if ( !isValidParentID( root, parentID ) ) categoryView.print( Constants.NOT_EXIST_MESSAGE );
            }
            catch ( InputMismatchException e )
            {
                categoryView.print( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || !isValidParentID( root, parentID ) );

        return parentID;
    }

    public Category enterRequestedCategory () throws SQLException
    {
        categoryView.print( Constants.PROPOSE_PROPOSAL_SCREEN );
        this.listAllLeafs();

        return getCategoryRepository().getCategoryByID( super.readInt( "\n" + Constants.ENTER_REQUESTED_CATEGORY_ID, Constants.NOT_EXIST_MESSAGE, ( input ) -> {
            try 
            {
                return ( getCategoryRepository().getCategoryByID( (Integer) input ) ) == null || !( getCategoryRepository().getCategoryByID( (Integer) input ).isLeaf() );
            } catch ( SQLException e ) 
            {
                return false;
            }
        } ) );
    }

    public int enterRequestedHours ( Category requestedCategory )
    {
        categoryView.print( Constants.PROPOSE_PROPOSAL_SCREEN );
        
        return super.readInt( Constants.ENTER_REQUESTED_HOURS + Constants.CYAN + requestedCategory.getName() + Constants.RESET + ": ", Constants.ERROR_HOUR, ( input ) -> (Integer) input <= 0 );
    }

    public Category enterOfferedCategory ( Category requestedCategory ) throws SQLException
    {
        categoryView.print( Constants.PROPOSE_PROPOSAL_SCREEN );
        this.listAllLeafsWithout( requestedCategory );

        return getCategoryRepository().getCategoryByID( super.readInt( "\n" + Constants.ENTER_OFFERED_CATEGORY_ID, Constants.NOT_EXIST_MESSAGE, ( input ) -> {
            try 
            {
                return (Integer) input == requestedCategory.getID() || ( getCategoryRepository().getCategoryByID( (Integer) input ) ) == null || !( getCategoryRepository().getCategoryByID( (Integer) input ) ).isLeaf();
            } 
            catch ( SQLException e ) 
            {
                return false;
            }
        } ) );
    }

    public int enterID ( int hierarchyID )
    {
        return super.readInt( Constants.ENTER_CATEGORY_ID, Constants.NOT_EXIST_MESSAGE, ( input ) -> {
            try 
            {
                return ( getCategoryRepository().getCategoryByID( (Integer) input ) ) == null || ( getCategoryRepository().getCategoryByID( (Integer) input ) ).getHierarchyID() != hierarchyID;
            } 
            catch ( SQLException e ) 
            {
                return false;
            }
        } );
    }

    public String enterFieldType ( int parentID )
    {
        return super.readString( Constants.ENTER_FIELD_TYPE, Constants.ERROR_FIELD_VALUE, ( str ) -> {
            try 
            {
                return ( Controls.checkPattern( str, 0, 25 ) || str.equals( "<" ) ) || ( this.existValueOfField( str, getCategoryRepository().getCategoryByID( parentID ) ) );
            } 
            catch ( SQLException e ) 
            {
                return false;
            }
        } );
    }

    public void navigateHierarchy () throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        categoryView.print( Constants.HIERARCHIES_LIST );

        if ( getCategoryRepository().getAllRoot().isEmpty() )
        {
            categoryView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        this.listAllRoots();
        int hierarchyID = this.enterRootID();
        if ( hierarchyID == 0 ) return;

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        categoryView.print( Constants.CATEGORY_INFO );
        this.viewInfo( getCategoryRepository().getCategoryByID( hierarchyID ) );

        ArrayList<Category> history = new ArrayList<Category>();
        history.add( getCategoryRepository().getCategoryByID( hierarchyID ) );
        do
        {
            String domainValue = super.readString( Constants.ENTER_VALUE_OF_FIELD_MESSAGE, Constants.NOT_EXIST_FIELD_MESSAGE, ( input ) -> input.isEmpty() );
            super.clearConsole( Constants.TIME_SWITCH_MENU );
            categoryView.print( Constants.CATEGORY_INFO );
            if ( domainValue.equals( "<" ) )
            {
                history.remove( history.size() - 1 );
                if ( history.isEmpty() )
                {
                    this.navigateHierarchy();
                    return; 
                } 
            }
            else
            {
                if ( !this.existValueOfField( domainValue, history.get( history.size() - 1 ) ) )
                {
                    this.viewInfo( history.get( history.size() - 1 ) );
                    categoryView.println( Constants.NOT_EXIST_FIELD_MESSAGE );
                    continue;
                }
                history.add( this.controllerGRASP.getRelationshipsBetweenCategoriesRepository().getChildCategoryByFieldAndParentID( domainValue, history.get( history.size() - 1 ) ) );
            }
            this.viewInfo( history.get( history.size() - 1 ) );
        } while ( !history.get( history.size() - 1 ).isLeaf() );

        categoryView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }*/

    private boolean existValueOfField ( String field, Category parent ) throws SQLException
    {
        return this.categoryService.existValueOfField(field,parent);
    }

    public Category getRootByLeaf ( Category category ) throws SQLException
    {
        return categoryService.getRootByLeaf(category);
    }

    /*public void enterHierarchy ( ConfiguratorController configuratorController ) throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        categoryView.print( Constants.HIERARCHY_SCREEN );

        boolean isRoot = true, notFirstIteration = false;
        String insertContinue = Constants.NO_MESSAGE;
        Category root = null;

        do
        {
            if ( notFirstIteration )
            {
                super.clearConsole( Constants.TIME_SWITCH_MENU );
                categoryView.println( "\n" );
                this.viewBuiltHierarchy( root.getHierarchyID() );
                categoryView.println( Constants.LINE );
            } 

            String categoryName = this.enterName();

            if ( !notFirstIteration && getCategoryRepository().getRootCategoryByName( categoryName ) != null )
            {
                categoryView.print( Constants.ROOT_CATEGORY_ALREADY_PRESENT );
                super.clearConsole( Constants.TIME_ERROR_MESSAGE );
                return;
            }
            if ( notFirstIteration && isPresentInternalCategory( root, categoryName ) )
            {
                categoryView.print( Constants.INTERNAL_CATEGORY_ALREADY_PRESENT );
                super.clearConsole( Constants.TIME_ERROR_MESSAGE );
                continue;
            }

            String leafCategory = Constants.NO_MESSAGE;
            if ( notFirstIteration ) leafCategory = this.enterYesOrNo( Constants.LEAF_CATEGORY_MESSAGE );

            String field = null;
            String description = null;
            if ( leafCategory.equals( Constants.NO_MESSAGE ) ) field = this.enterField();
            if ( notFirstIteration ) description = this.enterDescription();

            if ( isRoot )
                configuratorController.createCategory( categoryName, field, description, isRoot, null );
            else
                configuratorController.createCategory( categoryName, field, description, isRoot, root.getHierarchyID() );

            notFirstIteration = true;

            if ( isRoot ) 
            {
                isRoot = false;
                root = getCategory();
                continue;
            }

            int parentID = this.enterParentID( root );
            
            String fieldType = this.enterFieldType( parentID );

            createRelationship( parentID, fieldType );

            if ( leafCategory.equals( Constants.NO_MESSAGE ) || getCategoryRepository().getCategoriesWithoutChild().size() > 0 ) continue;

            insertContinue = enterYesOrNo( Constants.END_ADD_MESSAGE );

        } while( insertContinue.equals( Constants.NO_MESSAGE ) );

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        categoryView.print( "\n" );
        this.viewBuiltHierarchy( root.getHierarchyID() );
        categoryView.println( Constants.OPERATION_COMPLETED );
        super.clearConsole( Constants.TIME_MESSAGE );
    }*/

    public void saveCategories () throws SQLException
    {
        this.categoryService.saveCategories();
    }

    public void createRelationship ( int parentID, String fieldType ) throws SQLException
    {
        this.categoryService.createRelationship(parentID, fieldType);
    }

    public boolean isPresentInternalCategory ( Category root, String nameToCheck ) throws SQLException
    {
        return this.categoryService.isPresentInternalCategory(root, nameToCheck);
    }

    public boolean isValidParentID ( Category root, int IDToCheck ) throws SQLException
    {
        return this.categoryService.isValidParentID(root, IDToCheck);
    }

    public void startInsertNewHierarchyView( ConfiguratorController configuratorController) throws SQLException
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
                            configuratorController.createCategory( categoryName, field, null, true, null );
                            insertNewHierarchyView.afterInsertRoot();
                            continueInsertCategoryAfterRoot(configuratorController, getCategory());
                        }      
                    }
                    catch(SQLException ex)
                    {
                        ex.printStackTrace();
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

    public boolean validateRootFileds( String categoryName, String field) throws SQLException
    {
        boolean wrongPatternCategoryName = ControlPatternService.checkPattern( categoryName, 0, 50 );
        boolean existRootCategory =  getCategoryRepository().getRootCategoryByName( categoryName ) != null;
        boolean wrongPatternField = ControlPatternService.checkPattern( field, 0, 25 );
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

    public void continueInsertCategoryAfterRoot(ConfiguratorController configuratorController, Category root) throws SQLException
    {
        insertNewHierarchyView.initInsertCategoryAfterRoot();
        insertNewHierarchyView.setTextArea( categoryService.buildHierarchy( root.getHierarchyID(), new StringBuffer(), new StringBuffer() ));
        this.insertNewHierarchyView.getInsertCategoryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {

                    String categoryName = insertNewHierarchyView.getTextCategory();
                    String field = insertNewHierarchyView.getTextField();
                    String description = insertNewHierarchyView.getTextDescription();
                    
                    if( validateCategoryFields( categoryName, field, description, root ) )
                    {
                        if(!insertNewHierarchyView.getChckbxLeafCategory().isSelected())
                        {
                            configuratorController.createCategory( categoryName, field, description, false, root.getHierarchyID() );
                        }
                        else
                        {
                            configuratorController.createCategory( categoryName, null, description, false, root.getHierarchyID() );
                        }
                        insertNewHierarchyView.setLblDescriptionError("");
                        insertNewHierarchyView.getInsertCategoryButton().removeActionListener(insertNewHierarchyView.getInsertCategoryButton().getActionListeners()[0]);
                        continueInsertWithFiledType(configuratorController, root);
                    }
                                   
                }
                catch(SQLException ex)
                {
                    ex.printStackTrace();
                }

            }
                
        });
    }

    public boolean validateCategoryFields ( String categoryName, String field, String description, Category root) throws SQLException
    {
        boolean wrongPatternCategoryName = ControlPatternService.checkPattern( categoryName, 0, 50 );
        boolean isPresentInternalCategory = isPresentInternalCategory( root, categoryName );
        boolean wrongPatternDescription = Controls.checkPattern( description, -1, 100 );

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
            boolean wrongPatternField = ControlPatternService.checkPattern( field, 0, 25 );
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

    public void continueInsertWithFiledType(ConfiguratorController configuratorController, Category root) throws SQLException
    {
        insertNewHierarchyView.initInsertFiledTypeAndParentID();

        if (insertNewHierarchyView.getInsertParentIdButton().getActionListeners().length == 0) 
        {
            for ( Category toPrint : getCategoryRepository().getParentCategories( root.getID()) ) addRadioButton( toPrint );

            this.insertNewHierarchyView.getInsertParentIdButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    try
                    {
                        String fieldType = insertNewHierarchyView.getTextFieldType();
                        if(radioButtonObjectMapForParentiID.get(insertNewHierarchyView.getSelectedRadioButton()) != null)
                        {
                            int parentID = radioButtonObjectMapForParentiID.get(insertNewHierarchyView.getSelectedRadioButton()).getID();

                            if( validateFiledType(fieldType, parentID) )
                            {
                                createRelationship( parentID, fieldType );
                                insertNewHierarchyView.afterInsertParentIDAndFiledType();
                                insertNewHierarchyView.getSelectedRadioButton().setSelected(false);
                                insertNewHierarchyView.getGroup().clearSelection();
                                continueInsertCategoryAfterRoot(configuratorController, root);
                            }
                        } 
                    }
                    catch(SQLException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
        }
            
    }

    public boolean validateFiledType ( String fieldType, int parentID ) throws SQLException
    {
        boolean wrongPatternFiledType = (ControlPatternService.checkPattern( fieldType, 0, 25 ) || fieldType.equals( "<" ) ) || ( existValueOfField( fieldType, getCategoryRepository().getCategoryByID( parentID ) ) );
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

    private void addRadioButton( Category category ) throws SQLException 
    {
        String info = "  " + category.getID() + ". " + category.getName();
        JRadioButton radioButton = insertNewHierarchyView.addRadioButton( info );
        radioButtonObjectMapForParentiID.put( radioButton, category );
    }

    private void addRadioButtonCategoryForInformations( Category category ) throws SQLException 
    {
        
        JRadioButton radioButton = hierarchyView.addRadioButton( category.getName() );
        radioButtonObjectMapForCategory.put( radioButton, category );

        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if (radioButton.isSelected()) 
                {
                    try 
                    {
                        hierarchyView.setLblInformations(category.getName());
                        hierarchyView.setTextAreaInfo(categoryService.info( radioButtonObjectMapForCategory.get( getSelectedRadioButton() ) ) );
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } 
            }
        });
    }

    private void addRadioButtonFieladsOfCategory( Category category, boolean addHistory ) throws SQLException 
    {
        navigateHierarchyView.getBackButton().setVisible(true);
        navigateHierarchyView.reset();
        navigateHierarchyView.setLblInformations(category.getName());
        navigateHierarchyView.setTextAreaInfo(categoryService.info( category ) );
        if ( addHistory) history.add(category);

        for (String field :  categoryService.getFieldValuesFromParentID(category.getID()) )
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
                            addRadioButtonFieladsOfCategory( categoryService.getChildCategoryByFieldAndParentID(field, category), true);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    } 
                }
            });
        }
    }

    public void startHierarchyView() throws SQLException
    {
        hierarchyView.init();

        for ( Category toPrint : categoryService.getAllRoot() ) 
        {
            addMenuItemForHierarchyView( toPrint, " " + toPrint.getID() + ". " + ControlPatternService.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + ControlPatternService.padRight( toPrint.getName() , 50 )  );
        } 
    }

    public void closeHierarchyView()
    {
        hierarchyView.dispose();
    }

    public void startNavigateHierarchyView () throws SQLException
    {
        navigateHierarchyView.init();

        for ( Category toPrint : categoryService.getAllRoot() ) 
        {
            addMenuItemForNavigateHierarchyView( toPrint, " " + toPrint.getID() + ". " + ControlPatternService.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + ControlPatternService.padRight( toPrint.getName() , 50 )  );
        } 
    }

    public void closeNavigateHierarchyView ()
    {
        navigateHierarchyView.dispose();
    }

    public void addMenuItemForHierarchyView( Category root, String info)
    {
        JMenuItem categoryItem = hierarchyView.addMenuItem(info);
        categoryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try
                {
                    hierarchyView.getPanelCategory().removeAll();
                    hierarchyView.setTextArea( categoryService.buildHierarchy( root.getHierarchyID(), new StringBuffer(), new StringBuffer() ) );
                    hierarchyView.setTextAreaInfo("");
                    hierarchyView.setLblInformations("INFORMATIONS");
                    hierarchyView.setLblHierarchy(root.getName());
                    for ( Category toPrint : categoryService.getAllCategoriesFromRoot(root) )
                    {
                        addRadioButtonCategoryForInformations( toPrint);
                    }
                }
                catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
    }

    public void addMenuItemForNavigateHierarchyView( Category root, String info)
    {
        JMenuItem categoryItem = navigateHierarchyView.addMenuItem(info);
        categoryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try
                {
                    history.clear();
                    navigateHierarchyView.getBackButton().setVisible(false);
                    navigateHierarchyView.getPanelCategory().removeAll();
                    navigateHierarchyView.setTextArea( categoryService.buildHierarchy( root.getHierarchyID(), new StringBuffer(), new StringBuffer() ) );
                    navigateHierarchyView.setTextAreaInfo("");
                    navigateHierarchyView.setLblInformations("INFORMATIONS");
                    navigateHierarchyView.setLblFields("FIELDS");
                    addRadioButtonFieladsOfCategory(root, true);
                    
                }
                catch (SQLException e1) {
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

    public List <Category> getAllSavedLeaf () throws SQLException
    {
        return categoryService.getAllSavedLeaf();
    }
    
    public List <Category> getAllNotSavedLeaf () throws SQLException
    {
        return categoryService.getAllNotSavedLeaf();
    }

    public Category getCategoryByID ( int categoryID ) throws SQLException
    {
        return categoryService.getCategoryByID ( categoryID);
    }
}