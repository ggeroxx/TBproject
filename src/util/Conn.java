package util;
import java.sql.*;
import java.util.ArrayList;

public class Conn
{
    
    private static String url = "jdbc:mysql://localhost/TBDB";
    private static String user = "root";
    private static String pass = "";
    private static Connection conn;

    public static void openConnection () throws SQLException
    {
        conn = DriverManager.getConnection(url, user, pass);
    }

    public static ResultSet exQuery ( String query ) throws SQLException
    {
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery( query );

        return rs;
    }

    public static ResultSet exQuery ( String query, ArrayList<String> parameters ) throws SQLException
    {
        int i = 1;
        
        PreparedStatement stmt = conn.prepareStatement( query );
        for ( String parameter : parameters ) 
        {
            stmt.setString( i, parameter );
            i++;    
        }
        ResultSet rs = stmt.executeQuery();

        return rs;
    }

    public static void queryUpdate ( String query, ArrayList<String> parameters ) throws SQLException
    {
        int i = 1;
        
        PreparedStatement stmt = conn.prepareStatement( query );
        for ( String parameter : parameters ) 
        {
            stmt.setString( i, parameter );
            i++;    
        }
        stmt.executeUpdate();
    }

    public static void closeConnection () throws SQLException
    {
        conn.close();
    }

}
