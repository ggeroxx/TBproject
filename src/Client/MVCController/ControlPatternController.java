package Client.MVCController;

import java.io.IOException;

import Client.ClientClasse;
import Request.SomeRequestPattern;

public class ControlPatternController 
{
    private ClientClasse client;
    private SomeRequestPattern requestPattern;

    public ControlPatternController (ClientClasse client)
    {
        this.client = client;
    }

    public String padRight ( String str, int maxLenght ) throws ClassNotFoundException, IOException
    {
        requestPattern = new SomeRequestPattern("PADRIGHT", str, 0, maxLenght , false);
        client.sendRequest(requestPattern);
        return (String) client.receiveResponse();
        //return Controls.padRight(str, maxLenght);
    }

    public  String messageErrorNewUsername( String newUsername, boolean isPresentUsername ) throws ClassNotFoundException, IOException
    {
        requestPattern = new SomeRequestPattern("GET_MSG_ERROR_NEW_USERNAME", newUsername, 0, 0, isPresentUsername);
        client.sendRequest(requestPattern);
        return (String) client.receiveResponse();
    }

    public String messageErrorNewPassword ( String newPassword ) throws ClassNotFoundException, IOException
    {
        requestPattern = new SomeRequestPattern("GET_MSG_ERROR_NEW_PASSWORD",  newPassword, 0, 0, false);
        client.sendRequest(requestPattern);
        return (String) client.receiveResponse();
    }

    public String messaggeErrorNewEmail( String newEmail ) throws ClassNotFoundException, IOException
    {
        requestPattern = new SomeRequestPattern("GET_MSG_ERROR_EMAIL",  newEmail, 0, 0, false);
        client.sendRequest(requestPattern);
        return (String) client.receiveResponse();
    }

    public boolean checkCorrectName( String name) throws IOException, ClassNotFoundException
    {
        requestPattern = new SomeRequestPattern("CHECK_PATTERN_NAME", name, 1, 50,false);
        client.sendRequest(requestPattern);
        return (boolean) client.receiveResponse();
    }

    public boolean checkPattern ( String strToCheck, int minLength, int maxLength ) throws ClassNotFoundException, IOException
    {
        requestPattern = new SomeRequestPattern("CHECK_PATTERN", strToCheck, minLength, maxLength,false);
        client.sendRequest(requestPattern);
        return (boolean) client.receiveResponse();
    }
    
}
