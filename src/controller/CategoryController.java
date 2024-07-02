package controller;

import java.sql.*;
import java.util.*;
import model.*;
import util.*;
import view.*;

public class CategoryController extends Controller {
    
    private CategoryView categoryView;
    private CategoryJDBC categoryJDBC;
    private RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC;

    public CategoryController ( CategoryView categoryView, CategoryJDBC categoryJDBC, RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC )
    {
        super( categoryView );
        this.categoryView = categoryView;
        this.categoryJDBC = categoryJDBC;
        this.relationshipsBetweenCategoriesJDBC = relationshipsBetweenCategoriesJDBC;
    }

    public CategoryJDBC getCategoryJDBC () 
    {
        return this.categoryJDBC;
    }

    public void viewHierarchy () throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        categoryView.print( Constants.HIERARCHIES_LIST );

        if ( categoryJDBC.getAllRoot().isEmpty() )
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
        this.viewInfo( categoryJDBC.getCategoryByID( categoryID ) );

        categoryView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }
    
    public void viewBuiltHierarchy ( int hierarchyID ) throws SQLException
    {
        categoryView.println( buildHierarchy( hierarchyID, new StringBuffer(), new StringBuffer() ) );
    }

    private String buildHierarchy ( int IDToPrint, StringBuffer toReturn, StringBuffer spaces ) throws SQLException
    {
        Category notLeaf = categoryJDBC.getCategoryByID( IDToPrint );

        if ( notLeaf.isRoot() ) toReturn.append( notLeaf.getID() + ". " + notLeaf.getName() + "\n\n" );
        else if ( categoryJDBC.getCategoriesWithoutChild().contains( notLeaf ) ) toReturn.append( spaces.toString() + Constants.RED + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + Constants.NO_CHILD + Constants.RESET + "\n\n" );
        else toReturn.append( spaces.toString() + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + "\n\n" );
        
        for ( Integer IDLeaf : relationshipsBetweenCategoriesJDBC.getChildIDsFromParentID( IDToPrint ) ) buildHierarchy( IDLeaf, toReturn, spaces.append( "\t" ) );

        if ( spaces.length() > 1 ) spaces.setLength( spaces.length() - 1 );
        else spaces.setLength( 0 );

        return toReturn.toString();
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
        for ( Category toPrint : categoryJDBC.getParentCategories( category.getHierarchyID() ) ) categoryView.println( "  " + Constants.YELLOW + toPrint.getID() + ". " + Constants.RESET + toPrint.getName() );
    }

    public void listAllRoots () throws SQLException
    {
        for ( Category toPrint : categoryJDBC.getAllSavedRoot() ) categoryView.println( " " + toPrint.getID() + ". " + toPrint.getName() );
        for ( Category toPrint : categoryJDBC.getAllNotSavedRoot() ) categoryView.println( " " + toPrint.getID() + ". " + toPrint.getName() + Constants.NOT_SAVED );
    }

    public void listAllLeafs () throws SQLException
    {
        for ( Category toPrint : categoryJDBC.getAllSavedLeaf() ) categoryView.println( " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + categoryJDBC.getRootByLeaf( toPrint ).getName() + " ]  " );
        for ( Category toPrint : categoryJDBC.getAllNotSavedLeaf() ) categoryView.println( " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + categoryJDBC.getRootByLeaf( toPrint ).getName() + " ]  " + Constants.NOT_SAVED );
    }

    public void listAllLeafsWithout ( Category toRemoved ) throws SQLException
    {
        for ( Category toPrint : categoryJDBC.getAllSavedLeaf() ) 
            if ( !toPrint.equals( toRemoved ) && !toPrint.getName().equals( toRemoved.getName() ) )
                categoryView.println( " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + categoryJDBC.getRootByLeaf( toPrint ).getName() + " ]  " );

        for ( Category toPrint : categoryJDBC.getAllNotSavedLeaf() ) 
            if ( !toPrint.equals( toRemoved ) && !toPrint.getName().equals( toRemoved.getName() ) )
                categoryView.println( " " + toPrint.getID() + ". " + super.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + super.padRight( toPrint.getName() , 50 ) + "  [ " + categoryJDBC.getRootByLeaf( toPrint ).getName() + " ]  " + Constants.NOT_SAVED );
    }

    public void viewInfo ( Category category ) throws SQLException
    {
        StringBuffer sb = new StringBuffer();    

        sb.append( Constants.NAME + ":" + super.padRight( Constants.NAME + ":", 20 ) + Constants.BOLD + category.getName() + Constants.RESET + "\n" );
        if ( !category.isRoot() ) sb.append( Constants.DESCRIPTION + ":" + super.padRight( Constants.DESCRIPTION + ":", 20 ) + category.getDescription() + "\n" );

        if ( category.getField() == null ) sb.append( Constants.VALUE_OF_DOMAIN + ":" + super.padRight( Constants.VALUE_OF_DOMAIN + ":", 20 ) + relationshipsBetweenCategoriesJDBC.getFieldValueFromChildID( category.getID() ) + "\n" );
        else 
        {
            sb.append( Constants.FIELD + ":" + super.padRight( Constants.FIELD + ":", 20 ) + category.getField() + " = { " );
            for ( String fieldValue : relationshipsBetweenCategoriesJDBC.getFieldValuesFromParentID( category.getID() ) ) sb.append( Constants.YELLOW + fieldValue + Constants.RESET + ", " );
            sb.deleteCharAt( sb.length() - 2 );
            sb.append( "}\n" );
        }

        categoryView.println( sb.toString() );
    }

    public int enterRootID () throws SQLException
    {
        int rootID = 0;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                rootID = categoryView.enterInt( Constants.ENTER_HIERARCHY_ID );
                if ( rootID == 0 ) return 0;
                if ( categoryJDBC.getRootCategoryByID( rootID ) == null ) categoryView.print( Constants.NOT_EXIST_MESSAGE );
            }
            catch ( InputMismatchException e )
            {
                categoryView.print( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || categoryJDBC.getRootCategoryByID( rootID ) == null );

        return rootID;
    }
        
    public int enterLeafID () throws SQLException
    {
        int leafID = 0;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                leafID = categoryView.enterInt( Constants.ENTER_CATEGORY_ID_WITH_EXIT );
                if ( leafID == 0 ) return 0;
                if ( ( categoryJDBC.getCategoryByID( leafID ) ) == null || !( categoryJDBC.getCategoryByID( leafID ) ).isLeaf() ) categoryView.print( Constants.NOT_EXIST_MESSAGE );
            }
            catch ( InputMismatchException e )
            {
                categoryView.print( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || ( categoryJDBC.getCategoryByID( leafID ) ) == null || !( categoryJDBC.getCategoryByID( leafID ) ).isLeaf() );

        return leafID;
    }

    public int enterParentID ( Category category, Category root ) throws SQLException
    {
        int parentID = 0;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                categoryView.print( "\n" + Constants.CATEGORY_LIST );
                listAllPossibleDad( category );
                parentID = categoryView.enterInt( Constants.ENTER_DAD_MESSAGE );
                if ( !root.isValidParentID( parentID ) ) categoryView.print( Constants.NOT_EXIST_MESSAGE );
            }
            catch ( InputMismatchException e )
            {
                categoryView.print( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || !root.isValidParentID( parentID ) );

        return parentID;
    }

    public Category enterRequestedCategory () throws SQLException
    {
        categoryView.print( Constants.PROPOSE_PROPOSAL_SCREEN );
        this.listAllLeafs();

        int requestedID = 0;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                requestedID = categoryView.enterInt( "\n" + Constants.ENTER_REQUESTED_CATEGORY_ID );
                if ( ( categoryJDBC.getCategoryByID( requestedID ) ) == null || !( categoryJDBC.getCategoryByID( requestedID ).isLeaf() ) ) categoryView.print( Constants.NOT_EXIST_MESSAGE );
            }
            catch ( InputMismatchException e )
            {
                categoryView.print( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || ( categoryJDBC.getCategoryByID( requestedID ) ) == null || !( categoryJDBC.getCategoryByID( requestedID ).isLeaf() ) );

        return categoryJDBC.getCategoryByID( requestedID );
    }

    public int enterRequestedHours ( Category requestedCategory )
    {
        categoryView.print( Constants.PROPOSE_PROPOSAL_SCREEN );
        
        int requestedHours = 0;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                requestedHours = categoryView.enterInt( Constants.ENTER_REQUESTED_HOURS + Constants.CYAN + requestedCategory.getName() + Constants.RESET + ": " );
                if ( requestedHours <= 0 ) categoryView.print( Constants.ERROR_HOUR );
            }
            catch ( InputMismatchException e )
            {
                categoryView.print( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || requestedHours <= 0 );

        return requestedHours;
    }

    public Category enterOfferedCategory ( Category requestedCategory ) throws SQLException
    {
        categoryView.print( Constants.PROPOSE_PROPOSAL_SCREEN );
        this.listAllLeafsWithout( requestedCategory );

        int offeredID = 0;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                offeredID = categoryView.enterInt( "\n" + Constants.ENTER_OFFERED_CATEGORY_ID );
                if ( offeredID == requestedCategory.getID() || ( categoryJDBC.getCategoryByID( offeredID ) ) == null || !( categoryJDBC.getCategoryByID( offeredID ).isLeaf() ) ) categoryView.print( Constants.NOT_EXIST_MESSAGE );
            }
            catch ( InputMismatchException e )
            {
                categoryView.print( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || offeredID == requestedCategory.getID() || ( categoryJDBC.getCategoryByID( offeredID ) ) == null || !( categoryJDBC.getCategoryByID( offeredID ).isLeaf() ) );

        return categoryJDBC.getCategoryByID( offeredID );
    }

    public int enterID ( int hierarchyID ) throws SQLException
    {
        int ID = 0;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                ID = categoryView.enterInt( Constants.ENTER_CATEGORY_ID );
                if ( ( categoryJDBC.getCategoryByID( ID ) ) == null || ( categoryJDBC.getCategoryByID( ID ) ).getHierarchyID() != hierarchyID ) categoryView.print( Constants.NOT_EXIST_MESSAGE );
            }
            catch ( InputMismatchException e )
            {
                categoryView.print( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || ( categoryJDBC.getCategoryByID( ID ) ) == null || ( categoryJDBC.getCategoryByID( ID ) ).getHierarchyID() != hierarchyID );

        return ID;
    }

    public String enterFieldType ( int parentID )
    {
        return super.readString( Constants.ENTER_FIELD_TYPE, Constants.ERROR_FIELD_VALUE, ( str ) -> {
            try 
            {
                return ( Controls.checkPattern( str, 0, 25 ) || str.equals( "<" ) ) || ( this.existValueOfField( str, categoryJDBC.getCategoryByID( parentID ) ) );
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

        if ( categoryJDBC.getAllRoot().isEmpty() )
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
        this.viewInfo( categoryJDBC.getCategoryByID( hierarchyID ) );

        ArrayList<Category> history = new ArrayList<Category>();
        history.add( categoryJDBC.getCategoryByID( hierarchyID ) );
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
                history.add( relationshipsBetweenCategoriesJDBC.getChildCategoryByFieldAndParentID( domainValue, history.get( history.size() - 1 ) ) );
            }
            this.viewInfo( history.get( history.size() - 1 ) );
        } while ( !history.get( history.size() - 1 ).isLeaf() );

        categoryView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

    private boolean existValueOfField ( String field, Category parent ) throws SQLException
    {
        return relationshipsBetweenCategoriesJDBC.getChildCategoryByFieldAndParentID( field, parent ) != null;
    }

    public void enterHierarchy ( Configurator configurator ) throws SQLException
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

            if ( !notFirstIteration && categoryJDBC.getRootCategoryByName( categoryName ) != null )
            {
                categoryView.print( Constants.ROOT_CATEGORY_ALREADY_PRESENT );
                super.clearConsole( Constants.TIME_ERROR_MESSAGE );
                return;
            }
            if ( notFirstIteration && root.isPresentInternalCategory( categoryName ) )
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
            Category newCategory = isRoot ? ( configurator.createCategory( categoryName, field, description, isRoot, null ) ) : ( configurator.createCategory( categoryName, field, description, isRoot, root.getHierarchyID() ) );

            notFirstIteration = true;

            if ( isRoot ) 
            {
                isRoot = false;
                root = newCategory;
                continue;
            }

            int parentID = this.enterParentID( newCategory, root );
            
            String fieldType = this.enterFieldType( parentID );

            newCategory.createRelationship( parentID, fieldType );

            if ( leafCategory.equals( Constants.NO_MESSAGE ) || categoryJDBC.getCategoriesWithoutChild().size() > 0 ) continue;

            insertContinue = enterYesOrNo( Constants.END_ADD_MESSAGE );

        } while( insertContinue.equals( Constants.NO_MESSAGE ) );

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        categoryView.print( "\n" );
        this.viewBuiltHierarchy( root.getHierarchyID() );
        categoryView.println( Constants.OPERATION_COMPLETED );
        super.clearConsole( Constants.TIME_MESSAGE );
    }

    public void saveCategories () throws SQLException
    {
        categoryJDBC.saveTmpCategories();

        relationshipsBetweenCategoriesJDBC.saveTmpRelationshipsBetweenCategories();     

        relationshipsBetweenCategoriesJDBC.deleteTmpRelationshipsBetweenCategories();

        categoryJDBC.deleteTmpCategories();
    }

}