package projectClass;

import java.sql.*;
import java.util.*;

public interface ConversionFactorsJDBC {
    
    List<ConversionFactor> getAll () throws SQLException;

    void saveAll ( ConversionFactors conversionFactors ) throws SQLException;

    ConversionFactor getConversionFactor ( Category c1, Category c2 ) throws SQLException;
    
}
