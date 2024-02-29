import java.util.Scanner;
//import org.mindrot.jbcrypt.BCrypt;
import projectClass.*;
import util.*;
import java.io.Console;
import java.sql.SQLException;

public class Main 
{
    public static void main(String[] args) 
    {
        //System.out.println( BCrypt.hashpw( "password1", BCrypt.gensalt() ) );
        //System.out.println( BCrypt.hashpw( "password2", BCrypt.gensalt() ) );
        //System.out.println( BCrypt.hashpw( "password3", BCrypt.gensalt() ) );
        try
        {
            Scanner scanner = new Scanner( System.in );
            Console console = System.console();
            Conn.openConnection();

            clearConsole( Costants.TIME_ZERO );

            String choice;
            do 
            {
                System.out.print( Costants.MAIN_MENU );
                choice = scanner.nextLine();

                switch ( choice ) 
                {
                    case "1":
                            caseOneMainMenu( scanner, console );
                        break;

                    case "2":
                            System.out.println( Costants.BYE_BYE_MESSAGE );
                        break;

                    default:
                            System.out.println( Costants.INVALID_OPTION );
                            clearConsole( Costants.TIME_ERROR_MESSAGE );
                        break;
                }
            } while ( !choice.equals("2") );
            
            Conn.closeConnection();
            scanner.close();
        }
        catch ( SQLException e ) {
            System.out.println("Errore database: ");
            e.printStackTrace();
        }
        catch ( Exception e ) {
            System.out.println( "Errore generico:" );
            e.printStackTrace();
        }
    }

    public static void clearConsole ( int millis ) throws Exception
    {
        Thread.sleep(millis);
        if ( System.getProperty("os.name").contains("Windows") )
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            new ProcessBuilder("clear").inheritIO().start().waitFor();
    }

    public static void caseOneMainMenu ( Scanner scanner, Console console ) throws SQLException, Exception
    {
        clearConsole( Costants.TIME_SWITCH_MENU );
        System.out.print( Costants.LOGIN_SCREEN );

        System.out.print( Costants.ENTER_USERNAME );
        String username = scanner.nextLine();
        
        char[] passwordChars = console.readPassword( Costants.ENTER_PASSWORD );
        String password = new String( passwordChars );

        if ( !Configurator.login( username, password ) )
        {   
            System.out.println( Costants.LOGIN_ERROR );
            clearConsole( Costants.TIME_ERROR_MESSAGE );
            return;
        }

        Configurator conf = new Configurator( username, password );

        if ( conf.getFirstAccess() )
        {
            clearConsole( Costants.TIME_SWITCH_MENU );
            System.out.print( Costants.REGISTRATION_SCREEN );

            String newUsername, newPassword;
            boolean checkUsername1 = false, checkUsername2 = false;
            do
            {
                System.out.print( Costants.ENTER_NEW_USERNAME );
                newUsername = scanner.nextLine();
                checkUsername1 = Configurator.isPresentUsername( newUsername );
                if ( checkUsername1 ) System.out.println( Costants.USERNAME_NOT_AVAILABLE );
                checkUsername2 = Configurator.checkPatternUsername( newUsername );
                if ( !checkUsername2 ) System.out.println( Costants.ERROR_PATTERN_USERNAME );
            } while ( checkUsername1 || !checkUsername2 );

            boolean checkPassword;
            do
            {   
                passwordChars = console.readPassword( Costants.ENTER_NEW_PASSWORD );
                newPassword = new String( passwordChars );
                checkPassword = Configurator.checkPatternPassword( newPassword );
                if ( !checkPassword ) System.out.println( Costants.ERROR_PATTERN_PASSWORD );
            } while ( !checkPassword );
            
            conf.changeCredentials( newUsername, newPassword );
        }
        clearConsole( Costants.TIME_SWITCH_MENU );
        configuratorMenu( scanner, conf );
    }

    public static void configuratorMenu ( Scanner scanner, Configurator conf ) throws SQLException, Exception
    {
        String choice;
        
        do
        {
            System.out.print( Costants.CONFIGURATOR_MENU );
            choice = scanner.nextLine();

            switch ( choice )
            {
                case "1":
                        caseOneConfiguratorMenu( scanner, conf );
                    break;

                case "2":
                    break;

                case "9":
                        clearConsole( Costants.TIME_LOGOUT );
                    break;

                default:
                        System.out.println( Costants.INVALID_OPTION );
                        clearConsole( Costants.TIME_ERROR_MESSAGE );
                    break;
            }
        } while ( !choice.equals("9") );
    }

    public static void caseOneConfiguratorMenu ( Scanner scanner, Configurator conf ) throws SQLException, Exception
    {
        String districName;
        do
        {
            System.out.print( Costants.ENTER_DISTRICT_NAME );
            districName = scanner.nextLine();
            if ( District.checkPatternName( districName ) )
            {
                System.out.println( Costants.ERROR_PATTERN_DISTRICT_NAME );
                clearConsole( Costants.TIME_ERROR_MESSAGE );
            } 
        } while ( District.checkPatternName( districName ) );
        
        if ( District.isPresentDistrict( districName ) )
        {
            System.out.println( Costants.DISTRICT_NAME_ALREADY_PRESENT );
            clearConsole( Costants.TIME_ERROR_MESSAGE );
            return;
        } 
        District newDistrict = conf.createDistrict( districName );

        String municipalityName, continueInsert = "n";
        do
        {
            System.out.print( Costants.ENTER_MUNICIPALITY );
            municipalityName = scanner.nextLine();
            if ( !Municipality.existMunicipality( municipalityName ) ) 
            {
                System.out.println( Costants.NOT_EXIST_MESSAGE );
                continue;
            }
            Municipality municipalityToAdd = new Municipality( municipalityName );

            if ( newDistrict.isPresentMunicipalityInDistrict( municipalityToAdd ) )
            {
                System.out.println( Costants.MUNICIPALITY_NAME_ALREADY_PRESENT );
                continue;
            }
            newDistrict.addMunicipality( municipalityToAdd );
            System.out.println( Costants.ADDED_SUCCESFULL_MESSAGE );

            System.out.print( Costants.END_ADD_MESSAGE );
            continueInsert = scanner.nextLine();
        } while ( !continueInsert.equals("y") );

        System.out.println( Costants.OPERATION_COMPLETED );
        clearConsole( Costants.TIME_ERROR_MESSAGE );
    }

}