package projectClass;

import java.sql.*;

public class Municipality {
    
    private int ID;
    private String name;
    private String CAP;
    private String province;

    public Municipality ( int ID, String name, String CAP, String province ) throws SQLException
    {
        this.ID = ID;
        this.name = name;
        this.CAP = CAP;
        this.province = province;
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