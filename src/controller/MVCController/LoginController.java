package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.awt.event.MouseEvent;
import model.Configurator;
import model.User;
import model.util.Constants;
import repository.AccessRepository;
import service.ConnectionService;
import service.SessionService;
import view.LoginView;

public class LoginController  {
    
	private LoginView loginView;
    private ChangeCredentialsConfiguratorController registrationConfiguratorController;
    private RegistrationUserController registrationUserController;

    private SessionService sessionService;
    private AccessRepository accessRepository;
    private ConfiguratorController configuratorController;
    private UserController userController;

    public LoginController (LoginView loginView, ChangeCredentialsConfiguratorController registrationConfiguratorController, RegistrationUserController registrationUserController, SessionService sessionService, AccessRepository accessRepository, DistrictController districtController, ConfiguratorController configuratorController, UserController userController )
    {
        this.sessionService = sessionService;
        this.accessRepository = accessRepository;
        this.configuratorController = configuratorController;
        this.userController = userController;

        this.loginView = loginView;
        this.registrationConfiguratorController = registrationConfiguratorController;
        this.registrationUserController = registrationUserController;

        this.loginView.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    login();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.loginView.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loginView.resetFiled();
                    registrationUserController.start();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.loginView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                try {
                    close();
                    System.exit(0);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }	
			}
		});

    }

    public void start () 
    {
        try 
        {
            ConnectionService.openConnection();
            loginView.setUndecorated(true);
            loginView.setVisible(true);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

    }

    public void close() throws SQLException
    {
        loginView.resetFiled();
        loginView.dispose();
        ConnectionService.closeConnection();
    }

    public void login () throws SQLException 
    {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        sessionService.login( username, password );
        if ( !sessionService.getStatus() && sessionService.getSubject() == null )
        {   
            loginView.setMessage( Constants.LOGIN_ERROR );
            return;
        }
        if ( !sessionService.getStatus() && sessionService.getSubject() == 'c' )
        {
        	loginView.setMessage( String.format( Constants.DENIED_ACCESS, accessRepository.getPermission().getUsername() ) );
            return;
        }

        if ( sessionService.getSubject() == 'c' ) 
        {
            Configurator conf = configuratorController.getConfiguratorByUsername( username );
            configuratorController.setConfigurator( conf );

            if ( conf.getFirstAccess() )
            {
                loginView.resetFiled();
                registrationConfiguratorController.start();   
            }
            else
            {
                loginView.resetFiled();
                configuratorController.start();
            }
            return;
        }

        User user = userController.getUserByUsername( username );
        userController.setUser( user );
        userController.start();
        
    }
}
