package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class DistrictJDBCImpl implements DistrictJDBC {
    
    String GET_DISTRICT_BY_NAME_QUERY = "SELECT id, idconfigurator FROM districts WHERE name = ? UNION SELECT id, idconfigurator FROM tmp_districts WHERE name = ?";
    String GET_DISTRICT_BY_ID_QUERY = "SELECT name, idconfigurator FROM districts WHERE id = ? UNION SELECT name, idconfigurator FROM tmp_districts WHERE id = ?";
    String CREATE_DISTRICT_QUERY = "INSERT INTO tmp_districts (name, idconfigurator) VALUES (?, ?)";
    String GET_ALL_SAVED_DISTRICTS = "SELECT id FROM districts";
    String GET_ALL_NOT_SAVED_DISTRICTS = "SELECT id FROM tmp_districts";

    @Override
    public District getDistrictByName( String name ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( GET_DISTRICT_BY_NAME_QUERY, new ArrayList<>( Arrays.asList( name ) ) );
        return rs.next() ? new District( rs.getInt( "id" ), name, rs.getInt( "idconfigurator" ) ) : null;
    }

    @Override
    public District getDistrictByID( int ID ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( GET_DISTRICT_BY_ID_QUERY, new ArrayList<>( Arrays.asList( ID ) ) );
        return rs.next() ? new District( ID, rs.getString( "name" ), rs.getInt( "idconfigurator" ) ) : null;
    }

    @Override
    public District createDistrict( String districtName, int configuratorID ) throws SQLException 
    {
        Conn.queryUpdate( CREATE_DISTRICT_QUERY, new ArrayList<>( Arrays.asList( districtName, configuratorID ) ) );
        return new DistrictJDBCImpl().getDistrictByName( districtName );
    }

    @Override
    public List<District> getAllSavedDistricts() throws SQLException 
    {
        return getAll( GET_ALL_SAVED_DISTRICTS );
    }

    @Override
    public List<District> getAllNotSavedDistricts() throws SQLException 
    {
        return getAll( GET_ALL_NOT_SAVED_DISTRICTS );
    }

    private List<District> getAll( String query ) throws SQLException
    {
        List<District> toReturn = new ArrayList<District>();
        ResultSet rs = Conn.exQuery( query );
        while ( rs.next() ) toReturn.add( getDistrictByID( rs.getInt( 1 ) ) );
        return toReturn;
    }

}
