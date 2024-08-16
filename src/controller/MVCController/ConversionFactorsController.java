package controller.MVCController;

import java.sql.SQLException;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map.Entry;

import controller.GRASPController.*;
import model.*;
import util.Constants;
import view.*;

public class ConversionFactorsController extends Controller {
    
    private ConversionFactorsView conversionFactorsView;
    private ConversionFactorController conversionFactorController;
    private CategoryController categoryController;
    private ConversionFactorsGRASPController businessController; 

    public ConversionFactorsController ( ConversionFactorsView conversionFactorsView, ConversionFactorsRepository conversionFactorsRepository, ConversionFactorController conversionFactorController, CategoryController categoryController )
    {
        super( conversionFactorsView );
        this.conversionFactorsView = conversionFactorsView;
        this.conversionFactorController = conversionFactorController;
        this.categoryController = categoryController;
        this.businessController = new ConversionFactorsGRASPController(conversionFactorsRepository, categoryController);
    }

    public ConversionFactorsRepository getconversionFactorsRepository () 
    {
        return this.businessController.getconversionFactorsRepository();
    }

    public ConversionFactors getConversionFactors ()
    {
        return this.businessController.getConversionFactors();
    }

    public void resetConversionFactors ()
    {
        this.businessController.resetConversionFactors();
    }

    public void listAll () throws SQLException
    {
        conversionFactorsView.print( "\n" );

        for ( Entry<Integer, ConversionFactor> entry : getConversionFactors().getList().entrySet() )
        {
            conversionFactorsView.print( " " + entry.getKey() + ". " + super.padRight( Integer.toString( entry.getKey() ), 5 ) );
            conversionFactorController.print( entry.getValue() );
        }

        conversionFactorsView.print( "\n" );
    }

    public void listConversionFactorsByLeaf ( Category leaf ) throws SQLException
    {
        populate();

        for ( Entry<Integer, ConversionFactor> entry : getConversionFactors().getList().entrySet() )
            if ( entry.getValue().getLeaf_1().getID() == leaf.getID() || entry.getValue().getLeaf_2().getID() == leaf.getID() )
                conversionFactorController.print( entry.getValue() );
    }

    public void listConversionFactorsOfLeaf () throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorsView.print( Constants.LEAF_CATEGORY_LIST );

        if ( categoryController.getCategoryRepository().getAllLeaf().isEmpty() )
        {
            conversionFactorsView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        categoryController.listAllLeafs();
        int leafID = categoryController.enterLeafID();
        if ( leafID == 0 ) return;

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorsView.println( Constants.CONVERSION_FACTORS_LIST );

        this.listConversionFactorsByLeaf( categoryController.getCategoryRepository().getCategoryByID( leafID ) );

        conversionFactorsView.enterString( "\n" + Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public int enterID ()
    {
        return super.readInt( Constants.ENTER_CHOICE_PAIR, Constants.NOT_EXIST_MESSAGE, ( input ) -> !getConversionFactors().getList().containsKey( (Integer) input ) || !(getConversionFactors().getList().get( (Integer) input ).getValue() == null ) );
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
                value = conversionFactorsView.enterDouble( Constants.ENTER_VALUE_CONVERSION_FACTOR + rangeToPrint );
                if ( value < range[ 0 ] || value > range[ 1 ] ) conversionFactorsView.print( Constants.OUT_OF_RANGE_ERROR );
            }
            catch ( InputMismatchException e )
            {
                conversionFactorsView.println( Constants.INVALID_OPTION );
                hasExceptionOccured = true;
            }
        } while ( hasExceptionOccured || value < range[ 0 ] || value > range[ 1 ] );

        return value;
    }

    public void listAllConversionFactors () throws SQLException
    {
        populate();

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorsView.print( Constants.CONVERSION_FACTORS_LIST );

        if ( getConversionFactors().getList().isEmpty() )
        {
            conversionFactorsView.println( Constants.NOT_EXIST_MESSAGE + "\n" );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        this.listAll();

        conversionFactorsView.enterString( Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public void enterConversionFactors () throws SQLException
    {
        populate();

        if ( isComplete() )
        {
            this.listAllConversionFactors();
            return;
        }

        do
        {
            super.clearConsole( Constants.TIME_SWITCH_MENU );
            conversionFactorsView.print( "\n" );
            this.listAll();

            int index = this.enterID();
            double[] range = calculateRange( index );
            Double value = this.enterValue( range );

            calculate( index, value );
        } while ( !isComplete() );

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorsView.print( "\n" );
        this.listAll();
        conversionFactorsView.print( "\n" );
        conversionFactorsView.println( Constants.OPERATION_COMPLETED );
        super.clearConsole( Constants.TIME_MESSAGE );
    }
    
    public void saveConversionFactors () throws SQLException
    {
        this.businessController.saveConversionFactors();
    }

    public void populate () throws SQLException
    {
        this.businessController.populate();
    }

    public void calculate ( int index, Double value ) throws SQLException
    {
        this.businessController.calculate(index, value);
    }

    public void calculateEq ( int index, Double value, boolean check, HashMap<Integer, ConversionFactor> copyCFs, List<String> equations )
    {
        this.businessController.calculateEq(index, value, check, copyCFs, equations);
    }

    public double[] calculateRange ( int index )
    {
        return this.businessController.calculateRange(index);
    }

    public boolean isComplete () throws SQLException
    {
        return this.businessController.isComplete();
    }

}
