package controller.MVCController;

import java.sql.SQLException;
import java.util.*;

import controller.GRASPController.*;
import view.*;
import model.*;
import util.*;

public class UserController extends SubjectController {
    
    private UserView userView;
    private Session session;
    private CategoryController categoryController;
    private ProposalController proposalController;
    private UserGRASPController businessController;

    public UserController ( UserView userView, Session session, UserRepository userRepository, CategoryController categoryController, ProposalController proposalController )
    {
        super( userView );
        this.userView = userView;
        this.session = session;
        this.categoryController = categoryController;
        this.proposalController = proposalController;
        this.businessController = new UserGRASPController( userRepository, proposalController );
    }

    public User getUser ()
    {
        return this.businessController.getUser();
    }

    public void setUser ( User user )
    {
        this.businessController.setUser(user);
    }

    public UserRepository getuserRepository ()
    {
        return this.businessController.getuserRepository();
    }

    public void start ()
    {
        int choice = 0;

        super.forcedClosure( session );

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
                            session.logout();
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
        this.businessController.insertProposal(toInsert);
    }

    public void retireProposal ( Proposal toRetire ) throws SQLException
    {
        this.businessController.retireProposal(toRetire);
    }

}