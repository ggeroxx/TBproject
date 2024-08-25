package controller.MVCController;

import java.sql.SQLException;
import controller.GRASPController.SubjectGRASPController;
import model.util.Constants;
import model.util.Controls;
import view.SubjectView;

public class SubjectController extends Controller {
    
    private SubjectView subjectView;
    private SubjectGRASPController controllerGRASP;

    public SubjectController ( SubjectView subjectView, SubjectGRASPController controllerGRASP ) 
    {
        super( subjectView );
        this.subjectView = subjectView;
        this.controllerGRASP = controllerGRASP;
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
        return super.readString( Constants.ENTER_MAIL, Constants.ERROR_PATTERN_MAIL, ( input ) -> !Controls.checkPatternMail( input, 4, 51 ) );
    }

    public boolean isPresentUsername ( String usernameToCheck ) throws SQLException
    {
        return this.controllerGRASP.isPresentUsername(usernameToCheck);
    }

}
