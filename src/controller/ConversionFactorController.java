package controller;

import java.sql.*;
import java.util.*;
import java.util.Map.*;
import model.*;
import util.*;
import view.*;

public class ConversionFactorController extends Controller {

    private ConversionFactorView conversionFactorView;
    private ConversionFactors conversionFactors;
    private ConversionFactorsJDBC conversionFactorsJDBC;
    private CategoryController categoryController;

    public ConversionFactorController ( ConversionFactorView conversionFactorView, ConversionFactorsJDBC conversionFactorsJDBC, CategoryController categoryController ) 
    {
        super( conversionFactorView );
        this.conversionFactorView = conversionFactorView;
        this.conversionFactors = new ConversionFactors();
        this.conversionFactorsJDBC = conversionFactorsJDBC;
        this.categoryController = categoryController;
    }

    public ConversionFactorsJDBC getConversionFactorsJDBC () 
    {
        return this.conversionFactorsJDBC;
    }

    public ConversionFactors getConversionFactors ()
    {
        return this.conversionFactors;
    }

    public void resetConversionFactors ()
    {
        this.conversionFactors = new ConversionFactors();
    }

    public void listAll () throws SQLException
    {
        String COLOR, rootLeaf1, rootLeaf2;

        conversionFactorView.print( "\n" );

        for ( Entry<Integer, ConversionFactor> entry : this.conversionFactors.getList().entrySet() )
        {
            rootLeaf1 = ( categoryController.getCategoryJDBC().getNumberOfEqualsCategories( entry.getValue().getLeaf_1() ) > 1 ) ? ( "  [ " + categoryController.getCategoryJDBC().getRootByLeaf( entry.getValue().getLeaf_1() ).getName() + " ]  " ) : "";
            rootLeaf2 = ( categoryController.getCategoryJDBC().getNumberOfEqualsCategories( entry.getValue().getLeaf_2() ) > 1 ) ? ( "  [ " + categoryController.getCategoryJDBC().getRootByLeaf( entry.getValue().getLeaf_2() ).getName() + " ]  " ) : "";

            COLOR = entry.getValue().getValue() == null ? Constants.RED : Constants.BOLD + Constants.GREEN;

            conversionFactorView.println( " " + entry.getKey() + ". " + super.padRight( Integer.toString( entry.getKey() ), 5 ) + entry.getValue().getLeaf_1().getName() + rootLeaf1 + super.padRight( entry.getValue().getLeaf_1().getName() + rootLeaf1, 70 ) + "-->\t\t" + entry.getValue().getLeaf_2().getName() + rootLeaf2 + super.padRight( entry.getValue().getLeaf_2().getName() + rootLeaf2, 70 ) + ": " + COLOR + String.format( java.util.Locale.US, "%.4f", entry.getValue().getValue() ) + Constants.RESET );
        }

        conversionFactorView.print( "\n" );
    }

    public void listConversionFactorsByLeaf ( Category leaf ) throws SQLException
    {
        conversionFactors.populate();

        for ( Entry<Integer, ConversionFactor> entry : this.conversionFactors.getList().entrySet() )
            if ( entry.getValue().getLeaf_1().getID() == leaf.getID() || entry.getValue().getLeaf_2().getID() == leaf.getID() )
                this.printConversionFactor( entry.getValue() );
    }

    public void listConversionFactorsOfLeaf () throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorView.print( Constants.LEAF_CATEGORY_LIST );

        if ( categoryController.getCategoryJDBC().getAllLeaf().isEmpty() )
        {
            conversionFactorView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        categoryController.listAllLeafs();
        int leafID = categoryController.enterLeafID();
        if ( leafID == 0 ) return;

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorView.println( Constants.CONVERSION_FACTORS_LIST );

        this.listConversionFactorsByLeaf( categoryController.getCategoryJDBC().getCategoryByID( leafID ) );

        conversionFactorView.enterString( "\n" + Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public int enterID () throws SQLException
    {
        int ID = 0;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                ID = conversionFactorView.enterInt( Constants.ENTER_CHOICE_PAIR );
                if ( !conversionFactors.getList().containsKey( ID ) || !( conversionFactors.getList().get( ID ).getValue() == null ) ) conversionFactorView.print( "\n" + Constants.NOT_EXIST_MESSAGE );
            }
            catch ( InputMismatchException e )
            {
                conversionFactorView.print( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || !conversionFactors.getList().containsKey( ID ) || !( conversionFactors.getList().get( ID ).getValue() == null ) );

        return ID;
    }

    public Double enterValue ( double[] range )
    {
        Double value = null;

        boolean hasExceptionOccured; 
        do
        {
            hasExceptionOccured = false;
            try
            {
                String rangeToPrint = "[ " + range[ 0 ] + ", " + range[ 1 ] + " ]: ";
                value = conversionFactorView.enterDouble( Constants.ENTER_VALUE_CONVERSION_FACTOR + rangeToPrint );
                if ( value < range[ 0 ] || value > range[ 1 ] ) conversionFactorView.print( Constants.OUT_OF_RANGE_ERROR );
            }
            catch ( InputMismatchException e )
            {
                conversionFactorView.println( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || value < range[ 0 ] || value > range[ 1 ] );

        return value;
    }

    public void listAllConversionFactors () throws SQLException
    {
        conversionFactors.populate();

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorView.print( Constants.CONVERSION_FACTORS_LIST );

        if ( conversionFactors.getList().isEmpty() )
        {
            conversionFactorView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        this.listAll();

        conversionFactorView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public void enterConversionFactors () throws SQLException
    {
        conversionFactors.populate();

        if ( conversionFactors.isComplete() )
        {
            this.listAllConversionFactors();
            return;
        }

        do
        {
            super.clearConsole( Constants.TIME_SWITCH_MENU );
            conversionFactorView.print( "\n" );
            this.listAll(); 
            conversionFactorView.print("\n" );

            int index = this.enterID();
            double[] range = conversionFactors.calculateRange( index );
            Double value = this.enterValue( range );

            conversionFactors.calculate( index, value );
        } while ( !conversionFactors.isComplete() );

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorView.print( "\n" );
        this.listAll();
        conversionFactorView.print( "\n" );
        conversionFactorView.println( Constants.OPERATION_COMPLETED );
        super.clearConsole( Constants.TIME_MESSAGE );
    }
    
    public void saveConversionFactors () throws SQLException
    {
        conversionFactorsJDBC.saveAll( this.conversionFactors );
    }

    public void printConversionFactor ( ConversionFactor conversionFactor )
    {
        String COLOR = conversionFactor.getValue() == null ? Constants.RED : Constants.BOLD + Constants.GREEN;
        conversionFactorView.println( conversionFactor.getLeaf_1().getName() + super.padRight( conversionFactor.getLeaf_1().getName(), 50 ) + "-->\t\t" + conversionFactor.getLeaf_2().getName() + super.padRight( conversionFactor.getLeaf_2().getName(), 50 ) + ": " + COLOR + conversionFactor.getValue() + Constants.RESET );
    }

}