package projectClass;

import java.util.*;
import java.util.Map.Entry;
import java.sql.*;
import util.*;

public class ConversionFactors implements Cloneable {
    
    private Integer index;
    private HashMap<Integer, ConversionFactor> list;

    public ConversionFactors() {
        this.index = 0;
        this.list = new HashMap<>();
    }

    public HashMap<Integer, ConversionFactor> getList() 
    {
        return list;
    }

    public void put ( ConversionFactor toPut )
    {
        this.list.put( index++, toPut);
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
        String query;
        ResultSet rs, fixed, mobile;

        query = "SELECT * FROM conversionFactors";
        rs = Conn.exQuery( query );

        while ( rs.next() )
        {
            ConversionFactor toAdd = new ConversionFactor( rs.getInt( 1 ), rs.getInt( 2 ), rs.getDouble( 3 ));
            if ( !this.contains( toAdd ) ) this.put( toAdd );
        }

        query = "SELECT id FROM categories WHERE field IS NULL UNION SELECT id FROM tmp_categories WHERE field IS NULL";
        fixed = Conn.exQuery( query );
        mobile = Conn.exQuery( query );
        
        while ( fixed.next() ) 
        {
            while ( mobile.next() ) 
            {
                if ( fixed.getInt( 1 ) != mobile.getInt( 1 ) )
                {
                    ConversionFactor toAdd = new ConversionFactor( fixed.getInt( 1 ), mobile.getInt( 1 ), null );
                    if ( !( this.contains( toAdd ) ) ) this.put( toAdd );
                }
                    
            }
            mobile = Conn.exQuery( query );
        }
    }

    public void calculate ( int index, Double value ) throws SQLException
    {
        this.list.get( index ).setValue( Math.round( value * 100.0 ) / 100.0 );
        int c1 = this.list.get( index ).getID_leaf_1();
        int c2 = this.list.get( index ).getID_leaf_2();

        for ( Entry<Integer, ConversionFactor> toControl : this.list.entrySet() )
        {
            if ( c2 == toControl.getValue().getID_leaf_1() && toControl.getValue().getValue() != null && c1 != toControl.getValue().getID_leaf_2() )
            {
                int c3 = toControl.getValue().getID_leaf_2();
                if (this.list.get( getKeyByValue( new ConversionFactor( c1, c3, null ) ) ).getValue() == null )
                {
                    Double val = toControl.getValue().getValue();
                    int newIndex = getKeyByValue( new ConversionFactor( c1, c3, null ) );
                    calculate( newIndex, value * val );
                }
            }
        }

        int contraryIndex = getKeyByValue( new ConversionFactor( c2, c1, null ) );
        if ( this.list.get( contraryIndex ).getValue() == null ) calculate( contraryIndex, 1 / value);
        
        return;
    }

    public boolean inRange ()
    {
        for ( Entry<Integer, ConversionFactor> entry : this.list.entrySet() )
            if ( entry.getValue().getValue() != null )
                if ( entry.getValue().getValue() < 0.5 || entry.getValue().getValue() > 2.0 ) return false;

        return true;
    }

    public boolean isComplete ()
    {
        for ( Entry<Integer, ConversionFactor> entry : this.list.entrySet() )
            if ( entry.getValue().getValue() == null ) return false;

        return true;
    }

    // public void copy ( ConversionFactors toCopy )
    // {
    //     this.index = toCopy.index;
    //     this.list = new HashMap<Integer, ConversionFactor>( toCopy.getList() );
    // }

    public void copy ( ConversionFactors toCopy )
    {
        this.index = toCopy.index;
        this.list = new HashMap<Integer, ConversionFactor>();
        for ( Entry<Integer, ConversionFactor> entry : toCopy.getList().entrySet() )
            this.list.put( entry.getKey(), entry.getValue() );
    }

    @Override
    public String toString() 
    {
        StringBuffer toReturn = new StringBuffer();

        for ( Integer index : list.keySet() ) toReturn.append( "  " + index + ".\t" + this.list.get(index).toString() + "\n" );

        return toReturn.toString();
    }

}
