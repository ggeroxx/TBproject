package projectClass;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.Conn;

public class District {
    
    private int ID;
    private String name;

    public District ( String name )
    {
        this.name = name;
    }

    public boolean attendedDistrict ( String newDistrict )
    {
        String query = "SELECT name FROM districts WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( newDistrict );

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

    public int getID () 
    {
        String query = "SELECT id FROM districts WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( this.name );

        ResultSet rs = Conn.exQuery( query, parameters );

        int toReturn = 0;
        try
        {
            rs.next();
            toReturn = rs.getInt(1);
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        return toReturn;
    }

    public void setID ( )
    {
        this.ID = this.getID();
    }

    public boolean existMunicipality ( String newMunicipality )
    {
        String query = "SELECT municipality FROM municipalities WHERE municipality = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( newMunicipality );

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

    public boolean attendedMunicipalityDistrict ( String newMunicipality )
    {
        boolean toReturn = false;

        try
        {
            String query = "SELECT id FROM municipalities WHERE municipality = ?";

            ArrayList<String> parameters = new ArrayList<String>();
            parameters.add( newMunicipality );

            ResultSet rs = Conn.exQuery( query, parameters );

            String otherQuery = "SELECT * FROM districtToMunicipalities WHERE IDDistrict = ? AND IDMunicipality = ?";

            ArrayList<String> otherParameters = new ArrayList<String>();
            otherParameters.add( Integer.toString( this.ID ) );
            rs.next();
            otherParameters.add( Integer.toString( rs.getInt(1) ) );

            ResultSet otherRs = Conn.exQuery( otherQuery, otherParameters );

            if ( otherRs.next() ) toReturn = true;
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }

        return toReturn;
    }

    public void insertMunicipality ( String newMunicipality )
    {
        try
        {
            String query = "SELECT id FROM municipalities WHERE municipality = ?";

            ArrayList<String> parameters = new ArrayList<String>();
            parameters.add( newMunicipality );

            ResultSet rs = Conn.exQuery( query, parameters );

            String otherQuery = "INSERT INTO districtToMunicipalities (IDDistrict, IDMunicipality) VALUES (?, ?)";

            ArrayList<String> otherParameters = new ArrayList<String>();
            otherParameters.add( Integer.toString( this.ID ) );
            rs.next();
            otherParameters.add( Integer.toString( rs.getInt(1) ) );

            Conn.updateRow( otherQuery, otherParameters );  
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
    }

}
