package projectClass;

import util.*;
import java.sql.*;
import java.util.*;

public class ConversionFactor {

    private int ID_leaf_1;
    private String name_leaf_1;
    private int ID_leaf_2;
    private String name_leaf_2;
    private Double value;

    public ConversionFactor ( int ID_leaf_1, int ID_leaf_2, Double value ) throws SQLException
    {
        this.ID_leaf_1 = ID_leaf_1;
        this.ID_leaf_2 = ID_leaf_2;
        this.name_leaf_1 = this.getNameFromIDs().get( 0 );
        this.name_leaf_2 = this.getNameFromIDs().get( 1 );
        this.value = value;
    }

    public int getID_leaf_1 ()
    {
        return ID_leaf_1;
    }

    public int getID_leaf_2 ()
    {
        return ID_leaf_2;
    }

    public Double getValue ()
    {
        return value;
    }

    public void setValue (Double value) 
    {
        this.value = value;
    }

    public List<String> getNameFromIDs() throws SQLException
    {
        List<String> toReturn = new ArrayList<>();

        String query;
        ResultSet rs;
        ArrayList<String> parameters;

        query = "SELECT name FROM categories WHERE id = ?" + 
                "UNION " +
                "SELECT name FROM tmp_categories WHERE id = ?";

        parameters = new ArrayList<String>();
        parameters.add( Integer.toString( this.ID_leaf_1 ) );
        parameters.add( Integer.toString( this.ID_leaf_1 ) );

        rs = Conn.exQuery( query, parameters );

        rs.next();
        toReturn.add( rs.getString( 1 ) );

        query = "SELECT name FROM categories WHERE id = ?" + 
                "UNION " +
                "SELECT name FROM tmp_categories WHERE id = ?";

        parameters = new ArrayList<String>();
        parameters.add( Integer.toString( this.ID_leaf_2 ) );
        parameters.add( Integer.toString( this.ID_leaf_2 ) );

        rs = Conn.exQuery( query, parameters );

        rs.next();
        toReturn.add( rs.getString( 1 ) );

        return toReturn;
    }

    @Override
    public boolean equals( Object obj ) 
    {
        return ( this.ID_leaf_1 == ((ConversionFactor)obj).ID_leaf_1 ) && ( this.ID_leaf_2 == ((ConversionFactor)obj).ID_leaf_2 );
    }

    @Override
    public String toString() 
    {
        StringBuffer toReturn = new StringBuffer();

        toReturn.append( this.name_leaf_1 + Util.padRight( this.name_leaf_1, 50 ) + "-->\t\t" + this.name_leaf_2 + Util.padRight( this.name_leaf_2, 50 ) + ": " + this.value );

        return toReturn.toString();
    }

}