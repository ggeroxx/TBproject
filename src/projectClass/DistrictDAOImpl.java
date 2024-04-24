package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class DistrictDAOImpl implements DistrictDAO {
    
    @Override
    public District getDistrictByName( String name ) throws SQLException 
    {
        String query = "SELECT id, idconfigurator FROM districts WHERE name = ? " + 
                       "UNION " +
                       "SELECT id, idconfigurator FROM tmp_districts WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( name );
        parameters.add( name );

        ResultSet rs = Conn.exQuery( query, parameters );

        if ( rs.next() ) return new District( rs.getInt( "id" ), name, rs.getInt( "idconfigurator" ) );
        else return null;
    }

    @Override
    public District getDistrictByID( int ID ) throws SQLException 
    {
        String query = "SELECT name, idconfigurator FROM districts WHERE id = ? " + 
                       "UNION " +
                       "SELECT name, idconfigurator FROM tmp_districts WHERE id = ?";
                       
        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( ID ) );
        parameters.add( Integer.toString( ID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        if ( rs.next() ) return new District( ID, rs.getString( "name" ), rs.getInt( "idconfigurator" ) );
        else return null;
    }

    @Override
    public void addMunicipality( int districtID, int municipalityID ) throws SQLException 
    {
        String query = "INSERT INTO tmp_districttomunicipalities (IDDistrict, IDMunicipality) VALUES (?, ?)";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( districtID ) );
        parameters.add( Integer.toString( municipalityID ) );

        Conn.queryUpdate( query, parameters );  
    }

    @Override
    public boolean isPresentMunicipalityInDistrict(int districtID, int municipalityID) throws SQLException 
    {
        String query = "SELECT * FROM tmp_districttomunicipalities WHERE iddistrict = ? AND idmunicipality = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( districtID ) );
        parameters.add( Integer.toString( municipalityID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    }

}
