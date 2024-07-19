package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map.Entry;

import model.*;
import util.Constants;
import view.*;

public class ConversionFactorsController extends Controller {
    
    private ConversionFactorsView conversionFactorsView;
    private ConversionFactors conversionFactors;
    private ConversionFactorsJDBC conversionFactorsJDBC;
    private ConversionFactorController conversionFactorController;
    private CategoryController categoryController;

    public ConversionFactorsController ( ConversionFactorsView conversionFactorsView, ConversionFactorsJDBC conversionFactorsJDBC, ConversionFactorController conversionFactorController, CategoryController categoryController )
    {
        super( conversionFactorsView );
        this.conversionFactorsView = conversionFactorsView;
        this.conversionFactors = new ConversionFactors();
        this.conversionFactorsJDBC = conversionFactorsJDBC;
        this.conversionFactorController = conversionFactorController;
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
        conversionFactorsView.print( "\n" );

        for ( Entry<Integer, ConversionFactor> entry : this.conversionFactors.getList().entrySet() )
        {
            conversionFactorsView.print( " " + entry.getKey() + ". " + super.padRight( Integer.toString( entry.getKey() ), 5 ) );
            conversionFactorController.print( entry.getValue() );
        }

        conversionFactorsView.print( "\n" );
    }

    public void listConversionFactorsByLeaf ( Category leaf ) throws SQLException
    {
        populate();

        for ( Entry<Integer, ConversionFactor> entry : this.conversionFactors.getList().entrySet() )
            if ( entry.getValue().getLeaf_1().getID() == leaf.getID() || entry.getValue().getLeaf_2().getID() == leaf.getID() )
                conversionFactorController.print( entry.getValue() );
    }

    public void listConversionFactorsOfLeaf () throws SQLException
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        conversionFactorsView.print( Constants.LEAF_CATEGORY_LIST );

        if ( categoryController.getCategoryJDBC().getAllLeaf().isEmpty() )
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

        this.listConversionFactorsByLeaf( categoryController.getCategoryJDBC().getCategoryByID( leafID ) );

        conversionFactorsView.enterString( "\n" + Constants.ENTER_TO_EXIT );
        super.clearConsole( Constants.TIME_SWITCH_MENU );
    }

    public int enterID ()
    {
        return super.readInt( Constants.ENTER_CHOICE_PAIR, Constants.NOT_EXIST_MESSAGE, ( input ) -> !conversionFactors.getList().containsKey( (Integer) input ) || !( conversionFactors.getList().get( (Integer) input ).getValue() == null ) );
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

        if ( conversionFactors.getList().isEmpty() )
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
        conversionFactorsJDBC.saveAll( this.conversionFactors );
    }

    public void populate () throws SQLException
    {
        for ( ConversionFactor toAdd : conversionFactorsJDBC.getAll() )
            if ( !conversionFactors.contains( toAdd ) && !toAdd.getLeaf_1().getName().equals( toAdd.getLeaf_2().getName() ) )
                conversionFactors.put( toAdd );
        
        for ( Category fixed : new CategoryJDBCImpl().getAllLeaf() ) 
        {
            for ( Category mobile : new CategoryJDBCImpl().getAllLeaf() ) 
            {
                if ( fixed.getID() != mobile.getID() )
                {  
                    ConversionFactor toAdd = new ConversionFactor( categoryController.getCategoryJDBC().getCategoryByID( fixed.getID() ), categoryController.getCategoryJDBC().getCategoryByID( mobile.getID() ), null );
                    if ( !conversionFactors.contains( toAdd ) && !toAdd.getLeaf_1().getName().equals( toAdd.getLeaf_2().getName() ) ) conversionFactors.put( toAdd );
                }
            }
        }
    }

    public void calculate ( int index, Double value ) throws SQLException
    {
        ConversionFactor AB = conversionFactors.getList().get( index );
        AB.setValue( Math.round( value * 10000.0 ) / 10000.0 );
        
        for ( Entry<Integer, ConversionFactor> toControl : conversionFactors.getList().entrySet() )
        {
            ConversionFactor BC = toControl.getValue();
            if ( AB.getLeaf_2().getID() == BC.getLeaf_1().getID() && BC.getValue() != null && AB.getLeaf_1().getID() != BC.getLeaf_2().getID() )
            {
                Integer indexAC = conversionFactors.getIndex( AB.getLeaf_1(), BC.getLeaf_2() );
                if ( indexAC != null )
                {
                    ConversionFactor AC = conversionFactors.getList().get( indexAC );
                    if ( AC.getValue() == null ) calculate( indexAC, AB.getValue() * BC.getValue() );
                }
            }
        }

        Integer indexBA = conversionFactors.getIndex( AB.getLeaf_2(), AB.getLeaf_1() );
        ConversionFactor BA = conversionFactors.getList().get( indexBA );
        if ( BA.getValue() == null ) calculate( indexBA, 1 / value );
        
        return;
    }

    public void calculateEq ( int index, Double value, boolean check, HashMap<Integer, ConversionFactor> copyCFs, List<String> equations )
    {
        ConversionFactor AB = copyCFs.get( index );
        AB.setValue( Math.round( value * 100000.0 ) / 100000.0 );
        equations.add( check ? "x * " + AB.getValue().toString() : "1/x * " + AB.getValue().toString() );
        
        for ( Entry<Integer, ConversionFactor> toControl : copyCFs.entrySet() )
        {
            ConversionFactor BC = toControl.getValue();
            if ( AB.getLeaf_2().getID() == BC.getLeaf_1().getID() && BC.getValue() != null && AB.getLeaf_1().getID() != BC.getLeaf_2().getID() )
            {
                Integer indexAC = conversionFactors.getIndex( AB.getLeaf_1(), BC.getLeaf_2() );
                if ( indexAC != null )
                {
                    ConversionFactor AC = copyCFs.get( indexAC );
                    if ( AC.getValue() == null )
                        calculateEq( indexAC, AB.getValue() * BC.getValue(), check, copyCFs, equations );
                }
            }
        }

        Integer indexBA = conversionFactors.getIndex( AB.getLeaf_2(), AB.getLeaf_1() );
        ConversionFactor BA = copyCFs.get( indexBA );
        if ( BA.getValue() == null ) 
            calculateEq( indexBA, 1 / value, check ? false : true, copyCFs, equations );
        
        return;
    }

    public double[] calculateRange ( int index )
    {
        HashMap<Integer, ConversionFactor> copyCFs = new HashMap<>();
        for (  Entry<Integer, ConversionFactor> cf : conversionFactors.getList().entrySet() )
            copyCFs.put( cf.getKey(), new ConversionFactor( cf.getValue().getLeaf_1(), cf.getValue().getLeaf_2(), cf.getValue().getValue() ) );

        List<String> equations = new ArrayList<>();
        calculateEq( index, Constants.NEUTRAL_VALUE, true, copyCFs, equations );
    
        double exSX = Constants.MIN_VALUES_CONVERSION_FACTOR, exDX = Constants.MAX_VALUES_CONVERSION_FACTOR;

        for ( String eq : equations )
        {
            if ( eq.contains( "1/x" ) )
            {
                eq = eq.replace( "1/x * " , "" );
                Double tmp = Double.parseDouble( eq );
                exSX = tmp * Constants.MIN_VALUES_CONVERSION_FACTOR > exSX ? tmp * Constants.MIN_VALUES_CONVERSION_FACTOR : exSX;
                exDX = tmp * Constants.MAX_VALUES_CONVERSION_FACTOR < exDX ? tmp * Constants.MAX_VALUES_CONVERSION_FACTOR : exDX;
            }
            else
            {
                eq = eq.replace( "x * " , "" );
                Double tmp = Double.parseDouble( eq );
                exSX = Constants.MIN_VALUES_CONVERSION_FACTOR / tmp > exSX ? Constants.MIN_VALUES_CONVERSION_FACTOR / tmp : exSX;
                exDX = Constants.MAX_VALUES_CONVERSION_FACTOR / tmp < exDX ? Constants.MAX_VALUES_CONVERSION_FACTOR / tmp : exDX;
            }
        }

        exDX = Math.floor( exDX * 10000.0 ) / 10000.0;
        exSX = Math.ceil( exSX * 10000.0 ) / 10000.0;

        return new double[]{ exSX, exDX };
    }

    public boolean isComplete () throws SQLException
    {
        populate();
        return conversionFactors.getList().entrySet().stream().allMatch( entry -> entry.getValue().getValue() != null );
    }

}
