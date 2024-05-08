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
    private static AccessJDBC accessJDBC = new AccessJDBDImpl();
    private static Scanner scanner = new Scanner( System.in );
    private static Console console = System.console();

    public static void main(String[] args) 
    {
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
                            caseOneMainMenu( session );
                        break;

                    case "2":
                            caseTwoMainMenu( session );
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
                session.logout();
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

    public static void caseOneMainMenu ( Session session ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.LOGIN_SCREEN );

        printService.print( Constants.ENTER_USERNAME );
        String username = scanner.nextLine();
        
        char[] passwordChars = console.readPassword( Constants.ENTER_PASSWORD );
        String password = new String( passwordChars );

        session.login( username, password );
        if ( !session.getStatus() && session.getSubject() == null ) 
        {   
            printService.println( Constants.LOGIN_ERROR );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }
        if ( !session.getStatus() && session.getSubject() == 'c' )
        {
            printService.print( String.format( Constants.DENIED_ACCESS, accessJDBC.getPermission().getUsername() ) );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        if ( session.getSubject() == 'c' ) 
        {
            Configurator conf = configuratorJDBC.getConfiguratorByUsername( username );

            if ( conf.getFirstAccess() )
            {
                Util.clearConsole( Constants.TIME_SWITCH_MENU );
                printService.print( Constants.REGISTRATION_SCREEN );

                String newUsername = enterNewUsername();

                String newPassword = enterNewPassword();
                
                conf.changeCredentials( newUsername, newPassword );
            }
            Util.clearConsole( Constants.TIME_SWITCH_MENU );
            ConfiguratorMenu.menu( conf, session );
            return;
        }

        User user = userJDBC.getUserByUsername( username );
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        UserMenu.menu( user, session );        
    }

    public static void caseTwoMainMenu ( Session session ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.REGISTRATION_SCREEN );

        for ( District toPrint : districtJDBC.getAllDistricts() )
        {
            printService.print( Constants.BOLD + Constants.ITALIC + toPrint.getID() + ". " + toPrint.getName() + ":\n" + Constants.RESET );
            printService.printAllMunicipalitiesOfDistrict( toPrint );
        }

        String districtID;
        do
        {
            printService.print( Constants.ENTER_DISTRICT_OR_EXIT );
            districtID = scanner.nextLine();
            if ( districtID.equals( "e" ) )
            {
                Util.clearConsole( Constants.TIME_SWITCH_MENU );
                return;
            }
            if ( districtID.isEmpty() || !Controls.isInt( districtID ) || !Controls.isPresentDistrict( Integer.parseInt( districtID ) ) ) printService.print( Constants.NOT_EXIST_MESSAGE );
        } while ( districtID.isEmpty() || !Controls.isInt( districtID ) || !Controls.isPresentDistrict( Integer.parseInt( districtID ) ) );

        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.REGISTRATION_SCREEN );

        String newUsername = enterNewUsername();

        String newPassword = enterNewPassword();

        String mail = Util.insertWithCheck( Constants.ENTER_MAIL, Constants.ERROR_PATTERN_MAIL, ( input ) -> !Controls.checkPatternMail( input, 4, 51 ), scanner );

        userJDBC.insertUser( new User( null, newUsername, BCrypt.hashpw( newPassword, BCrypt.gensalt() ), Integer.parseInt( districtID ), mail ) );

        printService.print( Constants.OPERATION_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    }

    private static String enterNewUsername () throws SQLException
    {
        String newUsername;
        boolean checkUsername1 = false, checkUsername2 = false;

        do
        {
            printService.print( Constants.ENTER_NEW_USERNAME );
            newUsername = scanner.nextLine();
            checkUsername1 = Controls.isPresentUsername( newUsername );
            if ( checkUsername1 ) printService.println( Constants.USERNAME_NOT_AVAILABLE );
            checkUsername2 = Controls.checkPatternUsername( newUsername, 2, 21 );
            if ( checkUsername2 ) printService.println( Constants.ERROR_PATTERN_USERNAME );
        } while ( checkUsername1 || checkUsername2 );

        return newUsername;
    }

    private static String enterNewPassword () throws SQLException
    {
        char[] passwordChars;
        String newPassword;

        boolean checkPassword;
        do
        {   
            passwordChars = console.readPassword( Constants.ENTER_NEW_PASSWORD );
            newPassword = new String( passwordChars );
            checkPassword = Controls.checkPatternPassword( newPassword, 7, 26 );
            if ( !checkPassword ) printService.println( Constants.ERROR_PATTERN_PASSWORD );
        } while ( !checkPassword );

        return newPassword;
    }

}