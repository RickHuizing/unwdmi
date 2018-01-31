package threadPooledServer;

import resources.Settings;
import resources.ExecutorServices;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * manages server socket
 * accepts incoming connections
 * initializes the connection manager
 */
public class ThreadPooledServer implements Runnable{

    private int          serverPort   = Settings.ServerSettings.SERVER_PORT;
    private ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;

    protected ConnectionManager connectionManager = new ConnectionManager();

    private ScheduledFuture<?> initConnectionTask = null;
    ThreadPooledServer( ){openServerSocket();}

    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        Runnable task = this::initialiseConnection;
        initConnectionTask = ExecutorServices.MAIN_EXECUTOR.scheduleAtFixedRate(task, 1, 1, TimeUnit.MILLISECONDS);
    }

    private void initialiseConnection() {
        Socket clientSocket = null;
        clientSocket = acceptConnection();
        SocketConnection connection = new SocketConnection(clientSocket);
        this.connectionManager.addConnection(connection);
        ExecutorServices.connections++;
        if (ExecutorServices.connections >= 800) {
            initConnectionTask.cancel(true);
            ExecutorServices.MAIN_EXECUTOR.shutdown();
            ThreadPooledServerRunner.isRunning = false;
        }
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