package repository;

import java.sql.SQLException;
import model.Configurator;

public interface ConfiguratorRepository {
    
    Configurator getConfiguratorByUsername ( String username ) throws SQLException;

    Configurator getConfiguratorByID ( int ID ) throws SQLException;

    void changeCredentials ( String oldUsername, String approvedUsername, String newPassword ) throws SQLException;

    Configurator getOneConfiguratorForTest () throws SQLException;

    Configurator getNewConfiguratorForTest () throws SQLException;

    void deleteNewConfiguratorForTest (String userName) throws SQLException;

}
