package projectClass;

import java.sql.*;
import util.*;

public class AccessJDBDImpl implements AccessJDBC {
    
    private ConfiguratorJDBC configuratorJDBC = new ConfiguratorJDBCImpl();

    @Override
    public Configurator getPermission() throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_PERMISSION_QUERY );
        return rs.next() ? configuratorJDBC.getConfiguratorByID( rs.getInt( 1 ) ) : null;
    }

}
