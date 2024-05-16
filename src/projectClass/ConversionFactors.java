package projectClass;

import java.util.*;
import java.util.Map.*;
import util.*;
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

    public Integer getKeyByValue ( ConversionFactor value )
    {
        for ( Entry<Integer, ConversionFactor> entry : this.list.entrySet() )
            if ( entry.getValue().equals( value ) ) return entry.getKey();

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
        CategoryJDBC categoryJDBC = new CategoryJDBCImpl();

        this.list.get( index ).setValue( Math.round( value * 100.0 ) / 100.0 );
        int c1 = this.list.get( index ).getLeaf_1().getID();
        int c2 = this.list.get( index ).getLeaf_2().getID();

        for ( Entry<Integer, ConversionFactor> toControl : this.list.entrySet() )
        {
            if ( c2 == toControl.getValue().getLeaf_1().getID() && toControl.getValue().getValue() != null && c1 != toControl.getValue().getLeaf_2().getID() )
            {
                int c3 = toControl.getValue().getLeaf_2().getID();
                if ( !( categoryJDBC.getCategoryByID( c1 ).getName().equals( categoryJDBC.getCategoryByID( c3 ).getName() ) ) && this.list.get( getKeyByValue( new ConversionFactor( categoryJDBC.getCategoryByID( c1 ), categoryJDBC.getCategoryByID( c3 ), null ) ) ).getValue() == null )
                {
                    Double val = toControl.getValue().getValue();
                    int newIndex = getKeyByValue( new ConversionFactor( categoryJDBC.getCategoryByID( c1 ), categoryJDBC.getCategoryByID( c3 ), null ) );
                    calculate( newIndex, value * val );
                }
            }
        }

        int contraryIndex = getKeyByValue( new ConversionFactor( categoryJDBC.getCategoryByID( c2 ), categoryJDBC.getCategoryByID( c1 ), null ) );
        if ( this.list.get( contraryIndex ).getValue() == null ) calculate( contraryIndex, 1 / value);
        
        return;
    }

    public double[] calculateRange ( int index )
    {
        int ID_leaf1 = this.getList().get(index).getLeaf_1().getID();
        int ID_leaf2 = this.getList().get(index).getLeaf_2().getID();
        double max = Constants.MIN_VALUES_CONVERSION_FACTOR, min = Constants.MAX_VALUES_CONVERSION_FACTOR;

        ArrayList<Double> involvedValues = new ArrayList<>();

        for ( Entry<Integer, ConversionFactor> entry : this.getList().entrySet() )
            if ( entry.getValue().getValue() != null && ( entry.getValue().getLeaf_2().getID() == ID_leaf1 || entry.getValue().getLeaf_1().getID() == ID_leaf2 ) )
                involvedValues.add( entry.getValue().getValue() );

        if ( involvedValues.isEmpty() ) return new double[]{ Constants.MIN_VALUES_CONVERSION_FACTOR, Constants.MAX_VALUES_CONVERSION_FACTOR }; 
        else
        {
            for ( Double value : involvedValues )
            {
                max = value > max ? value : max;
                min = value < min ? value : min;
            }
            max = ( Constants.MAX_VALUES_CONVERSION_FACTOR / max >= Constants.MAX_VALUES_CONVERSION_FACTOR ) ? Constants.MAX_VALUES_CONVERSION_FACTOR : ( Constants.MAX_VALUES_CONVERSION_FACTOR / max );
            min = ( Constants.MIN_VALUES_CONVERSION_FACTOR / min <= Constants.MIN_VALUES_CONVERSION_FACTOR ) ? Constants.MIN_VALUES_CONVERSION_FACTOR : ( Constants.MIN_VALUES_CONVERSION_FACTOR / min );
            max = Math.floor( max * 100.0 ) / 100.0;
            min = Math.ceil( min * 100.0 ) / 100.0;
            return new double[]{ min, max };
        }
    }

    public boolean isComplete ()
    {
        return this.list.entrySet().stream().allMatch( entry -> entry.getValue().getValue() != null );
    }

    @Override
    public String toString() 
    {
        StringBuffer toReturn = new StringBuffer();

        for ( Integer index : list.keySet() ) toReturn.append( "  " + index + ".\t" + this.list.get(index).toString() + "\n" );

        return toReturn.toString();
    }

}
