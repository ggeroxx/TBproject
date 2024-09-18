package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import controller.ClientServer.Client;
import controller.ClientServer.SomeRequestUser;
import view.UserMenuView;

public class UserController {
    
    private UserMenuView userMenuView;
    private SessionController sessionController;
    private CategoryController categoryController;
    private ProposalController proposalController;

    private Client client;
    private SomeRequestUser requestUser;

    public UserController ( UserMenuView userMenuView, SessionController sessionController, CategoryController categoryController, ProposalController proposalController, Client client )
    {
        this.userMenuView = userMenuView;
        this.sessionController = sessionController;
        this.categoryController = categoryController;
        this.proposalController = proposalController;
        this.client = client;

        this.userMenuView.getNavigateHierarchiesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    categoryController.startNavigateHierarchyView();
                }
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) {
                    e1.printStackTrace();
                }
			}
		});

        this.userMenuView.getProposProposalButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    proposalController.startProposeProposalView( UserController.this );
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) {
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
                    proposalController.startRetireProposalView( getUserUsername() );
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
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
                    proposalController.startProposalOfUserView( getUserUsername() );
                }
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
			}
		});

        this.userMenuView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                try 
                {
                    close();
                    System.exit(0);
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
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
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
			}
		});
    }

    public void start ()
    {
        userMenuView.setUndecorated(true);
        userMenuView.setVisible(true);
    }

    public void close() throws ClassNotFoundException, IOException
    {
        sessionController.logout();
        userMenuView.dispose();
    }

    public String getUserUsername () throws ClassNotFoundException, IOException
    {
        requestUser = new SomeRequestUser("GET_USER_NAME", null, null, 0, null);
        client.sendRequest(requestUser);
        return (String) client.receiveResponse();
        //return this.userService.getUser();
    }

    public void setUser ( String userName ) throws IOException
    {
        requestUser = new SomeRequestUser("SET_USER",userName, null,  0, null);
        client.sendRequest(requestUser); 
        //this.userService.setUser(user);
    }

    public void insertUser (String userName, String password, int districtID, String email ) throws ClassNotFoundException, IOException
    {
        requestUser = new SomeRequestUser("INSERT_USER", userName, password, districtID, email);
        client.sendRequest(requestUser);
        client.receiveResponse();
    }

    /*public UserRepository getuserRepository ()
    {
        return this.userService.getuserRepository();
    }

    public void retireProposal ( Proposal toRetire ) throws SQLException
    {
        this.userService.retireProposal(toRetire);
    }

    public User getUserByUsername ( String username ) throws SQLException
    {
        return userService.getUserByUsername ( username );
    }*/

}