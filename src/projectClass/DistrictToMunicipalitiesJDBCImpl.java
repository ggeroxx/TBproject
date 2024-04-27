package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class DistrictToMunicipalitiesJDBCImpl implements DistrictToMunicipalitiesJDBC {
    
    String ADD_MUNICIPALITY_QUERY = "INSERT INTO tmp_districttomunicipalities (IDDistrict, IDMunicipality) VALUES (?, ?)";
    String IS_PRESENT_MUNICIPALITY_IN_DISTRICT_QUERY = "SELECT * FROM tmp_districttomunicipalities WHERE iddistrict = ? AND idmunicipality = ?";
    String SELECT_ALL_MUNICIPALITY_OF_DISTRICT = "SELECT municipalities.name FROM districttomunicipalities JOIN municipalities ON districttomunicipalities.idmunicipality = municipalities.id WHERE districttomunicipalities.iddistrict = ? UNION SELECT municipalities.name FROM tmp_districttomunicipalities JOIN municipalities ON tmp_districttomunicipalities.idmunicipality = municipalities.id WHERE tmp_districttomunicipalities.iddistrict = ?";
    String SAVE_TMP_DISTRICT_TO_MUNICIPALITIES_QUERY = "INSERT INTO districttomunicipalities (iddistrict, idmunicipality) SELECT iddistrict, idmunicipality FROM tmp_districttomunicipalities";
    String DELETE_TMP_DISTRICT_TO_MUNICIPALITIES_QUERY = "DELETE FROM tmp_districttomunicipalities";;

    @Override
    public void addMunicipality( int districtID, int municipalityID ) throws SQLException 
    {
        Conn.queryUpdate( ADD_MUNICIPALITY_QUERY, new ArrayList<>( Arrays.asList( districtID, municipalityID ) ) );  
    }

    @Override
    public boolean isPresentMunicipalityInDistrict( int districtID, int municipalityID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( IS_PRESENT_MUNICIPALITY_IN_DISTRICT_QUERY, new ArrayList<>( Arrays.asList( districtID, municipalityID ) ) );
        return rs.next();
    }

    @Override
    public List<Municipality> selectAllMunicipalityOfDistrict( District district ) throws SQLException 
    {
        List<Municipality> toReturn = new ArrayList<Municipality>();
        ResultSet rs = Conn.exQuery( SELECT_ALL_MUNICIPALITY_OF_DISTRICT, new ArrayList<>( Arrays.asList( district.getID(), district.getID() ) ) );
        while ( rs.next() ) toReturn.add( new MunicipalityJDBCImpl().getMunicipalityByName( rs.getString( 1 ) ) );
        return toReturn;
    }

    @Override
    public void saveTmpDistrictToMunicipalities () throws SQLException 
    {
        Conn.exQuery( SAVE_TMP_DISTRICT_TO_MUNICIPALITIES_QUERY );
    }

    @Override
    public void deleteTmpDistrictToMunicipalities () throws SQLException 
    {
        Conn.exQuery( DELETE_TMP_DISTRICT_TO_MUNICIPALITIES_QUERY );
    }

}
