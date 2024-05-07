package projectClass;

import java.sql.*;

public interface UserJDBC {
    
    User getUserByUsername ( String username ) throws SQLException;

    void insertUser ( User user ) throws SQLException;

}
