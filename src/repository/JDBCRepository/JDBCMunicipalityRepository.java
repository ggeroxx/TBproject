package repository.JDBCRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import model.Municipality;
import model.util.Conn;
import model.util.Queries;
import repository.MunicipalityRepository;

public class JDBCMunicipalityRepository implements MunicipalityRepository {

    @Override
    public Municipality getMunicipalityByName( String name ) throws SQLException
    {
        ResultSet rs = Conn.exQuery( Queries.SELECT_QUERY, new ArrayList<String>( Arrays.asList( name ) ) );
        return rs.next() ? new Municipality( rs.getInt( 1 ), rs.getString( 3 ), rs.getString( 2 ), rs.getString( 4 ) ) : null;
    }

}