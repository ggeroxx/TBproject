package Client;

import java.io.*;
import java.net.Socket;

import Request.SomeRequestDistrict;

public class ClientClasse {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientClasse(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void sendRequest(Object request) throws IOException {
        out.writeObject(request);
        out.flush();
        System.out.println("Request sent to server: " + request);
    }

    public Object receiveResponse() throws IOException, ClassNotFoundException {
        Object response = in.readObject();
        System.out.println("Response received from server: " + response);
        return response;
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            ClientClasse client = new ClientClasse("localhost", 12345);
            SomeRequestDistrict request = new SomeRequestDistrict("CREATE_DISTRICT", "requestData");
            client.sendRequest(request);
            Object response = client.receiveResponse();

            request = new SomeRequestDistrict("CREATE_DISTRICT", "requestData");
            client.sendRequest(request);
            response = client.receiveResponse();
            
            client.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
