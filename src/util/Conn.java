package util;
import java.sql.*;
import java.util.ArrayList;

public class Conn 
{
    
    private static String url = "jdbc:mysql://localhost/TBDB";
    private static String user = "root";
    private static String pass = "";
    private static Connection conn;

    public static void openConnection () 
    {
        try 
        {
            conn = DriverManager.getConnection(url, user, pass);
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public static ResultSet exQuery ( String query, ArrayList<String> parameters )
    {
        ResultSet rs = null;
        int i = 1;
        
        try 
        {
            PreparedStatement stmt = conn.prepareStatement( query );
            for ( String parameter : parameters ) 
            {
                stmt.setString( i, parameter );
                i++;    
            }
            rs = stmt.executeQuery();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return rs;
    }

    public static void updateRow ( String query, ArrayList<String> parameters )
    {
        int i = 1;
        
        try 
        {
            PreparedStatement stmt = conn.prepareStatement( query );
            for ( String parameter : parameters ) 
            {
                stmt.setString( i, parameter );
                i++;    
            }
            stmt.executeUpdate();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public static void closeConnection ()
    {
        try 
        {
            conn.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
