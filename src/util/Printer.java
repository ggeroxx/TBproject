package util;

import java.sql.*;
import java.util.ArrayList;
import projectClass.*;

public class Printer {
    
    public static String printCategoriesList ( int hierarchyID ) throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();

        String query = "SELECT id, name FROM categories WHERE hierarchyID = ? AND field IS NOT NULL AND description IS NOT NULL AND id NOT IN (SELECT MAX(id) FROM categories) " +
                       "UNION " +
                       "SELECT id, name FROM tmp_categories WHERE hierarchyID = ? AND field IS NOT NULL AND description IS NOT NULL AND id NOT IN (SELECT MAX(id) FROM tmp_categories)";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( hierarchyID ) );
        parameters.add( Integer.toString( hierarchyID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        while ( rs.next() ) toReturn.append( "  " + rs.getInt( 1 ) + ". " + rs.getString( 2 ) + "\n" );

        return toReturn.toString();
    }

    public static String printAllRoot () throws SQLException
    {
        String query;
        ResultSet rs;
        ArrayList<String> parameters;
        StringBuffer toReturn = new StringBuffer();

        query = "SELECT name, hierarchyid FROM categories WHERE root = ?";
        parameters = new ArrayList<String>();
        parameters.add( String.valueOf( 1 ) );
        rs = Conn.exQuery( query, parameters );

        while ( rs.next() ) toReturn.append( " " + rs.getString( 2 ) + ". " + rs.getString( 1 ) + "\n" );

        query = "SELECT name, hierarchyid FROM tmp_categories WHERE root = ?";
        parameters = new ArrayList<String>();
        parameters.add( String.valueOf( 1 ) );
        rs = Conn.exQuery( query, parameters );

        while ( rs.next() ) toReturn.append( " " + rs.getString( 2 ) + ". " + rs.getString( 1 ) + "  -->  (not saved)\n" );

        return toReturn.toString();
    }

    public static String printAllDistricts () throws SQLException
    {
        String query;
        ResultSet rs;
        District tmp;
        StringBuffer toReturn = new StringBuffer();

        query = "SELECT name FROM districts ";
        rs = Conn.exQuery( query );
        while ( rs.next() )
        {
            tmp = new District( rs.getString(1) );
            toReturn.append( tmp.toString() + "\n");
        }

        query = "SELECT name FROM tmp_districts";
        rs = Conn.exQuery( query );
        while ( rs.next() )
        {
            tmp = new District( rs.getString(1) );
            toReturn.append( tmp.toString() + "  -->  (not saved)\n");
        }

        return toReturn.toString();
    }

    public static String printHierarchy ( int IDToPrint ) throws SQLException
    {
        return printHierarchy( IDToPrint, new StringBuffer(), new StringBuffer() );
    }

    public static String printHierarchy ( int IDToPrint, StringBuffer toReturn, StringBuffer spaces ) throws SQLException
    {
        String query;
        ResultSet rs;
        ArrayList<String> parameters;

        query = "SELECT name, root FROM categories WHERE id = ? " +
                "UNION " +
                "SELECT name, root FROM tmp_categories WHERE id = ?";

        parameters = new ArrayList<String>();
        parameters.add( Integer.toString( IDToPrint ) );
        parameters.add( Integer.toString( IDToPrint ) );

        rs = Conn.exQuery( query, parameters );
        rs.next();
        if ( rs.getBoolean( 2 ) ) toReturn.append( rs.getString( 1 ) + "\n\n" );
        else toReturn.append( spaces.toString() + "└──── " + rs.getString( 1 ) + "\n\n" );

        query = "SELECT childid FROM relationshipsBetweenCategories WHERE parentid = ? " +
                "UNION " +
                "SELECT childid FROM tmp_relationshipsBetweenCategories WHERE parentid = ?";

        parameters = new ArrayList<String>();
        parameters.add( Integer.toString( IDToPrint ) );
        parameters.add( Integer.toString( IDToPrint ) );

        rs = Conn.exQuery( query, parameters );
        
        while ( rs.next() ) 
        {
            spaces.append("\t");
            printHierarchy( rs.getInt( 1 ), toReturn, spaces );
        }
        if ( spaces.length() > 1 ) spaces.setLength( spaces.length() - 1 );
        else spaces.setLength( 0 );

        return toReturn.toString();
    } 

}