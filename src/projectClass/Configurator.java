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

    public boolean getFirstAccess()
    {
        return this.firstAccess;
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

    public District createDistrict ( String districtName ) throws SQLException
    {
        if ( Controls.isPresentDistrict( districtName ) ) return null;

        String query = "INSERT INTO tmp_districts (name, idconfigurator) VALUES (?, ?)";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( districtName );
        parameters.add( Integer.toString(this.ID) );

        Conn.queryUpdate( query, parameters );

        District newDistrict = new District( districtName );

        return newDistrict;
    }

    public Category createCategory ( String name, String field, String description, boolean isRoot, Integer hierarchyID ) throws SQLException
    {
        String query;
        if ( isRoot )
        {
            query = "SELECT MAX(id) AS max_id FROM ( " +
                    "SELECT id FROM categories " +
                    "UNION " +
                    "SELECT id FROM tmp_categories " +
                    ") AS combinedTables";

            ResultSet rs = Conn.exQuery( query );
            rs.next();

            hierarchyID = rs.getInt( 1 ) + 1;
        }

        query = "INSERT INTO tmp_categories (name, field, description, root, hierarchyid, idconfigurator) VALUES (?, ?, ?, ?, ?, ?)";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( name );
        parameters.add( field );
        parameters.add( description );
        parameters.add( String.valueOf( isRoot ? 1 : 0 ) );
        parameters.add( Integer.toString( hierarchyID ) );
        parameters.add( Integer.toString( this.ID ) );

        Conn.queryUpdate( query, parameters );

        Category newCategory = new Category( name, field, description, isRoot, hierarchyID, this.ID );

        return newCategory;
    }

    public void saveAll () throws SQLException
    {
        Save.saveAll();
    }

}
