package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.awt.event.MouseEvent;
import service.ControlPatternService;
import view.ChangeCredentialsConfiguratorView;;

public class ChangeCredentialsConfiguratorController  {
    
    private ChangeCredentialsConfiguratorView changeCredentialsConfiguratorView;
    private ConfiguratorController configuratorController;
    private SubjectController subjectController;

    public ChangeCredentialsConfiguratorController (ChangeCredentialsConfiguratorView changeCredentialsConfiguratorView, ConfiguratorController configuratorController,  SubjectController subjectController )
    {
        this.changeCredentialsConfiguratorView = changeCredentialsConfiguratorView;
        this.configuratorController = configuratorController;
        this.subjectController = subjectController;

        this.changeCredentialsConfiguratorView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                close();
			}
		});

        this.changeCredentialsConfiguratorView.getButtonChangeCredentials().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				try 
				{
					if( validateFields() )
					{
						configuratorController.changeCredentials( changeCredentialsConfiguratorView.getUsername(), changeCredentialsConfiguratorView.getPassword() );
						close();
						configuratorController.start();
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
        changeCredentialsConfiguratorView.setUndecorated(true);
        changeCredentialsConfiguratorView.setVisible(true);
    }

    public void close ()
    {
        changeCredentialsConfiguratorView.resetFiled();
        changeCredentialsConfiguratorView.dispose();
    }

    private boolean validateFields() throws SQLException 
    {
        String msgErrorUsername = ControlPatternService.messageErrorNewUsername( changeCredentialsConfiguratorView.getUsername(), subjectController );
        String msgErrorPassword = ControlPatternService.messageErrorNewPassword( changeCredentialsConfiguratorView.getPassword() );
    
        changeCredentialsConfiguratorView.setMessageErrorUsername( msgErrorUsername == null ? "" : msgErrorUsername );
        changeCredentialsConfiguratorView.setMessageErrorPassword( msgErrorPassword == null ? "" : msgErrorPassword );
    
        return msgErrorUsername == null && msgErrorPassword == null;
    }

}
