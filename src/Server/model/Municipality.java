package Server.model;

public class Municipality {
    
    private int ID;
    private String name;
    private String CAP;
    private String province;

    public Municipality ( int ID, String name, String CAP, String province )
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

}