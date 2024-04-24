package projectClass;

import java.sql.*;

public interface CategoryDAO {
    
    Category getCategoryByID ( int ID ) throws SQLException;

}
