package controller.MVCController;

import java.io.IOException;
import controller.ClientServer.Client;
import controller.ClientServer.SomeRequestSession;

public class SessionController {

    private Client client;
    private SomeRequestSession requestSession;

    public SessionController (Client client)
    {
        this.client = client;
    }

    public void login (String username, String password) throws ClassNotFoundException, IOException
    {
        requestSession = new SomeRequestSession("LOGIN", username, password);
        client.sendRequest(requestSession);
        client.receiveResponse();
    }

    public void logout() throws ClassNotFoundException, IOException
    {
        requestSession = new SomeRequestSession("LOGOUT", null, null);
        client.sendRequest(requestSession);
        client.receiveResponse();
    }

    public boolean getStatus() throws ClassNotFoundException, IOException
    {
        requestSession = new SomeRequestSession("GET_STATUS", null, null);
        client.sendRequest(requestSession);
        return (Boolean) client.receiveResponse();
    }

    public Character getSubject() throws ClassNotFoundException, IOException
    {
        requestSession = new SomeRequestSession("GET_SUBJECT", null, null);
        client.sendRequest(requestSession);
        return (Character) client.receiveResponse();
    }

    public String getUsernamePermission() throws ClassNotFoundException, IOException
    {
        requestSession = new SomeRequestSession("GET_USERNAME_PERMISSION", null, null);
        client.sendRequest(requestSession);
        return (String) client.receiveResponse();
    }
    
}
