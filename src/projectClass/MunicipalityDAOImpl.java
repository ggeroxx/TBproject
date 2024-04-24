package projectClass;


import java.sql.*;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import util.*;

public class MunicipalityDAOImpl implements MunicipalityDAO {

    private 

    String SELECT_QUERY = "SELECT id, cap, province FROM municipalities WHERE name = ?";

    @Override
    public Municipality getMunicipalityByName( String name ) throws SQLException
    {
        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( name );

        ResultSet rs = Conn.exQuery( SELECT_QUERY, parameters );

        if ( rs.next() ) return new Municipality( rs.getInt( "id" ), name, rs.getString( "cap" ), rs.getString( "province" ) );
        else return null;
    }

    private static class MunicipalityRowMapper implements RowMapper<Municipality>
    {
        @Override
        public Municipality mapRow( ResultSet rs, int rowNum ) throws SQLException 
        {
            return new Municipality( rs.getInt( "id" ), rs.getString( "name" ), rs.getString( "cap" ), rs.getString( "province" ) );
        }
    }

}