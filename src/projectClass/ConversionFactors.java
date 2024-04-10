package projectClass;

import java.util.*;
import java.util.Map.Entry;
import java.sql.*;
import util.*;

public class ConversionFactors {
    
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
        for ( Entry<Integer, ConversionFactor> toControl : this.list.entrySet() )
            if ( toControl.getValue().getID_leaf_1() == this.list.get( index ).getID_leaf_1() && toControl.getValue().getValue() != null )
            {
                this.list.get( index ).setValue( value );
                calculate( getKeyByValue( new ConversionFactor( this.list.get( index ).getID_leaf_1(), toControl.getValue().getID_leaf_2(), null ) ), toControl.getValue().getValue() * this.list.get( index ).getValue() );
            }

        calculate( getKeyByValue( new ConversionFactor( this.list.get( index ).getID_leaf_2(), this.list.get( index ).getID_leaf_1(), null ) ), 1 / value);
        return;
    }

    @Override
    public String toString() 
    {
        StringBuffer toReturn = new StringBuffer();

        for ( Integer index : list.keySet() ) toReturn.append( "  " + index + ".\t" + this.list.get(index).toString() + "\n" );

        return toReturn.toString();
    }

}
