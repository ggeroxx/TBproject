package controller;

import java.sql.*;
import model.*;
import util.*;
import view.*;

public class SubjectController extends Controller {
    
    private SubjectView subjectView;
    private ConfiguratorJDBC configuratorJDBC;
    private UserJDBC userJDBC;

    public SubjectController ( SubjectView subjectView ) 
    {
        super( subjectView );
        this.subjectView = subjectView;
        this.configuratorJDBC = new ConfiguratorJDBCImpl();
        this.userJDBC = new UserJDBCImpl();
    }

    public String enterString ( String msg )
    {
        return subjectView.enterString( msg );
    }

    public String enterPassword ()
    {
        return subjectView.enterPassword();
    }
        
    public String enterNewUsername () throws SQLException
    {
        String newUsername;
        boolean checkUsername1 = false, checkUsername2 = false;

        do
        {
            newUsername = subjectView.enterString( Constants.ENTER_NEW_USERNAME );
            checkUsername1 = this.isPresentUsername( newUsername );
            if ( checkUsername1 ) subjectView.println( Constants.USERNAME_NOT_AVAILABLE );
            checkUsername2 = Controls.checkPatternUsername( newUsername, 2, 21 );
            if ( checkUsername2 ) subjectView.println( Constants.ERROR_PATTERN_USERNAME );
        } while ( checkUsername1 || checkUsername2 );

        return newUsername;
    }

    public String enterNewPassword () throws SQLException
    {
        String newPassword;
        boolean checkPassword;

        do
        {   
            newPassword = subjectView.enterNewPassword();
            checkPassword = Controls.checkPatternPassword( newPassword, 7, 26 );
            if ( !checkPassword ) subjectView.println( Constants.ERROR_PATTERN_PASSWORD );
        } while ( !checkPassword );

        return newPassword;
    }

    public String enterMail ()
    {
        String mail;
        boolean checkMail;

        do
        {   
            mail = subjectView.enterString( Constants.ENTER_MAIL );
            checkMail = Controls.checkPatternMail( mail, 4, 51 );
            if ( !checkMail ) subjectView.println( Constants.ERROR_PATTERN_MAIL );
        } while ( !checkMail );

        return mail;
    }

    public boolean isPresentUsername ( String usernameToCheck ) throws SQLException
    {
        Configurator configurator = this.configuratorJDBC.getConfiguratorByUsername( usernameToCheck );
        User user = this.userJDBC.getUserByUsername( usernameToCheck );
        return configurator != null || user != null;
    }

}
