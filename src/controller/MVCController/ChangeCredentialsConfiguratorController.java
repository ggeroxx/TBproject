package controller.MVCController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import controller.ClientServer.Client;
import controller.ClientServer.SomeRequestPattern;
import java.awt.event.MouseEvent;
import java.io.IOException;
import view.ChangeCredentialsConfiguratorView;;

public class ChangeCredentialsConfiguratorController  {
    
    private ChangeCredentialsConfiguratorView changeCredentialsConfiguratorView;
    private ConfiguratorController configuratorController;
    private SubjectController subjectController;
    private SessionController sessionController;

    private Client client;
    private SomeRequestPattern requestPattern;

    public ChangeCredentialsConfiguratorController (ChangeCredentialsConfiguratorView changeCredentialsConfiguratorView, ConfiguratorController configuratorController,  SubjectController subjectController, SessionController sessionController, Client client )
    {
        this.changeCredentialsConfiguratorView = changeCredentialsConfiguratorView;
        this.configuratorController = configuratorController;
        this.subjectController = subjectController;
        this.sessionController = sessionController;

        this.client = client;

        this.changeCredentialsConfiguratorView.getCloseLabel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                close();
                try 
                {
                    sessionController.logout();
                }
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                } 
                catch (ClassNotFoundException e1) 
                {
                    e1.printStackTrace();
                }
                System.exit(0);
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
        changeCredentialsConfiguratorView.setUndecorated(true);
        changeCredentialsConfiguratorView.setVisible(true);
    }

    public void close ()
    {
        changeCredentialsConfiguratorView.resetFiled();
        changeCredentialsConfiguratorView.dispose();
    }

    private boolean validateFields() throws IOException, ClassNotFoundException 
    {
        requestPattern = new SomeRequestPattern("GET_MSG_ERROR_NEW_USERNAME", changeCredentialsConfiguratorView.getUsername(), 0, 0, subjectController.isPresentUsername(changeCredentialsConfiguratorView.getUsername()));
        client.sendRequest(requestPattern);
        String msgErrorUsername = (String) client.receiveResponse();
        
        requestPattern = new SomeRequestPattern("GET_MSG_ERROR_NEW_PASSWORD", changeCredentialsConfiguratorView.getPassword() , 0, 0, false);
        client.sendRequest(requestPattern);
        String msgErrorPassword = (String) client.receiveResponse();
    
        changeCredentialsConfiguratorView.setMessageErrorUsername( msgErrorUsername == null ? "" : msgErrorUsername );
        changeCredentialsConfiguratorView.setMessageErrorPassword( msgErrorPassword == null ? "" : msgErrorPassword );
    
        return msgErrorUsername == null && msgErrorPassword == null;
    }

}
