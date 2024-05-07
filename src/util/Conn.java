package util;

import java.sql.*;
import java.util.*;

public class Conn
{
    
    private static final String URL = "jdbc:mysql://localhost/tbdb";
    private static final String USER = "root";
    private static final String PASS = "";
    private static Connection conn;

    public static void openConnection () throws SQLException
    {
        conn = DriverManager.getConnection( URL, USER, PASS );
    }

    public static ResultSet exQuery ( String query ) throws SQLException
    {
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery( query );

        return rs;
    }

    public static <T> ResultSet exQuery ( String query, List<T> parameters ) throws SQLException
    {
        int i = 1;
        
        PreparedStatement stmt = conn.prepareStatement( query );
        for ( T parameter : parameters ) 
        {
            if ( parameter == null ) stmt.setNull( i, java.sql.Types.NULL );
            else if( parameter instanceof Integer ) stmt.setInt( i , Integer.parseInt( parameter.toString() ) );
            else stmt.setString( i, String.valueOf( parameter ) );
            i++;
        }
        ResultSet rs = stmt.executeQuery();

        return rs;
    }

    public static void queryUpdate ( String query ) throws SQLException
    {
        Statement stmt = conn.createStatement();
        
        stmt.executeUpdate( query );
    }

    public static <T> void queryUpdate ( String query, List<T> parameters ) throws SQLException
    {
        int i = 1;
        
        PreparedStatement stmt = conn.prepareStatement( query );
        for ( T parameter : parameters ) 
        {
            if ( parameter == null ) stmt.setString( i, null );
            else if( parameter instanceof Integer ) stmt.setInt( i , Integer.parseInt( parameter.toString() ) );
            else stmt.setString( i, String.valueOf( parameter ) );
            i++;    
        }
        stmt.executeUpdate();
    }

    public static void closeConnection () throws SQLException
    {
        conn.close();
    }

}
