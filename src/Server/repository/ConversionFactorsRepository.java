package Server.repository;

import java.sql.SQLException;
import java.util.List;

import Server.model.Category;
import Server.model.ConversionFactor;
import Server.model.ConversionFactors;

public interface ConversionFactorsRepository {
    
    List<ConversionFactor> getAll () throws SQLException;

    void saveAll ( ConversionFactors conversionFactors ) throws SQLException;

    ConversionFactor getConversionFactor ( Category c1, Category c2 ) throws SQLException;
    
}
