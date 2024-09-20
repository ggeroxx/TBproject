package Server.repository;

import java.sql.SQLException;
import java.util.List;

import Server.model.District;
import Server.model.Municipality;

public interface DistrictToMunicipalitiesRepository {
    
    void addMunicipality ( int districtID, int municipalityID ) throws SQLException;

    boolean isPresentMunicipalityInDistrict ( int districtID, int municipalityID ) throws SQLException;

    List<Municipality> selectAllMunicipalityOfDistrict ( District district ) throws SQLException;

    void saveTmpDistrictToMunicipalities () throws SQLException;

    void deleteTmpDistrictToMunicipalities () throws SQLException;

}
