package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class UserMenu {
    
    private static PrintService printService = new PrintService();
    private static CategoryJDBC categoryJDBC = new CategoryJDBCImpl();
    private static ConversionFactorsJDBC conversionFactorsJDBC = new ConversionFactorsJDBCImpl();
    private static RelationshipsBetweenCategoriesJDBC relationshipsBetweenCategoriesJDBC = new RelationshipsBetweenCategoriesJDBCImpl();
    private static ProposalJDBC proposalJDBC = new ProposalJDBCImpl();
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
                        caseTwo( user, session );
                    break;

                case "3":
                        caseThree( user, session );
                    break;

                case "4":
                        caseFour( user );
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

    private static void caseTwo ( User user, Session session ) throws SQLException, Exception
    {
        if ( categoryJDBC.getAllLeaf().isEmpty() || categoryJDBC.getAllLeaf().size() == 1 )
        {
            Util.clearConsole( Constants.TIME_SWITCH_MENU );
            printService.print( Constants.PROPOSE_PROPOSAL_SCREEN );
            printService.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        Util.clearConsole( Constants.TIME_SWITCH_MENU );

        String requestedCategoryID;
        do
        {
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
        Proposal newProposal = new Proposal( null, requestedCategory, offeredCategory, (Integer.parseInt( requestedHours )), offeredHours, user, "open" );
        printService.printProposal( newProposal );

        String saveOrNot = Util.insertWithCheck( Constants.CONFIRM_PROPOSAL, Constants.NOT_EXIST_MESSAGE, ( input ) -> !input.equals(Constants.NO_MESSAGE) && !input.equals(Constants.YES_MESSAGE) );

        if ( saveOrNot.equals( Constants.YES_MESSAGE ) )
        {
            printService.print( Constants.PROPOSAL_SAVED );
            Util.clearConsole( Constants.TIME_MESSAGE );
            user.insertProposal( newProposal );
        }
        else
        {
            printService.print( Constants.PROPOSAL_NOT_SAVED );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
        }

        verifyProposal( newProposal, null, new ArrayList<Proposal>() );
    }

    private static void caseThree ( User user, Session session ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.PROPOSAL_LIST );

        List<Proposal> openProposalsByUser = proposalJDBC.getAllOpenProposalByUser( user );

        if ( openProposalsByUser.isEmpty() )
        {
            printService.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        printService.printProposals( openProposalsByUser );
        printService.print( Constants.ENTER_PROPOSAL_ID );
        String proposalID = scanner.nextLine();

        List<Integer> IDs = new ArrayList<Integer>();
        for( Proposal toAdd : openProposalsByUser ) IDs.add( toAdd.getID() );

        if ( proposalID.isEmpty() || !Controls.isInt( proposalID ) || !IDs.contains( Integer.parseInt( proposalID ) ) )
        {
            printService.println( Constants.NOT_EXIST_MESSAGE );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        user.retireProposal( openProposalsByUser.get( IDs.lastIndexOf( Integer.parseInt( proposalID ) ) ) );
        printService.println( Constants.OPERATION_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    }

    private static void caseFour ( User user ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.PROPOSAL_LIST );

        List<Proposal> proposalsByUser = proposalJDBC.getAllProposalsByUser( user );

        printService.printProposals( proposalsByUser );

        printService.print( Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

    private static void verifyProposal ( Proposal inserted, Proposal toVerify, List<Proposal> toCloses ) throws SQLException, Exception
    {
        List<Proposal> compatibleProposals = proposalJDBC.getAllCompatibleProposals( toVerify != null ? toVerify : inserted );

        if ( compatibleProposals.isEmpty() ) return;

        if ( toVerify != null && inserted.getOfferedCategory().getID() == toVerify.getOfferedCategory().getID() && inserted.getRequestedCategory().getID() == toVerify.getRequestedCategory().getID() )
        {
            closeProposals( toCloses );
            return;
        }

        toCloses.add( compatibleProposals.get( 0 ) );
        verifyProposal( inserted, compatibleProposals.get( 0 ), toCloses );
    }

    private static void closeProposals ( List<Proposal> toCloses ) throws SQLException, Exception
    {
        for ( Proposal toClose : toCloses )
            proposalJDBC.closeProposal( toClose );
    }

}
