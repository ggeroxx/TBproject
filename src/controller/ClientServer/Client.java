package controller.ClientServer;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(String host, int port) throws IOException {
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
            Client client = new Client("localhost", 12345);
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
