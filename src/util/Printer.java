package util;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import projectClass.*;

public class Printer {

    public static String printCategoriesList ( int hierarchyID ) throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();

        String query = "SELECT id, name FROM categories WHERE hierarchyID = ? AND field IS NOT NULL AND id NOT IN (SELECT MAX(id) FROM categories) " +
                       "UNION " +
                       "SELECT id, name FROM tmp_categories WHERE hierarchyID = ? AND field IS NOT NULL AND id NOT IN (SELECT MAX(id) FROM tmp_categories)";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( hierarchyID ) );
        parameters.add( Integer.toString( hierarchyID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        while ( rs.next() ) toReturn.append( "  " + Constants.YELLOW + rs.getInt( 1 ) + ". " + Constants.RESET + rs.getString( 2 ) + "\n" );

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

    public static String printAllLeafCategory () throws SQLException
    {
        String query;
        ResultSet rs;
        StringBuffer toReturn = new StringBuffer();

        query = "SELECT id FROM categories WHERE field IS NULL";
        rs = Conn.exQuery( query );

        while ( rs.next() )
        {
            Category toPrint = new Category( rs.getInt( 1 ) );
            toReturn.append( " " + toPrint.getID() + ". " + Util.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + Util.padRight( toPrint.getName() , 50 ) + "  [ " + Category.getRootByLeaf( toPrint ).getName() + " ]  " + "\n" );
        }

        query = "SELECT id FROM tmp_categories WHERE field IS NULL";
        rs = Conn.exQuery( query );

        while ( rs.next() )
        {
            Category toPrint = new Category( rs.getInt( 1 ) );
            toReturn.append( " " + toPrint.getID() + ". " + Util.padRight( Integer.toString( toPrint.getID() ) , 3 ) + toPrint.getName() + Util.padRight( toPrint.getName() , 50 ) + "  [ " + Category.getRootByLeaf( toPrint ).getName() + " ]  " + "  -->  (not saved)\n" );
        }

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

        query = "SELECT id, name, root FROM categories WHERE id = ? " +
                "UNION " +
                "SELECT id, name, root FROM tmp_categories WHERE id = ?";

        parameters = new ArrayList<String>();
        parameters.add( Integer.toString( IDToPrint ) );
        parameters.add( Integer.toString( IDToPrint ) );

        rs = Conn.exQuery( query, parameters );
        rs.next();
        if ( rs.getBoolean( 3 ) ) toReturn.append( rs.getInt( 1 ) + ". " + rs.getString( 2 ) + "\n\n" );
        else toReturn.append( spaces.toString() + "└──── " + rs.getInt( 1 ) + ". " + rs.getString( 2 ) + "\n\n" );

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

    public static String printInfoCategory( int IDCategoryToPrint ) throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();    

        String query;
        ResultSet rs = null;
        ArrayList<String> parameters = new ArrayList<String>();

        query = "SELECT * FROM categories WHERE id = ? UNION SELECT * FROM tmp_categories WHERE id = ?";
        parameters.add( Integer.toString( IDCategoryToPrint ) );
        parameters.add( Integer.toString( IDCategoryToPrint ) );
        rs = Conn.exQuery( query, parameters );
        rs.next();

        Category toPrint = new Category( rs.getString( 2 ), rs.getString( 3 ), rs.getString( 4 ), rs.getBoolean( 5 ), rs.getInt( 6 ), rs.getInt( 7 ) );

        if ( toPrint.getField() != null )
        {
            query = "SELECT fieldtype FROM relationshipsbetweencategories WHERE parentid = ? UNION SELECT fieldtype FROM tmp_relationshipsbetweencategories WHERE parentid = ?";
            parameters = new ArrayList<String>();
            parameters.add( Integer.toString( toPrint.getID() ) );
            parameters.add( Integer.toString( toPrint.getID() ) );
            rs = Conn.exQuery( query, parameters );

            toReturn.append( "name:" + Util.padRight( "name:", 20 ) + Constants.BOLD + toPrint.getName() + Constants.RESET + "\n" );
            toReturn.append( "description:" + Util.padRight( "description:", 20 ) + toPrint.getDescription() + "\n" );
            toReturn.append( "field:" + Util.padRight( "field:", 20 ) + toPrint.getField() + " = { " );
            while ( rs.next() ) toReturn.append( rs.getString( 1 ) + ", " );
            toReturn.deleteCharAt( toReturn.length() - 2 );
            toReturn.append( "}\n" );
        }
        else
        {
            query = "SELECT fieldtype FROM relationshipsbetweencategories WHERE childid = ? UNION SELECT fieldtype FROM tmp_relationshipsbetweencategories WHERE childid = ?";
            parameters = new ArrayList<String>();
            parameters.add( Integer.toString( toPrint.getID() ) );
            parameters.add( Integer.toString( toPrint.getID() ) );
            rs = Conn.exQuery( query, parameters );
            rs.next();

            toReturn.append( "name:" + Util.padRight( "name:", 20 ) + Constants.BOLD + toPrint.getName() + Constants.RESET + "\n" );
            toReturn.append( "description:" + Util.padRight( "description:", 20 ) + toPrint.getDescription() + "\n" );
            toReturn.append( "value of domain:" + Util.padRight( "value of domain:", 20 ) + rs.getString( 1 ) + "\n" );
        }

        return toReturn.toString();
    }

    public static String printConversionFactors ( ConversionFactors conversionFactors ) throws SQLException
    {
        StringBuffer toReturn = new StringBuffer();
        ArrayList<String> parameters;
        ResultSet rs;
        String COLOR, rootLeaf1, rootLeaf2;

        String query = "SELECT ( SELECT COUNT(*) FROM categories WHERE name = ? ) + ( SELECT COUNT(*) FROM tmp_categories WHERE name = ? )";

        for ( Entry<Integer, ConversionFactor> entry : conversionFactors.getList().entrySet() )
        {
            rootLeaf1 = "";

            parameters = new ArrayList<String>();
            parameters.add( entry.getValue().getLeaf_1().getName() );
            parameters.add( entry.getValue().getLeaf_1().getName() );

            rs = Conn.exQuery( query, parameters );
            rs.next();

            if ( rs.getInt( 1 ) > 1 ) rootLeaf1 = "  [ " + Category.getRootByLeaf( entry.getValue().getLeaf_1() ).getName() + " ]  ";

            rootLeaf2 = "";

            parameters = new ArrayList<String>();
            parameters.add( entry.getValue().getLeaf_2().getName() );
            parameters.add( entry.getValue().getLeaf_2().getName() );

            rs = Conn.exQuery( query, parameters );
            rs.next();

            if ( rs.getInt( 1 ) > 1 ) rootLeaf2 = "  [ " + Category.getRootByLeaf( entry.getValue().getLeaf_2() ).getName() + " ]  ";

            COLOR = entry.getValue().getValue() == null ? Constants.RED : Constants.BOLD + Constants.GREEN;
            toReturn.append( " " + entry.getKey() + ". " + Util.padRight( Integer.toString( entry.getKey() ), 5 ) + entry.getValue().getLeaf_1().getName() + rootLeaf1 + Util.padRight( entry.getValue().getLeaf_1().getName() + rootLeaf1, 70 ) + "-->\t\t" + entry.getValue().getLeaf_2().getName() + rootLeaf2 + Util.padRight( entry.getValue().getLeaf_2().getName() + rootLeaf2, 70 ) + ": " + COLOR + entry.getValue().getValue() + Constants.RESET + "\n" );
        }

        return toReturn.toString();
    } 

    public static String printConversionFactorsByLeaf ( int IDLeafCategory, ConversionFactors conversionFactors )
    {
        StringBuffer toReturn = new StringBuffer();

        for ( Entry<Integer, ConversionFactor> entry : conversionFactors.getList().entrySet() )
            if ( entry.getValue().getLeaf_1().getID() == IDLeafCategory || entry.getValue().getLeaf_2().getID() == IDLeafCategory )
                toReturn.append( "  " + entry.getValue().toString() + "\n" );
        
        return toReturn.toString();
    } 

}