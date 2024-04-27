package projectClass;

import java.sql.*;
import java.util.*;

public interface ConversionFactorsJDBC {
    
    List<ConversionFactor> getAll () throws SQLException;

    void saveAll ( ConversionFactors conversionFactors ) throws SQLException;

}
