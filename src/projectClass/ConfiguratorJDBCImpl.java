package projectClass;

import java.sql.*;
import java.util.*;

import util.Conn;

public class ConfiguratorJDBCImpl implements ConfiguratorJDBC {
    
    String GET_CONFIGURATOR_BY_USERNAME_AND_PASSWORD_QUERY = "SELECT id, firstaccess FROM configurators WHERE username = ?";
    String CHANGE_CREDENTIALS_QUERY = "UPDATE configurators SET username = ?, password = ?, firstAccess = 0 WHERE username = ?";

    @Override
    public Configurator getConfiguratorByUsernameAndPassword( String username, String password ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( GET_CONFIGURATOR_BY_USERNAME_AND_PASSWORD_QUERY, new ArrayList<>( Arrays.asList( username ) ) );
        return rs.next() ? new Configurator( rs.getInt( 1 ) , username, password, rs.getBoolean( 2 ) ) : null;
    }

    @Override
    public void changeCredentials( String oldUsername, String approvedUsername, String hashedPassword ) throws SQLException 
    {
        Conn.queryUpdate( CHANGE_CREDENTIALS_QUERY, new ArrayList<>( Arrays.asList( approvedUsername, hashedPassword, oldUsername ) ) );
    }

}