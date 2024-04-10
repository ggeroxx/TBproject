package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import projectClass.*;

public class Controls {
    
    public static boolean checkPattern ( String strToCheck, int minLength, int maxLength )
    {
        return strToCheck.length() <= minLength || strToCheck.length() >= maxLength;
    }

    public static boolean checkPatternPassword ( String strToCheck, int minLength, int maxLength ) 
    {
        int contDigit = 0, contChar = 0;

        if ( strToCheck.length() <= minLength || strToCheck.length() >= maxLength ) return false;

        for ( char character : strToCheck.toCharArray() )
            if( Character.isDigit(character) ) contDigit++;
        for ( char character : strToCheck.toCharArray() )
            if( Character.isLetter(character) ) contChar++;

        return (contDigit == 0 || contChar == 0) ? false : true;
    }

    public static boolean isPresentUsername ( String usernameToCheck ) throws SQLException
    {
        String query = "SELECT username FROM configurators WHERE username = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( usernameToCheck );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    } 

    public static boolean existMunicipality ( String nameToCheck ) throws SQLException
    {
        String query = "SELECT name FROM municipalities WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( nameToCheck );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    } 

    public static boolean isPresentDistrict ( String nameToCheck ) throws SQLException
    {
        String query = "SELECT name FROM districts WHERE name = ?" +
                       "UNION " +
                       "SELECT name FROM tmp_districts WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( nameToCheck );
        parameters.add( nameToCheck );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    }

    public static <T> boolean isPresentRootCategory ( T toCheck ) throws SQLException
    {
        String query = null;

        if ( toCheck instanceof Integer ) query = "SELECT name FROM categories WHERE id = ? AND root = ? " +
                                                  "UNION " +
                                                  "SELECT name FROM tmp_categories WHERE id = ? AND root = ?";

        if ( toCheck instanceof String ) query = "SELECT name FROM categories WHERE name = ? AND root = ? " +
                                                 "UNION " +
                                                 "SELECT name FROM tmp_categories WHERE id = ? AND root = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( toCheck.toString() );
        parameters.add( String.valueOf( 1 ) );
        parameters.add( toCheck.toString() );
        parameters.add( String.valueOf( 1 ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    }

}
