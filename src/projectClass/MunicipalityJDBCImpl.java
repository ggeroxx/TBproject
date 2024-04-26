package projectClass;

import java.sql.*;
import java.util.*;
import util.*;

public class MunicipalityJDBCImpl implements MunicipalityJDBC {

    String SELECT_QUERY = "SELECT id, cap, province FROM municipalities WHERE name = ?";

    @Override
    public Municipality getMunicipalityByName( String name ) throws SQLException
    {
        ResultSet rs = Conn.exQuery( SELECT_QUERY, new ArrayList<String>( Arrays.asList( name ) ) );
        return rs.next() ? new Municipality( rs.getInt( "id" ), name, rs.getString( "cap" ), rs.getString( "province" ) ) : null;
    }

}