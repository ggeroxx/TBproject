package controller;

import java.sql.SQLException;
import java.util.*;
import view.*;
import model.*;
import util.*;

public class UserController extends SubjectController {
    
    private UserView userView;
    private User user;
    private Session session;
    private UserJDBC userJDBC;
    private CategoryController categoryController;
    private ProposalController proposalController;

    public UserController ( UserView userView, Session session, UserJDBC userJDBC, CategoryController categoryController, ProposalController proposalController )
    {
        super( userView );
        this.userView = userView;
        this.session = session;
        this.userJDBC = userJDBC;
        this.categoryController = categoryController;
        this.proposalController = proposalController;
    }

    public User getUser ()
    {
        return this.user;
    }

    public void setUser ( User user )
    {
        this.user = user;
    }

    public UserJDBC getUserJDBC ()
    {
        return this.userJDBC;
    }

    public void start ()
    {
        int choice = 0;

        super.forcedClosure( Constants.LOG_OUT, session );

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
                            proposalController.listProposalsByUser( this.user );
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
        proposalController.getProposalJDBC().insertProposal( toInsert.getRequestedCategory(), toInsert.getOfferedCategory(), toInsert.getRequestedHours(), toInsert.getOfferedHours(), toInsert.getUser(), toInsert.getState() );
    }

    public void retireProposal ( Proposal toRetire ) throws SQLException
    {
        proposalController.getProposalJDBC().retireProposal( toRetire );
    }

}