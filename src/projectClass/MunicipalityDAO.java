package projectClass;

import java.sql.SQLException;

public interface MunicipalityDAO {
    
    Municipality getMunicipalityByName( String name ) throws SQLException;

}