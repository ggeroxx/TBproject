package projectClass;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

import util.Conn;

public class Session {
    
    private Boolean status;
    private ConfiguratorJDBC configuratorJDBC = new ConfiguratorJDBCImpl();

    public Boolean getStatus() 
    {
        return status;
    }

    public void login ( String usernameToCheck, String passwordToCheck ) throws SQLException
    {
        Configurator conf = configuratorJDBC.getConfiguratorByUsername( usernameToCheck );
        if( conf == null) 
        {
            this.status = false;
            return;
        }
        else
        {
            if ( !BCrypt.checkpw( passwordToCheck, conf.getPassword() ) )
            {
                this.status = false;
                return;
            }
        }
        this.status = true;
        Conn.creationTmpDistrictTable();
        Conn.creationTmpCategoryTable();
    }

    public void logout () throws SQLException
    {
        this.status = false;
        Conn.eliminationTmpTable();
    }

}
