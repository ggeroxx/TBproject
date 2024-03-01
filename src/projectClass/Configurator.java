package projectClass;
import util.*;
import java.sql.*;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class Configurator 
{
    
    private int ID;
    private String username;
    private String password;
    private boolean firstAccess;

    public Configurator ( String username, String password ) throws SQLException
    {
        this.username = username;
        this.password = password;
        this.ID = takeID();
        this.firstAccess = takeFirstAccess();
    }

    private int takeID () throws SQLException
    {
        String query = "SELECT id FROM configurators WHERE username = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( this.username );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        return rs.getInt( 1 );
    }

    private boolean takeFirstAccess () throws SQLException
    {
        String query = "SELECT firstaccess FROM configurators WHERE username = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( this.username );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        return rs.getBoolean( 1 );
    }

    public static boolean login ( String usernameToCheck, String passwordToCheck ) throws SQLException
    {
        String query = "SELECT * FROM configurators WHERE username = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( usernameToCheck );

        ResultSet rs = Conn.exQuery( query, parameters );

        if ( !rs.next() ) return false;
        else
        {
            if ( !BCrypt.checkpw( passwordToCheck, rs.getString(3) ) ) return false;
        }

        return true;
    }

    public boolean getFirstAccess()
    {
        return this.firstAccess;
    }

    public static boolean isPresentUsername ( String usernameToCheck ) throws SQLException
    {
        String query = "SELECT username FROM configurators WHERE username = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( usernameToCheck );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    } 

    public void changeCredentials ( String approvedUsername, String newPassword ) throws SQLException
    {
        String hashedPassword = BCrypt.hashpw( newPassword, BCrypt.gensalt() );
        String query = "UPDATE configurators SET username = ?, password = ?, firstAccess = 0 WHERE username = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( approvedUsername );
        parameters.add( hashedPassword );
        parameters.add( username );

        Conn.queryUpdate( query, parameters );

        this.username = approvedUsername;
        this.password = newPassword;
        this.firstAccess = false;
    }

    public static boolean checkPatternUsername ( String usernameToCheck ) 
    {
        boolean toReturn = true;

        if ( usernameToCheck.length() < 3 || usernameToCheck.length() > 20 ) toReturn = false;

        return toReturn;
    }

    public static boolean checkPatternPassword ( String passwordToCheck ) 
    {
        int contDigit = 0, contChar = 0;

        if ( passwordToCheck.length() < 8 || passwordToCheck.length() > 25 ) return false;

        for ( char character : passwordToCheck.toCharArray() )
            if( Character.isDigit(character) ) contDigit++;
        for ( char character : passwordToCheck.toCharArray() )
            if( Character.isLetter(character) ) contChar++;

        return (contDigit == 0 || contChar == 0) ? false : true;
    }

    public District createDistrict ( String districtName ) throws SQLException
    {
        if ( District.isPresentDistrict( districtName ) ) return null;

        String query = "INSERT INTO tmp_districts (name, idconfigurator) VALUES (?, ?)";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( districtName );
        parameters.add( Integer.toString(this.ID) );

        Conn.queryUpdate( query, parameters );

        District newDistrict = new District( districtName );

        return newDistrict;
    }

    public void saveAll () throws SQLException
    {
        District.saveAll();
    }

}
