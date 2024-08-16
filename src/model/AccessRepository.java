package model;

import java.sql.*;

public interface AccessRepository {
    
    Configurator getPermission () throws SQLException;

    void denyPermission ( Configurator configurator ) throws SQLException;

    void allowPermission () throws SQLException;

}
