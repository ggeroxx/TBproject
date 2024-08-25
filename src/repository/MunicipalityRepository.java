package repository;

import java.sql.SQLException;

import model.Municipality;

public interface MunicipalityRepository {
    
    Municipality getMunicipalityByName( String name ) throws SQLException;

}