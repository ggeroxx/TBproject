package Client.MVCController;

import java.io.IOException;

import Client.ClientClasse;
import Request.SomeRequestSubject;

public class SubjectController {
    
    private ClientClasse client;
    private SomeRequestSubject requestSubject;

    public SubjectController (ClientClasse client ) 
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
