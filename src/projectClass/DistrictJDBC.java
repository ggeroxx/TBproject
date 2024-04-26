package projectClass;

import java.sql.*;
import java.util.*;

public interface DistrictJDBC {
    
    District getDistrictByName ( String name ) throws SQLException;

    District getDistrictByID ( int ID ) throws SQLException;

    District createDistrict ( String districtName, int configuratorID ) throws SQLException;

    List<District> getAllSavedDistricts () throws SQLException;

    List<District> getAllNotSavedDistricts () throws SQLException;

}
