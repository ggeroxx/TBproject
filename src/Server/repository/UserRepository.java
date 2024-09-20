package Server.repository;

import java.sql.SQLException;

import Server.model.User;

public interface UserRepository {
    
    User getUserByUsername ( String username ) throws SQLException;

    User getUserByID ( int ID ) throws SQLException;

    void insertUser ( User user ) throws SQLException;

    void deleteNewUserForTest ( String userName ) throws SQLException;

}
