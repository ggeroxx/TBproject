package model;

import java.util.*;
import java.util.Map.*;

import util.Constants;

import java.sql.*;

public class ConversionFactors {
    
    private Integer index;
    private HashMap<Integer, ConversionFactor> list;
    private ConversionFactorsJDBC conversionFactorsJDBC;
    private CategoryJDBC categoryJDBC;

    public ConversionFactors () 
    {
        this.index = 0;
        this.list = new HashMap<>();
        this.conversionFactorsJDBC = new ConversionFactorsJDBCImpl();
        this.categoryJDBC = new CategoryJDBCImpl();
    }

    public HashMap<Integer, ConversionFactor> getList() 
    {
        return this.list;
    }

    public void put ( ConversionFactor toPut )
    {
        this.list.put( index++, toPut );
    }

    public boolean contains ( ConversionFactor toFind )
    {
        return this.list.containsValue( toFind );
    }

    public Integer getIndex ( Category leaf1, Category leaf2 )
    {
        for ( Entry<Integer, ConversionFactor> entry : this.list.entrySet() )
            if ( entry.getValue().getLeaf_1().equals( leaf1 ) && entry.getValue().getLeaf_2().equals( leaf2 ) ) return entry.getKey();

        return null;
    }

    public void populate () throws SQLException
    {
        for ( ConversionFactor toAdd : conversionFactorsJDBC.getAll() )
            if ( !this.contains( toAdd ) && !toAdd.getLeaf_1().getName().equals( toAdd.getLeaf_2().getName() ) )
                this.put( toAdd );
        
        for ( Category fixed : new CategoryJDBCImpl().getAllLeaf() ) 
        {
            for ( Category mobile : new CategoryJDBCImpl().getAllLeaf() ) 
            {
                if ( fixed.getID() != mobile.getID() )
                {  
                    ConversionFactor toAdd = new ConversionFactor( categoryJDBC.getCategoryByID( fixed.getID() ), categoryJDBC.getCategoryByID( mobile.getID() ), null );
                    if ( !this.contains( toAdd ) && !toAdd.getLeaf_1().getName().equals( toAdd.getLeaf_2().getName() ) ) this.put( toAdd );
                }
            }
        }
    }

    public void calculate ( int index, Double value ) throws SQLException
    {
        ConversionFactor AB = this.list.get( index );
        AB.setValue( Math.round( value * 100000.0 ) / 100000.0 );
        
        for ( Entry<Integer, ConversionFactor> toControl : this.list.entrySet() )
        {
            ConversionFactor BC = toControl.getValue();
            if ( AB.getLeaf_2().getID() == BC.getLeaf_1().getID() && BC.getValue() != null && AB.getLeaf_1().getID() != BC.getLeaf_2().getID() )
            {
                Integer indexAC = getIndex( AB.getLeaf_1(), BC.getLeaf_2() );
                if ( indexAC != null )
                {
                    ConversionFactor AC = this.list.get( indexAC );
                    if ( AC.getValue() == null ) calculate( indexAC, AB.getValue() * BC.getValue() );
                }
            }
        }

        Integer indexBA = getIndex( AB.getLeaf_2(), AB.getLeaf_1() );
        ConversionFactor BA = this.list.get( indexBA );
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
                Integer indexAC = getIndex( AB.getLeaf_1(), BC.getLeaf_2() );
                if ( indexAC != null )
                {
                    ConversionFactor AC = copyCFs.get( indexAC );
                    if ( AC.getValue() == null )
                        calculateEq( indexAC, AB.getValue() * BC.getValue(), check, copyCFs, equations );
                }
            }
        }

        Integer indexBA = getIndex( AB.getLeaf_2(), AB.getLeaf_1() );
        ConversionFactor BA = copyCFs.get( indexBA );
        if ( BA.getValue() == null ) 
            calculateEq( indexBA, 1 / value, check ? false : true, copyCFs, equations );
        
        return;
    }

    public double[] calculateRange ( int index )
    {
        HashMap<Integer, ConversionFactor> copyCFs = new HashMap<>();
        for (  Entry<Integer, ConversionFactor> cf : this.list.entrySet() )
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
                exSX = tmp * Constants.MAX_VALUES_CONVERSION_FACTOR > exSX ? tmp * Constants.MAX_VALUES_CONVERSION_FACTOR : exSX;
                exDX = tmp * Constants.MIN_VALUES_CONVERSION_FACTOR < exDX ? tmp * Constants.MIN_VALUES_CONVERSION_FACTOR : exDX;
            }
            else
            {
                eq = eq.replace( "x * " , "" );
                Double tmp = Double.parseDouble( eq );
                exSX = tmp * Constants.MIN_VALUES_CONVERSION_FACTOR > exSX ? tmp * Constants.MIN_VALUES_CONVERSION_FACTOR : exSX;
                exDX = tmp * Constants.MAX_VALUES_CONVERSION_FACTOR < exDX ? tmp * Constants.MAX_VALUES_CONVERSION_FACTOR : exDX;
            }
        }

        return new double[]{ exSX, exDX };
    }

    public boolean isComplete ()
    {
        return this.list.entrySet().stream().allMatch( entry -> entry.getValue().getValue() != null );
    }

}
