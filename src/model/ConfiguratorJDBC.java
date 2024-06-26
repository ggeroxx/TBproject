package model;

import java.sql.*;

public interface ConfiguratorJDBC {
    
    Configurator getConfiguratorByUsername ( String username ) throws SQLException;

    Configurator getConfiguratorByID ( int ID ) throws SQLException;

    void changeCredentials ( String oldUsername, String approvedUsername, String newPassword ) throws SQLException;

}
