package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class UserMenu {
    
    private static PrintService printService = new PrintService();
    private static CategoryJDBC categoryJDBC = new CategoryJDBCImpl();
    private static UserJDBC userJDBC = new UserJDBCImpl();
    private static ConversionFactorsJDBC conversionFactorsJDBC = new ConversionFactorsJDBCImpl();
    private static RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC = new RelationshipsBetweenCategoriesJDBCImpl();
    private static Scanner scanner = new Scanner( System.in );

    public static void menu ( User user, Session session ) throws SQLException, Exception
    {
        String choice;

        do
        {
            printService.print( Constants.USER_MENU );
            choice = scanner.nextLine();

            switch ( choice )
            {
                case "1":
                        caseOne();
                    break;

                case "2":
                        caseTwo( session );
                    break;

                case "9":
                        session.logout();
                        printService.println( Constants.LOG_OUT );
                        Util.clearConsole( Constants.TIME_LOGOUT );
                    break;

                default:
                        printService.println( Constants.INVALID_OPTION );
                        Util.clearConsole( Constants.TIME_ERROR_MESSAGE);
                    break;
            }
        } while ( !choice.equals( "9" ) );
    }

    private static void caseOne () throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.HIERARCHY_LIST );

        if ( categoryJDBC.getAllRoot().isEmpty() )
        {
            printService.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        printService.printAllRoots();
        printService.print( Constants.ENTER_HIERARCHY_ID );
        String hierarchyID = scanner.nextLine();

        if ( hierarchyID.isEmpty() || !Controls.isInt( hierarchyID ) || !Controls.isPresentRootCategory( Integer.parseInt( hierarchyID ) ) )
        {
            printService.println( Constants.NOT_EXIST_MESSAGE );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.CATEGORY_INFO );
        printService.printInfoCategory( categoryJDBC.getCategoryByID( Integer.parseInt( hierarchyID ) ) );

        ArrayList<Category> history = new ArrayList<Category>();
        history.add( categoryJDBC.getCategoryByID( Integer.parseInt( hierarchyID ) ) );
        do
        {
            String domainValue = Util.insertWithCheck( Constants.ENTER_VALUE_OF_FIELD_MESSAGE, Constants.NOT_EXIST_FIELD_MESSAGE, ( input ) -> input.isEmpty() );
            Util.clearConsole( Constants.TIME_SWITCH_MENU );
            printService.print( Constants.CATEGORY_INFO );
            if ( domainValue.equals( "<" ) )
            {
                history.remove( history.size() - 1 );
                if ( history.isEmpty() )
                {
                    caseOne();
                    return; 
                } 
            }
            else
            {
                if ( !Controls.existValueOfField( domainValue, history.get( history.size() - 1 ) ) )
                {
                    printService.printInfoCategory( history.get( history.size() - 1 ) );
                    printService.println( Constants.NOT_EXIST_FIELD_MESSAGE );
                    continue;
                }
                history.add( relationshipsBetweenCategoriesJDBC.getChildCategoryByFieldAndParentID( domainValue, history.get( history.size() - 1 ) ) );
            }
            printService.printInfoCategory( history.get( history.size() - 1 ) );
        } while ( !history.get( history.size() - 1 ).isLeaf() );

        printService.print( Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

    private static void caseTwo ( Session session ) throws SQLException, Exception
    {
        if ( categoryJDBC.getAllLeaf().isEmpty() || categoryJDBC.getAllLeaf().size() == 1 )
        {
            Util.clearConsole( Constants.TIME_SWITCH_MENU );
            printService.print( Constants.PROPOSE_PROPOSAL_SCREEN );
            printService.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        String requestedCategoryID;
        do
        {
            Util.clearConsole( Constants.TIME_SWITCH_MENU );
            printService.print( Constants.PROPOSE_PROPOSAL_SCREEN );
            printService.printAllLeafCategories();
            printService.print( Constants.ENTER_REQUESTED_CATEGORY_ID );
            requestedCategoryID = scanner.nextLine();

            if ( requestedCategoryID.isEmpty() || !Controls.isInt( requestedCategoryID ) || ( categoryJDBC.getCategoryByID( Integer.parseInt( requestedCategoryID ) ) ) == null || !( categoryJDBC.getCategoryByID( Integer.parseInt( requestedCategoryID ) ) ).isLeaf() )
            {
                printService.println( Constants.NOT_EXIST_MESSAGE );
                Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
                continue;
            }
        } while ( requestedCategoryID.isEmpty() || !Controls.isInt( requestedCategoryID ) || ( categoryJDBC.getCategoryByID( Integer.parseInt( requestedCategoryID ) ) ) == null || !( categoryJDBC.getCategoryByID( Integer.parseInt( requestedCategoryID ) ) ).isLeaf() );
        Category requestedCategory = categoryJDBC.getCategoryByID( Integer.parseInt( requestedCategoryID ) );

        Util.clearConsole( Constants.TIME_SWITCH_MENU );

        String offeredCategoryID;
        do
        {
            printService.print( Constants.PROPOSE_PROPOSAL_SCREEN );
            printService.printLeafCategoriesWithout( categoryJDBC.getCategoryByID( Integer.parseInt( requestedCategoryID ) ) );
            printService.print( Constants.ENTER_OFFERED_CATEGORY_ID );
            offeredCategoryID = scanner.nextLine();

            if ( offeredCategoryID.isEmpty() || !Controls.isInt( offeredCategoryID ) || offeredCategoryID.equals( requestedCategoryID ) || ( categoryJDBC.getCategoryByID( Integer.parseInt( offeredCategoryID ) ) ) == null || !( categoryJDBC.getCategoryByID( Integer.parseInt( offeredCategoryID ) ) ).isLeaf() )
            {
                printService.println( Constants.NOT_EXIST_MESSAGE );
                Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
                continue;
            }
        } while ( offeredCategoryID.isEmpty() || !Controls.isInt( offeredCategoryID ) || offeredCategoryID.equals( requestedCategoryID ) || ( categoryJDBC.getCategoryByID( Integer.parseInt( offeredCategoryID ) ) ) == null || !( categoryJDBC.getCategoryByID( Integer.parseInt( offeredCategoryID ) ) ).isLeaf() );
        Category offeredCategory = categoryJDBC.getCategoryByID( Integer.parseInt( offeredCategoryID ) );

        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.PROPOSE_PROPOSAL_SCREEN );
        String requestedHours = Util.insertWithCheck( Constants.ENTER_REQUESTED_HOURS + Constants.CYAN + categoryJDBC.getCategoryByID( Integer.parseInt( requestedCategoryID ) ).getName() + Constants.RESET + ": ", Constants.ERROR_HOUR, ( input ) -> input.isEmpty() || !Controls.isInt( input ) || Integer.parseInt( input ) <= 0 );
        
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.PROPOSE_PROPOSAL_SCREEN );

        int offeredHours = (int) Math.round( conversionFactorsJDBC.getConversionFactor( requestedCategory, offeredCategory ).getValue() * Integer.parseInt( requestedHours ) );
        Proposal newProposal = new Proposal( null, requestedCategory, offeredCategory, (Integer.parseInt( requestedHours )), offeredHours, userJDBC.getUserByUsername( session.getUsername() ), "open" );
        printService.printProposal( newProposal );

        String saveOrNot = Util.insertWithCheck( Constants.CONFIRM_PROPOSAL, Constants.NOT_EXIST_MESSAGE, ( input ) -> !input.equals(Constants.NO_MESSAGE) && !input.equals(Constants.YES_MESSAGE) );

        if ( saveOrNot.equals( Constants.YES_MESSAGE ) )
        {
            printService.print( Constants.PROPOSAL_SAVED );
            Util.clearConsole( Constants.TIME_MESSAGE );
            newProposal.save();
        }
        else
        {
            printService.print( Constants.PROPOSAL_NOT_SAVED );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
        }

    }

}
