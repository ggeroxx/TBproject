package controller.GRASPController;

import java.sql.*;
import model.*;

public class SubjectGRASPController {
    
    private ConfiguratorRepository configuratorRepository;
    private UserRepository userRepository;

    public SubjectGRASPController ( ) 
    {
        this.configuratorRepository = new JDBCConfiguratorRepository();
        this.userRepository = new JDBCUserRepository();
    }

    public boolean isPresentUsername ( String usernameToCheck ) throws SQLException
    {
        Configurator configurator = this.configuratorRepository.getConfiguratorByUsername( usernameToCheck );
        User user = this.userRepository.getUserByUsername( usernameToCheck );
        return configurator != null || user != null;
    }

}
