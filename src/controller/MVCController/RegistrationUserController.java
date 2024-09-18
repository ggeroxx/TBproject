package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import org.mindrot.jbcrypt.BCrypt;
import java.awt.event.MouseEvent;
import java.io.IOException;
import view.RegistrationUserView;


public class RegistrationUserController  {
    
    private RegistrationUserView registrationUserView;
    private DistrictController districtController;
    private SubjectController subjectController;
    private UserController userController;
    private MunicipalityController municipalityController;
    private ControlPatternController controlPatternController;

    public RegistrationUserController ( RegistrationUserView registrationUserView, DistrictController districtController, MunicipalityController municipalityController, SubjectController subjectController, UserController userController, ControlPatternController controlPatternController )
    {
        this.registrationUserView = registrationUserView;
        this.districtController = districtController;
        this.municipalityController = municipalityController;
        this.subjectController = subjectController;
        this.userController = userController;
        this.controlPatternController = controlPatternController;

        this.registrationUserView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
            {
                close();
                System.exit(0);
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
                        registrationUserView.setTextMunicipalities(municipalityController.getAllMunicipalityFromDistrict(selectedItem));
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
                        int districtID = districtController.getDistrictIDByName(districtName);
                        userController.insertUser( registrationUserView.getUsername(), BCrypt.hashpw( registrationUserView.getPassword(), BCrypt.gensalt() ), districtID, registrationUserView.getEmail() );
                        close();
					}
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

        this.registrationUserView.getLoginButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
            {
                close();
            }
		});
    }

    public void start () throws ClassNotFoundException, IOException
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

    public void populateComboBoxWithDistrictName() throws ClassNotFoundException, IOException 
    {
        for (String name : districtController.allDistrictName()) 
        {
            this.registrationUserView.getComboBoxDistrict().addItem(name);
        }

    }

    private boolean validateFields() throws ClassNotFoundException, IOException 
    {
        String msgErrorUsername = controlPatternController.messageErrorNewUsername( registrationUserView.getUsername(), subjectController.isPresentUsername(registrationUserView.getUsername()) );
        String msgErrorPassword = controlPatternController.messageErrorNewPassword( registrationUserView.getPassword() );
        String msgErrorEmail = controlPatternController.messaggeErrorNewEmail( registrationUserView.getEmail() );
    
        registrationUserView.setMessageErrorUsername( msgErrorUsername == null ? "" : msgErrorUsername );
        registrationUserView.setMessageErrorPassword( msgErrorPassword == null ? "" : msgErrorPassword );
        registrationUserView.setMessageErrorEmail( msgErrorEmail == null ? "" : msgErrorEmail );
    
        return msgErrorUsername == null && msgErrorPassword == null && msgErrorEmail == null;
    }

}
