package util;

import java.sql.SQLException;
import java.util.Scanner;
import projectClass.*;

public class ConfiguratorMenu {
    
    public static void menu ( Scanner scanner, Configurator conf, Session session ) throws SQLException, Exception
    {
        String choice;
        
        do
        {
            System.out.print( Constants.CONFIGURATOR_MENU );
            choice = scanner.nextLine();

            switch ( choice )
            {
                case "1":
                        caseOne( scanner, conf );
                    break;

                case "2":
                        caseTwo(scanner, conf);
                    break;

                case "5":
                        caseFive( conf );
                    break;

                case "6":
                        caseSix( scanner );
                    break;

                case "7":
                        caseSeven( scanner );
                    break;

                case "9":
                        session.logout();
                        System.out.println( Constants.LOG_OUT );
                        Util.clearConsole( Constants.TIME_LOGOUT );
                    break;

                default:
                        System.out.println( Constants.INVALID_OPTION );
                        Util.clearConsole( Constants.TIME_ERROR_MESSAGE);
                    break;
            }
        } while ( !choice.equals("9") );
    }

    public static void caseOne ( Scanner scanner, Configurator conf ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        String districName = Util.insertWithCheck( Constants.ENTER_DISTRICT_NAME, Constants.ERROR_PATTERN_NAME, ( input ) -> Controls.checkPattern( input, 0, 50 ), scanner );
        
        if ( Controls.isPresentDistrict( districName ) )
        {
            System.out.println( Constants.DISTRICT_NAME_ALREADY_PRESENT );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        } 
        District newDistrict = conf.createDistrict( districName );

        String municipalityName, continueInsert = "n";
        do
        {
            System.out.print( Constants.ENTER_MUNICIPALITY );
            municipalityName = scanner.nextLine();
            if ( !Controls.existMunicipality( municipalityName ) ) 
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

            continueInsert = Util.insertWithCheck( Constants.END_ADD_MESSAGE, Constants.INVALID_OPTION, ( input ) -> !input.equals("n") && !input.equals("y"), scanner);
            
        } while ( !continueInsert.equals("y") );

        System.out.println( Constants.OPERATION_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    }

    public static void caseTwo ( Scanner scanner, Configurator conf ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.print( Constants.HIERARCHY_SCREEN );

        boolean isRoot = true, notFirstIteration = false;
        String insertContinue = "n";
        Category root = null;

        do
        {
            if ( notFirstIteration )
            {
                Util.clearConsole( Constants.TIME_SWITCH_MENU );
                System.out.println( "\n" + Printer.printHierarchy( root.getHierarchyID() ) );
                System.out.println( Constants.LINE );
            } 

            String categoryName = Util.insertWithCheck( Constants.ENTER_CATEGORY_NAME, Constants.ERROR_PATTERN_NAME, ( input ) -> Controls.checkPattern( input, 0, 50 ), scanner );

            if ( !notFirstIteration && Controls.isPresentRootCategory( categoryName ) )
            {
                System.out.print( Constants.ROOT_CATEGORY_ALREADY_PRESENT );
                Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
                return;
            }
            if ( notFirstIteration && root.isPresentInternalCategory( categoryName ) )
            {
                System.out.print( Constants.INTERNAL_CATEGORY_ALREADY_PRESENT );
                Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
                continue;
            }

            String leafCategory = "n";
            if ( notFirstIteration ) leafCategory = Util.insertWithCheck( Constants.LEAF_CATEGORY_MESSAGE, Constants.INVALID_OPTION, ( input ) -> !input.equals("n") && !input.equals("y"), scanner );
            notFirstIteration = true;

            String field = null;
            String description = null;
            if ( leafCategory.equals( "n" ) )
            {
                field = Util.insertWithCheck( Constants.ENTER_FIELD, Constants.ERROR_PATTERN_FIELD , ( input ) -> Controls.checkPattern( input, 0, 25 ), scanner );
                description = Util.insertWithCheck( Constants.ENTER_DESCRIPTION, Constants.ERROR_PATTERN_DESCRIPTION, ( input ) -> Controls.checkPattern( input, -1, 100 ), scanner );
            }

            Category newCategory = isRoot ? ( conf.createCategory( categoryName, field, description, isRoot, null ) ) : ( conf.createCategory( categoryName, field, description, isRoot, root.getHierarchyID() ) );

            if ( isRoot ) 
            {
                isRoot = false;
                root = newCategory;
                continue;
            }

            int parentID;
            do 
            {
                System.out.print( Constants.CATEGORY_LIST + Printer.printCategoriesList( root.getHierarchyID() ) );
                System.out.print( Constants.ENTER_DAD_MESSAGE );
                parentID = Integer.parseInt( scanner.nextLine() );
                if ( !root.isValidParentID( parentID ) ) System.out.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            } while ( !root.isValidParentID( parentID ) );
            
            String fieldType = Util.insertWithCheck( Constants.ENTER_FIELD_TYPE, Constants.ERROR_PATTERN_FIELD, ( input ) -> Controls.checkPattern( input, 0, 25 ), scanner );

            newCategory.createRelationship( parentID, fieldType );

            if ( leafCategory.equals( "n" ) ) continue;

            insertContinue = Util.insertWithCheck( "\n" + Constants.END_ADD_MESSAGE, Constants.INVALID_OPTION, ( input ) -> !input.equals("n") && !input.equals("y"), scanner );

        } while( insertContinue.equals( "n" ) );

        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.print( "\n" + Printer.printHierarchy( root.getHierarchyID() ) );
        System.out.println( Constants.OPERATION_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    }

    public static void caseFive ( Configurator conf ) throws SQLException, Exception
    {
        conf.saveAll();
        System.out.println( Constants.SAVE_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    }

    public static void caseSix ( Scanner scanner ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.print( Constants.DISTRICT_LIST );

        if ( Printer.printAllDistricts().equals( "" ) )
        {
            System.out.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        System.out.println( Printer.printAllDistricts() );
        System.out.print( Constants.ENTER_DISTRICT_TO_VIEW );
        String districtName = scanner.nextLine();

        if ( !Controls.isPresentDistrict( districtName ) || districtName.equals( "" ) )
        {
            System.out.println( Constants.NOT_EXIST_MESSAGE );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        District tmp = new District( districtName );
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.println( "\n" + tmp.getName() + ":\n" );
        System.out.println( tmp.printAllMunicipalities() );

        System.out.print( Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

    public static void caseSeven ( Scanner scanner ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.print( Constants.HIERARCHY_LIST );

        if ( Printer.printAllRoot().equals( "" ) )
        {
            System.out.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        System.out.println( Printer.printAllRoot() );
        System.out.print( Constants.ENTER_HIERARCHY_ID );
        String hierarchyID = scanner.nextLine();

        if ( !Controls.isPresentRootCategory( Integer.parseInt( hierarchyID ) ) || hierarchyID.equals( "" ) )
        {
            System.out.println( Constants.NOT_EXIST_MESSAGE );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.println( "\n" + Printer.printHierarchy( Integer.parseInt( hierarchyID ) ) );

        System.out.print( "\n" + Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

}
