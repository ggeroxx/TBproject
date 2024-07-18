package model;

public class District {
    
    private int ID;
    private String name;
    private int IDConfigurator;

    public District ( int ID, String name, int IDConfigurator )
    {
        this.ID = ID;
        this.name = name;
        this.IDConfigurator = IDConfigurator;
    }

    public int getID () 
    {
        return this.ID;
    }

    public String getName () 
    {
        return this.name;
    }

    public int getIDConfigurator ()
    {
        return this.IDConfigurator;
    }

}