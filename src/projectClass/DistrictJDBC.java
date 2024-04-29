package projectClass;

import java.sql.*;
import java.util.*;

public interface DistrictJDBC {
    
    District getDistrictByName ( String name ) throws SQLException;

    District getDistrictByID ( int ID ) throws SQLException;

    District createDistrict ( String districtName, int configuratorID ) throws SQLException;

    List<District> getAllDistricts () throws SQLException;

    List<District> getAllSavedDistricts () throws SQLException;

    List<District> getAllNotSavedDistricts () throws SQLException;

    void saveTmpDistricts () throws SQLException;

    void deleteTmpDistricts () throws SQLException;

    void dropTmpDistrictsTable () throws SQLException;

    void createTmpTable () throws SQLException;

    Integer getMaxID () throws SQLException;

    void setIDValueAutoIncrement ( int newValue ) throws SQLException;

    void setTmpIDValueAutoIncrement ( int newValue ) throws SQLException;

}
