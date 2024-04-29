package projectClass;

import java.sql.*;

public interface ConfiguratorJDBC {
    
    Configurator getConfiguratorByUsername ( String username ) throws SQLException;

    void changeCredentials ( String oldUsername, String approvedUsername, String newPassword ) throws SQLException;

}
