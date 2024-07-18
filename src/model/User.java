package model;

public class User {
    
    private Integer ID;
    private String username;
    private String password;
    private int districtID;
    private String mail;

    public User ( Integer ID, String username, String password, int districtID, String mail )
    {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.districtID = districtID;
        this.mail = mail;
    }

    public int getID () 
    {
        return this.ID;
    }

    public String getUsername () 
    {
        return this.username;
    }

    public String getPassword ()
    {
        return this.password;
    }

    public int getDistrictID () 
    {
        return this.districtID;
    }

    public String getMail () 
    {
        return this.mail;
    }

}
