package model;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationService_PureFabrication {
    private ConfiguratorRepository configuratorRepository;
    private UserRepository userRepository;
    private AccessRepository accessRepository;

    public AuthenticationService_PureFabrication( ConfiguratorRepository configuratorRepository, UserRepository userRepository, AccessRepository accessRepository ) 
    {
        this.configuratorRepository = configuratorRepository;
        this.userRepository = userRepository;
        this.accessRepository = accessRepository;
    }

    public AccessRepository getAccessJDBC() 
    {
        return this.accessRepository;
    }

    public Character authenticate( String username, String password, Session session ) throws SQLException 
    {
        Configurator conf = configuratorRepository.getConfiguratorByUsername( username );
        User user = userRepository.getUserByUsername( username );

        if ( conf != null && BCrypt.checkpw(password, conf.getPassword()) ) 
        {
            if ( accessRepository.getPermission() == null ) 
            {
                session.setStatus(true);
                accessRepository.denyPermission( conf );
            }
            else session.setStatus(false);

            return 'c';

        } else if ( user != null && BCrypt.checkpw( password, user.getPassword() ) )
        {
            session.setStatus(true);
            return 'u';
        }

        session.setStatus(false);
        return null;
    }
}
