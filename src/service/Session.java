package service;

import java.sql.SQLException;
import service.pure_fabrication.AuthenticationService;
import service.pure_fabrication.TemporaryOperationsManager;

public class Session {
    
    private Boolean status;
    private Character subject;
    private AuthenticationService authenticationService;
    private TemporaryOperationsManager tempOpsManager;

    public Session( AuthenticationService authenticationService, TemporaryOperationsManager tempOpsManager)
    {
        this.authenticationService = authenticationService;
        this.tempOpsManager = tempOpsManager;
    }

    public Boolean getStatus () 
    {
        return this.status;
    }

    public void setStatus (Boolean val) 
    {
        this.status = val;
    }

    public Character getSubject () 
    {
        return this.subject;
    }

    public void login( String usernameToCheck, String passwordToCheck ) throws SQLException 
    {
        this.subject = authenticationService.authenticate (usernameToCheck, passwordToCheck, this );
        if (this.status && this.subject == 'c') 
        {
            tempOpsManager.prepareTemporaryData();
        }
    }

    public void logout() throws SQLException {
        this.status = false;

        if (this.subject != null && this.subject == 'c') 
        {
            tempOpsManager.clearTemporaryData();
            authenticationService.getAccessJDBC().allowPermission();
        }

        this.subject = null;
    }

}
