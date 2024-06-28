package model;

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

    public int getID () 
    {
        return this.ID;
    }

    public String getName () 
    {
        return this.name;
    }

    public String getCAP () 
    {
        return this.CAP;
    }

    public String getProvince () 
    {
        return this.province;
    }

    @Override
    public String toString () 
    {
        return new StringBuffer().append( "  " + this.CAP + "  " + this.province + "  " + this.name + "\n" ).toString();
    }

}