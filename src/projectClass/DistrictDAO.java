package projectClass;

import java.sql.SQLException;

public interface DistrictDAO {
    
    District getDistrictByName( String name ) throws SQLException;

    District getDistrictByID ( int ID ) throws SQLException;

    void addMunicipality ( int districtID, int municipalityID ) throws SQLException;

    boolean isPresentMunicipalityInDistrict ( int districtID, int municipalityID ) throws SQLException;

}
