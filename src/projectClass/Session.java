package projectClass;

import java.sql.*;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;

import util.Conn;

public class Session {
    
    private Boolean status;

    public Boolean getStatus() 
    {
        return status;
    }

    public void login ( String usernameToCheck, String passwordToCheck ) throws SQLException
    {
        String query = "SELECT * FROM configurators WHERE username = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( usernameToCheck );

        ResultSet rs = Conn.exQuery( query, parameters );

        if ( !rs.next() ) this.status = false;
        else
        {
            if ( !BCrypt.checkpw( passwordToCheck, rs.getString(3) ) ) this.status = false;
        }

        this.status = true;
    }

    public void logout () 
    {
        this.status = false;
    }

}
