package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class ConfiguratorMenu {
    
    private static PrintService printService = new PrintService();
    private static MunicipalityJDBC municipalityJDBC = new MunicipalityJDBCImpl();
    private static DistrictJDBC districtJDBC = new DistrictJDBCImpl();
    private static CategoryJDBC categoryJDBC = new CategoryJDBCImpl();
    private static ConversionFactors conversionFactors = new ConversionFactors();

    public static void menu ( Scanner scanner, Configurator conf, Session session ) throws SQLException, Exception
    {
        String choice;
        conversionFactors.populate();

        do
        {
            printService.print( Constants.CONFIGURATOR_MENU );
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
                        caseThree( scanner );
                    break;

                case "4":
                        caseFour( conf );
                    break;

                case "5":
                        caseFive( scanner );
                    break;

                case "6":
                        caseSix( scanner );
                    break;

                case "7":
                        caseSeven( scanner );
                    break;

                case "8":
                        caseEight( scanner );
                    break;

                case "9":
                        session.logout();
                        conversionFactors = new ConversionFactors();
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

    public static void caseOne ( Scanner scanner, Configurator conf ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        String districName = Util.insertWithCheck( Constants.ENTER_DISTRICT_NAME, Constants.ERROR_PATTERN_NAME, ( input ) -> Controls.checkPattern( input, 0, 50 ), scanner );
        
        if ( Controls.isPresentDistrict( districName ) )
        {
            printService.println( Constants.DISTRICT_NAME_ALREADY_PRESENT );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        } 
        District newDistrict = conf.createDistrict( districName );

        String municipalityName, continueInsert = Constants.NO_MESSAGE;
        do
        {
            printService.print( Constants.ENTER_MUNICIPALITY );
            municipalityName = scanner.nextLine();
            if ( !Controls.existMunicipality( municipalityName ) ) 
            {
                printService.println( Constants.NOT_EXIST_MESSAGE );
                continue;
            }

            Municipality municipalityToAdd = municipalityJDBC.getMunicipalityByName( municipalityName );

            if ( newDistrict.isPresentMunicipalityInDistrict( municipalityToAdd ) )
            {
                printService.println( Constants.MUNICIPALITY_NAME_ALREADY_PRESENT );
                continue;
            }
            newDistrict.addMunicipality( municipalityToAdd );
            printService.println( Constants.ADDED_SUCCESFULL_MESSAGE );

            continueInsert = Util.insertWithCheck( Constants.END_ADD_MESSAGE, Constants.INVALID_OPTION, ( input ) -> !input.equals(Constants.NO_MESSAGE) && !input.equals(Constants.YES_MESSAGE), scanner );
            
        } while ( !continueInsert.equals(Constants.YES_MESSAGE) );

        printService.println( Constants.OPERATION_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    }

    public static void caseTwo ( Scanner scanner, Configurator conf ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.HIERARCHY_SCREEN );

        boolean isRoot = true, notFirstIteration = false;
        String insertContinue = Constants.NO_MESSAGE;
        Category root = null;

        do
        {
            if ( notFirstIteration )
            {
                Util.clearConsole( Constants.TIME_SWITCH_MENU );
                printService.println( "\n" );
                printService.printHierarchy( root.getHierarchyID() );
                printService.println( Constants.LINE );
            } 

            String categoryName = Util.insertWithCheck( Constants.ENTER_CATEGORY_NAME, Constants.ERROR_PATTERN_NAME, ( input ) -> Controls.checkPattern( input, 0, 50 ), scanner );

            if ( !notFirstIteration && Controls.isPresentRootCategory( categoryName ) )
            {
                printService.print( Constants.ROOT_CATEGORY_ALREADY_PRESENT );
                Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
                return;
            }
            if ( notFirstIteration && root.isPresentInternalCategory( categoryName ) )
            {
                printService.print( Constants.INTERNAL_CATEGORY_ALREADY_PRESENT );
                Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
                continue;
            }

            String leafCategory = Constants.NO_MESSAGE;
            if ( notFirstIteration ) leafCategory = Util.insertWithCheck( Constants.LEAF_CATEGORY_MESSAGE, Constants.INVALID_OPTION, ( input ) -> !input.equals(Constants.NO_MESSAGE) && !input.equals(Constants.YES_MESSAGE), scanner );

            String field = null;
            String description = null;
            if ( leafCategory.equals( Constants.NO_MESSAGE ) )
                field = Util.insertWithCheck( Constants.ENTER_FIELD, Constants.ERROR_PATTERN_FIELD , ( input ) -> Controls.checkPattern( input, 0, 25 ), scanner );
            if ( notFirstIteration ) description = Util.insertWithCheck( Constants.ENTER_DESCRIPTION, Constants.ERROR_PATTERN_DESCRIPTION, ( input ) -> Controls.checkPattern( input, -1, 100 ), scanner );
            Category newCategory = isRoot ? ( conf.createCategory( categoryName, field, description, isRoot, null ) ) : ( conf.createCategory( categoryName, field, description, isRoot, root.getHierarchyID() ) );

            notFirstIteration = true;

            if ( isRoot ) 
            {
                isRoot = false;
                root = newCategory;
                continue;
            }

            String parentID;
            do 
            {
                printService.print( "\n" + Constants.CATEGORY_LIST );
                printService.printCategoriesList( root.getHierarchyID() );
                printService.print( Constants.ENTER_DAD_MESSAGE );
                parentID = scanner.nextLine();
                if ( parentID.isEmpty() || !Controls.isInt( parentID ) || !root.isValidParentID( Integer.parseInt( parentID ) ) ) printService.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            } while ( parentID.isEmpty() || !Controls.isInt( parentID ) || !root.isValidParentID( Integer.parseInt( parentID ) ) );
            
            String fieldType = Util.insertWithCheck( Constants.ENTER_FIELD_TYPE, Constants.ERROR_PATTERN_FIELD, ( input ) -> Controls.checkPattern( input, 0, 25 ), scanner );

            newCategory.createRelationship( Integer.parseInt( parentID ), fieldType );

            if ( leafCategory.equals( Constants.NO_MESSAGE ) || categoryJDBC.getCategoriesWithoutChild().size() > 0 ) continue;

            insertContinue = Util.insertWithCheck( "\n" + Constants.END_ADD_MESSAGE, Constants.INVALID_OPTION, ( input ) -> !input.equals(Constants.NO_MESSAGE) && !input.equals(Constants.YES_MESSAGE), scanner );

        } while( insertContinue.equals( Constants.NO_MESSAGE ) );

        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( "\n" );
        printService.printHierarchy( root.getHierarchyID() );
        printService.println( Constants.OPERATION_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    }

    public static void caseThree ( Scanner scanner ) throws SQLException, Exception
    {
        conversionFactors.populate();

        if ( conversionFactors.isComplete() )
        {
            caseSeven( scanner );
            return;
        }

        do
        {
            Util.clearConsole( Constants.TIME_SWITCH_MENU );
            printService.print( "\n" );
            printService.printConversionFactors( conversionFactors ); 
            printService.print("\n" );

            int index = Integer.parseInt( Util.insertWithCheck( Constants.ENTER_CHOICE_PAIR, Constants.INVALID_OPTION, ( input ) -> !( !input.equals("") && Controls.isInt( input ) && ( conversionFactors.getList().containsKey( Integer.parseInt( input ) ) && conversionFactors.getList().get( Integer.parseInt( input ) ).getValue() == null ) ), scanner ) );
            double[] range = conversionFactors.calculateRange( index );
            String rangeToPrint = "[ "+range[0]+", "+range[1]+" ]: ";
            Double value = Double.parseDouble( Util.insertWithCheck( Constants.ENTER_VALUE_CONVERSION_FACTOR + rangeToPrint, Constants.OUT_OF_RANGE_ERROR, ( input ) -> ( input.equals("") || !Controls.isDouble( input ) || ( Double.parseDouble( input ) < range[0] ) || ( Double.parseDouble( input ) > range[1] ) ), scanner ) );

            conversionFactors.calculate( index, value );
        } while ( !conversionFactors.isComplete() );

        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( "\n" );
        printService.printConversionFactors( conversionFactors ); 
        printService.print( "\n" );
        printService.println( Constants.OPERATION_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    } 

    public static void caseFour ( Configurator conf ) throws SQLException, Exception
    {
        conversionFactors.populate();
        if ( !conversionFactors.isComplete() )
        {
            printService.println( Constants.IMPOSSIBLE_SAVE_CF );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }
        conf.saveAll( conversionFactors );
        printService.println( Constants.SAVE_COMPLETED );
        Util.clearConsole( Constants.TIME_MESSAGE );
    }

    public static void caseFive ( Scanner scanner ) throws SQLException, Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.DISTRICT_LIST );

        if ( districtJDBC.getAllDistricts().isEmpty() )
        {
            printService.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        printService.printAllDistricts();
        printService.print( Constants.ENTER_DISTRICT_TO_VIEW );
        String districtID = scanner.nextLine();

        if ( districtID.isEmpty() || !Controls.isInt( districtID ) || !Controls.isPresentDistrict( Integer.parseInt( districtID ) ) )
        {
            printService.println( Constants.NOT_EXIST_MESSAGE );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        District tmp = districtJDBC.getDistrictByID( Integer.parseInt( districtID ) );
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.println( "\n" + tmp.getName() + ":\n" );
        printService.printAllMunicipalitiesOfDistrict( tmp );

        printService.print( Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

    public static void caseSix ( Scanner scanner ) throws SQLException, Exception
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
        printService.println( "\n" );
        printService.printHierarchy( Integer.parseInt( hierarchyID ) );

        printService.print( "\n" + Constants.ENTER_CATEGORY_ID );
        String categoryID = scanner.nextLine();

        if ( categoryID.isEmpty() || !Controls.isInt( categoryID ) || ( categoryJDBC.getCategoryByID( Integer.parseInt( categoryID ) ) ) == null || ( categoryJDBC.getCategoryByID( Integer.parseInt( categoryID ) ) ).getHierarchyID() != Integer.parseInt( hierarchyID ) )
        {
            printService.println( Constants.NOT_EXIST_MESSAGE );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        printService.println( "\n" );
        printService.printInfoCategory( Integer.parseInt( categoryID ) );

        printService.print( "\n" + Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

    public static void caseSeven ( Scanner scanner ) throws Exception
    {
        conversionFactors.populate();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );

        printService.print( "\n" );
        printService.printConversionFactors( conversionFactors ); 
        printService.print( "\n" );

        printService.print( Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

    public static void caseEight ( Scanner scanner ) throws Exception
    {
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.print( Constants.LEAF_CATEGORY_LIST );

        if ( categoryJDBC.getAllLeaf().isEmpty() )
        {
            printService.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        printService.printAllLeafCategory();
        printService.print( Constants.ENTER_CATEGORY_ID );
        String categoryID = scanner.nextLine();

        if ( categoryID.isEmpty() || !Controls.isInt( categoryID ) || ( categoryJDBC.getCategoryByID( Integer.parseInt( categoryID ) ) ) == null || !( categoryJDBC.getCategoryByID( Integer.parseInt( categoryID ) ) ).isLeaf() )
        {
            printService.println( Constants.NOT_EXIST_MESSAGE );
            Util.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        printService.println( "\n" );
        printService.printConversionFactorsByLeaf( Integer.parseInt( categoryID ), conversionFactors );

        printService.print( "\n" + Constants.ENTER_TO_EXIT );
        scanner.nextLine();
        Util.clearConsole( Constants.TIME_SWITCH_MENU );
        return;
    }

}