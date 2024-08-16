package model;

import java.sql.*;
import java.util.*;

import util.Conn;
import util.Queries;

public class JDBCConfiguratorRepository implements ConfiguratorRepository {

    @Override
    public Configurator getConfiguratorByUsername ( String username ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_CONFIGURATOR_BY_USERNAME_QUERY, new ArrayList<>( Arrays.asList( username ) ) );
        return rs.next() ? new Configurator( rs.getInt( 1 ) , username, rs.getString( 3 ), rs.getBoolean( 4 ) ) : null;
    }

    @Override
    public Configurator getConfiguratorByID ( int ID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_CONFIGURATOR_BY_ID_QUERY, new ArrayList<>( Arrays.asList( ID ) ) );
        return rs.next() ? new Configurator( ID , rs.getString( 2 ), rs.getString( 3 ), rs.getBoolean( 4 ) ) : null;
    }

    @Override
    public void changeCredentials( String oldUsername, String approvedUsername, String hashedPassword ) throws SQLException 
    {
        Conn.queryUpdate( Queries.CHANGE_CREDENTIALS_QUERY, new ArrayList<>( Arrays.asList( approvedUsername, hashedPassword, oldUsername ) ) );
    }

}