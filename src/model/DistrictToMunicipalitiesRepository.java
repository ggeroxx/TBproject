package model;

import java.sql.*;
import java.util.*;

public interface DistrictToMunicipalitiesRepository {
    
    void addMunicipality ( int districtID, int municipalityID ) throws SQLException;

    boolean isPresentMunicipalityInDistrict ( int districtID, int municipalityID ) throws SQLException;

    List<Municipality> selectAllMunicipalityOfDistrict ( District district ) throws SQLException;

    void saveTmpDistrictToMunicipalities () throws SQLException;

    void deleteTmpDistrictToMunicipalities () throws SQLException;

}
