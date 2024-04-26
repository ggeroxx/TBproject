package projectClass;

import java.sql.*;

public interface ConfiguratorJDBC {
    
    Configurator getConfiguratorByUsernameAndPassword ( String username, String password ) throws SQLException;

    void changeCredentials ( String oldUsername, String approvedUsername, String newPassword ) throws SQLException;

}
