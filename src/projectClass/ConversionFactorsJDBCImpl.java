package projectClass;

import java.sql.*;
import java.util.*;

import util.Conn;

public class ConversionFactorsJDBCImpl implements ConversionFactorsJDBC {
    
    String GET_ALL_QUERY = "SELECT * FROM conversionFactors";

    @Override
    public List<ConversionFactor> getAll() throws SQLException 
    {
        List<ConversionFactor> toReturn = new ArrayList<ConversionFactor>();
        ResultSet rs = Conn.exQuery( GET_ALL_QUERY );
        while ( rs.next() ) toReturn.add( new ConversionFactor( new CategoryJDBCImpl().getCategoryByID( rs.getInt( 1 ) ), new CategoryJDBCImpl().getCategoryByID( rs.getInt( 2 ) ), rs.getDouble( 3 ) ) );
        return toReturn;
    }

}
