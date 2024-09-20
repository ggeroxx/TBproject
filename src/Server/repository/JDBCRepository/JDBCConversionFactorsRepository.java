package Server.repository.JDBCRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import Server.model.Category;
import Server.model.ConversionFactor;
import Server.model.ConversionFactors;
import Server.model.util.Conn;
import Server.model.util.Queries;
import Server.repository.ConversionFactorsRepository;

public class JDBCConversionFactorsRepository implements ConversionFactorsRepository {

    @Override
    public List<ConversionFactor> getAll() throws SQLException 
    {
        List<ConversionFactor> toReturn = new ArrayList<ConversionFactor>();
        ResultSet rs = Conn.exQuery( Queries.GET_ALL_CONVERION_FACTORS_QUERY );
        while ( rs.next() ) toReturn.add( new ConversionFactor( new JDBCCategoryRepository().getCategoryByID( rs.getInt( 1 ) ), new JDBCCategoryRepository().getCategoryByID( rs.getInt( 2 ) ), rs.getDouble( 3 ) ) );
        return toReturn;
    }

    @Override
    public void saveAll ( ConversionFactors conversionFactors ) throws SQLException 
    {
        for ( Entry<Integer, ConversionFactor> entry : conversionFactors.getList().entrySet() ) save( entry.getValue() );
    }

    private void save ( ConversionFactor conversionFactor ) throws SQLException
    {
        Conn.queryUpdate( Queries.SAVE_CONVERSION_FACTORS_QUERY, new ArrayList<>( Arrays.asList( conversionFactor.getLeaf_1().getID(), conversionFactor.getLeaf_2().getID(), conversionFactor.getValue() ) ) );
    }

    @Override
    public ConversionFactor getConversionFactor ( Category c1, Category c2 ) throws SQLException 
    {
        ResultSet rs = Conn.exQuery( Queries.GET_CONVERSION_FACTOR_QUERY, new ArrayList<>( Arrays.asList( c1.getID(), c2.getID() ) ) );
        return rs.next() ? new ConversionFactor( c1, c2, rs.getDouble( 3 ) ) : null;
    }

}
