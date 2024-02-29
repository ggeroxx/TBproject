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

    private int takeID () throws SQLException
    {
        String query = "SELECT id FROM districts WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( this.name );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        return rs.getInt( 1 );
    }

    private int takeIDConfigurator () throws SQLException
    {
        String query = "SELECT idconfigurator FROM districts WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( this.name );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        return rs.getInt( 1 );
    }

    public static boolean isPresentDistrict ( String nameToCheck ) throws SQLException
    {
        String query = "SELECT name FROM districts WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( nameToCheck );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    }

    public boolean isPresentMunicipalityInDistrict ( Municipality municipalityToCheck ) throws SQLException
    {
        String query = "SELECT * FROM districtToMunicipalities WHERE IDDistrict = ? AND IDMunicipality = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( this.ID ) );
        parameters.add( Integer.toString( municipalityToCheck.getID() ) );

        ResultSet rs = Conn.exQuery( query, parameters );

        return rs.next();
    }

    public void addMunicipality ( Municipality municipalityToAdd ) throws SQLException
    {
        String query = "INSERT INTO districtToMunicipalities (IDDistrict, IDMunicipality) VALUES (?, ?)";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( Integer.toString( this.ID ) );
        parameters.add( Integer.toString( municipalityToAdd.getID() ) );

        Conn.queryUpdate( query, parameters );  
    }

    public static boolean checkPatternName ( String nameToCheck ) 
    {
        return nameToCheck.length() <= 0 || nameToCheck.length() >= 50;
    }

}
