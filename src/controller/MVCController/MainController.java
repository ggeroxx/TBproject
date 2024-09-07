package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import java.awt.event.MouseEvent;
import model.Configurator;
import model.User;
import model.util.Conn;
import model.util.Constants;
import model.util.Controls;
import repository.AccessRepository;
import service.Session;
import view.LoginView;
import view.MainView;
import view.RegistrationConfiguratorView;
import view.RegistrationUserView;
import view.FieldsView;

public class MainController extends Controller {
    
	private LoginView loginView;
    private RegistrationConfiguratorView registrationConfiguratorView;
    private RegistrationUserView registrationUserView;
    private MainView mainView;
    private Session session;
    private AccessRepository accessRepository;
    private DistrictController districtController;
    private SubjectController subjectController;
    private ConfiguratorController configuratorController;
    private UserController userController;

    public MainController (LoginView loginView, RegistrationConfiguratorView registrationConfiguratorView, RegistrationUserView registrationUserView, MainView mainView, Session session, AccessRepository accessRepository, DistrictController districtController, SubjectController subjectController, ConfiguratorController configuratorController, UserController userController )
    {
        super( mainView );
        this.mainView = mainView;
        this.session = session;
        this.accessRepository = accessRepository;
        this.districtController = districtController;
        this.subjectController = subjectController;
        this.configuratorController = configuratorController;
        this.userController = userController;

        this.loginView = loginView;
        this.registrationConfiguratorView = registrationConfiguratorView;
        this.registrationUserView = registrationUserView;

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
                    signUp();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        this.loginView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                try {
                    Conn.closeConnection();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
				System.exit(0);
			}
		});

        this.registrationConfiguratorView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                try {
                    Conn.closeConnection();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
				System.exit(0);
			}
		});

        this.registrationConfiguratorView.getButtonChangeCredentials().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				try 
				{
					boolean checkNewUsername = checkNewUsername( registrationConfiguratorView );
					boolean checkNewPassword = checkNewPassword ( registrationConfiguratorView );
					if( checkNewUsername && checkNewPassword)
					{
						configuratorController.changeCredentials( registrationConfiguratorView.getUsername(), registrationConfiguratorView.getPassword() );
						resetFiled(registrationConfiguratorView);
                        registrationConfiguratorView.dispose();
						configuratorController.start();
					}
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
				
			}
		});

        this.registrationUserView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                try {
                    Conn.closeConnection();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
				System.exit(0);
			}
		});

        this.registrationUserView.getComboBoxDistrict().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) registrationUserView.getComboBoxDistrict().getSelectedItem();
                try 
                {
                    registrationUserView.setTextMunicipalities(districtController.getAllMunicipalityFromDistrict(districtController.getDistrictRepository().getDistrictByName(selectedItem)));
                } 
                catch (SQLException e1) 
                {
                    e1.printStackTrace();
                }
            }
        });

        this.registrationUserView.getSignUpButton().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                try 
				{
					boolean checkNewUsername = checkNewUsername( registrationUserView );
					boolean checkNewPassword = checkNewPassword( registrationUserView );
                    boolean checkPatternEmail = checkPatternEmail();
					if( checkNewUsername && checkNewPassword && checkPatternEmail)
					{
                        String districtName = (String)registrationUserView.getComboBoxDistrict().getSelectedItem();
                        int districtID = districtController.getDistrictRepository().getDistrictByName(districtName).getID();
                        userController.getuserRepository().insertUser( new User( null, registrationUserView.getUsername(), BCrypt.hashpw( registrationUserView.getPassword(), BCrypt.gensalt() ), districtID, registrationUserView.getEmail() ) );
                        resetFiled(registrationUserView);
                        registrationUserView.setTextEmail("");
                        registrationUserView.setMessageErrorEmail("");
                        registrationUserView.dispose();
                        start();
					}
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
    }

    public void start () 
    {
        try 
        {
            Conn.openConnection();
            loginView.setUndecorated(true);
            loginView.setVisible(true);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

    }

    public void login () throws SQLException 
    {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        session.login( username, password );
        if ( !session.getStatus() && session.getSubject() == null )
        {   
            loginView.setMessage( Constants.LOGIN_ERROR );
            return;
        }
        if ( !session.getStatus() && session.getSubject() == 'c' )
        {
        	loginView.setMessage( String.format( Constants.DENIED_ACCESS, accessRepository.getPermission().getUsername() ) );
            return;
        }

        if ( session.getSubject() == 'c' ) 
        {
            Configurator conf = configuratorController.getconfiguratorRepository().getConfiguratorByUsername( username );
            configuratorController.setConfigurator( conf );

            if ( conf.getFirstAccess() )
            {
                registrationConfiguratorView.setUndecorated(true);
                registrationConfiguratorView.setVisible(true);
                resetFiled(loginView);
                loginView.dispose();
                
            }
            else
            {
                resetFiled(loginView);
                loginView.dispose();
                configuratorController.start();
            }
            
            return;
        }

        User user = userController.getuserRepository().getUserByUsername( username );
        userController.setUser( user );
        userController.start();
        
    }

    public void signUp () throws Exception 
    {
        resetFiled(loginView);
        loginView.dispose();
        registrationUserView.setUndecorated(true);
        registrationUserView.setVisible(true);

        for (String name : districtController.allDistrictName()) 
        {
            this.registrationUserView.getComboBoxDistrict().addItem(name);
        }

    }

    public boolean checkNewUsername( FieldsView registrationView ) throws SQLException
    {
        String newUsername;
        boolean checkUsername = false;
        newUsername = registrationView.getUsername();
        
        checkUsername = subjectController.isPresentUsername( newUsername );
        if ( checkUsername ) 
        {
        	registrationView.setMessageErrorUsername( Constants.USERNAME_NOT_AVAILABLE );
        	return false;
        }
        
        checkUsername = Controls.checkPatternUsername( newUsername, 2, 21 );
        if ( checkUsername ) 
    	{
    		registrationView.setMessageErrorUsername( Constants.ERROR_PATTERN_USERNAME );
    		return false;
    	}
   
        this.registrationConfiguratorView.setMessageErrorUsername("");
        return true;
    } 

    public boolean checkNewPassword ( FieldsView registrationView ) throws SQLException
    {
        String newPassword;
        boolean checkPassword;
        newPassword = registrationView.getPassword();
        
        checkPassword = Controls.checkPatternPassword( newPassword, 7, 26 );
        if ( !checkPassword ) 
        {
        	registrationView.setMessageErrorPassword( Constants.ERROR_PATTERN_PASSWORD );
        	return false;
        }

        registrationView.setMessageErrorPassword("");
        return true;
    }

    public boolean checkPatternEmail()
    {
        String email;
        boolean checkEmail;
        email = this.registrationUserView.getEmail();
        
        checkEmail = Controls.checkPatternMail( email, 4, 51 );
        if ( !checkEmail ) 
        {
        	this.registrationUserView.setMessageErrorEmail( Constants.ERROR_PATTERN_MAIL );
        	return false;
        }

        this.registrationUserView.setMessageErrorEmail("");
        return true;
    }

    public void resetFiled( FieldsView registrationView)
    {
        registrationView.setTextUsername("");
        registrationView.setMessageErrorUsername("");
        registrationView.setPasswordField("");
        registrationView.setMessageErrorPassword("");
    }
}
