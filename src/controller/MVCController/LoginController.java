package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import model.util.Constants;
import view.LoginView;

public class LoginController  {
    
	private LoginView loginView;
    private ChangeCredentialsConfiguratorController registrationConfiguratorController;
    private RegistrationUserController registrationUserController;
    private ConfiguratorController configuratorController;
    private SessionController sessionController;
    private UserController userController;

    public LoginController (LoginView loginView, ChangeCredentialsConfiguratorController registrationConfiguratorController, RegistrationUserController registrationUserController, DistrictController districtController, ConfiguratorController configuratorController, UserController userController, SessionController sessionController )
    {
        this.configuratorController = configuratorController;
        this.userController = userController;
        this.loginView = loginView;
        this.registrationConfiguratorController = registrationConfiguratorController;
        this.registrationUserController = registrationUserController;
        this.sessionController = sessionController;



        this.loginView.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try 
                {
                    login();
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                } catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
            }
        });

        this.loginView.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginView.resetFiled();
                try 
                {
                    registrationUserController.start();
                } 
                catch (ClassNotFoundException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        this.loginView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                close();
                System.exit(0);
			}
		});

    }

    public void start () 
    {
        loginView.setUndecorated(true);
        loginView.setVisible(true);
    }

    public void close()
    {
        loginView.resetFiled();
        loginView.dispose();
    }

    public void login () throws ClassNotFoundException, IOException 
    {
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        sessionController.login( username, password );
        Boolean status = sessionController.getStatus();
        Character subject = sessionController.getSubject();

        if ( !status && subject == null )
        {   
            loginView.setMessage( Constants.LOGIN_ERROR );
            return;
        }
        if ( !status && subject == 'c' )
        {
        	loginView.setMessage( String.format( Constants.DENIED_ACCESS, sessionController.getUsernamePermission() ) );
            return;
        }

        if ( subject == 'c' ) 
        {
            //Configurator conf = configuratorController.getConfiguratorByUsername( username );
            configuratorController.setConfigurator( username );

            if ( configuratorController.getFirstAccess() )
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

        //User user = userController.getUserByUsername( username );
        userController.setUser( username );
        loginView.resetFiled();
        userController.start();
        
    }
}
