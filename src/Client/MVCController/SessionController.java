package Client.MVCController;

import java.io.IOException;

import Client.ClientClasse;
import Request.SomeRequestSession;

public class SessionController {

    private ClientClasse client;
    private SomeRequestSession requestSession;

    public SessionController (ClientClasse client)
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

    public Boolean getStatus() throws ClassNotFoundException, IOException
    {
        requestSession = new SomeRequestSession("GET_STATUS", null, null);
        client.sendRequest(requestSession);
        return ((Boolean) client.receiveResponse());
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
