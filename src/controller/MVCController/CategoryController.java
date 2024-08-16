package controller.MVCController;

import java.sql.*;
import java.util.*;

import controller.GRASPController.*;
import model.*;
import util.*;
import view.*;

public class CategoryController extends Controller {
    
    private CategoryView categoryView;
    private RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository;
    private CategoryGRASPController businessController;

    public CategoryController ( CategoryView categoryView, CategoryRepository categoryRepository, RelationshipsBetweenCategoriesRepository relationshipsBetweenCategoriesRepository )
    {
        super( categoryView );
        this.categoryView = categoryView;
        this.relationshipsBetweenCategoriesRepository = relationshipsBetweenCategoriesRepository;
        this.businessController = new CategoryGRASPController( categoryRepository, relationshipsBetweenCategoriesRepository);
    }

    public void setCategory ( Category category ) 
    {
        this.businessController.setCategory(category);
    }

    public Category getCategory () 
    {
        return this.businessController.getCategory();
    }


    public CategoryRepository getCategoryRepository()
    {
        return this.businessController.getCategoryRepository();
    }

    public void viewHierarchy () throws SQLException
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
        categoryView.println( this.businessController.buildHierarchy( hierarchyID, new StringBuffer(), new StringBuffer() ) );
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
        StringBuffer sb = new StringBuffer();    

        sb.append( Constants.NAME + ":" + super.padRight( Constants.NAME + ":", 20 ) + Constants.BOLD + category.getName() + Constants.RESET + "\n" );
        if ( !category.isRoot() ) sb.append( Constants.DESCRIPTION + ":" + super.padRight( Constants.DESCRIPTION + ":", 20 ) + category.getDescription() + "\n" );

        if ( category.getField() == null ) sb.append( Constants.VALUE_OF_DOMAIN + ":" + super.padRight( Constants.VALUE_OF_DOMAIN + ":", 20 ) + relationshipsBetweenCategoriesRepository.getFieldValueFromChildID( category.getID() ) + "\n" );
        else 
        {
            sb.append( Constants.FIELD + ":" + super.padRight( Constants.FIELD + ":", 20 ) + category.getField() + " = { " );
            for ( String fieldValue : relationshipsBetweenCategoriesRepository.getFieldValuesFromParentID( category.getID() ) ) sb.append( Constants.YELLOW + fieldValue + Constants.RESET + ", " );
            sb.deleteCharAt( sb.length() - 2 );
            sb.append( "}\n" );
        }

        categoryView.println( sb.toString() );
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
                history.add( relationshipsBetweenCategoriesRepository.getChildCategoryByFieldAndParentID( domainValue, history.get( history.size() - 1 ) ) );
            }
            this.viewInfo( history.get( history.size() - 1 ) );
        } while ( !history.get( history.size() - 1 ).isLeaf() );

        categoryView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

    private boolean existValueOfField ( String field, Category parent ) throws SQLException
    {
        return this.businessController.existValueOfField(field,parent);
    }

    public void enterHierarchy ( ConfiguratorController configuratorController ) throws SQLException
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
    }

    public void saveCategories () throws SQLException
    {
        this.businessController.saveCategories();
    }

    public void createRelationship ( int parentID, String fieldType ) throws SQLException
    {
        this.businessController.createRelationship(parentID, fieldType);
    }

    public boolean isPresentInternalCategory ( Category root, String nameToCheck ) throws SQLException
    {
        return this.businessController.isPresentInternalCategory(root, nameToCheck);
    }

    public boolean isValidParentID ( Category root, int IDToCheck ) throws SQLException
    {
        return this.businessController.isValidParentID(root, IDToCheck);
    }

}