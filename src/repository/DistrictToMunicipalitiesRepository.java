package repository;

import java.sql.SQLException;
import java.util.List;
import model.District;
import model.Municipality;

public interface DistrictToMunicipalitiesRepository {
    
    void addMunicipality ( int districtID, int municipalityID ) throws SQLException;

    boolean isPresentMunicipalityInDistrict ( int districtID, int municipalityID ) throws SQLException;

    List<Municipality> selectAllMunicipalityOfDistrict ( District district ) throws SQLException;

    void saveTmpDistrictToMunicipalities () throws SQLException;

    void deleteTmpDistrictToMunicipalities () throws SQLException;

}
