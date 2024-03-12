package projectClass;
import java.sql.*;
import java.util.ArrayList;

import util.Conn;

public class Municipality {
    
    private int ID;
    private String name;
    private String CAP;
    private String province;

    public Municipality ( String name ) throws SQLException
    {
        this.name = name;
        this.ID = takeID();
        this.CAP = takeCAP();
        this.province = takeProvince();
    }

    private int takeID () throws SQLException
    {
        String query = "SELECT id FROM municipalities WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( this.name );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        return rs.getInt( 1 );
    }

    private String takeCAP () throws SQLException
    {
        String query = "SELECT cap FROM municipalities WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( this.name );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        return rs.getString( 1 );
    }

    private String takeProvince () throws SQLException
    {   
        String query = "SELECT province FROM municipalities WHERE name = ?";

        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add( this.name );

        ResultSet rs = Conn.exQuery( query, parameters );

        rs.next();
        return rs.getString( 1 );
    }

    public int getID() 
    {
        return this.ID;
    }

    @Override
    public String toString() 
    {
        StringBuffer toReturn = new StringBuffer();

        toReturn.append( "  " + this.CAP + "  " + this.province + "  " + this.name + "\n" );

        return toReturn.toString();
    }
}
