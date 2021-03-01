
package Server;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;
/**
 * The class Server.
 * handles clients requests by using a Threads pool.
 * @author Daniel Goldman and Dor Levy
 * @version 1.0
 * @since   2020-15-03
 */
public class Server {
    private int port;//The port
    private int listeningInterval;
    private IServerStrategy serverStrategy;//The strategy for handling clients
    private volatile boolean stop;
    private ExecutorService threadPoolExecutor;

    /**
     * This method is a Constructor to the Client object.
     * @param listeningInterval, the time the server waits for clients connection in ms.
     * @param port, the port of the server.
     * @param serverStrategy, the Client handler.
     */
    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        this.stop = false;
        this.threadPoolExecutor = Executors.newFixedThreadPool(Configurations.getThreadsInPool());
    }
    /**
     * This method is used to start the server as a Thread.
     */
    public void start(){
        new Thread(() -> {
            startServer();
        }).start();
    }

    /**
     * This method is the server starter.
     * the connection with the clients is made through a Thread pool executor.
     */
    public void startServer()
    {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);

            while (!stop)
            {
                try {
                    Socket clientSocket = serverSocket.accept();
                     threadPoolExecutor.execute(()->{
                        clientHandle(clientSocket);
                     });

                }
                catch (IOException e) {
                    System.out.println("Where are the clients??");
                }
            }
            serverSocket.close();
            threadPoolExecutor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function receives client socket and handles it
     * @param clientSocket - The client socket
     */
    private void clientHandle(Socket clientSocket) {
        try {
            InputStream inFromClient = clientSocket.getInputStream();
            OutputStream outToClient = clientSocket.getOutputStream();
            this.serverStrategy.handleClient(inFromClient, outToClient);

            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method stops the server.
     */
    public void stop()
    {
        System.out.println("The server has stopped!");
        this.stop = true;
    }


}
