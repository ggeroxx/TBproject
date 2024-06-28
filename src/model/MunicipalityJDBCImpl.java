package model;

import java.sql.*;
import java.util.*;
import util.*;

public class MunicipalityJDBCImpl implements MunicipalityJDBC {

    @Override
    public Municipality getMunicipalityByName( String name ) throws SQLException
    {
        ResultSet rs = Conn.exQuery( Queries.SELECT_QUERY, new ArrayList<String>( Arrays.asList( name ) ) );
        return rs.next() ? new Municipality( rs.getInt( 1 ), rs.getString( 3 ), rs.getString( 2 ), rs.getString( 4 ) ) : null;
    }

}