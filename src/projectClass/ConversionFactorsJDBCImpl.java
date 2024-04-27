package projectClass;

import java.sql.*;
import java.util.*;
import java.util.Map.*;
import util.*;

public class ConversionFactorsJDBCImpl implements ConversionFactorsJDBC {
    
    String GET_ALL_QUERY = "SELECT * FROM conversionFactors";
    String SAVE_QUERY = "INSERT IGNORE INTO conversionFactors (ID_leaf_1, ID_leaf_2, value) VALUES (?, ? ,?)";;

    @Override
    public List<ConversionFactor> getAll() throws SQLException 
    {
        List<ConversionFactor> toReturn = new ArrayList<ConversionFactor>();
        ResultSet rs = Conn.exQuery( GET_ALL_QUERY );
        while ( rs.next() ) toReturn.add( new ConversionFactor( new CategoryJDBCImpl().getCategoryByID( rs.getInt( 1 ) ), new CategoryJDBCImpl().getCategoryByID( rs.getInt( 2 ) ), rs.getDouble( 3 ) ) );
        return toReturn;
    }

    @Override
    public void saveAll ( ConversionFactors conversionFactors ) throws SQLException 
    {
        for ( Entry<Integer, ConversionFactor> entry : conversionFactors.getList().entrySet() ) save( entry.getValue() );
    }

    private void save ( ConversionFactor conversionFactor ) throws SQLException
    {
        Conn.exQuery( SAVE_QUERY, new ArrayList<>( Arrays.asList( conversionFactor.getLeaf_1().getID(), conversionFactor.getLeaf_2().getID(), conversionFactor.getValue() ) ) );
    }

}
