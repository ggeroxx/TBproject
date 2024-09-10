package service;

import java.sql.SQLException;

public class SessionService {
    
    private Session session;

    public SessionService ( Session session )
    {
        this.session = session;
    }

    public Session getSession()
    {
        return this.session;
    }

    public Boolean getStatus () 
    {
        return this.session.getStatus();
    }

    public void setStatus (Boolean val) 
    {
        this.session.setStatus(val);
    }

    public Character getSubject () 
    {
        return this.session.getSubject();
    }

    public void login( String usernameToCheck, String passwordToCheck ) throws SQLException 
    {
        this.session.login(usernameToCheck, passwordToCheck);
    }

    public void logout() throws SQLException 
    {
        this.session.logout();
    }

}
