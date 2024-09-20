package Server.repository;

import java.sql.SQLException;

import Server.model.Municipality;

public interface MunicipalityRepository {
    
    Municipality getMunicipalityByName( String name ) throws SQLException;

}