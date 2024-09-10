package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import java.awt.event.MouseEvent;
import model.User;
import service.ControlPatternService;
import view.RegistrationUserView;


public class RegistrationUserController  {
    
    private RegistrationUserView registrationUserView;
    private DistrictController districtController;
    private SubjectController subjectController;
    private UserController userController;


    public RegistrationUserController ( RegistrationUserView registrationUserView, DistrictController districtController, SubjectController subjectController, UserController userController )
    {
        this.registrationUserView = registrationUserView;
        this.districtController = districtController;
        this.subjectController = subjectController;
        this.userController = userController;

        this.registrationUserView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                close();
			}
		});

        this.registrationUserView.getComboBoxDistrict().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(registrationUserView.getComboBoxDistrict().isEnabled())
                {
                    String selectedItem = (String) registrationUserView.getComboBoxDistrict().getSelectedItem();
                    try 
                    {
                        registrationUserView.setTextMunicipalities(districtController.getAllMunicipalityFromDistrict(districtController.getDistrictByName(selectedItem)));
                    } 
                    catch (SQLException e1) 
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });

        this.registrationUserView.getSignUpButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
            {
                try 
				{
					if(  validateFields() )
					{
                        String districtName = (String)registrationUserView.getComboBoxDistrict().getSelectedItem();
                        int districtID = districtController.getDistrictByName(districtName).getID();
                        userController.insertUser( new User( null, registrationUserView.getUsername(), BCrypt.hashpw( registrationUserView.getPassword(), BCrypt.gensalt() ), districtID, registrationUserView.getEmail() ) );
                        close();
					}
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});

        this.registrationUserView.getLoginButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
            {
                close();
            }
		});
    }

    public void start () throws SQLException 
    {
        registrationUserView.activeComboBox();
        populateComboBoxWithDistrictName();
        registrationUserView.setUndecorated(true);
        registrationUserView.setVisible(true);

    }

    public void close ()
    { 
        registrationUserView.disactiveComboBox();
        registrationUserView.resetFiled();  
        registrationUserView.dispose();       
    }

    public void populateComboBoxWithDistrictName() throws SQLException 
    {
        for (String name : districtController.allDistrictName()) 
        {
            this.registrationUserView.getComboBoxDistrict().addItem(name);
        }

    }

    private boolean validateFields() throws SQLException 
    {
        String msgErrorUsername = ControlPatternService.messageErrorNewUsername( registrationUserView.getUsername(), subjectController );
        String msgErrorPassword = ControlPatternService.messageErrorNewPassword( registrationUserView.getPassword() );
        String msgErrorEmail = ControlPatternService.messaggeErrorNewEmail( registrationUserView.getEmail() );
    
        registrationUserView.setMessageErrorUsername( msgErrorUsername == null ? "" : msgErrorUsername );
        registrationUserView.setMessageErrorPassword( msgErrorPassword == null ? "" : msgErrorPassword );
        registrationUserView.setMessageErrorEmail( msgErrorEmail == null ? "" : msgErrorEmail );
    
        return msgErrorUsername == null && msgErrorPassword == null && msgErrorEmail == null;
    }

}
