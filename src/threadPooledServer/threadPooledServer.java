package threadPooledServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * manages server socket
 * accepts incoming connections
 * initializes the connection manager
 */
public class threadPooledServer implements Runnable{

    private int          serverPort   = 7789;
    private ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    protected ExecutorService executorService =
            Executors.newFixedThreadPool(10);
    private ScheduledExecutorService scheduledExecutor =
            Executors.newScheduledThreadPool(1);
    protected  connectionManager connectionManager = new connectionManager();

    threadPooledServer(int port){
        this.serverPort = port;
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        Runnable task =()->{initialiseConnection();};
        scheduledExecutor.scheduleAtFixedRate(task, 1,1, TimeUnit.MILLISECONDS);
    }

    private void initialiseConnection() {
        Socket clientSocket = null;
        clientSocket = acceptConnection();
        socketConnection connection = new socketConnection(clientSocket);
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