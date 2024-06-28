package model;

import java.sql.*;

public class District {
    
    private int ID;
    private String name;
    private int IDConfigurator;
    private DistrictJDBC districtJDBC;
    private DistrictToMunicipalitiesJDBC districtToMunicipalitiesJDBC;

    public District ( int ID, String name, int IDConfigurator ) throws SQLException
    {
        this.ID = ID;
        this.name = name;
        this.IDConfigurator = IDConfigurator;
        this.districtJDBC = new DistrictJDBCImpl();
        this.districtToMunicipalitiesJDBC = new DistrictToMunicipalitiesJDBCImpl();
    }

    public int getID() 
    {
        return this.ID;
    }

    public String getName() 
    {
        return this.name;
    }

    public boolean isPresentMunicipalityInDistrict ( Municipality municipalityToCheck ) throws SQLException
    {
        return districtToMunicipalitiesJDBC.isPresentMunicipalityInDistrict( this.ID, municipalityToCheck.getID() );
    }

    public void addMunicipality ( Municipality municipalityToAdd ) throws SQLException
    {
        districtToMunicipalitiesJDBC.addMunicipality( this.ID, municipalityToAdd.getID() );
    }

    @Override
    public String toString() 
    {
        return new StringBuffer().append( " " + this.ID + ". " + this.name ).toString();
    }

}