package projectClass;

import java.sql.*;
import java.util.*;

public interface DistrictToMunicipalitiesJDBC {
    
    void addMunicipality ( int districtID, int municipalityID ) throws SQLException;

    boolean isPresentMunicipalityInDistrict ( int districtID, int municipalityID ) throws SQLException;

    List<Municipality> selectAllMunicipalityOfDistrict ( District district ) throws SQLException;

    void saveTmpDistrictToMunicipalities () throws SQLException;

    void deleteTmpDistrictToMunicipalities () throws SQLException;

    void dropTmpDistrictToMunicipalitiesTable () throws SQLException;

    void createTmpTable () throws SQLException;

}
