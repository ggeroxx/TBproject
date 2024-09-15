package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import model.Proposal;
import model.User;
import repository.UserRepository;
import service.SessionService;
import service.UserService;
import view.UserMenuView;

public class UserController {
    
    private UserMenuView userMenuView;
    private CategoryController categoryController;
    private ProposalController proposalController;
    private SessionService sessionService;
    private UserService userService;

    public UserController ( UserMenuView userMenuView,  SessionService sessionService, CategoryController categoryController, ProposalController proposalController, UserService userService )
    {
        this.userMenuView = userMenuView;
        this.sessionService = sessionService;
        this.categoryController = categoryController;
        this.proposalController = proposalController;
        this.userService = userService;

        this.userMenuView.getNavigateHierarchiesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    categoryController.startNavigateHierarchyView();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
			}
		});

        this.userMenuView.getProposProposalButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    proposalController.startProposeProposalView( UserController.this );
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
			}
		});

        this.userMenuView.getRetireProposalButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    proposalController.startRetireProposalView( UserController.this );
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
			}
		});

        this.userMenuView.getViewAllProposalButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    proposalController.startProposalOfUserView( getUser() );
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
			}
		});

        this.userMenuView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                try 
                {
                    close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
			}
		});

        this.userMenuView.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
			}
		});
    }

    public void start () throws SQLException
    {
        userMenuView.setUndecorated(true);
        userMenuView.setVisible(true);
    }

    public void close() throws SQLException
    {
        sessionService.logout();
        userMenuView.dispose();
    }

    public User getUser ()
    {
        return this.userService.getUser();
    }

    public void setUser ( User user )
    {
        this.userService.setUser(user);
    }

    public UserRepository getuserRepository ()
    {
        return this.userService.getuserRepository();
    }

    /*public void start ()
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
                            //categoryController.navigateHierarchy();
                        break;

                    case 2:
                            //proposalController.proposeProposal( this );
                        break;

                    case 3:
                            //proposalController.retireProposal( this );
                        break;

                    case 4:
                            //proposalController.listProposalsByUser( getUser() );
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
    }*/

    public void insertProposal ( Proposal toInsert ) throws SQLException
    {
        this.userService.insertProposal(toInsert);
    }

    public void retireProposal ( Proposal toRetire ) throws SQLException
    {
        this.userService.retireProposal(toRetire);
    }

    public void insertUser (User newUser ) throws SQLException
    {
        userService.insertUser(newUser);
    }

    public User getUserByUsername ( String username ) throws SQLException
    {
        return userService.getUserByUsername ( username );
    }

}