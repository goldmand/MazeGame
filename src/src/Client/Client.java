package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
/**
 * The class Client.
 * the main function of the class is to decompress a byte array to a maze representation.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */
public class Client {
    private IClientStrategy clientStrategy;
    private InetAddress serverIP;
    private int port;

    /**
     * This method is a Constructor to the Client object.
     * @param serverIP, the IP address of the server.
     * @param port, the port of the server.
     * @param clientStrategy, the Client request.
     */
    public Client(InetAddress serverIP, int port, IClientStrategy clientStrategy) {
        this.clientStrategy = clientStrategy;
        this.serverIP = serverIP;
        this.port = port;
    }


    /**
     * This method is a starts the connection with the server.
     * @param serverIP, the IP address of the server.
     * @param port, the port of the server.
     */
    public void start(InetAddress serverIP, int port)
    {
        try {
            Socket socket = new Socket(serverIP,port);
            System.out.println("Client is connected to server!");
            clientStrategy.clientStrategy(socket.getInputStream(),socket.getOutputStream());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void communicateWithServer() {
        start(serverIP, port);
    }
}
