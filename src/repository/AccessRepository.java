package repository;

import java.sql.SQLException;

import model.Configurator;

public interface AccessRepository {
    
    Configurator getPermission () throws SQLException;

    void denyPermission ( Configurator configurator ) throws SQLException;

    void allowPermission () throws SQLException;

}
