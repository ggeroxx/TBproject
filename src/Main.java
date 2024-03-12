import java.util.*;
import projectClass.*;
import util.*;
import java.io.Console;
import java.sql.*;

public class Main 
{
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
                System.out.print( Constants.MAIN_MENU );
                choice = scanner.nextLine();

                switch ( choice ) 
                {
                    case "1":
                            caseOneMainMenu( scanner, console, session );
                        break;

                    case "2":
                            System.out.println( Constants.BYE_BYE_MESSAGE );
                        break;

                    case "":
                            Configurator conf = new Configurator( "user1", "password1" );
                            Util.clearConsole( Constants.TIME_SWITCH_MENU );
                            ConfiguratorMenu.menu( scanner, conf, session );
                        break;

                    default:
                            System.out.println( Constants.INVALID_OPTION );
                            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
                        break;
                }
            } while ( !choice.equals("2") );
        }
        catch ( SQLException e ) {
            System.out.println( Constants.SQL_EXCEPTION_MESSAGE );
            e.printStackTrace();
        }
        catch ( Exception e ) {
            System.out.println( Constants.GENERIC_EXCEPTION_MESSAGE );
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
                System.out.println( Constants.SQL_EXCEPTION_MESSAGE );
                e.printStackTrace();
            }
        }
    }

    public static void caseOneMainMenu ( Scanner scanner, Console console, Session session ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.print( Constants.LOGIN_SCREEN );

        System.out.print( Constants.ENTER_USERNAME );
        String username = scanner.nextLine();
        
        char[] passwordChars = console.readPassword( Constants.ENTER_PASSWORD );
        String password = new String( passwordChars );

        session.login( username, password );
        if ( !session.getStatus() ) 
        {   
            System.out.println( Constants.LOGIN_ERROR );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        Configurator conf = new Configurator( username, password );

        if ( conf.getFirstAccess() )
        {
            Util.clearConsole( Constants.TIME_SWITCH_MENU );
            System.out.print( Constants.REGISTRATION_SCREEN );

            String newUsername, newPassword;
            boolean checkUsername1 = false, checkUsername2 = false;
            do
            {
                System.out.print( Constants.ENTER_NEW_USERNAME );
                newUsername = scanner.nextLine();
                checkUsername1 = Controls.isPresentUsername( newUsername );
                if ( checkUsername1 ) System.out.println( Constants.USERNAME_NOT_AVAILABLE );
                checkUsername2 = Controls.checkPattern( newUsername, 2, 21 );
                if ( !checkUsername2 ) System.out.println( Constants.ERROR_PATTERN_USERNAME );
            } while ( checkUsername1 || !checkUsername2 );

            boolean checkPassword;
            do
            {   
                passwordChars = console.readPassword( Constants.ENTER_NEW_PASSWORD );
                newPassword = new String( passwordChars );
                checkPassword = Controls.checkPatternPassword( newPassword, 7, 26 );
                if ( !checkPassword ) System.out.println( Constants.ERROR_PATTERN_PASSWORD );
            } while ( !checkPassword );
            
            conf.changeCredentials( newUsername, newPassword );
        }
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        ConfiguratorMenu.menu( scanner, conf, session );
    }

}