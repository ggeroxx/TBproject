package service.pure_fabrication;

import java.sql.SQLException;
import repository.AccessRepository;
import service.Session;

public interface Authentication {
    
    AccessRepository getAccessJDBC();

    Character authenticate(String username, String password, Session session) throws SQLException;
}