package projectClass;
import util.Conn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class Configurator 
{
    
    private String username;
    private String password;

    public Configurator ( String username, String password ) 
    {
        this.username = username;
        this.password = password;
    }

    public boolean login ()
    {
        String query = "SELECT * FROM configurators WHERE username = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( username );

        ResultSet rs = Conn.exQuery( query, parameters );

        try
        {
            if ( !rs.next() ) return false;
            else
            {
                //rs.next();
                if ( !BCrypt.checkpw( password, rs.getString(3) ) ) return false;
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return true;
    }

    public boolean firstAccess () 
    {
        String query = "SELECT firstAccess FROM configurators WHERE username = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( username );

        ResultSet rs = Conn.exQuery( query, parameters );

        Boolean toReturn = false;
        try 
        {
            rs.next();
            toReturn = rs.getBoolean(1);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return toReturn;
    }

    public boolean attendedUsername ( String newUsername ) 
    {
        String query = "SELECT username FROM configurators WHERE username = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( newUsername );

        ResultSet rs = Conn.exQuery( query, parameters );

        boolean toReturn = false;
        try 
        {
            if ( rs.next() ) toReturn = true;
        } 
        catch ( SQLException e ) 
        {
            e.printStackTrace();
        }

        return toReturn;
    } 

    public void changeCredentials ( String approvedUsername, String newPassword )
    {
        String hashedPassword = BCrypt.hashpw( newPassword, BCrypt.gensalt() );
        String query = "UPDATE configurators SET username = ?, password = ?, firstAccess = 0 WHERE username = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( approvedUsername );
        parameters.add( hashedPassword );
        parameters.add( username );

        Conn.updateRow( query, parameters );
    }

    public boolean checkPatternUsername ( String usernameToCheck ) 
    {
        boolean toReturn = true;

        if ( usernameToCheck.length() < 3 || usernameToCheck.length() > 20 ) toReturn = false;

        return toReturn;
    }

    public boolean checkPatternPassword ( String passwordToCheck ) 
    {
        int contDigit = 0, contChar = 0;

        if ( passwordToCheck.length() < 8 || passwordToCheck.length() > 25 ) return false;

        for ( char character : passwordToCheck.toCharArray() )
            if( Character.isDigit(character) ) contDigit++;
        for ( char character : passwordToCheck.toCharArray() )
            if( Character.isLetter(character) ) contChar++;

        return (contDigit == 0 || contChar == 0) ? false : true;
    }

}
