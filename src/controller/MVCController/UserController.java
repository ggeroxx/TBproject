package controller.MVCController;

import java.sql.SQLException;
import java.util.*;

import controller.GRASPController.*;
import view.*;
import model.*;
import util.*;

public class UserController extends SubjectController {
    
    private UserView userView;
    private CategoryController categoryController;
    private ProposalController proposalController;
    private UserGRASPController controllerGRASP;
    private SessionGRASPController sessionGRASPController;

    public UserController ( UserView userView, SubjectGRASPController subjectGRASPController, SessionGRASPController sessionGRASPController, CategoryController categoryController, ProposalController proposalController, UserGRASPController controllerGRASP )
    {
        super( userView, subjectGRASPController);
        this.userView = userView;
        this.sessionGRASPController = sessionGRASPController;
        this.categoryController = categoryController;
        this.proposalController = proposalController;
        this.controllerGRASP = controllerGRASP;
    }

    public User getUser ()
    {
        return this.controllerGRASP.getUser();
    }

    public void setUser ( User user )
    {
        this.controllerGRASP.setUser(user);
    }

    public UserRepository getuserRepository ()
    {
        return this.controllerGRASP.getuserRepository();
    }

    public void start ()
    {
        int choice = 0;

        super.forcedClosure( this.sessionGRASPController.getSession() );

        do
        {
            try
            {
                super.clearConsole( Constants.TIME_SWITCH_MENU );
                choice = userView.viewUserMenu();

                switch ( choice ) 
                {
                    case 1:
                            categoryController.navigateHierarchy();
                        break;

                    case 2:
                            proposalController.proposeProposal( this );
                        break;

                    case 3:
                            proposalController.retireProposal( this );
                        break;

                    case 4:
                            proposalController.listProposalsByUser( getUser() );
                        break;

                    case 5:
                            this.sessionGRASPController.logout();
                            userView.println( Constants.LOG_OUT );
                            super.clearConsole( Constants.TIME_LOGOUT );
                        break;

                    default:
                            userView.print( Constants.INVALID_OPTION );
                            super.clearConsole( Constants.TIME_ERROR_MESSAGE);
                        break;
                }

            }
            catch ( InputMismatchException e )
            {
                userView.print( Constants.INVALID_OPTION );
                continue;
            }
            catch ( Exception e )
            {
                userView.print( Constants.GENERIC_EXCEPTION_MESSAGE );
                e.printStackTrace();
                continue;
            }
        } while ( choice != 5 );
    }

    public void insertProposal ( Proposal toInsert ) throws SQLException
    {
        this.controllerGRASP.insertProposal(toInsert);
    }

    public void retireProposal ( Proposal toRetire ) throws SQLException
    {
        this.controllerGRASP.retireProposal(toRetire);
    }

}