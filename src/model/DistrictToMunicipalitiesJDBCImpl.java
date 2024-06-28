package model;

import java.sql.*;
import java.util.*;

import util.*;

public class DistrictToMunicipalitiesJDBCImpl implements DistrictToMunicipalitiesJDBC {

    @Override
    public void addMunicipality( int districtID, int municipalityID ) throws SQLException 
    {
        Conn.queryUpdate( Queries.ADD_MUNICIPALITY_QUERY, new ArrayList<>( Arrays.asList( districtID, municipalityID ) ) );  
    }

    @Override
    public boolean isPresentMunicipalityInDistrict( int districtID, int municipalityID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.IS_PRESENT_MUNICIPALITY_IN_DISTRICT_QUERY, new ArrayList<>( Arrays.asList( districtID, municipalityID ) ) );
        return rs.next();
    }

    @Override
    public List<Municipality> selectAllMunicipalityOfDistrict( District district ) throws SQLException 
    {
        List<Municipality> toReturn = new ArrayList<Municipality>();
        ResultSet rs = Conn.exQuery( Queries.SELECT_ALL_MUNICIPALITY_OF_DISTRICT, new ArrayList<>( Arrays.asList( district.getID(), district.getID() ) ) );
        while ( rs.next() ) toReturn.add( new MunicipalityJDBCImpl().getMunicipalityByName( rs.getString( 1 ) ) );
        return toReturn;
    }

    @Override
    public void saveTmpDistrictToMunicipalities () throws SQLException 
    {
        Conn.queryUpdate( Queries.SAVE_TMP_DISTRICT_TO_MUNICIPALITIES_QUERY );
    }

    @Override
    public void deleteTmpDistrictToMunicipalities () throws SQLException 
    {
        Conn.queryUpdate( Queries.DELETE_TMP_DISTRICT_TO_MUNICIPALITIES_QUERY );
    }

}
