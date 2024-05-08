package projectClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import util.*;

public class AccessJDBDImpl implements AccessJDBC {
    
    private ConfiguratorJDBC configuratorJDBC = new ConfiguratorJDBCImpl();

    @Override
    public Configurator getPermission() throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_PERMISSION_QUERY );
        return rs.next() ? configuratorJDBC.getConfiguratorByID( rs.getInt( 1 ) ) : null;
    }

    @Override
    public void denyPermission ( Configurator configurator ) throws SQLException 
    {
        Conn.queryUpdate( Queries.DENY_PERMISSION_QUERY, new ArrayList<>( Arrays.asList( configurator.getID() ) ) );
    }

    @Override
    public void allowPermission () throws SQLException 
    {
        Conn.queryUpdate( Queries.ALLOW_PERMISSION_QUERY );
    }

}
