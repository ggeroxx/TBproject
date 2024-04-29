package projectClass;

import java.sql.*;
import java.util.*;

import util.Conn;

public class ConfiguratorJDBCImpl implements ConfiguratorJDBC {

    @Override
    public Configurator getConfiguratorByUsername ( String username ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_CONFIGURATOR_BY_USERNAME_AND_PASSWORD_QUERY, new ArrayList<>( Arrays.asList( username ) ) );
        return rs.next() ? new Configurator( rs.getInt( 1 ) , username, rs.getString( 3 ), rs.getBoolean( 4 ) ) : null;
    }

    @Override
    public void changeCredentials( String oldUsername, String approvedUsername, String hashedPassword ) throws SQLException 
    {
        Conn.queryUpdate( Queries.CHANGE_CREDENTIALS_QUERY, new ArrayList<>( Arrays.asList( approvedUsername, hashedPassword, oldUsername ) ) );
    }

}