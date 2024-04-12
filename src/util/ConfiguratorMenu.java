package util;

import java.sql.*;
import java.util.*;
import projectClass.*;

public class ConfiguratorMenu {
    
    public static void menu ( Scanner scanner, Configurator conf, Session session ) throws SQLException, Exception
    {
        String choice;
        ConversionFactors conversionFactors = new ConversionFactors();

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
                        caseTwo( scanner, conf );
                    break;

                case "3":
                        caseThree( scanner, conversionFactors );
                    break;

                case "4":
                        caseFour( conf, conversionFactors );
                    break;

                case "5":
                        caseFive( scanner );
                    break;

                case "6":
                        caseSix( scanner );
                    break;

                case "7":
                        caseSeven( scanner, conversionFactors );
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

    public static void caseThree ( Scanner scanner, ConversionFactors conversionFactors ) throws SQLException, Exception
    {
        ConversionFactors tmp_conversionFactors = new ConversionFactors();
        conversionFactors.populate();

        if ( conversionFactors.isComplete() )
        {
            caseSeven( scanner, conversionFactors );
            return;
        }

        do
        {
            tmp_conversionFactors.copy( conversionFactors );
            Util.clearConsole( Constants.TIME_SWITCH_MENU );
            System.out.println( "\n" + conversionFactors.toString() + "\n" );
            int index = Integer.parseInt( Util.insertWithCheck( Constants.ENTER_CHOICE_PAIR, Constants.INVALID_OPTION, ( input ) -> !( ( conversionFactors.getList().containsKey( Integer.parseInt( input ) ) && conversionFactors.getList().get( Integer.parseInt( input ) ).getValue() == null ) ), scanner ) );
            Double value = Double.parseDouble( Util.insertWithCheck( Constants.ENTER_VALUE_CONVERSION_FACTOR, Constants.OUT_OF_RANGE_ERROR, ( input ) -> ( ( Double.parseDouble( input ) < 0.5 ) || ( Double.parseDouble( input ) > 2.0 ) ), scanner) );

            tmp_conversionFactors.calculate( index, value );

            if ( tmp_conversionFactors.inRange() ) conversionFactors.copy( tmp_conversionFactors );
            else 
            {
                System.out.println( Constants.OUT_OF_RANGE_VALUE );
                Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            }
        } while ( !conversionFactors.isComplete() );

        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.println( "\n" + conversionFactors.toString() + "\n" );
        System.out.println( Constants.OPERATION_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    } 

    public static void caseFour ( Configurator conf, ConversionFactors toSave ) throws SQLException, Exception
    {
        if ( !toSave.isComplete() )
        {
            System.out.println( Constants.IMPOSSIBLE_SAVE_CF );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }
        conf.saveAll( toSave );
        System.out.println( Constants.SAVE_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    }

    public static void caseFive ( Scanner scanner ) throws SQLException, Exception
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
        Integer districtID = Integer.parseInt( scanner.nextLine() );

        if ( !Controls.isPresentDistrict( districtID ) || districtID == null )
        {
            System.out.println( Constants.NOT_EXIST_MESSAGE );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        District tmp = new District( districtID );
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        System.out.println( "\n" + tmp.getName() + ":\n" );
        System.out.println( tmp.printAllMunicipalities() );

        System.out.print( Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

    public static void caseSix ( Scanner scanner ) throws SQLException, Exception
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

    public static void caseSeven ( Scanner scanner, ConversionFactors conversionFactors ) throws Exception
    {
        conversionFactors.populate();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );

        System.out.print( "\n" + conversionFactors.toString() + "\n" );

        System.out.print( Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

}
