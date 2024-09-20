package Server.service.pure_fabrication;

import java.sql.SQLException;

public interface TemporaryOperations {
    
    void prepareTemporaryData() throws SQLException;

    void clearTemporaryData() throws SQLException;
}