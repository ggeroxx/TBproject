package Server.service.pure_fabrication;

import java.sql.SQLException;

import Server.repository.AccessRepository;
import Server.service.Session;

public interface Authentication {
    
    AccessRepository getAccessJDBC();

    Character authenticate(String username, String password, Session session) throws SQLException;
}