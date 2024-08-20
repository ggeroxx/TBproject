package service;

import java.sql.*;
import model.*;

public class SubjectService{
    
    private ConfiguratorService configuratorService;
    private UserService userService;

    public SubjectService ( ConfiguratorService configuratorService, UserService userService ) 
    {
        this.configuratorService = configuratorService;
        this.userService = userService;
    }

    public boolean isPresentUsername ( String usernameToCheck ) throws SQLException
    {
        Configurator configurator = this.configuratorService.getconfiguratorRepository().getConfiguratorByUsername( usernameToCheck );
        User user = this.userService.getuserRepository().getUserByUsername( usernameToCheck );
        return configurator != null || user != null;
    }

}