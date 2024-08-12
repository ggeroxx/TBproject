package model;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationService_PureFabrication {
    private ConfiguratorJDBC configuratorJDBC;
    private UserJDBC userJDBC;
    private AccessJDBC accessJDBC;

    public AuthenticationService_PureFabrication( ConfiguratorJDBC configuratorJDBC, UserJDBC userJDBC, AccessJDBC accessJDBC ) 
    {
        this.configuratorJDBC = configuratorJDBC;
        this.userJDBC = userJDBC;
        this.accessJDBC = accessJDBC;
    }

    public AccessJDBC getAccessJDBC() 
    {
        return this.accessJDBC;
    }

    /**
     * @param username
     * @param password
     * @param session
     * @return
     * @throws SQLException
     */
    public Character authenticate( String username, String password, Session session ) throws SQLException 
    {
        Configurator conf = configuratorJDBC.getConfiguratorByUsername( username );
        User user = userJDBC.getUserByUsername( username );

        if ( conf != null && BCrypt.checkpw(password, conf.getPassword()) ) 
        {
            if ( accessJDBC.getPermission() == null ) 
            {
                session.setStatus(true);
                accessJDBC.denyPermission( conf );
                return 'c';
            }
            else session.setStatus(false);

        } else if ( user != null && BCrypt.checkpw( password, user.getPassword() ) )
        {
            session.setStatus(true);
            return 'u';
        }

        session.setStatus(false);
        return null;
    }
}
