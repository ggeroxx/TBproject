package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class UserMenu {
    
    private static PrintService printService = new PrintService();
    private static CategoryJDBC categoryJDBC = new CategoryJDBCImpl();
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

        printService.printAllRoot();
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

        Category toNavigate = categoryJDBC.getCategoryByID( Integer.parseInt( hierarchyID ) );
        do
        {
            String domainValue = Util.insertWithCheck( "Enter value of field --> ", Constants.NOT_EXIST_MESSAGE, ( input ) -> input.isEmpty(), scanner );
            Util.clearConsole( Constants.TIME_SWITCH_MENU );
            printService.print( Constants.CATEGORY_INFO );
            toNavigate = relationshipsBetweenCategoriesJDBC.getChildCategoryByFieldAndParentID( domainValue, toNavigate );
            printService.printInfoCategory( toNavigate );
        } while ( !toNavigate.isLeaf() );

        printService.print( Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

}
