package model;

import java.sql.SQLException;

public interface MunicipalityRepository {
    
    Municipality getMunicipalityByName( String name ) throws SQLException;

}