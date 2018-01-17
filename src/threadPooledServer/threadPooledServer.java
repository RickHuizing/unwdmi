package threadPooledServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * manages server socket
 * accepts incoming connections
 * initializes the connection manager
 */
public class threadPooledServer implements Runnable{

    protected int          serverPort   = 7789;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    protected ExecutorService executorService =
            Executors.newFixedThreadPool(10);
    protected  connectionManager connectionManager = new connectionManager();

    threadPooledServer(int port){
        this.serverPort = port;
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        initConnection();

        while(! isStopped()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //connectionManager.fetchData();
            /*this.socketExecutor.execute(
                    new receiver(clientSocket, socketExecutor));
            System.out.println(this.socketExecutor.toString());*/
        }
        this.executorService.shutdown();
        System.out.println("Server Stopped.") ;
    }

    private void initConnection(){
        Runnable task = this::initialiseConnection;
        Thread thread = new Thread(task);
        thread.start();
    }
    private void initialiseConnection() {
        Socket clientSocket = null;
        clientSocket = acceptConnection();
        socketConnection connection = new socketConnection(clientSocket);
        this.connectionManager.addConnection(connection);
        initConnection();
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

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
}