package service.pure_fabrication;

import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import model.Configurator;
import model.User;
import repository.AccessRepository;
import repository.ConfiguratorRepository;
import repository.UserRepository;
import service.Session;

public class AuthenticationService implements Authentication{
    
    private ConfiguratorRepository configuratorRepository;
    private UserRepository userRepository;
    private AccessRepository accessRepository;

    public AuthenticationService( ConfiguratorRepository configuratorRepository, UserRepository userRepository, AccessRepository accessRepository ) 
    {
        this.configuratorRepository = configuratorRepository;
        this.userRepository = userRepository;
        this.accessRepository = accessRepository;
    }

    @Override
    public AccessRepository getAccessJDBC() 
    {
        return this.accessRepository;
    }

    @Override
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
