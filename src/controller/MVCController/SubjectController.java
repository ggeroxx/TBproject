package controller.MVCController;

import java.io.IOException;
import controller.ClientServer.Client;
import controller.ClientServer.SomeRequestSubject;

public class SubjectController {
    
    private Client client;
    private SomeRequestSubject requestSubject;

    public SubjectController (Client client ) 
    {
        this.client = client;
    }

    public boolean isPresentUsername ( String usernameToCheck ) throws IOException, ClassNotFoundException
    {
        requestSubject = new SomeRequestSubject("IS_PRESENT_USERNAME", usernameToCheck);
        client.sendRequest(requestSubject);
        return (boolean) client.receiveResponse();
    }

}
