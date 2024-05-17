package projectClass;

import java.sql.*;
import java.util.*;

import util.Conn;

public class UserJDBCImpl implements UserJDBC {
    
    @Override
    public User getUserByUsername ( String username ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_USER_BY_USERNAME_QUERY, new ArrayList<>( Arrays.asList( username ) ) );
        return rs.next() ? new User( rs.getInt( 1 ) , username, rs.getString( 3 ), rs.getInt( 4 ), rs.getString( 5 ) ) : null;
    }

    @Override
    public User getUserByID ( int ID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_USER_BY_ID_QUERY, new ArrayList<>( Arrays.asList( ID ) ) );
        return rs.next() ? new User( ID, rs.getString( 2 ), rs.getString( 3 ), rs.getInt( 4 ), rs.getString( 5 ) ) : null;
    }

    @Override
    public void insertUser( User user ) throws SQLException 
    {
        Conn.queryUpdate( Queries.INSERT_USER_QUERY, new ArrayList<>( Arrays.asList( user.getUsername(), user.getPassword(), user.getDistrictID(), user.getMail() ) ) );
    }

}
