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

        try
        {
            Conn.openConnection();

            clearConsole( Constants.TIME_ZERO );

            String choice;
            do 
            {
                System.out.print( Constants.MAIN_MENU );
                choice = scanner.nextLine();

                switch ( choice ) 
                {
                    case "1":
                            caseOneMainMenu( scanner, console );
                        break;

                    case "2":
                            System.out.println( Constants.BYE_BYE_MESSAGE );
                        break;

                    case "":
                            Configurator conf = new Configurator( "user1", "password1" );
                            clearConsole( Constants.TIME_SWITCH_MENU );
                            configuratorMenu( scanner, conf );
                        break;

                    default:
                            System.out.println( Constants.INVALID_OPTION );
                            clearConsole( Constants.TIME_ERROR_MESSAGE );
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

    public static void clearConsole ( int millis ) throws Exception
    {
        Thread.sleep( millis );
        if ( System.getProperty("os.name").contains("Windows") )
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            new ProcessBuilder("clear").inheritIO().start().waitFor();
    }

    public static void caseOneMainMenu ( Scanner scanner, Console console ) throws SQLException, Exception
    {
        clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.print( Constants.LOGIN_SCREEN );

        System.out.print( Constants.ENTER_USERNAME );
        String username = scanner.nextLine();
        
        char[] passwordChars = console.readPassword( Constants.ENTER_PASSWORD );
        String password = new String( passwordChars );

        if ( !Configurator.login( username, password ) )
        {   
            System.out.println( Constants.LOGIN_ERROR );
            clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        Configurator conf = new Configurator( username, password );

        if ( conf.getFirstAccess() )
        {
            clearConsole( Constants.TIME_SWITCH_MENU );
            System.out.print( Constants.REGISTRATION_SCREEN );

            String newUsername, newPassword;
            boolean checkUsername1 = false, checkUsername2 = false;
            do
            {
                System.out.print( Constants.ENTER_NEW_USERNAME );
                newUsername = scanner.nextLine();
                checkUsername1 = Configurator.isPresentUsername( newUsername );
                if ( checkUsername1 ) System.out.println( Constants.USERNAME_NOT_AVAILABLE );
                checkUsername2 = Configurator.checkPatternUsername( newUsername );
                if ( !checkUsername2 ) System.out.println( Constants.ERROR_PATTERN_USERNAME );
            } while ( checkUsername1 || !checkUsername2 );

            boolean checkPassword;
            do
            {   
                passwordChars = console.readPassword( Constants.ENTER_NEW_PASSWORD );
                newPassword = new String( passwordChars );
                checkPassword = Configurator.checkPatternPassword( newPassword );
                if ( !checkPassword ) System.out.println( Constants.ERROR_PATTERN_PASSWORD );
            } while ( !checkPassword );
            
            conf.changeCredentials( newUsername, newPassword );
        }
        clearConsole( Constants.TIME_SWITCH_MENU );
        configuratorMenu( scanner, conf );
    }

    public static void configuratorMenu ( Scanner scanner, Configurator conf ) throws SQLException, Exception
    {
        String choice;
        
        do
        {
            System.out.print( Constants.CONFIGURATOR_MENU );
            choice = scanner.nextLine();

            switch ( choice )
            {
                case "1":
                        caseOneConfiguratorMenu( scanner, conf );
                    break;

                case "2":
                        caseTwoConfiguratorMenu(scanner, conf);
                    break;

                case "5":
                        caseFiveConfiguratorMenu( conf );
                    break;

                case "6":
                        caseSixConfiguratorMenu( scanner );
                    break;

                case "7":
                        caseSevenConfiguratorMenu( scanner );
                    break;

                case "9":
                        System.out.println( Constants.LOG_OUT );
                        clearConsole( Constants.TIME_LOGOUT );
                    break;

                default:
                        System.out.println( Constants.INVALID_OPTION );
                        clearConsole( Constants.TIME_ERROR_MESSAGE);
                    break;
            }
        } while ( !choice.equals("9") );
    }

    public static void caseOneConfiguratorMenu ( Scanner scanner, Configurator conf ) throws SQLException, Exception
    {
        String districName;
        do
        {
            System.out.print( Constants.ENTER_DISTRICT_NAME );
            districName = scanner.nextLine();
            if ( District.checkPatternName( districName ) )
            {
                System.out.println( Constants.ERROR_PATTERN_NAME );
                clearConsole( Constants.TIME_ERROR_MESSAGE );
            } 
        } while ( District.checkPatternName( districName ) );
        
        if ( District.isPresentDistrict( districName ) )
        {
            System.out.println( Constants.DISTRICT_NAME_ALREADY_PRESENT );
            clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        } 
        District newDistrict = conf.createDistrict( districName );

        String municipalityName, continueInsert = "n";
        do
        {
            System.out.print( Constants.ENTER_MUNICIPALITY );
            municipalityName = scanner.nextLine();
            if ( !Municipality.existMunicipality( municipalityName ) ) 
            {
                System.out.println( Constants.NOT_EXIST_MESSAGE );
                continue;
            }
            Municipality municipalityToAdd = new Municipality( municipalityName );

            if ( newDistrict.isPresentMunicipalityInDistrict( municipalityToAdd ) )
            {
                System.out.println( Constants.MUNICIPALITY_NAME_ALREADY_PRESENT );
                continue;
            }
            newDistrict.addMunicipality( municipalityToAdd );
            System.out.println( Constants.ADDED_SUCCESFULL_MESSAGE );

            do
            {
                System.out.print( Constants.END_ADD_MESSAGE );
                continueInsert = scanner.nextLine();
                if ( !continueInsert.equals("n") && !continueInsert.equals("y") ) System.out.println( Constants.INVALID_OPTION );
            } while ( !continueInsert.equals("y") && !continueInsert.equals("n") );
            
        } while ( !continueInsert.equals("y") );

        System.out.println( Constants.OPERATION_COMPLETED );
        clearConsole( Constants.TIME_MESSAGE );
    }

    public static void caseTwoConfiguratorMenu ( Scanner scanner, Configurator conf ) throws SQLException, Exception
    {
        clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.print( Constants.HIERARCHY_SCREEN );

        boolean isRoot = true, firstIteration = false;
        Integer hierarchyID = null;
        String insertContinue = "n";

        do
        {
            if ( firstIteration )
            {
                clearConsole( Constants.TIME_SWITCH_MENU );
                System.out.println( "\n" + Category.printHierarchy( hierarchyID ) );
                System.out.println( Constants.LINE );
            } 

            String categoryName;
            do
            {
                System.out.print( Constants.ENTER_CATEGORY_NAME );
                categoryName = scanner.nextLine();
                if ( Category.checkPatternName( categoryName ) ) System.out.print( Constants.ERROR_PATTERN_NAME );
            } while ( Category.checkPatternName( categoryName ) );

            if ( !firstIteration && Category.isPresentRootCategory( categoryName ) )
            {
                System.out.print( Constants.ROOT_CATEGORY_ALREADY_PRESENT );
                clearConsole( Constants.TIME_ERROR_MESSAGE );
                return;
            }
            if ( firstIteration && Category.isPresentInternalCategory( categoryName, hierarchyID ) )
            {
                System.out.print( Constants.INTERNAL_CATEGORY_ALREADY_PRESENT );
                clearConsole( Constants.TIME_ERROR_MESSAGE );
                continue;
            }

            String leafCategory = "n";
            if ( firstIteration )
            {
                do
                {
                    System.out.print( Constants.LEAF_CATEGORY_MESSAGE );
                    leafCategory = scanner.nextLine();
                    if ( !leafCategory.equals("n") && !leafCategory.equals("y") ) System.out.println( Constants.INVALID_OPTION );
                } while ( !leafCategory.equals("y") && !leafCategory.equals("n") );
            }
            firstIteration = true;

            String field = null;
            String description = null;
            if ( leafCategory.equals( "n" ) )
            {
                do
                {
                    System.out.print( Constants.ENTER_FIELD );
                    field = scanner.nextLine();
                    if ( Category.checkPatternField( field ) ) System.out.println( Constants.ERROR_PATTERN_FIELD );
                } while ( Category.checkPatternField( field ) );
                do
                {
                    System.out.print( Constants.ENTER_DESCRIPTION );
                    description = scanner.nextLine();
                    if ( Category.checkPatternDescription( description ) ) System.out.println( Constants.ERROR_PATTERN_DESCRIPTION );
                } while ( Category.checkPatternDescription( description ) );
            }

            Category newCategory = conf.createCategory( categoryName, field, description, isRoot, hierarchyID);

            if ( isRoot ) 
            {
                isRoot = false;
                hierarchyID = newCategory.getHierarchyID();
                continue;
            }

            int parentID;
            do 
            {
                System.out.print( Constants.CATEGORY_LIST + Category.printCategoriesList( hierarchyID ) );
                System.out.print( Constants.ENTER_DAD_MESSAGE );
                parentID = Integer.parseInt( scanner.nextLine() );
                if ( !Category.isPresentInternalCategory( parentID, hierarchyID ) ) System.out.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            } while ( !Category.isPresentInternalCategory( parentID, hierarchyID ) );
            
            String fieldType;
            do
            {
                System.out.print( Constants.ENTER_FIELD_TYPE );
                fieldType = scanner.nextLine();
                if ( Category.checkPatternField( fieldType ) ) System.out.println( Constants.ERROR_PATTERN_FIELD );
            } while ( Category.checkPatternField( fieldType ) );            

            newCategory.createRelationship( parentID, fieldType );

            if ( leafCategory.equals( "n" ) ) continue;

            do
            {
                System.out.print( "\n" + Constants.END_ADD_MESSAGE );
                insertContinue = scanner.nextLine();
                if ( !insertContinue.equals("n") && !insertContinue.equals("y") ) System.out.println( Constants.INVALID_OPTION );
            } while ( !insertContinue.equals("y") && !insertContinue.equals("n") );

        } while( insertContinue.equals( "n" ) );

        System.out.println( Constants.OPERATION_COMPLETED );
        clearConsole( Constants.TIME_MESSAGE );
    }

    public static void caseFiveConfiguratorMenu ( Configurator conf ) throws SQLException, Exception
    {
        conf.saveAll();
        System.out.println( Constants.SAVE_COMPLETED );
        clearConsole( Constants.TIME_MESSAGE );
    }

    public static void caseSixConfiguratorMenu ( Scanner scanner ) throws SQLException, Exception
    {
        clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.print( Constants.DISTRICT_LIST );

        if ( District.printAll().equals( "" ) )
        {
            System.out.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        System.out.println( District.printAll() );
        System.out.print( Constants.ENTER_DISTRICT_ID );
        String districtID = scanner.nextLine();

        if ( !District.isPresentDistrict( Integer.parseInt( districtID ) ) || districtID.equals( "" ) )
        {
            System.out.println( Constants.NOT_EXIST_MESSAGE );
            clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        String query = "SELECT name FROM districts where id = ? " +
                       "UNION " +
                       "SELECT name FROM tmp_districts where id = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( districtID );
        parameters.add( districtID );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        District tmp = new District( rs.getString(1) );
        clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.println( "\n" + tmp.getName() + ":\n" );
        System.out.println( tmp.printAllMunicipalities() );

        System.out.print( Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

    public static void caseSevenConfiguratorMenu ( Scanner scanner ) throws SQLException, Exception
    {
        clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.print( Constants.HIERARCHY_LIST );

        if ( Category.printAllRoot().equals( "" ) )
        {
            System.out.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        System.out.println( Category.printAllRoot() );
        System.out.print( Constants.ENTER_HIERARCHY_ID );
        String hierarchyID = scanner.nextLine();

        if ( !Category.isPresentRootCategory( Integer.parseInt( hierarchyID ) ) || hierarchyID.equals( "" ) )
        {
            System.out.println( Constants.NOT_EXIST_MESSAGE );
            clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.println( "\n" + Category.printHierarchy( Integer.parseInt( hierarchyID ) ) );

        System.out.print( "\n" + Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

}