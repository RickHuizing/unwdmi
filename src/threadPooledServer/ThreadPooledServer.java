package threadPooledServer;

import resources.Constants;
import resources.ExecutorServices;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * manages server socket
 * accepts incoming connections
 * initializes the connection manager
 */
public class ThreadPooledServer implements Runnable{

    private int          serverPort   = Constants.ServerSettings.SERVER_PORT;
    private ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;

    protected ConnectionManager connectionManager = new ConnectionManager();

    ThreadPooledServer( ){}

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        Runnable task = this::initialiseConnection;
        ExecutorServices.MAIN_EXECUTOR.scheduleAtFixedRate(task, 1,1, TimeUnit.MILLISECONDS);
    }

    private void initialiseConnection() {
        Socket clientSocket = null;
        clientSocket = acceptConnection();
        SocketConnection connection = new SocketConnection(clientSocket);
        this.connectionManager.addConnection(connection);
    }

    private Socket acceptConnection(){
        Socket clientSocket = null;
        try {
            clientSocket = this.serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(
                    "Error accepting client connection", e);
        }
        return clientSocket;
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
}