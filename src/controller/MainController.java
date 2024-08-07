package controller;

import java.sql.SQLException;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;
import util.*;
import view.*;
import model.*;

public class MainController extends Controller {
    
    private MainView mainView;
    private Session session;
    private AccessJDBC accessJDBC;
    private DistrictController districtController;
    private SubjectController subjectController;
    private ConfiguratorController configuratorController;
    private UserController userController;

    public MainController ( MainView mainView, Session session, AccessJDBC accessJDBC, DistrictController districtController, SubjectController subjectController, ConfiguratorController configuratorController, UserController userController )
    {
        super( mainView );
        this.mainView = mainView;
        this.session = session;
        this.accessJDBC = accessJDBC;
        this.districtController = districtController;
        this.subjectController = subjectController;
        this.configuratorController = configuratorController;
        this.userController = userController;
    }

    public void start () 
    {
        int choice = 0;
        super.clearConsole( Constants.TIME_SWITCH_MENU );

        super.forcedClosure( null );

        do
        {
            try
            {
                Conn.openConnection();
                
                choice = mainView.viewMainMenu();

                switch ( choice ) 
                {
                    case 1:
                            caseOne();
                        break;

                    case 2:
                            caseTwo();
                        break;

                    case 3:
                            mainView.print( Constants.BYE_BYE_MESSAGE );
                        break;

                    default:
                            mainView.print( Constants.INVALID_OPTION );
                            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
                        break;
                }

                Conn.closeConnection();
            }
            catch ( InputMismatchException e )
            {
                mainView.print( Constants.INVALID_OPTION );
                super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            }
            catch ( Exception e )
            {
                mainView.print( Constants.GENERIC_EXCEPTION_MESSAGE );
                e.printStackTrace();
            }
        } while ( choice != 3 );
    }

    private void caseOne () throws SQLException 
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        mainView.print( Constants.LOGIN_SCREEN );

        String username = subjectController.enterString( Constants.ENTER_USERNAME );
        String password = subjectController.enterPassword();

        session.login( username, password );
        if ( !session.getStatus() && session.getSubject() == null )
        {   
            mainView.println( Constants.LOGIN_ERROR );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }
        if ( !session.getStatus() && session.getSubject() == 'c' )
        {
            mainView.println( String.format( Constants.DENIED_ACCESS, accessJDBC.getPermission().getUsername() ) );
            super.clearConsole( Constants.TIME_ERROR_MESSAGE );
            return;
        }

        if ( session.getSubject() == 'c' ) 
        {
            Configurator conf = configuratorController.getConfiguratorJDBC().getConfiguratorByUsername( username );
            configuratorController.setConfigurator( conf );

            if ( conf.getFirstAccess() )
            {
                super.clearConsole( Constants.TIME_SWITCH_MENU );
                mainView.print( Constants.REGISTRATION_SCREEN );

                String newUsername = configuratorController.enterNewUsername();
                String newPassword = configuratorController.enterNewPassword();
                
                configuratorController.changeCredentials( newUsername, newPassword );
            }
            
            configuratorController.start();
            return;
        }

        User user = userController.getUserJDBC().getUserByUsername( username );
        userController.setUser( user );
        userController.start();
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        
    }

    private void caseTwo () throws Exception 
    {
        super.clearConsole( Constants.TIME_SWITCH_MENU );
        mainView.print( Constants.REGISTRATION_SCREEN );

        districtController.listAllWithMunicipalities();

        int districtID = districtController.chooseDistrict();

        if ( districtID == 0 )
        {
            super.clearConsole( Constants.TIME_SWITCH_MENU );
            return;
        }

        super.clearConsole( Constants.TIME_SWITCH_MENU );
        mainView.print( Constants.REGISTRATION_SCREEN );

        String newUsername = userController.enterNewUsername();
        String newPassword = userController.enterNewPassword();

        String mail = userController.enterMail();

        userController.getUserJDBC().insertUser( new User( null, newUsername, BCrypt.hashpw( newPassword, BCrypt.gensalt() ), districtID, mail ) );

        mainView.print( Constants.OPERATION_COMPLETED );
        super.clearConsole( Constants.TIME_MESSAGE );
    }

}
