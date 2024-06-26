package model;

import java.sql.*;

public interface UserJDBC {
    
    User getUserByUsername ( String username ) throws SQLException;

    User getUserByID ( int ID ) throws SQLException;

    void insertUser ( User user ) throws SQLException;

}
