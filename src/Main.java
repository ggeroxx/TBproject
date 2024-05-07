import java.util.*;

import org.mindrot.jbcrypt.BCrypt;

import projectClass.*;
import util.*;
import java.io.Console;
import java.sql.*;

public class Main 
{
    private static PrintService printService = new PrintService();
    private static ConfiguratorJDBC configuratorJDBC = new ConfiguratorJDBCImpl();
    private static DistrictJDBC districtJDBC = new DistrictJDBCImpl();
    private static UserJDBC userJDBC = new UserJDBCImpl();

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner( System.in );
        Console console = System.console();
        Session session = new Session();

        try
        {
            Conn.openConnection();

            Util.clearConsole( Constants.TIME_ZERO );

            String choice;
            do 
            {
                printService.print( Constants.MAIN_MENU );
                choice = scanner.nextLine();

                switch ( choice ) 
                {
                    case "1":
                            caseOneMainMenu( scanner, console, session );
                        break;

                    case "2":
                            caseTwoMainMenu( scanner, console, session );
                        break;

                    case "3":
                            printService.println( Constants.BYE_BYE_MESSAGE );
                        break;

                    default:
                            printService.println( Constants.INVALID_OPTION );
                            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
                        break;
                }
            } while ( !choice.equals("3") );
        }
        catch ( SQLException e ) {
            printService.println( Constants.SQL_EXCEPTION_MESSAGE );
            e.printStackTrace();
        }
        catch ( Exception e ) {
            printService.println( Constants.GENERIC_EXCEPTION_MESSAGE );
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                Conn.closeConnection();
                scanner.close();
            }
            catch ( Exception e )
            {
                printService.println( Constants.SQL_EXCEPTION_MESSAGE );
                e.printStackTrace();
            }
        }
    }

    public static void caseOneMainMenu ( Scanner scanner, Console console, Session session ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.LOGIN_SCREEN );

        printService.print( Constants.ENTER_USERNAME );
        String username = scanner.nextLine();
        
        char[] passwordChars = console.readPassword( Constants.ENTER_PASSWORD );
        String password = new String( passwordChars );

        session.login( username, password );
        if ( !session.getStatus() ) 
        {   
            printService.println( Constants.LOGIN_ERROR );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        Configurator conf = configuratorJDBC.getConfiguratorByUsername( username );

        if ( conf.getFirstAccess() )
        {
            Util.clearConsole( Constants.TIME_SWITCH_MENU );
            printService.print( Constants.REGISTRATION_SCREEN );

            String newUsername, newPassword;
            boolean checkUsername1 = false, checkUsername2 = false;
            do
            {
                printService.print( Constants.ENTER_NEW_USERNAME );
                newUsername = scanner.nextLine();
                checkUsername1 = Controls.isPresentUsername( newUsername );
                if ( checkUsername1 ) printService.println( Constants.USERNAME_NOT_AVAILABLE );
                checkUsername2 = Controls.checkPattern( newUsername, 2, 21 );
                if ( checkUsername2 ) printService.println( Constants.ERROR_PATTERN_USERNAME );
            } while ( checkUsername1 || checkUsername2 );

            boolean checkPassword;
            do
            {   
                passwordChars = console.readPassword( Constants.ENTER_NEW_PASSWORD );
                newPassword = new String( passwordChars );
                checkPassword = Controls.checkPatternPassword( newPassword, 7, 26 );
                if ( !checkPassword ) printService.println( Constants.ERROR_PATTERN_PASSWORD );
            } while ( !checkPassword );
            
            conf.changeCredentials( newUsername, newPassword );
        }
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        ConfiguratorMenu.menu( scanner, conf, session );
    }

    public static void caseTwoMainMenu ( Scanner scanner, Console console, Session session ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.REGISTRATION_SCREEN );

        char[] passwordChars;
        String newUsername, newPassword;
        boolean checkUsername1 = false, checkUsername2 = false;
        do
        {
            printService.print( Constants.ENTER_NEW_USERNAME );
            newUsername = scanner.nextLine();
            checkUsername1 = Controls.isPresentUsername( newUsername );
            if ( checkUsername1 ) printService.println( Constants.USERNAME_NOT_AVAILABLE );
            checkUsername2 = Controls.checkPattern( newUsername, 2, 21 );
            if ( checkUsername2 ) printService.println( Constants.ERROR_PATTERN_USERNAME );
        } while ( checkUsername1 || checkUsername2 );

        boolean checkPassword;
        do
        {   
            passwordChars = console.readPassword( Constants.ENTER_NEW_PASSWORD );
            newPassword = new String( passwordChars );
            checkPassword = Controls.checkPatternPassword( newPassword, 7, 26 );
            if ( !checkPassword ) printService.println( Constants.ERROR_PATTERN_PASSWORD );
        } while ( !checkPassword );

        for ( District toPrint : districtJDBC.getAllDistricts() )
        {
            printService.print( "\n" + toPrint.getID() + ". " + toPrint.getName() + ":\n" );
            printService.printAllMunicipalitiesOfDistrict( toPrint );
        }

        String districtID;
        do
        {
            printService.print( "\n" + Constants.ENTER_DISTRICT_TO_VIEW );
            districtID = scanner.nextLine();
            if ( districtID.isEmpty() || !Controls.isInt( districtID ) || !Controls.isPresentDistrict( Integer.parseInt( districtID ) ) ) printService.print( Constants.NOT_EXIST_MESSAGE );
        } while ( districtID.isEmpty() || !Controls.isInt( districtID ) || !Controls.isPresentDistrict( Integer.parseInt( districtID ) ) );

        String mail = Util.insertWithCheck( Constants.ENTER_MAIL, Constants.ERROR_PATTERN_MAIL, ( input ) -> !Controls.checkPatternMail( input, 4, 51 ), scanner );

        userJDBC.insertUser( new User( null, newUsername, BCrypt.hashpw( newPassword, BCrypt.gensalt() ), Integer.parseInt( districtID ), mail ) );

        printService.print( Constants.OPERATION_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    }

}