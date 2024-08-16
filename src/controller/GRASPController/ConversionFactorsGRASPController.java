package controller.GRASPController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import controller.MVCController.*;
import model.*;
import util.Constants;
public class ConversionFactorsGRASPController{
    
    private ConversionFactors conversionFactors;
    private ConversionFactorsRepository conversionFactorsRepository;
    private CategoryController categoryController;

    public ConversionFactorsGRASPController ( ConversionFactorsRepository conversionFactorsRepository, CategoryController categoryController )
    {
        this.conversionFactors = new ConversionFactors();
        this.conversionFactorsRepository = conversionFactorsRepository;
        this.categoryController = categoryController;
    }

    public ConversionFactorsRepository getconversionFactorsRepository () 
    {
        return this.conversionFactorsRepository;
    }

    public ConversionFactors getConversionFactors ()
    {
        return this.conversionFactors;
    }

    public void resetConversionFactors ()
    {
        this.conversionFactors = new ConversionFactors();
    }
    
    public void saveConversionFactors () throws SQLException
    {
        conversionFactorsRepository.saveAll( this.conversionFactors );
    }

    public void populate () throws SQLException
    {
        for ( ConversionFactor toAdd : conversionFactorsRepository.getAll() )
            if ( !conversionFactors.contains( toAdd ) && !toAdd.getLeaf_1().getName().equals( toAdd.getLeaf_2().getName() ) )
                conversionFactors.put( toAdd );
        
        for ( Category fixed : new JDBCCategoryRepository().getAllLeaf() ) 
        {
            for ( Category mobile : new JDBCCategoryRepository().getAllLeaf() ) 
            {
                if ( fixed.getID() != mobile.getID() )
                {  
                    ConversionFactor toAdd = new ConversionFactor( categoryController.getCategoryRepository().getCategoryByID( fixed.getID() ), categoryController.getCategoryRepository().getCategoryByID( mobile.getID() ), null );
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
