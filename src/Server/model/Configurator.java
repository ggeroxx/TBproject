package Server.model;

public class Configurator {
    
    private int ID;
    private String username;
    private String password;
    private boolean firstAccess;

    public Configurator ( int ID, String username, String password, boolean firstAccess )
    {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.firstAccess = firstAccess;
    }

    public int getID() 
    {
        return this.ID;
    }

    public String getUsername() 
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public boolean getFirstAccess()
    {
        return this.firstAccess;
    }

    public void setUsername ( String username )
    {
        this.username = username;
    }

    public void setPassword ( String password )
    {
        this.password = password;
    }

    public void setFirstAccess ( boolean firstAccess )
    {
        this.firstAccess = firstAccess;
    }

}
