package Server.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Server.model.util.Conn;

public class ConnectionService
{

    public static void openConnection () throws SQLException 
    {
        Conn.openConnection();
    }

    public static ResultSet exQuery ( String query ) throws SQLException 
    {
        return Conn.exQuery(query);
    }

    public static <T> ResultSet exQuery ( String query, List<T> parameters ) throws SQLException 
    {
        return Conn.exQuery(query, parameters);
    }

    public static void queryUpdate ( String query ) throws SQLException 
    {
        Conn.queryUpdate(query);
    }

    public static <T> void queryUpdate ( String query, List<T> parameters ) throws SQLException 
    {
        Conn.queryUpdate(query, parameters);
    }

    public static void closeConnection () throws SQLException 
    {
        Conn.closeConnection();
    }

}
