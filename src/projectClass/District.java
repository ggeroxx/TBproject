package projectClass;
import util.*;
import java.sql.*;
import java.util.ArrayList;

public class District {
    
    private int ID;
    private String name;
    private int IDConfigurator;

    public District ( String name ) throws SQLException
    {
        this.name = name;
        this.ID = takeID();
        this.IDConfigurator = takeIDConfigurator();
    }

    public District ( int ID ) throws SQLException
    {
        this.ID = ID;
        this.name = takeName();
        this.IDConfigurator = takeIDConfigurator();
    }

    public String getName() 
    {
        return name;
    }

    private int takeID () throws SQLException
    {
        String query = "SELECT id FROM districts WHERE name = ? " +
                       "UNION " +
                       "SELECT id FROM tmp_districts WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( this.name );
        parameters.add( this.name );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        return rs.getInt( 1 );
    }

    private String takeName() throws SQLException
    {
        String query = "SELECT name FROM districts WHERE id = ? " +
                       "UNION " +
                       "SELECT name FROM tmp_districts WHERE id = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( this.ID ) );
        parameters.add( Integer.toString( this.ID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        return rs.getString( 1 );
    }

    private int takeIDConfigurator () throws SQLException
    {
        String query = "SELECT idconfigurator FROM districts WHERE name = ? " +
                       "UNION " +
                       "SELECT idconfigurator FROM tmp_districts WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( this.name );
        parameters.add( this.name );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        return rs.getInt( 1 );
    }

    public boolean isPresentMunicipalityInDistrict ( Municipality municipalityToCheck ) throws SQLException
    {
        String query = "SELECT * FROM tmp_districttomunicipalities WHERE iddistrict = ? AND idmunicipality = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( this.ID ) );
        parameters.add( Integer.toString( municipalityToCheck.getID() ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    }

    public void addMunicipality ( Municipality municipalityToAdd ) throws SQLException
    {
        String query = "INSERT INTO tmp_districttomunicipalities (IDDistrict, IDMunicipality) VALUES (?, ?)";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( this.ID ) );
        parameters.add( Integer.toString( municipalityToAdd.getID() ) );

        Conn.queryUpdate( query, parameters );  
    }

    public String printAllMunicipalities () throws SQLException
    {
        String query = "SELECT municipalities.name " +
                       "FROM districttomunicipalities " +
                       "JOIN municipalities ON districttomunicipalities.idmunicipality = municipalities.id " + 
                       "WHERE districttomunicipalities.iddistrict = ?" +
                       "UNION " +
                       "SELECT municipalities.name " +
                       "FROM tmp_districttomunicipalities " +
                       "JOIN municipalities ON tmp_districttomunicipalities.idmunicipality = municipalities.id " + 
                       "WHERE tmp_districttomunicipalities.iddistrict = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( this.ID ) );
        parameters.add( Integer.toString( this.ID ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        Municipality tmp;
        StringBuffer toReturn = new StringBuffer();
        while ( rs.next() )
        {
            tmp = new Municipality( rs.getString(1) );
            toReturn.append( tmp.toString() );
        }

        return toReturn.toString();
    }

    @Override
    public String toString() 
    {
        StringBuffer toReturn = new StringBuffer();

        toReturn.append( " " + this.ID + ". " + this.name);

        return toReturn.toString();
    }

}
