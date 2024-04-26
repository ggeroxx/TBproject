package projectClass;

import java.sql.SQLException;

public interface MunicipalityJDBC {
    
    Municipality getMunicipalityByName( String name ) throws SQLException;

}