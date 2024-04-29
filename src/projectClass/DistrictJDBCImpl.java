package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class DistrictJDBCImpl implements DistrictJDBC {

    @Override
    public District getDistrictByName( String name ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_DISTRICT_BY_NAME_QUERY, new ArrayList<>( Arrays.asList( name ) ) );
        return rs.next() ? new District( rs.getInt( 1 ), name, rs.getInt( 2 ) ) : null;
    }

    @Override
    public District getDistrictByID( int ID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_DISTRICT_BY_ID_QUERY, new ArrayList<>( Arrays.asList( ID ) ) );
        return rs.next() ? new District( ID, rs.getString( 1 ), rs.getInt( 2 ) ) : null;
    }

    @Override
    public District createDistrict( String districtName, int configuratorID ) throws SQLException 
    {
        Conn.queryUpdate( Queries.CREATE_DISTRICT_QUERY, new ArrayList<>( Arrays.asList( districtName, configuratorID ) ) );
        return new DistrictJDBCImpl().getDistrictByName( districtName );
    }

    @Override
    public List<District> getAllDistricts () throws SQLException 
    {
        return getAll( Queries.GET_ALL_DISTRICTS_QUERY );
    }

    @Override
    public List<District> getAllSavedDistricts() throws SQLException 
    {
        return getAll( Queries.GET_ALL_SAVED_DISTRICTS );
    }

    @Override
    public List<District> getAllNotSavedDistricts() throws SQLException 
    {
        return getAll( Queries.GET_ALL_NOT_SAVED_DISTRICTS );
    }

    private List<District> getAll( String query ) throws SQLException
    {
        List<District> toReturn = new ArrayList<District>();
        ResultSet rs = Conn.exQuery( query );
        while ( rs.next() ) toReturn.add( getDistrictByID( rs.getInt( 1 ) ) );
        return toReturn;
    }

    @Override
    public void saveTmpDistricts () throws SQLException 
    {
        Conn.queryUpdate( Queries.SAVE_TMP_DISTRICTS_QUERY );
    }

    @Override
    public void deleteTmpDistricts () throws SQLException 
    {
        Conn.queryUpdate( Queries.DELETE_TMP_DISTRICTS_QUERY );
    }

    @Override
    public void dropTmpDistrictsTable () throws SQLException 
    {
        Conn.queryUpdate( Queries.DROP_TMP_DISTRICTS_TABLE_QUERY );   
    }

    @Override
    public void createTmpTable () throws SQLException 
    {
        Conn.queryUpdate( Queries.CREATE_TMP_DISTRICT_TABLE_QUERY );
    }

    @Override
    public Integer getMaxID () throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_MAX_ID_DISTRICT_QUERY );
        return rs.next() ? rs.getInt( 1 ) : null;
    }

    @Override
    public void setIDValueAutoIncrement ( int newValue ) throws SQLException 
    {
        Conn.exQuery( Queries.SET_DISTRICT_ID_VALUE_AUTO_INCREMENT_QUERY, new ArrayList<>( Arrays.asList( newValue ) ) );
    }

    @Override
    public void setTmpIDValueAutoIncrement ( int newValue ) throws SQLException 
    {
        Conn.exQuery( Queries.SET_DISTRICT_TMP_ID_VALUE_AUTO_INCREMENT_QUERY, new ArrayList<>( Arrays.asList( newValue ) ) );
    }

}
