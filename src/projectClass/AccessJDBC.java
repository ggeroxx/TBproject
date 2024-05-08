package projectClass;

import java.sql.*;

public interface AccessJDBC {
    
    Configurator getPermission () throws SQLException;

    void denyPermission ( Configurator configurator ) throws SQLException;

    void allowPermission () throws SQLException;

}
