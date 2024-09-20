package Server.repository;

import java.sql.SQLException;
import java.util.List;

import Server.model.District;

public interface DistrictRepository {
    
    District getDistrictByName ( String name ) throws SQLException;

    District getDistrictByID ( int ID ) throws SQLException;

    District createDistrict ( String districtName, int configuratorID ) throws SQLException;

    List<District> getAllDistricts () throws SQLException;

    List<District> getAllSavedDistricts () throws SQLException;

    List<District> getAllNotSavedDistricts () throws SQLException;

    void saveTmpDistricts () throws SQLException;

    void deleteTmpDistricts () throws SQLException;

    Integer getMaxID () throws SQLException;

    void setTmpIDValueAutoIncrement ( int newValue ) throws SQLException;

    District getOneDistrictForTest () throws SQLException;

}
